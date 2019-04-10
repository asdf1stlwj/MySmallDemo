package com.asdf1st.mydemo.UI.Swipe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.asdf1st.mydemo.R;
import com.asdf1st.mydemo.Utils.Data;
import com.asdf1st.mydemo.UI.DragSort.MyItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.List;

public class SwipeActivity extends AppCompatActivity implements MyItemTouchHelperCallback.OnItemRemoveListener{
    List<Data> dataList;
    RecyclerView recyclerView;
    SwipeAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);
        initView();
        initData();
    }

    private void initView() {
        recyclerView= (RecyclerView) findViewById(R.id.reclclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,false));

    }

    private void initData() {
        dataList=new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            dataList.add(new Data(i,"data: " + i));
        }
        adapter=new SwipeAdapter(this,dataList);
        recyclerView.setAdapter(adapter);
        new ItemTouchHelper(new MyItemTouchHelperCallback(this,adapter,this))
                           .attachToRecyclerView(recyclerView);

    }

    @Override
    public void onItemRemove(int position) {
        dataList.remove(position);
    }
}
