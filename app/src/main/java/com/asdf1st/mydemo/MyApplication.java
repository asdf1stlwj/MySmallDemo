package com.asdf1st.mydemo;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by hasee on 2018/9/17.
 */

public class MyApplication extends Application{
    public RefWatcher mRefWatcher;
    @Override
    public void onCreate() {
        super.onCreate();
        mRefWatcher=LeakCanary.install(this);

    }
}
