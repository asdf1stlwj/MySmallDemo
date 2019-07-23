package com.asdf1st.mydemo.Video.Ijkplayer;

import tv.danmaku.ijk.media.player.IMediaPlayer;

/**
 * Created by lwj on 2019/7/16
 */
public abstract class VideoPlayerListener implements IMediaPlayer.OnPreparedListener,
        IMediaPlayer.OnInfoListener, IMediaPlayer.OnSeekCompleteListener,
        IMediaPlayer.OnBufferingUpdateListener, IMediaPlayer.OnErrorListener {

}
