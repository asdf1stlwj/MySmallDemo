package com.asdf1st.mydemo.UI.Animation;

import android.animation.Animator;
import android.view.View;

import com.asdf1st.mydemo.Base.Presenter.IPresenter;
import com.asdf1st.mydemo.Base.View.Activity.BaseActivity;
import com.asdf1st.mydemo.R;
import com.asdf1st.mydemo.Utils.XiaoYuAnimationUtils;

import butterknife.BindView;

public class AnimationMove2Activity extends BaseActivity {
    @BindView(R.id.view_tar)
    View view_tar;
    @BindView(R.id.view_src0)
    View view_src0;
    @BindView(R.id.view_src1)
    View view_src1;
    @BindView(R.id.view_src2)
    View view_src2;
    @BindView(R.id.view_src3)
    View view_src3;
    @BindView(R.id.view_src4)
    View view_src4;
    @Override
    public int getLayoutId() {
        return R.layout.activity_animation_move2;
    }

    @Override
    protected void afterInit() {
        super.afterInit();
        view_src0.postDelayed(new Runnable() {
            @Override
            public void run() {
                XiaoYuAnimationUtils.translateViewToViewAlpha_Obj(view_src0, view_tar, new XiaoYuAnimationUtils.SimpleAnimatorListener() {
                    @Override
                    public void onAnimationEnd(Animator animator) {

                    }
                },400,true);
            }
        },0*100);
        view_src1.postDelayed(new Runnable() {
            @Override
            public void run() {
                XiaoYuAnimationUtils.translateViewToViewAlpha_Obj(view_src1, view_tar, new XiaoYuAnimationUtils.SimpleAnimatorListener() {
                    @Override
                    public void onAnimationEnd(Animator animator) {

                    }
                },400,true);
            }
        },1*100);
        view_src2.postDelayed(new Runnable() {
            @Override
            public void run() {
                XiaoYuAnimationUtils.translateViewToViewAlpha_Obj(view_src2, view_tar, new XiaoYuAnimationUtils.SimpleAnimatorListener() {
                    @Override
                    public void onAnimationEnd(Animator animator) {

                    }
                },400,true);
            }
        },2*100);
        view_src3.postDelayed(new Runnable() {
            @Override
            public void run() {
                XiaoYuAnimationUtils.translateViewToViewAlpha_Obj(view_src3, view_tar, new XiaoYuAnimationUtils.SimpleAnimatorListener() {
                    @Override
                    public void onAnimationEnd(Animator animator) {

                    }
                },400,true);
            }
        },3*100);

    }

    @Override
    public IPresenter createPresenter() {
        return null;
    }
}
