package com.asdf1st.mydemo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.widget.ListView;
import android.widget.Toast;

import com.asdf1st.mydemo.Algorithm.AlgorithmActivity;
import com.asdf1st.mydemo.ImageControl.ChooseImageActivity;
import com.asdf1st.mydemo.Mqtt.MyMqttActivity;
import com.asdf1st.mydemo.Scan.ScanTextActivity;
import com.asdf1st.mydemo.Socket.SocketClientActivity;
import com.asdf1st.mydemo.UI.Animation.AnimationMove2Activity;
import com.asdf1st.mydemo.UI.Animation.AnimationMoveActivity;
import com.asdf1st.mydemo.UI.Collapsed.CollapseActivity;
import com.asdf1st.mydemo.UI.Drag.DragViewActivity;
import com.asdf1st.mydemo.UI.DragSort.DragSortActivity;
import com.asdf1st.mydemo.UI.Immensive.ImmensiveActivity;
import com.asdf1st.mydemo.UI.Progess.ProgressDialogActivity;
import com.asdf1st.mydemo.UI.Swipe.SwipeActivity;
import com.asdf1st.mydemo.UI.Wave.WaveActivity;
import com.asdf1st.mydemo.Video.Ijkplayer.IjkPlayerActivity;
import com.asdf1st.mydemo.Video.JiaoZi.JiaoZiPlayerActivity;
import com.asdf1st.mydemo.aidl.BookManagerActivity;
import com.asdf1st.mydemo.location.LocateActivity;
import com.asdf1st.mydemo.memoryControl.MemoryShadeActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private static final int PERMISSION_REQUEST_CODE=1;
    private ListView lv_actitems;
    private List<ActItem> dataList=new ArrayList<>();
    private ActItemAdapter adapter;
    private String[] permissions={Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.SYSTEM_ALERT_WINDOW,Manifest.permission.CAMERA,Manifest.permission.INTERNET,
    Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.WAKE_LOCK,Manifest.permission.READ_PHONE_STATE,Manifest.permission.READ_EXTERNAL_STORAGE};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        requestPermissions(permissions);
    }

    private void initView() {
        lv_actitems= (ListView) findViewById(R.id.lv_items);
        adapter=new ActItemAdapter(dataList,this);
        lv_actitems.setAdapter(adapter);
    }

    private void initData() {
        dataList.add(new ActItem(ImmensiveActivity.class,"沉浸式标题"));
        dataList.add(new ActItem(ProgressDialogActivity.class,"圆形进度对话框"));
        dataList.add(new ActItem(DragViewActivity.class,"拖拽"));
        dataList.add(new ActItem(ScanTextActivity.class,"条形码扫描"));
        dataList.add(new ActItem(WaveActivity.class,"波浪效果"));
        dataList.add(new ActItem(ChooseImageActivity.class,"拍照抠图"));
        dataList.add(new ActItem(CollapseActivity.class,"collapse"));
        dataList.add(new ActItem(DragSortActivity.class,"拖拽排序"));
        dataList.add(new ActItem(AlgorithmActivity.class,"快速排序"));
        dataList.add(new ActItem(SocketClientActivity.class,"socket"));
        dataList.add(new ActItem(LocateActivity.class,"原生定位"));
        dataList.add(new ActItem(MyMqttActivity.class,"Mqtt"));
        dataList.add(new ActItem(IjkPlayerActivity.class,"IjkPlayer"));
        dataList.add(new ActItem(JiaoZiPlayerActivity.class,"JiaoZiVideoPlayer"));
        dataList.add(new ActItem(MemoryShadeActivity.class,"内存抖动"));
        dataList.add(new ActItem(SwipeActivity.class,"侧滑删除"));
        dataList.add(new ActItem(AnimationMoveActivity.class,"item移动动画"));
        dataList.add(new ActItem(AnimationMove2Activity.class,"item移动动画2"));
        dataList.add(new ActItem(BookManagerActivity.class,"AIDL测试"));
//        dataList.add(new ActItem(RecyclerActivity.class,"RecyclerView添加header"));
//        dataList.add(new ActItem(RefreshLayoutActivity.class,"NestScroll下拉加载"));


        adapter.notifyDataSetChanged();
    }

    public void requestPermissions(String[] permissions) {
        //定义一个权限list
        List<String> permissionLists = new ArrayList<>();
        for (String permission : permissions) {
            //判断所申请的权限是不是已经通过，没通过返回false,通过返回true，则提示出来并拨打电话
            if (ContextCompat.checkSelfPermission(MainActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionLists.add(permission);
            }
        }
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(intent, 1);
            } else {
                //TODO do something you need
            }
        }
        if (!permissionLists.isEmpty()) {
            //申请权限回调函数
            ActivityCompat.requestPermissions(this, permissionLists.toArray(new String[permissionLists.size()]), PERMISSION_REQUEST_CODE);
        } else {
            Toast.makeText(this, "权限已全部被申请通过咯！", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length>0){
                    List<String> denidPermissionList = new ArrayList<>();
                    for (int i=0;i<grantResults.length;i++){
                        int grandResult = grantResults[i];
                        String permission = permissions[i];
                        if (grandResult!=PackageManager.PERMISSION_GRANTED){
                            denidPermissionList.add(permission);
                        }
                    }
                    if (denidPermissionList.size()==1 && denidPermissionList.get(0).equals(Manifest.permission.SYSTEM_ALERT_WINDOW)){
                        Toast.makeText(this, "普通权限已全部通过！", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this, "权限被拒绝了！", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
            default:
                break;
        }

    }
}
