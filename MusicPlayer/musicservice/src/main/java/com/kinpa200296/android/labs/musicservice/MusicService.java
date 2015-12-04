package com.kinpa200296.android.labs.musicservice;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.Messenger;

import com.kinpa200296.android.labs.musicservicelibrary.MusicServiceBroadcastReceiver;
import com.kinpa200296.android.labs.musicservicelibrary.MusicServiceMessageHandler;

import java.util.Timer;

public class MusicService extends Service {

    MediaPlayer ambientMediaPlayer;
    Timer timer;

    @Override
    public IBinder onBind(Intent intent) {
        return new Messenger(new MusicServiceMessageHandler(ambientMediaPlayer)).getBinder();
    }

    @Override
    public void onCreate() {
        ambientMediaPlayer = MediaPlayer.create(this, R.raw.numb);
        ambientMediaPlayer.setLooping(true);
        timer = new Timer();
        timer.schedule(new MusicServiceProgressNotifier(this), 0, 500);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ambientMediaPlayer.start();
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        ambientMediaPlayer.stop();

        timer.cancel();

        Intent intent = new Intent(MusicServiceBroadcastReceiver.BROADCAST_ACTION);

        intent.putExtra(MusicServiceBroadcastReceiver.ARG_STATUS, getString(R.string.stopped));
        intent.putExtra(MusicServiceBroadcastReceiver.ARG_PASSED_LENGTH,
                MusicServiceProgressNotifier.toFormattedTime(getString(R.string.timeFormat), 0));
        intent.putExtra(MusicServiceBroadcastReceiver.ARG_TOTAL_LENGTH,
                MusicServiceProgressNotifier.toFormattedTime(getString(R.string.timeFormat),
                        ambientMediaPlayer.getDuration()));

        sendBroadcast(intent);
    }
}
