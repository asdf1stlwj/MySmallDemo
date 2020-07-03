package com.asdf1st.mydemo.Utils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.airbnb.lottie.LottieAnimationView;

/**
 * Created by tanyaping on 2019/12/13.
 */

public class XiaoYuAnimationUtils {
    private static String TAG = XiaoYuAnimationUtils.class.getSimpleName();

//    public static void translateViewToViewAlpha(View startView, View endView, Animation.AnimationListener listener, long duration) {
//        startView.clearAnimation();
//        int locationStartView[] = new int[2], locationEndView[] = new int[2];
//        startView.getLocationOnScreen(locationStartView);
//        endView.getLocationOnScreen(locationEndView);
//        TranslateAnimation translateAni = new TranslateAnimation(
//                0, locationEndView[0] - locationStartView[0], 0, locationEndView[1] - locationStartView[1]);
//        //设置动画执行的时间，单位是毫秒
//        translateAni.setDuration(duration);
//        // 设置动画重复次数
//        // -1或者Animation.INFINITE表示无限重复，正数表示重复次数，0表示不重复只播放一次
//        translateAni.setRepeatCount(0);
//        AnimationSet animationSet = new AnimationSet();
//        animationSet.addAnimation(translateAni);
//        if (listener != null) {
//            translateAni.setAnimationListener(listener);
//        }
//    }

    public static void translateViewToViewAlpha_Obj(View startView, View endView, Animator.AnimatorListener listener, long duration,boolean repeat) {
        startView.clearAnimation();
        int locationStartView[] = new int[2], locationEndView[] = new int[2];
        startView.getLocationOnScreen(locationStartView);
        endView.getLocationOnScreen(locationEndView);
        //原来取得的是View左上角坐标,要转换成View中心坐标
        locationStartView[0]+=startView.getWidth()/2;
        locationStartView[1]+=startView.getHeight()/2;
        locationEndView[0]+=endView.getWidth()/2;
        locationEndView[1]+=endView.getHeight()/2;
        ObjectAnimator translateAniX = ObjectAnimator.ofFloat(startView, "translationX", locationEndView[0] - locationStartView[0]);
        ObjectAnimator translateAniY = ObjectAnimator.ofFloat(startView, "translationY", locationEndView[1] - locationStartView[1]);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(startView, "alpha", new float[]{1, 0});
        if (repeat){
            translateAniX.setRepeatCount(-1);
            translateAniY.setRepeatCount(-1);
            alpha.setRepeatCount(-1);
        }
        AnimatorSet animatorSet = new AnimatorSet();
        //设置动画执行的时间，单位是毫秒
        animatorSet.setDuration(duration);
        animatorSet.play(translateAniX).with(translateAniY).with(alpha);
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.start();
//        startView.startAnimation(translateAniX);
        if (listener != null) {
            translateAniX.addListener(listener);
        }
    }

    public static void scale_Obj(View startView, Animator.AnimatorListener listener, long duration) {
        startView.clearAnimation();
        ObjectAnimator ScaleX = ObjectAnimator.ofFloat(startView, "scaleX", new float[]{0, (float) 1.2, 1});
        ObjectAnimator ScaleY = ObjectAnimator.ofFloat(startView, "scaleY", new float[]{0, (float) 1.2, 1});
        AnimatorSet animatorSet = new AnimatorSet();
        //设置动画执行的时间，单位是毫秒
        animatorSet.setDuration(duration);
        //1.Android自带十几个插值器
        //2.OvershootInterpolator:动画会超过目标值一些，然后再弹回来。效果看起来有点像你一屁股坐在沙发上后又被弹起来一点的感觉。
        //animatorSet.setInterpolator(new OvershootInterpolator());
        animatorSet.play(ScaleX).with(ScaleY);
        animatorSet.start();

        if (listener != null) {
            ScaleX.addListener(listener);
        }
    }

    public static abstract class SimpleAnimatorListener implements Animator.AnimatorListener{

        @Override
        public void onAnimationStart(Animator animator) {

        }



        @Override
        public void onAnimationCancel(Animator animator) {

        }

        @Override
        public void onAnimationRepeat(Animator animator) {

        }
    }
}
