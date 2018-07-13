package com.asdf1st.mydemo.UI.Collapsed;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.*;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.asdf1st.mydemo.R;

import java.util.ArrayList;
import java.util.List;

public class CollapseActivity extends AppCompatActivity {
    ViewPager mViewPager;
    List<Fragment> mFragments=new ArrayList<>();
    Toolbar mToolbar;
    TabLayout mTablayout;
    String[]  mTitles=new String[]{
            "主页","微博","相册"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collapse);
        mViewPager= (ViewPager) findViewById(R.id.viewpager);
        mTablayout= (TabLayout) findViewById(R.id.tablayout);
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        for (int i=0;i<3;i++){
            mFragments.add(ListFragment.newInstance(mTitles[i]));
        }
        mTablayout.setupWithViewPager(mViewPager);
        BaseFragmentAdapter pageAdapter =
                new BaseFragmentAdapter(getSupportFragmentManager(),mFragments,mTitles);
        mViewPager.setAdapter(pageAdapter);
    }
}
