package com.kinpa200296.android.labs.musicplayer;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.os.RemoteException;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.kinpa200296.android.labs.musicservicelibrary.MusicServiceBroadcastReceiver;
import com.kinpa200296.android.labs.musicservicelibrary.MusicServiceMessageHandler;

public class MainActivity extends Activity {

    Intent intent;
    MusicServiceConnection serviceConnection;
    MusicServiceBroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = new Intent("com.kinpa200296.android.labs.musicservice");
        intent.setPackage("com.kinpa200296.android.labs.musicservice");
        serviceConnection = new MusicServiceConnection();

        bindService(intent, serviceConnection, BIND_ABOVE_CLIENT);

        TextView tvProgress = (TextView) findViewById(R.id.tvProgress);
        IntentFilter filter = new IntentFilter(MusicServiceBroadcastReceiver.BROADCAST_ACTION);
        broadcastReceiver = new MusicServiceBroadcastReceiver(tvProgress);
        registerReceiver(broadcastReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        unbindService(serviceConnection);
        unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }

    public void onControlMusicClick(View view) {
        switch (view.getId()) {
            case R.id.btnPlay:
                if (serviceConnection.messenger == null) {
                    bindService(intent, serviceConnection, BIND_ABOVE_CLIENT);
                    if (serviceConnection.messenger == null) {
                        startService(intent);
                        return;
                    }
                }
                try {
                    serviceConnection.messenger.send(Message.obtain(null, MusicServiceMessageHandler.MSG_PLAY, 0, 0));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnPause:
                if (serviceConnection.messenger == null) {
                    bindService(intent, serviceConnection, BIND_ABOVE_CLIENT);
                    if (serviceConnection.messenger == null) {
                        return;
                    }
                }
                try {
                    serviceConnection.messenger.send(Message.obtain(null, MusicServiceMessageHandler.MSG_PAUSE, 0, 0));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnStop:
                stopService(intent);
                break;
        }
    }
}
