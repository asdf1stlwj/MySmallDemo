package com.asdf1st.mydemo.RecyclerView1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asdf1st.mydemo.R;
import com.asdf1st.mydemo.Utils.Data;

import java.util.List;

/**
 * Created by hasee on 2018/7/17.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    List<Data> dataList;
    Context context;

    public MyAdapter(List<Data> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.list_simple_item,null));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Data data=dataList.get(position);
        holder.tv_name.setText(data.getName());
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
         TextView tv_name;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_name =itemView.findViewById(R.id.tv_name);
        }
    }
}
