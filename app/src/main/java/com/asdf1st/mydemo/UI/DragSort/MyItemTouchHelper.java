package com.asdf1st.mydemo.UI.DragSort;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by User on 2018/7/13.
 */

public class MyItemTouchHelper extends ItemTouchHelper.Callback {
    RecyclerView.Adapter adapter;
    Context context;
    MyItemTouchHelper(Context context, RecyclerView.Adapter adapter){
        this.context=context;
        this.adapter=adapter;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlag= ItemTouchHelper.UP| ItemTouchHelper.DOWN| ItemTouchHelper.LEFT| ItemTouchHelper.RIGHT;
        return makeMovementFlags(dragFlag,0);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int fromPosition = viewHolder.getAdapterPosition();
        int toPosition = target.getAdapterPosition();
        adapter.notifyItemMoved(fromPosition,toPosition);
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState== ItemTouchHelper.ACTION_STATE_DRAG){
            viewHolder.itemView.setScaleX((float) 1.2);
            viewHolder.itemView.setScaleY((float) 1.2);
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            viewHolder.itemView.setScaleX((float) 1.0);
            viewHolder.itemView.setScaleY((float) 1.0);

        super.clearView(recyclerView, viewHolder);
    }
}
