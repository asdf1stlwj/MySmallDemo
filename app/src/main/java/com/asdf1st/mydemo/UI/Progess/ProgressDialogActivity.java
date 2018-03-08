package com.asdf1st.mydemo.UI.Progess;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;

import com.asdf1st.mydemo.R;

public class ProgressDialogActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_dialog);
//        CustomDialog dialog = new CustomDialog(this);
//        dialog.show();
        showDialog();
    }

    private void showDialog(){
        ProgressDialog dialog=new ProgressDialog(this);
        //设置不可取消，点击其他区域不能取消，实际中可以抽出去封装供外包设置
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
        int nowheight = (int) (d.getHeight() *0.8);   //高度设置为屏幕的0.8(再减去系统默认边界值)
        int nowwidth = (int) (d.getWidth() );    //宽度设置为屏幕的宽度 (再减去系统默认边界值)
        dialog.getWindow().setLayout(nowwidth, nowheight);     //设置生效
        dialog.show();
    }
}
