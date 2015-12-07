package com.kinpa200296.android.labs.bouncyball;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Timer;

public class MainActivity extends Activity implements FieldCallback, SensorEventListener {

    TextView ballSize, bounceRate, friction;
    FieldView fieldView;
    Timer timer;
    SensorManager sensorManager;
    Sensor accelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ballSize = (TextView) findViewById(R.id.ballSize);
        bounceRate = (TextView) findViewById(R.id.bounceRate);
        friction = (TextView) findViewById(R.id.friction);

        LinearLayout layout = (LinearLayout) findViewById(R.id.field);
        fieldView = new FieldView(this);
        layout.addView(fieldView);

        timer = new Timer();
        timer.schedule(new ViewUpdater(new FieldViewUpdateHandler(fieldView)), 0, FieldView.REFRESH_RATE);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void displaySize(float size) {
        ballSize.setText(String.format("%s %.3g", getString(R.string.ballSize), size));
    }

    @Override
    public void displayBounceRate(float bounceRate) {
        this.bounceRate.setText(String.format("%s %.3g", getString(R.string.bounceRate), bounceRate));
    }

    @Override
    public void displayFriction(float friction){
        this.friction.setText(String.format("%s %.3g", getString(R.string.friction), friction));
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (fieldView.controller != null) {
            fieldView.controller.setGravityForce(event.values[0], event.values[1]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
