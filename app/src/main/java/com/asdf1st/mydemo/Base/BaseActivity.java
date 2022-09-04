package com.asdf1st.mydemo.Base;

import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;

public abstract class BaseActivity extends AppCompatActivity {
    protected Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeInit();
        setContentView(getLayoutId());
        initClass();
        initView();
        afterInit();
    }

    protected void beforeInit() {

    }

    protected abstract int getLayoutId();

    protected void initClass() {
        EventBus.getDefault().register(this);
        handler=new Handler();
    }

    protected void initView() {

    }

    protected void afterInit() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
