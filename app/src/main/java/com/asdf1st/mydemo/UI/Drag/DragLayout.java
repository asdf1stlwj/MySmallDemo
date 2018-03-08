package com.asdf1st.mydemo.UI.Drag;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asdf1st.mydemo.R;

/**
 * Created by User on 2017/11/17.
 */

public class DragLayout extends LinearLayout  {
    private final String TAG="DragLayout";
    Context context;
    View targetView;
    ViewGroup targetViewGroup;
    //ActionDown记录的初始x,y值(相对坐标)
    int startX,startY;
    //要拖拽View的初始位置(相对坐标)
    int preL,preT,preR,preB;
    //要拖拽View的左上角坐标(绝对坐标)
    int viewrawx,viewrawy;
    //触摸点在屏幕的绝对坐标
    int rawx,rawy;
    int clickOrTouch=0;//0状态未指定 1点击 2触摸
    //用于制作悬浮窗口 (悬浮窗口的坐标从状态栏开始计算,用屏幕的绝对坐标设值时需要先减去状态栏高度)
    WindowManager mWindowManager;
    //悬浮窗口的位置信息
    WindowManager.LayoutParams mWindowParams = new WindowManager.LayoutParams();
    int statusBarh;
    //用于拖拽的临时View
    View fakerView;
    OnDragUpListener onDragUpListener;

    int touchCount=0;
    GridViewAdapter oldAdapter,newAdapter;
    public DragLayout(Context context) {
        super(context);

    }

    public DragLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=  context;
        statusBarh=getTitleHeight();
    }

    public DragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setChildrenDrawingOrderEnabled(true);
    }

    /**
     *
     * @param ev
     * @return true 把触摸事件全部拦截,不给子View处理
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //getParent().requestDisallowInterceptTouchEvent(true);
        rawx= (int) event.getRawX();
        rawy= (int) event.getRawY();

        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:

                //Log.e(TAG, "touchCount:"+touchCount );
                Log.e(TAG, "ACTION_DOWN" );
                targetView=getGridItemByXY(rawx,rawy);
                startX= (int) event.getX();
                startY= (int) event.getY();
                if (targetView!=null){
                    getParent().requestDisallowInterceptTouchEvent(true);
                    addToWindow();
                    hideGridItem((ViewGroup) targetView);
                    preL=targetView.getLeft();
                    preT=targetView.getTop();
                    preR=targetView.getRight();
                    preB=targetView.getBottom();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "ACTION_MOVE" );
                if (targetView!=null){
                    int deltaX= (int) event.getX()-startX;
                    int deltaY= (int) event.getY()-startY;
                    mWindowParams.x=viewrawx+deltaX;
                    mWindowParams.y=viewrawy+deltaY-statusBarh;
                    // 使参数生效
                    mWindowManager.updateViewLayout(fakerView, mWindowParams);
                    if (clickOrTouch!=2){
                        clickOrTouch=clickOrTouchByMove(deltaX,deltaY);
                    }


                }

                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "ACTION_UP" );
                getParent().requestDisallowInterceptTouchEvent(false);
                if (targetView!=null){
//                    double endx=event.getX();
//                    double endy=event.getY();
//                    //double distance = Math.sqrt(Math.abs(startX-endx)*Math.abs(startX-endx)+Math.abs(startY-endy)*Math.abs(startY-endy));
//                    showGridItem((ViewGroup) targetView);
//                    mWindowManager.removeView(fakerView);
                    if (clickOrTouch!=2){
                        //触发点击事件
                        targetView.performClick();
                        showGridItem((ViewGroup) targetView);
                        mWindowManager.removeView(fakerView);
                    }else {
                        //处理拖拽按钮
                        dealWithDragActionUp();
                    }
                    clickOrTouch=0;
                    //targetView=null;

                }
                break;
        }
        return true;
    }

    /**
     *
     * @param deltaX
     * @param deltaY
     * @return 1代表click,2代表touch
     */
    private int clickOrTouchByMove(int deltaX,int deltaY){
        double distance = Math.sqrt(Math.abs(deltaX)*Math.abs(deltaX)+Math.abs(deltaY)*Math.abs(deltaY));
        if (distance<15)
            return 1;
        else
            return 2;
    }

    public void dealWithDragActionUp(){
        ViewGroup viewGroup;
        boolean isInGridView=false;
        GridView oldgv= (GridView) targetView.getParent();
        oldAdapter= (GridViewAdapter) oldgv.getAdapter();
        GridItem tarInfo=oldAdapter.dataList.get(Integer.valueOf(targetView.getContentDescription().toString()));
        GridView newgv;
        for ( int i=0;i<getChildCount();i++){
            viewGroup= (ViewGroup) getChildAt(i);
            if (isViewInViewGroup(fakerView,viewGroup)/*isFakerViewInViewGroup(viewGroup)*/){
                isInGridView=true;
                if (viewGroup.getChildAt(1)!=targetView.getParent()) {
                    //Toast.makeText(context,"changed!", Toast.LENGTH_SHORT).show();
                    newgv = (GridView) viewGroup.getChildAt(1);
                    newAdapter = (GridViewAdapter) newgv.getAdapter();

                    if (onDragUpListener != null) {
                        onDragUpListener.OnDragUpIn(oldAdapter, newAdapter, tarInfo);

                    } else {
                        dealWithFakerAndTar(true);
                        refreshDragLayout();
                    }
                    return;
                }
            }



        }

        if (isInGridView){
            dealWithFakerAndTar(true);
        }else {
            //来到这里说明是滑出4个gridView外
            if (onDragUpListener!=null){
                onDragUpListener.OnDragUpOut(oldAdapter,tarInfo);
                return;
//            showGridItem((ViewGroup) targetView);
//            mWindowManager.removeView(fakerView);
            }else {
                dealWithFakerAndTar(true);
                refreshDragLayout();
            }
            //dealWithFakerAndTar(true);
        }

    }

    public void dealWithFakerAndTar(boolean showTar){
        if (showTar)
            showGridItem((ViewGroup) targetView);
        else
            hideGridItem((ViewGroup) targetView);
        mWindowManager.removeView(fakerView);
    }

    public void refreshDragLayout(){
        if (oldAdapter!=null && newAdapter!=null){
            showGridItem((ViewGroup) targetView);
            mWindowManager.removeView(fakerView);
            GridItem tarInfo=oldAdapter.dataList.get(Integer.valueOf(targetView.getContentDescription().toString()));
            newAdapter.dataList.add(tarInfo);
            oldAdapter.dataList.remove(tarInfo);
            newAdapter.notifyDataSetChanged();
            oldAdapter.notifyDataSetChanged();
            oldAdapter=null;
            newAdapter=null;

        }
    }

    private ViewGroup getViewGroupFromPoint(int x, int y){
        for (int i=0;i<getChildCount();i++){
            ViewGroup viewGroup= (ViewGroup) getChildAt(i);
            if (isTouchPointInGridviewArea(viewGroup,x,y)){
                return viewGroup;
            }
        }
        return null;
    }

    private boolean isTouchPointInGridviewArea(View view,int x,int y){
        if (view == null) {
            return false;
        }
        if (view.getContentDescription()==null || !(view.getContentDescription().equals("haveDrag")))
            return false;
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0]+((ViewGroup)view).getChildAt(0).getMeasuredWidth();
        int top = location[1];
        int right = location[0] + view.getMeasuredWidth();
        int bottom = location[1] + view.getMeasuredHeight();
        if (y >= top && y <= bottom && x >= left
                && x <= right) {
            return true;
        }
        return false;
    }

    //(x,y)是否在view的区域内
    private boolean isTouchPointInView(View view, int x, int y) {
        if (view == null) {
            return false;
        }
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        //view.isClickable() &&
        if (y >= top && y <= bottom && x >= left
                && x <= right) {
            return true;
        }
        return false;
    }

    private boolean isViewInViewGroup(View view, ViewGroup viewGroup){
        int[] viewCenter=getCenterPointFromView(view);
        return isTouchPointInView(viewGroup,viewCenter[0],viewCenter[1]);
    }

    private int[] getCenterPointFromView(View view){
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        location[0]=(left+right)/2;
        location[1]=(top+bottom)/2;
        return  location;
    }

    private int[] getPointFromView(View view){
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return  location;
    }


    private View getItemFromGridView(GridView gridView, int x, int y){
        for (int i=0;i<gridView.getCount();i++){
            View view=gridView.getChildAt(i);
            if (isTouchPointInView(view,x,y))
                return view;
        }
        return null;
    }

    public View getGridItemByXY(int rawx, int rawy){
        ViewGroup viewGroup= getViewGroupFromPoint(rawx,rawy);
        if (viewGroup==null){
            targetViewGroup=null;
            return null;
        }

        targetViewGroup =viewGroup;
        GridView gridView= (GridView) viewGroup.getChildAt(1);
        View view=getItemFromGridView(gridView,rawx,rawy);
        return view;
    }



//    @Override
//    protected int getChildDrawingOrder(int childCount, int i) {
//        int position = getViewGroupIndex(targetViewGroup);
//        if(position<0){
//            return i;
//        }else{
//
//            if(i == childCount - 1){//这是最后一个需要刷新的item
//                Log.e("Order", "lastView:"+position );
//                return position;
//            }
//            if(i == position){//这是原本要在最后一个刷新的item
//                return childCount - 1;
//            }
//            return i;
//        }
//    }

    private void addToWindow(){
        // 获取Service
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        // 设置窗口类型，一共有三种Application windows, Sub-windows, System windows
        // API中以TYPE_开头的常量有23个
        mWindowParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        // 设置期望的bitmap格式
        mWindowParams.format = PixelFormat.RGBA_8888;
        // 以下属性在Layout Params中常见重力、坐标，宽高
        mWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
        int[] location=getPointFromView(targetView);
        mWindowParams.x = location[0];
        mWindowParams.y = location[1]-statusBarh;
        viewrawx=location[0];
        viewrawy=location[1];
        mWindowParams.width = targetView.getWidth();
        mWindowParams.height = targetView.getHeight();
        fakerView=createGridItem(targetView);
        mWindowManager.addView(fakerView,mWindowParams);
    }


    private View createGridItem(View targetView){
        Log.i("createGridItem ", "createGridItem");
        ViewGroup view= (ViewGroup) LayoutInflater.from(context).inflate(R.layout.drag_griditem,null);
        ViewGroup tarViewGroup=((ViewGroup)targetView);
        LayoutParams lp;
        lp= new LayoutParams(targetView.getWidth(),targetView.getHeight());
        view.setLayoutParams(lp);
        // LinearLayout item_jia = (LinearLayout)view;
        ImageView iv_car_jia= (ImageView) view.getChildAt(0);
        TextView tv_carnum_jia= (TextView) view.getChildAt(1);
        LinearLayout item = (LinearLayout)tarViewGroup;
        ImageView iv_car= (ImageView) tarViewGroup.getChildAt(0);
        TextView tv_carnum= (TextView) tarViewGroup.getChildAt(1);
        iv_car_jia.setImageDrawable(iv_car.getDrawable());
        tv_carnum_jia.setText(tv_carnum.getText().toString());
        //tv_carnum_jia.setTextColor(tv_carnum.getCurrentTextColor());
        //  item_jia.setBackground(item.getBackground());
        return view;
    }


    public int getTitleHeight() {
        /**
         * 获取状态栏高度——方法1
         * */
        int statusBarHeight1 = -1;
        //获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = getResources().getDimensionPixelSize(resourceId);
        }
        // Log.e("WangJ", "状态栏-方法1:" + statusBarHeight1);
        return statusBarHeight1;
    }

    private void hideGridItem(ViewGroup gridItem){
        gridItem.setVisibility(INVISIBLE);
//        for (int i=0;i<gridItem.getChildCount();i++){
//            gridItem.getChildAt(i).setVisibility(INVISIBLE);
//        }
    }

    private void showGridItem(ViewGroup gridItem){
        gridItem.setVisibility(VISIBLE);
//        for (int i=0;i<gridItem.getChildCount();i++){
//            gridItem.getChildAt(i).setVisibility(VISIBLE);
//        }
    }

    public interface OnDragUpListener{
        void OnDragUpIn(GridViewAdapter oldAdapter, GridViewAdapter newAdapter, GridItem gridItem);

        void OnDragUpOut(GridViewAdapter oldAdapter, GridItem gridItem);
    }

    public OnDragUpListener getOnDragUpListener() {
        return onDragUpListener;
    }

    public void setOnDragUpListener(OnDragUpListener onDragUpListener) {
        this.onDragUpListener = onDragUpListener;
    }


}
