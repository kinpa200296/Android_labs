package com.kinpa200296.android.labs.customgestures;

import android.app.Activity;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity implements GestureOverlayView.OnGesturePerformedListener{

    public static final String myLogs = "My Logs";

    GestureLibrary gestureLibrary;

    TextView tvText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gestureLibrary = GestureLibraries.fromRawResource(this, R.raw.gestures);
        if (!gestureLibrary.load()){
            Log.e(myLogs, "Can't load gestures");
            finish();
        }

        GestureOverlayView overlayView = new GestureOverlayView(this);
        View view = getLayoutInflater().inflate(R.layout.activity_main, null);
        overlayView.addView(view);
        overlayView.addOnGesturePerformedListener(this);
        setContentView(overlayView);

        tvText = (TextView) view.findViewById(R.id.tvText);
    }

    @Override
    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
        ArrayList<Prediction> predictions = gestureLibrary.recognize(gesture);
        tvText.setText(getString(R.string.verdict)+ " \"" + predictions.get(0).name + "\"");
    }
}
