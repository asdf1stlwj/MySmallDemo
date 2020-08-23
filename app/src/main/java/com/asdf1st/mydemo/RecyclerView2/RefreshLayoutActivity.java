package com.asdf1st.mydemo.RecyclerView2;

import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.asdf1st.mydemo.R;

import java.util.ArrayList;
import java.util.List;

public class RefreshLayoutActivity extends AppCompatActivity {


    RecyclerView mRecyclerView;
    SimpleRefreshLayout mSimpleRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mSimpleRefreshLayout = (SimpleRefreshLayout) findViewById(R.id.simple_refresh);

        mSimpleRefreshLayout.setScrollEnable(true);
        mSimpleRefreshLayout.setPullUpEnable(true);
        mSimpleRefreshLayout.setPullDownEnable(true);
        mSimpleRefreshLayout.setHeaderView(new SimpleRefreshView(this));
        mSimpleRefreshLayout.setFooterView(new SimpleLoadView(this));
        mSimpleRefreshLayout.setBottomView(new SimpleBottomView(this));

        mSimpleRefreshLayout.setOnSimpleRefreshListener(new SimpleRefreshLayout.OnSimpleRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mData.add(0, "新加刷新数据");
                        mAdapter.notifyDataSetChanged();
                        mSimpleRefreshLayout.onRefreshComplete();

                    }
                }, 1000);
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mData.add(mData.size(), "新加加载数据");
                        mAdapter.notifyDataSetChanged();
                        mSimpleRefreshLayout.onLoadMoreComplete();

                        if (mData.size() >= 18) {
                            mSimpleRefreshLayout.showNoMore(true);
                        }
                    }
                }, 1000);
            }
        });

        initData();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    List<String> mData = new ArrayList<>();

    private void initData() {
        for (int i = 0; i < 15; i++) {
            mData.add("第" + i + "个");
        }
    }

    private RecyclerView.Adapter mAdapter = new RecyclerView.Adapter() {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(RefreshLayoutActivity.this).inflate(R.layout.item_recycler_view, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            TextView tv = (TextView) holder.itemView.findViewById(R.id.tv_text);
            tv.setText(mData.get(position));

            ImageView image = (ImageView) holder.itemView.findViewById(R.id.iv_image);
            if ("新加刷新数据".equals(mData.get(position))) {
                image.setImageResource(R.mipmap.icon_refresh);
                return;
            }
            if ("新加加载数据".equals(mData.get(position))) {
                image.setImageResource(R.mipmap.icon_loadmore);
                return;
            }
            image.setImageResource(R.mipmap.icon_avatar);
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    };

    private class MyViewHolder extends RecyclerView.ViewHolder {

        TextView  mTextView;
        ImageView mIvImage;

        MyViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv_text);
            mIvImage = (ImageView) itemView.findViewById(R.id.iv_image);
        }
    }
}
