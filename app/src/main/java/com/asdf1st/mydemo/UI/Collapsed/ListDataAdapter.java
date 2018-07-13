package com.asdf1st.mydemo.UI.Collapsed;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asdf1st.mydemo.R;

import java.util.List;

/**
 * Created by User on 2018/6/28.
 */

public class ListDataAdapter extends RecyclerView.Adapter<ListDataAdapter.MyViewHolder> {
    private Context context;
    private List<ListData> dataList;

    ListDataAdapter(Context context,List dataList){
        this.context=context;
        this.dataList=dataList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.list_simple_item,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ListData data=dataList.get(position);
        holder.tv_name.setText(data.getText());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_name=itemView.findViewById(R.id.tv_name);
        }
    }
}
