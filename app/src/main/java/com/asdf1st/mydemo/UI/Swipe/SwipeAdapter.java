package com.asdf1st.mydemo.UI.Swipe;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asdf1st.mydemo.R;
import com.asdf1st.mydemo.Utils.Data;

import java.util.List;

/**
 * Created by hasee on 2018/7/16.
 */

public class SwipeAdapter extends RecyclerView.Adapter<SwipeAdapter.SwipeViewHolder> {
    public Context context;
    public List<Data> dataList;

    public SwipeAdapter(Context context, List<Data> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public SwipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SwipeViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_swipe,parent,false));
    }

    @Override
    public void onBindViewHolder(SwipeViewHolder holder, int position) {
        Data data=dataList.get(position);
        holder.tv_text1.setText(data.getName());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class SwipeViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_text1;
        public SwipeViewHolder(View itemView) {
            super(itemView);
            tv_text1=itemView.findViewById(R.id.tv_text1);
        }
    }
}
