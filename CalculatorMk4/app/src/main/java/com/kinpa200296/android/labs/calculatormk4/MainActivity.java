package com.kinpa200296.android.labs.calculatormk4;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements View.OnLongClickListener {

    TextView expression, resultPreview;
    Button btnDelete;

    public final String EXPRESSION = "savedExpression";
    public final String RESULT_STATE = "savedResultState";
    public final String CURRENT_BLOCK_ID = "savedCurrentBlockId";

    public static final String LOG_TAG = "CalculatorMk3";

    int currentBlockId;

    Fragment basicBlockFragment, advancedBlockFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expression = (TextView) findViewById(R.id.expression);
        resultPreview = (TextView) findViewById(R.id.resultPreview);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        btnDelete.setOnLongClickListener(this);

        basicBlockFragment = CalculatorBlockFragment.newInstance(CalculatorBlockFragment.basicBlockId);
        advancedBlockFragment = CalculatorBlockFragment.newInstance(CalculatorBlockFragment.advancedBlockId);

        if (savedInstanceState == null) {
            currentBlockId = CalculatorBlockFragment.basicBlockId;
            getSupportFragmentManager().beginTransaction().add(R.id.blockContainer, basicBlockFragment).commit();
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

        outState.putInt(CURRENT_BLOCK_ID, currentBlockId);
        outState.putString(EXPRESSION, Calculator.getExpression());
        outState.putString(RESULT_STATE, Calculator.getCurrentResultState().name());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        currentBlockId = savedInstanceState.getInt(CURRENT_BLOCK_ID);
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_change_block) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            switch (currentBlockId) {
                case CalculatorBlockFragment.basicBlockId:
                    currentBlockId = CalculatorBlockFragment.advancedBlockId;
                    fragmentTransaction.remove(basicBlockFragment);
                    fragmentTransaction.add(R.id.blockContainer, advancedBlockFragment);
                    item.setIcon(R.drawable.calculator_basic);
                    item.setTitle(R.string.title_basic_block);
                    break;
                case CalculatorBlockFragment.advancedBlockId:
                    currentBlockId = CalculatorBlockFragment.basicBlockId;
                    fragmentTransaction.remove(advancedBlockFragment);
                    fragmentTransaction.add(R.id.blockContainer, basicBlockFragment);
                    item.setIcon(R.drawable.calculator_advanced);
                    item.setTitle(R.string.title_advanced_block);
                    break;
                default:
                    Log.d(LOG_TAG, "Setting Calculator Block: currentBlockId = " + String.valueOf(currentBlockId) + " is invalid");
                    break;
            }
            fragmentTransaction.commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
