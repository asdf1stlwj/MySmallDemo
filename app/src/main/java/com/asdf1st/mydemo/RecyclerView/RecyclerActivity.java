package com.asdf1st.mydemo.RecyclerView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.asdf1st.mydemo.R;
import com.asdf1st.mydemo.Utils.Data;
import com.asdf1st.mydemo.Utils.WrapRecyclerAdapter;
import com.asdf1st.mydemo.Utils.WrapRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerActivity extends AppCompatActivity {
    WrapRecyclerView recyclerView;
    MyAdapter adapter;
    List<Data> dataList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        initView();
        initData();
    }

    private void initView() {
        recyclerView= (WrapRecyclerView) findViewById(R.id.recyclerView);

    }

    private void initData() {
        dataList=new ArrayList<>();
        for (int i=0;i<50;i++){
            dataList.add(new Data(i,"data"+i));
        }
        adapter=new MyAdapter(dataList,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);
        View headerView= LayoutInflater.from(this).inflate(R.layout.test_header,recyclerView,false);
        View footerView= LayoutInflater.from(this).inflate(R.layout.test_footer,recyclerView,false);
        recyclerView.addHeaderView(headerView);
        recyclerView.addFooterView(footerView);
    }
}
