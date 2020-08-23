package com.asdf1st.mydemo.UI.Animation;

import android.animation.Animator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.asdf1st.mydemo.Base.Presenter.IPresenter;
import com.asdf1st.mydemo.Base.View.Activity.BaseActivity;
import com.asdf1st.mydemo.R;
import com.asdf1st.mydemo.Utils.Data;
import com.asdf1st.mydemo.Utils.XiaoYuAnimationUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AnimationMoveActivity extends BaseActivity implements AnimationMoveAdapter.OnPlayAnimationListener {
    private String TAG=AnimationMoveActivity.class.getSimpleName();
    @BindView(R.id.view_tar)
    View view_tar;
    @BindView(R.id.rv_animation_item)
    RecyclerView rv_animation_item;
    List<Data> dataList=new ArrayList<>();
    List<View> animationViewList=new ArrayList<>();
    @Override
    public int getLayoutId() {
        return R.layout.activity_animation_move;
    }

    @Override
    protected void initClass() {
        super.initClass();
        for (int i=0;i<4;i++){
            dataList.add(new Data("编号"+i));
        }

    }

    @Override
    protected void initView() {
        super.initView();
        initRecyclerView();
    }

    private void initRecyclerView() {
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,4);
        AnimationMoveAdapter adapter=new AnimationMoveAdapter(this,dataList);
        adapter.setOnPlayAnimationListener(this);
        rv_animation_item.setLayoutManager(gridLayoutManager);
        rv_animation_item.setAdapter(adapter);
    }

    @Override
    protected void afterInit() {
        super.afterInit();
        rv_animation_item.getAdapter().notifyDataSetChanged();
    }

    @Override
    public IPresenter createPresenter() {
        return null;
    }


    @Override
    public void onItemViewPlayAnimation(int position, View itemView) {
        animationViewList.add(itemView);
        itemView.postDelayed(new Runnable() {
            @Override
            public void run() {
               XiaoYuAnimationUtils.scale_Obj(itemView, new XiaoYuAnimationUtils.SimpleAnimatorListener() {
                   @Override
                   public void onAnimationEnd(Animator animator) {
                       if (position==dataList.size()-1){
                           for (View view:animationViewList){
                               int index=animationViewList.indexOf(view);
                               itemView.postDelayed(new Runnable() {
                                   @Override
                                   public void run() {
                                       XiaoYuAnimationUtils.translateViewToViewAlpha_Obj(view, view_tar, new XiaoYuAnimationUtils.SimpleAnimatorListener() {
                                           @Override
                                           public void onAnimationEnd(Animator animator) {

                                           }
                                       }, 400,false);
                                   }
                               },index*50);
                           }
                       }
                   }
               }, 330);
            }
        },position*230);
    }


}
