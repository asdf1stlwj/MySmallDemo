package com.asdf1st.mydemo.UI.DragSort;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.asdf1st.mydemo.R;
import com.asdf1st.mydemo.Utils.Data;
import com.asdf1st.mydemo.Utils.MyItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DragSortActivity extends AppCompatActivity implements CurrentNodeAdapter.OnCurNodeSelListener, ChooseNodeAdapter.OnChooseSelListener, MyItemTouchHelperCallback.OnItemMoveListener {
    List<Data> chooseNodeList =new ArrayList<>();
    List<Data> curNodeList=new ArrayList<>();
    private RecyclerView currentNodeView, chooseNodeView;
    CurrentNodeAdapter currentNodeAdapter;
    ChooseNodeAdapter chooseNodeAdapter;
    ItemTouchHelper itemTouchHelper;
    TextView tv_finish;
    String tag="lwj";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_sort);
        initView();
        initData();
    }

    private void initView() {
        tv_finish= (TextView) findViewById(R.id.right_layout);
        chooseNodeView= (RecyclerView) findViewById(R.id.rv_chooseNode);
        currentNodeView= (RecyclerView) findViewById(R.id.rv_curNode);
        chooseNodeView.setNestedScrollingEnabled(false);
        currentNodeView.setNestedScrollingEnabled(false);
        tv_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printfStrs(curNodeList);
            }
        });
    }

    private void initData() {

        chooseNodeList.add(new Data("热点"));
        chooseNodeList.add(new Data("视频"));
        chooseNodeList.add(new Data("科技"));
        chooseNodeList.add(new Data("国际"));
        chooseNodeList.add(new Data("数码"));
        chooseNodeList.add(new Data("图片"));
        chooseNodeList.add(new Data("娱乐"));
        chooseNodeList.add(new Data("游戏"));
        chooseNodeAdapter=new ChooseNodeAdapter(this,chooseNodeList);
        currentNodeAdapter=new CurrentNodeAdapter(this,curNodeList);
        currentNodeView.setLayoutManager(new GridLayoutManager(this,3));
        chooseNodeView.setLayoutManager(new GridLayoutManager(this,3));
        currentNodeView.setAdapter(currentNodeAdapter);
        chooseNodeView.setAdapter(chooseNodeAdapter);
        currentNodeAdapter.setOnNodeSelListener(this);
        chooseNodeAdapter.setOnChooseSelListener(this);
        itemTouchHelper =new ItemTouchHelper(
                new MyItemTouchHelperCallback
                        (this,currentNodeAdapter,this)
                );
        itemTouchHelper.attachToRecyclerView(currentNodeView);
    }


    private boolean isRepeatAtList(Data data,List<Data> dataList){
        if (data==null || data.equals("")|| dataList==null){
            return true;
        }
        if (dataList.contains(data)){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void onChooseNodeSelected(Data data, List<Data> dataList) {
        if (!isRepeatAtList(data,curNodeList)){
            curNodeList.add(data);
            currentNodeAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCurNodeSelected(Data data, List<Data> dataList) {
        if (isRepeatAtList(data,dataList)){
            curNodeList.remove(data);
            currentNodeAdapter.notifyDataSetChanged();
        }
    }

    private void printfStrs(List<Data> dataList){
        String rs="";
        for (Data data:dataList){
            rs+=data.getName();
            rs+=",";
        }
        Log.e(tag,rs);
        Toast.makeText(this,rs,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        // 拖动排序的回调,这里交换集合中数据的位置
        Collections.swap(curNodeList, fromPosition, toPosition);
    }
}
