package com.kinpa200296.android.labs.musicservicelibrary;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

public class MusicServiceBroadcastReceiver extends BroadcastReceiver {

    public static final String BROADCAST_ACTION = "com.kinpa200296.android.labs.musicservicebroadcast";

    public static final String ARG_STATUS = "status";
    public static final String ARG_PASSED_LENGTH = "passedLength";
    public static final String ARG_TOTAL_LENGTH = "totalLength";

    private TextView textView;

    public MusicServiceBroadcastReceiver(TextView textView){
        this.textView = textView;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String status = intent.getStringExtra(ARG_STATUS);
        String passedLength = intent.getStringExtra(ARG_PASSED_LENGTH);
        String totalLength = intent.getStringExtra(ARG_TOTAL_LENGTH);

        textView.setText(String.format("%s: %s / %s", status, passedLength, totalLength));
    }
}
