package com.asdf1st.mydemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.asdf1st.mydemo.ImageControl.ChooseImageActivity;
import com.asdf1st.mydemo.Scan.ScanTextActivity;
import com.asdf1st.mydemo.UI.Drag.DragViewActivity;
import com.asdf1st.mydemo.UI.Immensive.ImmensiveActivity;
import com.asdf1st.mydemo.UI.Progess.ProgressDialogActivity;
import com.asdf1st.mydemo.UI.Wave.WaveActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private ListView lv_actitems;
    private List<ActItem> dataList=new ArrayList<>();
    private ActItemAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
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
        adapter.notifyDataSetChanged();
    }


}
