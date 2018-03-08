package com.asdf1st.mydemo.UI.Drag;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.asdf1st.mydemo.R;
import com.asdf1st.mydemo.Utils.ViewHolder;

import java.util.ArrayList;

/**
 * Created by User on 2017/11/17.
 */

public class GridViewAdapter extends BaseAdapter {
    int imgId=-1;
    ArrayList<GridItem> dataList;
    Context context;
    OnGriditemClickListener onGriditemClickListener;
    public GridViewAdapter(ArrayList<GridItem> dataList, Context context) {
        this(-1,dataList, context);
    }

    public GridViewAdapter(int imgId, ArrayList<GridItem> dataList, Context context) {
        this.imgId = imgId;
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return dataList!=null?dataList.size():0;
    }

    @Override
    public Object getItem(int i) {
        return dataList!=null ?dataList.get(i):null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view==null){
            viewHolder=new ViewHolder(context, R.layout.drag_griditem,viewGroup);
            view=viewHolder.getConvertView();

        }else {
            viewHolder= (ViewHolder) view.getTag();
        }
        //LinearLayout item = viewHolder.get(R.id.item);
        TextView tv_name=viewHolder.get(R.id.tv_name);
        ImageView iv_car=viewHolder.get(R.id.iv_car);
        final GridItem data=dataList.get(i);
        tv_name.setText(data.getName());
        iv_car.setImageResource(imgId);
        //这是必要的
        view.setContentDescription(i+"");
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onGriditemClickListener!=null)
                    onGriditemClickListener.onGridItemClick(imgId,v,data);
            }
        });
        return view;
    }

    public interface OnGriditemClickListener{
        //void onGridItemClick(View view,GridItem gridItem);
        void onGridItemClick(int state,View view,GridItem gridItem);
    }
    public OnGriditemClickListener getOnGriditemClickListener() {
        return onGriditemClickListener;
    }

    public void setOnGriditemClickListener(OnGriditemClickListener onGriditemClickListener) {
        this.onGriditemClickListener = onGriditemClickListener;
    }

    public int getImgId() {
        return imgId;
    }

}
