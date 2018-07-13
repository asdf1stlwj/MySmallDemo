package com.asdf1st.mydemo.UI.DragSort;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.asdf1st.mydemo.R;
import com.asdf1st.mydemo.Utils.Data;

import java.util.List;

/**
 * @author lwj
 */


public class CurrentNodeAdapter extends RecyclerView.Adapter<CurrentNodeAdapter.ViewHolder> {
    private List<Data> dataList;
    private OnCurNodeSelListener onNodeSelListener;
    private Context context;
    public CurrentNodeAdapter(Context context, List<Data> dataList ) {
        this.dataList=dataList;
        this.context=context;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView delete;
        TextView currentItem;

        public ViewHolder(View itemView) {
            super(itemView);
            delete = (ImageView) itemView.findViewById(R.id.iv_deleteItem);
            currentItem = (TextView) itemView.findViewById(R.id.tv_currentItem);
        }
    }

    @Override
    public CurrentNodeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_currentnode, parent, false);
        return new CurrentNodeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CurrentNodeAdapter.ViewHolder holder, final int position) {
        final Data data=dataList.get(position);
        holder.currentItem.setText(data.getName());
        holder.delete.setTag(position);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //holder.delete.setVisibility(View.GONE);
//                holder.delete.setFocusable(false);
                if (onNodeSelListener!=null){
                    onNodeSelListener.onCurNodeSelected(data,dataList);
                }
                //Toast.makeText(context,"节点名称:"+nameList.get(position),Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    interface OnCurNodeSelListener {
        void onCurNodeSelected(Data data, List<Data> dataList);
    }

    public OnCurNodeSelListener getOnNodeSelListener() {
        return onNodeSelListener;
    }

    public void setOnNodeSelListener(OnCurNodeSelListener onNodeSelListener) {
        this.onNodeSelListener = onNodeSelListener;
    }

}
