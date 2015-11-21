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

public class AdvancedCalculatorActivity extends Activity implements View.OnLongClickListener {

    TextView expression, resultPreview;
    Button btnDelete;

    public final String EXPRESSION = "savedExpression";
    public final String RESULT_STATE = "savedResultState";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced);

        expression = (TextView) findViewById(R.id.expression);
        resultPreview = (TextView) findViewById(R.id.resultPreview);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        btnDelete.setOnLongClickListener(this);

        Intent intent = getIntent();
        Calculator.setExpression(intent.getStringExtra(EXPRESSION));
        Calculator.setCurrentResultState(intent.getStringExtra(RESULT_STATE));

        if (Calculator.getCurrentResultState() == CalculatorResult.RESULT_ERROR) {
            Calculator.getInstance().setResultPreview(getString(R.string.error));
        }


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
        getMenuInflater().inflate(R.menu.menu_advanced, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_basic) {
            saveResult();
            finish();
            return true;
        }

        if (id == android.R.id.home){
            saveResult();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        saveResult();

        super.onBackPressed();
    }


    private void saveResult(){
        Intent intent = new Intent();
        intent.putExtra(EXPRESSION, Calculator.getExpression());
        intent.putExtra(RESULT_STATE, Calculator.getCurrentResultState().name());
        setResult(RESULT_OK, intent);
    }
}
