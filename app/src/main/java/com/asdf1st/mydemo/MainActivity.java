package com.asdf1st.mydemo;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.ListView;
import android.widget.Toast;

import com.asdf1st.mydemo.ImageControl.ChooseImageActivity;
import com.asdf1st.mydemo.RecyclerView.RecyclerActivity;
import com.asdf1st.mydemo.Scan.ScanTextActivity;
import com.asdf1st.mydemo.UI.Collapsed.CollapseActivity;
import com.asdf1st.mydemo.UI.Drag.DragViewActivity;
import com.asdf1st.mydemo.UI.DragSort.DragSortActivity;
import com.asdf1st.mydemo.UI.Immensive.ImmensiveActivity;
import com.asdf1st.mydemo.UI.Progess.ProgressDialogActivity;
import com.asdf1st.mydemo.UI.Swipe.SwipeActivity;
import com.asdf1st.mydemo.UI.Wave.WaveActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private static final int PERMISSION_REQUEST_CODE=1;
    private ListView lv_actitems;
    private List<ActItem> dataList=new ArrayList<>();
    private ActItemAdapter adapter;
    private String[] permissions={Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.SYSTEM_ALERT_WINDOW,Manifest.permission.CAMERA};
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
        dataList.add(new ActItem(SwipeActivity.class,"侧滑删除"));
        dataList.add(new ActItem(RecyclerActivity.class,"RecyclerView添加header"));
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

        if (!permissionLists.isEmpty()) {
            //申请权限回调函数
            ActivityCompat.requestPermissions(this, permissionLists.toArray(new String[permissionLists.size()]), PERMISSION_REQUEST_CODE);
        } else {
            Toast.makeText(this, "权限已全部被申请通过咯！", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
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
