package com.kinpa200296.android.labs.samplendk;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    static{
        System.loadLibrary("gcd");
    }

    static native int nativeGcd(long a, long b);

    EditText firstArg, secondArg;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstArg = (EditText) findViewById(R.id.firstArg);
        secondArg = (EditText) findViewById(R.id.secondArg);
        result = (TextView) findViewById(R.id.result);
    }

    public void onCalculateClick(View view) {
        long a, b;
        try {
            a = Long.parseLong(firstArg.getText().toString());
            b = Long.parseLong(secondArg.getText().toString());
        }catch (NumberFormatException e){
            Toast.makeText(this, "One of the arguments is not a valid long for 'long'", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (view.getId()) {
            case R.id.calculateGcd:
                result.setText(getString(R.string.result) + " " + nativeGcd(a, b));
                break;
            default:
                return;
        }
    }
}
