package com.asdf1st.mydemo.UI.Drag;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;

import com.asdf1st.mydemo.R;
import com.asdf1st.mydemo.Utils.NoScrollGridView;

import java.util.ArrayList;

public class DragViewActivity extends Activity {
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_view);
        initData();
        initView();
    }

    private void initData() {
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

    private void initView() {
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
}
