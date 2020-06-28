package com.asdf1st.mydemo.UI.Animation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asdf1st.mydemo.R;
import com.asdf1st.mydemo.Utils.Data;

import java.util.List;

/**
 * Created by lwj on 2020/6/28
 **/
public class AnimationMoveAdapter extends RecyclerView.Adapter<AnimationMoveAdapter.ViewHolder>{
    private String TAG=AnimationMoveAdapter.class.getSimpleName();
    private Context context;
    private List<Data> dataList;
    private OnPlayAnimationListener onPlayAnimationListener;

    public AnimationMoveAdapter(Context context, List<Data> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_animation_move,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Data data=dataList.get(position);
        holder.tv_item.setText(data.getName());
        if (onPlayAnimationListener!=null){
            onPlayAnimationListener.onItemViewPlayAnimation(position,holder.tv_item);
        }
        holder.tv_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick:"+position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public OnPlayAnimationListener getOnPlayAnimationListener() {
        return onPlayAnimationListener;
    }

    public void setOnPlayAnimationListener(OnPlayAnimationListener onPlayAnimationListener) {
        this.onPlayAnimationListener = onPlayAnimationListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_item;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_item=itemView.findViewById(R.id.tv_item);
        }
    }

    interface OnPlayAnimationListener{
        void onItemViewPlayAnimation(int position,View itemView);
    }
}
