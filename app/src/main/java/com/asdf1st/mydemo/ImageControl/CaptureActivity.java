package com.asdf1st.mydemo.ImageControl;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.asdf1st.mydemo.R;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class CaptureActivity extends Activity implements View.OnClickListener,
        SurfaceHolder.Callback, Camera.PictureCallback, Camera.AutoFocusCallback {
    String TAG="lwj";
    public static int Desired_Preview_Width = 1920;
    public static int Desired_Preview_Height = 1080;
    public static int Desired_Picture_Width = 1920;
    public static int Desired_Picture_Height = 1080;

    private SurfaceView mVideoView = null;
    private SurfaceHolder mHolder = null;
    private Camera mCamera = null;
    private TextView textViewRes=null;
    private ImageView imgViewRes=null;
    private Bitmap rsbitmap=null;

    //
    private ImageButton mBtnCapture = null;

    //超时计时
    private Timer mTimer = null;
    private Handler mMsgHandler = null;
    private int mCounter = 0;

    //对焦超时为5秒
    private static final int Timeout = 5;
    protected static final int Msg_Tick = 10001;

    private boolean cameraWorking=false;
    private View mViewFinder;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);
        setPreviewAndPicSize();
        mViewFinder=this.findViewById(R.id.viewfinder_view);
        //获取按钮控件并设置点击侦听
        mBtnCapture = (ImageButton)findViewById(R.id.btn_capture);
        mBtnCapture.setOnClickListener(this);

        textViewRes=(TextView)this.findViewById(R.id.textViewRes);
        imgViewRes=(ImageView)this.findViewById(R.id.imageViewRes);
        imgViewRes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                imgViewRes.setVisibility(View.GONE);
                startPreview();
            }
        });

        //获取视频渲染视图
        this.mVideoView = (SurfaceView)findViewById(R.id.preview_view);
        this.mHolder = this.mVideoView.getHolder();
        this.mHolder.addCallback(this);
        //this.mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        //初始化主线程消息队列处理器接口
        mMsgHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == Msg_Tick) {
                    mCounter++;
                    if(mCounter >= Timeout) {
                        timerTimeout();
                    }
                }
            }
        };

    }

    @Override
    protected void onDestroy() {

        //取消超时计时
        stopTimer();

        //停止预览，释放资源
        if(this.mCamera != null) {
            this.mCamera.stopPreview();
            this.mCamera.release();
            this.mCamera = null;
        }

        if (rsbitmap!=null){
            rsbitmap.recycle();
            rsbitmap=null;
        }

        // TODO Auto-generated method stub
        super.onDestroy();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {}
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //初始化照相机
        openCamera();
        startPreview();
    }

    private void openCamera() {
        try { //确认照相机处于关闭状态
            closeCamera();
            //打开照相机
            this.mCamera = Camera.open();
            //获取参数，设置参数
            Camera.Parameters params = this.mCamera.getParameters();

            //获取所支持的大小 @2015-06-30 兼容性解决方案
            getSupportedPreviewSize(params);
            getSupportedPictureSize(params);

            params.setPictureFormat(ImageFormat.JPEG);
            params.setPreviewSize(Desired_Preview_Width, Desired_Preview_Height);
            //设置场景
            //params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            //params.setSceneMode(Camera.Parameters.SCENE_MODE_FIREWORKS);
            //params.setFocusMode(Parameters.FOCUS_MODE_AUTO);
            params.setPictureSize(Desired_Picture_Width, Desired_Picture_Height);
            params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            //@2015-06-30 兼容性解决方案
            if (Build.VERSION.SDK_INT >= 8) {
                this.mCamera.setDisplayOrientation(90);
            } else {
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    params.set("orientation", "portrait");
                    params.set("rotation", 90);
                } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    params.set("orientation", "landscape");
                    params.set("rotation", 90);
                }
            }
            //设置白平衡
            //params.setWhiteBalance(Camera.Parameters.WHITE_BALANCE_FLUORESCENT);

            this.mCamera.setParameters(params);
            this.mCamera.setPreviewDisplay(mHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //获取支持的预览大小
    private void getSupportedPreviewSize(Camera.Parameters params) {

        List<Camera.Size> sizes = params.getSupportedPreviewSizes();
        Comparator<Camera.Size> comparator = new Comparator<Camera.Size>() {

            @Override
            public int compare(Camera.Size arg1, Camera.Size arg2) {

                //倒序
                if(arg2.width > arg1.width) {
                    return (1);
                } else if(arg2.width == arg1.width) {
                    if(arg2.height > arg1.height) {
                        return (1);
                    } else if (arg2.height == arg1.height) {
                        return (0);
                    } else {
                        return (-1);
                    }
                } else {
                    return (-1);
                }
            }

        };
        Collections.sort(sizes, comparator);

        StringBuffer sb  = new StringBuffer();
        boolean isGot = false;
        for(int i = 0; i < sizes.size(); ++i) {

            Camera.Size tSize = sizes.get(i);

            sb.append("(");
            sb.append(tSize.width);
            sb.append(",");
            sb.append(tSize.height);
            sb.append(") ");

            if( (tSize.width == Desired_Preview_Width) && !isGot) { //1280x
                Desired_Preview_Height = tSize.height;
                isGot = true;
            };
        }
        if(isGot == false) { //没有匹配上
            Camera.Size tSize = sizes.get(sizes.size()/2);
            Desired_Preview_Width = tSize.width;
            Desired_Preview_Height = tSize.height;
        }
        Log.i(TAG, "getSupportedPreviewSize: "+sb.toString());
        //Logger.getLogger(IDef.App_Tag).info("SupportedPreviewSizes="+sb.toString() );
    }

    //获取支持的图片大小
    private void getSupportedPictureSize(Camera.Parameters params) {

        List<Camera.Size> sizes = params.getSupportedPictureSizes();
        Comparator<Camera.Size> comparator = new Comparator<Camera.Size>() {

            @Override
            public int compare(Camera.Size arg1, Camera.Size arg2) {

                //倒序
                if(arg2.width > arg1.width) {
                    return (1);
                } else if(arg2.width == arg1.width) {
                    if(arg2.height > arg1.height) {
                        return (1);
                    } else if (arg2.height == arg1.height) {
                        return (0);
                    } else {
                        return (-1);
                    }
                } else {
                    return (-1);
                }
            }

        };
        Collections.sort(sizes, comparator);

        StringBuffer sb  = new StringBuffer();
        boolean isGot = false;
        for(int i = 0; i < sizes.size(); ++i) {

            Camera.Size tSize = sizes.get(i);

            sb.append("(");
            sb.append(tSize.width);
            sb.append(",");
            sb.append(tSize.height);
            sb.append(") ");

            if((tSize.width == Desired_Picture_Width) && !isGot) { //1280x
                Desired_Picture_Height = tSize.height;
                isGot = true;
            }
        }
        if(isGot == false) { //没有匹配上
            Camera.Size tSize = sizes.get(sizes.size()-1);
            Desired_Picture_Width = tSize.width;
            Desired_Picture_Height = tSize.height;
        }

        //Logger.getLogger(IDef.App_Tag).info("SupportedPictureSizes="+sb.toString() );
        Log.i(TAG, "getSupportedPictureSize: "+sb.toString());
    }

    private void closeCamera() {
        if (this.mCamera == null) {
            return;
        }
        try { // 使照相机重新获取资源的控制
            this.mCamera.reconnect();
            // 停止预览，释放资源
            this.mCamera.stopPreview();
            this.mCamera.release();
            this.mCamera = null;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    private void startPreview() {
        // 先使照相机处于停止预览状态
        this.mCamera.stopPreview();
        // 开始预览
        this.mCamera.startPreview();
        //testCap();
    }

    @Override
    public void onAutoFocus(boolean success, Camera camera) {

        if(success) {

            //停止聚焦超时计时器
            stopTimer();

            //Logger.getLogger(IDef.App_Tag).log(Level.FINE, "Camera autofocus OK");

            //startPreview();
            camera.cancelAutoFocus();//只有加上了这一句，才会自动对焦。
            //camera.autoFocus(null);
            camera.takePicture(null, null, this);

        }
    }

    public void testCap(){
        if(this.mCamera != null) {

            startPreview();
            this.mCamera.autoFocus(this);

            //隐藏按钮
            //mBtnCapture.setVisibility(View.INVISIBLE);

            //启动超时计时器
            startTimer();
            //mCamera.takePicture(null, null, this);
            //timerTimeout();
        }
    }

    @Override
    public void onClick(View v) {
        testCap();
        //mCamera.takePicture(null,null,this);
    }

    //自动对焦超时计时器开始计时
    private void startTimer() {
        this.mTimer = new Timer();
        this.mCounter = 0;

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                mMsgHandler.sendEmptyMessage(Msg_Tick);
            }
        };
        //规划任务执行的时点和频率
        this.mTimer.scheduleAtFixedRate(task, 0, 1000L);
    }

    //停止聚焦超时计时器
    private void stopTimer() {
        //取消超时计时
        if(this.mTimer != null) {
            this.mTimer.cancel();
            this.mTimer.purge();
            this.mTimer = null;
        }
    }


    //added by huz: 按比例裁剪图片
    public Bitmap cutImage(Bitmap bitmap) {
        //预览控件大小
        int pvW=mVideoView.getWidth();
        int pvH=mVideoView.getHeight();
        //扫描框大小
        int vfW=mViewFinder.getWidth();
        int vfH=mViewFinder.getHeight();

        //获取小框与窗口大小的比例
        double scW=vfW*1.0/pvW;
        double scH=vfH*1.0/pvH;

        //获取原始图片大小，注意高度和宽度对调，要转90度
        int sH=bitmap.getWidth();
        int sW=bitmap.getHeight();

        //计算裁剪后大小
        int rW=(int) Math.round(sW*scW);
        int rH=(int) Math.round(sH*scH);

        //计算裁剪起始位置
        int x=(sW-rW)/2;
        int y=(sH-rH)/2;

        //旋转90度
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap,y, x,
                rH,rW,  matrix, true);
//		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap,0, 0,
//				bitmap.getWidth(),bitmap.getHeight(),  matrix, true);
        bitmap.recycle();
        return resizedBitmap;
    }

    private void setPreviewAndPicSize(){
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Desired_Picture_Height = dm.widthPixels;
        Desired_Picture_Width=dm.heightPixels;
        Desired_Preview_Height=dm.widthPixels;
        Desired_Preview_Width=dm.heightPixels;
        Log.i(TAG, "setPreviewAndPicSize: preview w="+Desired_Preview_Width+",h="+Desired_Preview_Height);
        Log.i(TAG, "setPreviewAndPicSize: picsize w="+Desired_Picture_Width+",h="+Desired_Picture_Height);
    }


    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        //showRes("已拍照"+(new Date()));
//		recgThr.addImageData(data);
        cameraWorking=false;
		/*if(data.length>0){
        	startPreview();
			this.mCamera.autoFocus(this);
			startTimer();
			return;
		}*/

        rsbitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        Log.i(TAG, "surfaceview: w = " + mVideoView.getWidth() + " h = " + mVideoView.getHeight());
        Log.i(TAG, "viewfind: w = " + mViewFinder.getWidth() + " h = " + mViewFinder.getHeight());
        Log.i(TAG, "initial bitmap: w = " + rsbitmap.getWidth() + " h = " + rsbitmap.getHeight());
        //环境配置项
        String App_Dir = Environment.getExternalStorageDirectory()+ File.separator+"huzcapdemo";
        File fDir=new File(App_Dir);
        if(!fDir.exists())
            fDir.mkdirs();
        final String filePath = App_Dir+ File.separatorChar+"capImg.jpg";
        File f = new File(filePath);
        try {
//			imgViewRes.setImageBitmap(bmp);
//			imgViewRes.setVisibility(View.VISIBLE);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f));
            rsbitmap=cutImage(rsbitmap);
            Log.i(TAG, "cut bitmap: w = " + rsbitmap.getWidth() + " h = " + rsbitmap.getHeight());
            rsbitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            //拍完照之后会停止预览，需要恢复预览
            //startPreview();
            //returnResult(filePath);
            imgViewRes.setImageBitmap(rsbitmap);
            imgViewRes.setVisibility(View.VISIBLE);
            ByteArrayOutputStream byteos=new ByteArrayOutputStream();
            rsbitmap.compress(Bitmap.CompressFormat.JPEG,50,byteos);
            byte[] rsbyte=byteos.toByteArray();
            byteos.flush();
            byteos.close();
            Intent intent=new Intent();
            intent.putExtra("byteData",rsbyte);
            intent.putExtra("imgpath",App_Dir+ File.separatorChar+"capImg.jpg");
            setResult(RESULT_OK,intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //自动对焦超时
    protected void timerTimeout() {

        if(cameraWorking)
            return;

        //停止聚焦超时计时器
        stopTimer();

        //Logger.getLogger(IDef.App_Tag).log(Level.WARNING, "Camera autofocus timeout");

        if(this.mCamera != null) {
            //this.mCamera.cancelAutoFocus();//只有加上了这一句，才会自动对焦。
            //camera.autoFocus(null);
            cameraWorking=true;
            this.mCamera.takePicture(null, null, this);
        }
    }
}
