package com.kinpa200296.android.labs.musicservice;

import android.content.Intent;

import com.kinpa200296.android.labs.musicservicelibrary.MusicServiceBroadcastReceiver;

import java.util.TimerTask;

public class MusicServiceProgressNotifier extends TimerTask {

    private MusicService service;

    public MusicServiceProgressNotifier(MusicService service) {
        this.service = service;
    }

    @Override
    public void run() {
        Intent intent = new Intent(MusicServiceBroadcastReceiver.BROADCAST_ACTION);

        intent.putExtra(MusicServiceBroadcastReceiver.ARG_STATUS,
                service.ambientMediaPlayer.isPlaying()
                        ? service.getString(R.string.playing)
                        : service.getString(R.string.paused));
        intent.putExtra(MusicServiceBroadcastReceiver.ARG_PASSED_LENGTH,
                toFormattedTime(service.getString(R.string.timeFormat),
                        service.ambientMediaPlayer.getCurrentPosition()));
        intent.putExtra(MusicServiceBroadcastReceiver.ARG_TOTAL_LENGTH,
                toFormattedTime(service.getString(R.string.timeFormat),
                        service.ambientMediaPlayer.getDuration()));

        service.sendBroadcast(intent);
    }

    public static String toFormattedTime(String format, int time) {
        time /= 1000;
        return String.format(format, time / 60, time % 60);
    }
}
