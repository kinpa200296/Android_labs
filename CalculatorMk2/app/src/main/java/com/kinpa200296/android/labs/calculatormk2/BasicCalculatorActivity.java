package com.kinpa200296.android.labs.calculatormk2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BasicCalculatorActivity extends Activity implements View.OnLongClickListener {

    TextView expression, resultPreview;
    Button btnDelete;

    public final String EXPRESSION = "savedExpression";
    public final String RESULT_STATE = "savedResultState";

    public final int ADVANCED_BLOCK = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);

        expression = (TextView) findViewById(R.id.expression);
        resultPreview = (TextView) findViewById(R.id.resultPreview);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        btnDelete.setOnLongClickListener(this);

        viewCalculatorResult();
    }

    private void viewCalculatorResult() {

        switch (Calculator.getCurrentResultState()) {
            case RESULT_OK:
                expression.setTextAppearance(getApplicationContext(), R.style.Text_Expression);
                resultPreview.setTextAppearance(getApplicationContext(), R.style.Text_ResultPreview);
                break;
            case RESULT_ERROR:
                resultPreview.setText(R.string.error);
                expression.setTextAppearance(getApplicationContext(), R.style.Text_Error_Expression);
                resultPreview.setTextAppearance(getApplicationContext(), R.style.Text_Error_ResultPreview);
                break;
            default:
                return;
        }

        resultPreview.setText(Calculator.getResultPreview());
        expression.setText(Calculator.getExpression());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(EXPRESSION, Calculator.getExpression());
        outState.putString(RESULT_STATE, Calculator.getCurrentResultState().name());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Calculator.setExpression(savedInstanceState.getString(EXPRESSION));
        Calculator.setCurrentResultState(savedInstanceState.getString(RESULT_STATE));

        if (Calculator.getCurrentResultState() == CalculatorResult.RESULT_ERROR) {
            Calculator.getInstance().setResultPreview(getString(R.string.error));
        }

        viewCalculatorResult();
    }

    public void onCalculatorButtonClick(View view) {

        Calculator.processButtonClick(view.getId(), this);

        viewCalculatorResult();
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.btnDelete:
                Calculator.setExpression("");
                viewCalculatorResult();
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_basic, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_advanced) {
            Intent intent = new Intent(this, AdvancedCalculatorActivity.class);
            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(EXPRESSION, Calculator.getExpression());
            intent.putExtra(RESULT_STATE, Calculator.getCurrentResultState().name());
            startActivityForResult(intent, ADVANCED_BLOCK);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            if (requestCode == ADVANCED_BLOCK){
                Calculator.setExpression(data.getStringExtra(EXPRESSION));
                Calculator.setCurrentResultState(data.getStringExtra(RESULT_STATE));

                if (Calculator.getCurrentResultState() == CalculatorResult.RESULT_ERROR) {
                    Calculator.getInstance().setResultPreview(getString(R.string.error));
                }

                viewCalculatorResult();
            }
            else{
                Toast.makeText(this, R.string.unknown_request, Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, R.string.bad_result, Toast.LENGTH_SHORT).show();
        }
    }
}
