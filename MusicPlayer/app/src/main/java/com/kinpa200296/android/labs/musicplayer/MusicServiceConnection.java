package com.kinpa200296.android.labs.musicplayer;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Messenger;

public class MusicServiceConnection implements ServiceConnection {

    Messenger messenger;

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        messenger = new Messenger(service);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        messenger = null;
    }
}
