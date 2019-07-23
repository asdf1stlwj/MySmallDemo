package com.asdf1st.mydemo.Video.Ijkplayer;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.asdf1st.mydemo.Base.Presenter.IPresenter;
import com.asdf1st.mydemo.Base.View.Activity.BaseActivity;
import com.asdf1st.mydemo.R;
import com.asdf1st.mydemo.Utils.FileUtils;

import butterknife.BindView;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class IjkPlayerActivity extends BaseActivity {
    @BindView(R.id.videoView)
    VideoPlayerIJK videoView;
    String path;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_ijk_player;
    }

    @Override
    protected void initClass() {
        super.initClass();
        path=FileUtils.SDPATH+"test.mp4";
        initIjkPlayer();
    }

    @Override
    protected void afterInit() {
        super.afterInit();

    }

    private void initIjkPlayer() {
        if (!TextUtils.isEmpty(path)) {
            //加载native库
            try {
                IjkMediaPlayer.loadLibrariesOnce(null);
                IjkMediaPlayer.native_profileBegin("libijkplayer.so");
            } catch (Exception e) {
                this.finish();
            }
            videoView.setVideoPath(path);
            videoView.setListener(new VideoPlayerListener() {
                @Override
                public void onBufferingUpdate(IMediaPlayer iMediaPlayer, int i) {

                }

                @Override
                public boolean onError(IMediaPlayer iMediaPlayer, int i, int i1) {
                    return false;
                }

                @Override
                public boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i1) {
                    return false;
                }

                @Override
                public void onPrepared(IMediaPlayer iMediaPlayer) {
                    // 视频准备好播放了，但是他不会自动播放，需要手动让他开始。
//                    Thread thread=new Thread(){
//                        @Override
//                        public void run() {
//
//                        }
//                    };
//                    thread.setPriority(Thread.MAX_PRIORITY);
//                    thread.start();

                    videoView.start();
                }

                @Override
                public void onSeekComplete(IMediaPlayer iMediaPlayer) {

                }
            });

        }
    }

    @Override
    public IPresenter createPresenter() {
        return null;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (videoView != null)
            videoView.release();
        IjkMediaPlayer.native_profileEnd();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
