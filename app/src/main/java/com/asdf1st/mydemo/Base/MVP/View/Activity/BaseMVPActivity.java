package com.asdf1st.mydemo.Base.MVP.View.Activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asdf1st.mydemo.Base.BaseActivity;
import com.asdf1st.mydemo.Base.MVP.Presenter.IPresenter;
import com.asdf1st.mydemo.Base.MVP.View.IView;
import com.asdf1st.mydemo.R;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseMVPActivity<T extends IPresenter> extends BaseActivity implements IView {
    private String TAG = "BaseMVPActivity";
    @Nullable
    @BindView(R.id.tv_middle)
    public TextView tv_middle;

    @Nullable
    @BindView(R.id.ll_left)
    public ViewGroup ll_left;

    @Nullable
    @BindView(R.id.iv_right)
    public ImageView iv_right;

    @Nullable
    @BindView(R.id.tv_right)
    public TextView tv_right;

    public T mPresenter;

    private Unbinder unbinder;


    private boolean isExit = false;

    @Override
    protected void initClass() {
        super.initClass();
        unbinder = ButterKnife.bind(this);
        mPresenter = (T) createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        } else {
            Log.e(TAG, "当前Presenter为空");
        }
        handler=new Handler();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.dettachView();
        unbinder.unbind();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void dismissWaittingDialog() {

    }

    @Override
    public void showWaittingDialog() {

    }

    @Override
    public void showMessage(String message) {
    }

    @Override
    public void showAlert() {

    }

    public abstract int getLayoutId();

    @Subscribe
    public void onEvent(Object event) {
    }

    protected void beforeInit() {
    }

    protected void afterInit() {
    }

    protected void initView() {
        if (ll_left != null) {
            ll_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BaseMVPActivity.this.onBackPressed();
                }
            });
        }
    }

    public void showToast(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    //再按一次退出逻辑
    public void exit() {
        if (!isExit) {
            isExit = true;
            showMessage("再按一次退出");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    isExit=false;
                }
            },2000);
        } else {
            finish();
//            Intent intent = new Intent(Intent.ACTION_MAIN);
//            intent.addCategory(Intent.CATEGORY_HOME);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            this.startActivity(intent);
        }
    }

    public int getDrawableResourceId(String imageName){
        if (imageName==null)
            return 0;
        final int resId = getResources().getIdentifier(
                imageName, "drawable", getPackageName());
        return resId;
    }

}