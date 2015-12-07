package com.kinpa200296.android.labs.bouncyball;

import android.os.Handler;

import java.util.TimerTask;

public class ViewUpdater extends TimerTask {

    Handler updateHandler;

    public ViewUpdater(Handler updateTarget) {
        this.updateHandler = updateTarget;
    }

    @Override
    public void run() {
        updateHandler.sendEmptyMessage(FieldViewUpdateHandler.MSG_UPDATE);
    }
}
