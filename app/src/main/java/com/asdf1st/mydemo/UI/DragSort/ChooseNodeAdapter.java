package com.asdf1st.mydemo.UI.DragSort;

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
 * @author tzl
 */


public class ChooseNodeAdapter extends RecyclerView.Adapter<ChooseNodeAdapter.ViewHolder> {
    private static final String TAG = "ChooseNodeAdapter";
    private List<Data> dataList;
    //private List<String> chooseIDList;
    private Context context;
    private OnChooseSelListener onChooseSelListener;
    List<String> checkList;

    public ChooseNodeAdapter(Context context, List<Data> dataList){
        this.dataList = dataList;
        this.context=context;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView chooseItem;

        public ViewHolder(View itemView) {
            super(itemView);
            chooseItem = (TextView) itemView.findViewById(R.id.tv_chooseItem);
        }
    }

    @Override
    public ChooseNodeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_choosenode, parent, false);
        return new ChooseNodeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ChooseNodeAdapter.ViewHolder holder, final int position) {
        final Data data=dataList.get(position);
        holder.chooseItem.setText(data.getName());

        holder.chooseItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.e(TAG, "添加的值为"+idList.get(position) );
//                if (isRepeatAtList(idList.get(position),))
//                nameList.add(idList.get(position));
//                checkList=new ArrayList<>(new TreeSet<>(nameList));
//                nameList.clear();
//                for (int i=0;i<checkList.size();i++){
//                    nameList.add(checkList.get(i));
//                }
//                Log.e(TAG, "去重复前的常用list: "+nameList.size()+" "+nameList.toString() );
//                Log.e(TAG, "去重复后的常用list "+checkList.size()+" "+checkList.toString() );
//                //adapter.notifyDataSetChanged();
//                (new NodeSettingActivity()).updateData(chooseIDList.get(position));
                if (onChooseSelListener !=null){
                    onChooseSelListener.onChooseNodeSelected(data,dataList);
                }else {

                }
                //Toast.makeText(context,"节点名称:"+nameList.get(position),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }



    interface OnChooseSelListener {
        void onChooseNodeSelected(Data data, List<Data> dataList);
    }

    public OnChooseSelListener getOnChooseSelListener() {
        return onChooseSelListener;
    }

    public void setOnChooseSelListener(OnChooseSelListener onChooseSelListener) {
        this.onChooseSelListener = onChooseSelListener;
    }
}
