package com.kinpa200296.android.labs.bouncyball;

import android.os.Handler;
import android.os.Message;

public class FieldViewUpdateHandler extends Handler {

    public static final int MSG_UPDATE = 1;

    FieldView fieldView;

    public FieldViewUpdateHandler(FieldView fieldView) {
        this.fieldView = fieldView;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what){
            case MSG_UPDATE:
                fieldView.invalidate();
                break;
            default:
                super.handleMessage(msg);
                break;
        }
    }
}
