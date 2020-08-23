package com.asdf1st.mydemo.UI.Drag;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import androidx.appcompat.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.Toast;

import com.asdf1st.mydemo.Base.Presenter.IPresenter;
import com.asdf1st.mydemo.Base.View.Activity.BaseActivity;
import com.asdf1st.mydemo.R;
import com.asdf1st.mydemo.Utils.NoScrollGridView;

import java.util.ArrayList;

public class DragViewActivity extends BaseActivity {
    private NoScrollGridView gv1;
    private NoScrollGridView gv2;
    private NoScrollGridView gv3;
    private NoScrollGridView gv4;
    private DragLayout dragLayout;

    ArrayList<GridItem> dataList1;
    ArrayList<GridItem> dataList2;
    ArrayList<GridItem> dataList3;
    ArrayList<GridItem> dataList4;
    GridViewAdapter adapter1;
    GridViewAdapter adapter2;
    GridViewAdapter adapter3;
    GridViewAdapter adapter4;
    private ViewGroup ll_one;
    private ViewGroup ll_two;
    private int REQUEST_GET_PERMISSION=10086;

    @Override
    public int getLayoutId() {
        return R.layout.activity_drag_view;
    }

    @Override
    protected void afterInit() {
        super.afterInit();
        checkOverlaysPermission();
    }

    private void checkOverlaysPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.canDrawOverlays(this)) {
                Toast.makeText(this,"权限通过",Toast.LENGTH_SHORT).show();
            }else {
                //动态申请SYSTEM_ALERT_WINDOW
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, REQUEST_GET_PERMISSION);
            }
        }
    }

    @Override
    protected void initClass() {
        super.initClass();
        dataList1=new ArrayList<>();
        dataList2=new ArrayList<>();
        dataList3=new ArrayList<>();
        dataList4=new ArrayList<>();
        dataList1.add(new GridItem("123456"));
        dataList1.add(new GridItem("qwedsa6"));
        dataList3.add(new GridItem("1sad56"));
        dataList2.add(new GridItem("#1s12356"));
        dataList1.add(new GridItem("123456"));
        dataList2.add(new GridItem("qwedsa6"));
        dataList1.add(new GridItem("1sad56"));
        dataList2.add(new GridItem("#1s12356"));
        dataList1.add(new GridItem("123456"));
        dataList1.add(new GridItem("qwedsa6"));
        dataList2.add(new GridItem("1sad56"));
        dataList2.add(new GridItem("#1s12356")); dataList3.add(new GridItem("123456"));
        dataList2.add(new GridItem("qwedsa6"));
        dataList1.add(new GridItem("1sad56"));
        dataList3.add(new GridItem("123456"));
        dataList3.add(new GridItem("123456"));
        dataList4.add(new GridItem("125456"));
        dataList4.add(new GridItem("78456"));
        dataList4.add(new GridItem("345126"));
        dataList4.add(new GridItem("896"));
        dataList4.add(new GridItem("1234ka6"));
    }

    @Override
    protected void initView() {
        super.initView();
        gv1= (NoScrollGridView) findViewById(R.id.gv_text1);
        //ll_one=findViewById(R.id.ll_one);
        gv2= (NoScrollGridView) findViewById(R.id.gv_text2);
        gv3= (NoScrollGridView) findViewById(R.id.gv_text3);
        gv4= (NoScrollGridView) findViewById(R.id.gv_text4);
        //ll_two=findViewById(R.id.ll_two);
        dragLayout=(DragLayout) findViewById(R.id.ll_drag_view);

        //dragLayout.addViewGroupToLayout(ll_one);
        adapter1=new GridViewAdapter(R.drawable.car_yellow,dataList1,this);
        gv1.setAdapter(adapter1);
        // dragLayout.addViewGroupToLayout(ll_two);
        adapter2=new GridViewAdapter(R.drawable.car_red,dataList2,this);
        gv2.setAdapter(adapter2);

        adapter3=new GridViewAdapter(R.drawable.car_purple,dataList3,this);
        gv3.setAdapter(adapter3);

        adapter4=new GridViewAdapter(R.drawable.car_blue,dataList4,this);
        gv4.setAdapter(adapter4);
        //gv1.getItemIdAtPosition()
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //判断权限申请结果
        super.onActivityResult(requestCode, resultCode, data);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.canDrawOverlays(this)){
                Toast.makeText(this,"权限通过",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this,"你没有开启权限哦,页面即将关闭",Toast.LENGTH_SHORT).show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                },2000);
            }
        }
    }

    @Override
    public IPresenter createPresenter() {
        return null;
    }
}
