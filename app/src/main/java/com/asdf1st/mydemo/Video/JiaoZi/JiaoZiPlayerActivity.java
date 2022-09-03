package com.asdf1st.mydemo.Video.JiaoZi;

import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.asdf1st.mydemo.Base.MVP.Presenter.IPresenter;
import com.asdf1st.mydemo.Base.MVP.View.Activity.BaseMVPActivity;
import com.asdf1st.mydemo.R;

import butterknife.BindView;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class JiaoZiPlayerActivity extends BaseMVPActivity {
    @BindView(R.id.videoView)
    JzvdStd videoView;
    private String name,url;
    @Override
    public int getLayoutId() {
        return R.layout.activity_jiao_zi_player;
    }

    @Override
    public IPresenter createPresenter() {
        return null;
    }

    @Override
    protected void initClass() {
        super.initClass();
        name=getIntent().getStringExtra("name");
        url=getIntent().getStringExtra("url");
        if (TextUtils.isEmpty(name)&&TextUtils.isEmpty(url)){
            name="复仇者联盟4终局之战";
            url="http://supportfile.video-star.com.cn:38084/fzzlm.MP4";
        }else if (!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(url)){
            //无须做任何操作
        }else {
            Toast.makeText(this, "名字或URL为空", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void initView() {
        super.initView();
        videoView.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void afterInit() {
        super.afterInit();
        videoView.setUp(url
                , name,Jzvd.SCREEN_FULLSCREEN);
        //videoView.gotoScreenFullscreen();
        videoView.startVideo();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
        finish();
    }

}
