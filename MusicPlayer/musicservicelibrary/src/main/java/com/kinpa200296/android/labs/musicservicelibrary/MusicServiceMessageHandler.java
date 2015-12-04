package com.kinpa200296.android.labs.musicservicelibrary;

import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;

public class MusicServiceMessageHandler extends Handler {

    public static final int MSG_PLAY = 1;
    public static final int MSG_PAUSE = 2;

    private MediaPlayer player;

    public MusicServiceMessageHandler(MediaPlayer player) {
        this.player = player;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what){
            case MSG_PLAY:
                if (!player.isPlaying()){
                    player.start();
                }
                break;
            case MSG_PAUSE:
                player.pause();
                break;
            default:
                super.handleMessage(msg);
        }
    }
}
