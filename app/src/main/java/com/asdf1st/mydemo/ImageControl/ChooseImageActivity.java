package com.asdf1st.mydemo.ImageControl;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.asdf1st.mydemo.R;

public class ChooseImageActivity extends Activity {
    public static final int PHOTO_CROP=10001;
    private Bitmap bitmap;
    Button btn_chooseImg;
    ImageView iv_imageView;
    private String TAG ="crop";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_image);
        btn_chooseImg=findViewById(R.id.btn_image);
        iv_imageView=findViewById(R.id.iv_image);
        btn_chooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ChooseImageActivity.this,CaptureActivity.class);
                ChooseImageActivity.this.startActivityForResult(intent,PHOTO_CROP);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PHOTO_CROP && resultCode==RESULT_OK){
            if (data!=null){
                byte[] byteData=data.getByteArrayExtra("byteData");
                bitmap= BitmapFactory.decodeByteArray(byteData,0,byteData.length);
                if (bitmap!=null){
                    iv_imageView.setImageBitmap(bitmap);
                }
                String base64Str= Base64.encodeToString(byteData, Base64.NO_WRAP);
                Log.i(TAG, "onActivityResult: base64Str="+base64Str);
                //commit(base64Str);

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bitmap!=null){
            bitmap.recycle();
            bitmap=null;
        }

    }
}
