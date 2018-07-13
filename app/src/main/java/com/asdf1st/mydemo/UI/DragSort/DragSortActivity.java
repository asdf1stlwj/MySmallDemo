package com.asdf1st.mydemo.UI.DragSort;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.asdf1st.mydemo.R;
import com.asdf1st.mydemo.Utils.Data;

import java.util.ArrayList;
import java.util.List;

public class DragSortActivity extends AppCompatActivity implements CurrentNodeAdapter.OnCurNodeSelListener, ChooseNodeAdapter.OnChooseSelListener {
    List<Data> chooseNodeList =new ArrayList<>();
    List<Data> curNodeList=new ArrayList<>();
    private RecyclerView currentNodeView, chooseNodeView;
    CurrentNodeAdapter currentNodeAdapter;
    ChooseNodeAdapter chooseNodeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_sort);
        initView();
        initData();
    }

    private void initView() {
        chooseNodeView= (RecyclerView) findViewById(R.id.rv_chooseNode);
        currentNodeView= (RecyclerView) findViewById(R.id.rv_curNode);
        chooseNodeView.setNestedScrollingEnabled(false);
        currentNodeView.setNestedScrollingEnabled(false);
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
}
