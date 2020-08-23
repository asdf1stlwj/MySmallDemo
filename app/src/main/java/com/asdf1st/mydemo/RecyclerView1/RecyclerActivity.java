package com.asdf1st.mydemo.RecyclerView1;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

import com.asdf1st.mydemo.R;
import com.asdf1st.mydemo.Utils.Data;

import java.util.ArrayList;
import java.util.List;

public class RecyclerActivity extends AppCompatActivity {
    RefreshRecyclerView recyclerView;
    MyAdapter adapter;
    List<Data> dataList;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        initClass();
        initView();
        initData();
    }

    private void initClass() {
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                recyclerView.onStopRefresh();
                super.handleMessage(msg);
            }
        };
    }

    private void initView() {
        recyclerView= (RefreshRecyclerView) findViewById(R.id.recyclerView);

    }

    private void initData() {
        dataList=new ArrayList<>();
        for (int i=0;i<200;i++){
            dataList.add(new Data(i,"data"+i));
        }
        adapter=new MyAdapter(dataList,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);
        View headerView= LayoutInflater.from(this).inflate(R.layout.test_header,recyclerView,false);
        View footerView= LayoutInflater.from(this).inflate(R.layout.test_footer,recyclerView,false);
        //recyclerView.addHeaderView(headerView);
        //recyclerView.addFooterView(footerView);
        recyclerView.addRefreshViewCreator(new MyRefreshCreator());
    }

    public class MyRefreshCreator extends RefreshViewCreator {
        // 加载数据的ImageView
        private View mRefreshIv;
        private TextView tv_message;

        @Override
        public View getRefreshView(Context context, ViewGroup parent) {
            View refreshView = LayoutInflater.from(context).inflate(R.layout.custom_progress_dialog, parent, false);
            mRefreshIv = refreshView.findViewById(R.id.refresh_iv);
            tv_message=refreshView.findViewById(R.id.message);
            return refreshView;
        }

        @Override
        public void onPull(int currentDragHeight, int refreshViewHeight, int currentRefreshStatus) {
            float rotate = ((float) currentDragHeight) / refreshViewHeight;
            // 不断下拉的过程中不断的旋转图片
            mRefreshIv.setRotation(rotate * 360);
            if (currentDragHeight<0){
                tv_message.setText("下拉可以刷新");
            }else {
                tv_message.setText("松开可以刷新");
            }
        }

        @Override
        public void onRefreshing() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);
                        handler.sendMessage(new Message());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            // 刷新的时候不断旋转
            RotateAnimation animation = new RotateAnimation(0, 720,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setRepeatCount(-1);
            animation.setDuration(1000);
            mRefreshIv.startAnimation(animation);
            tv_message.setText("正在刷新");
        }

        @Override
        public void onStopRefresh() {
            // 停止加载的时候清除动画
            mRefreshIv.setRotation(0);
            mRefreshIv.clearAnimation();
        }
    }
}
