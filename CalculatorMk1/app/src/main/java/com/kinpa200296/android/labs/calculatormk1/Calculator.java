package com.kinpa200296.android.labs.calculatormk1;

import android.content.Context;
import android.util.Log;

import org.nfunk.jep.JEP;

import java.util.Locale;

/**
 * Created by Pavel on 11/17/2015.
 */
public class Calculator {

    private String expression, resultPreview;
    private JEP parser;
    private CalculatorResult currentResultState;

    private static Calculator instance = new Calculator();

    public static Calculator getInstance() {
        return instance;
    }

    private Calculator() {
        parser = new JEP();
        parser.addStandardFunctions();
        parser.addStandardConstants();
        parser.addConstant("Infinity", Double.POSITIVE_INFINITY);
        parser.addConstant("-Infinity", Double.NEGATIVE_INFINITY);
        expression = "";
        resultPreview = "";
    }

    public double CalculateExpression() {
        parser.parseExpression(expression);
        return parser.getValue();
    }

    public void updateResultPreview() {
        double res = instance.CalculateExpression();
        if (!Double.isNaN(res)) {
            instance.resultPreview = String.format(Locale.ENGLISH, "%.9g", res);
            currentResultState = CalculatorResult.RESULT_OK;
        } else {
            instance.resultPreview = "";
            if (expression.equals("")) {
                currentResultState = CalculatorResult.RESULT_OK;
            } else {
                currentResultState = CalculatorResult.RESULT_ERROR;
            }
        }
    }

    public boolean setResultPreview(String resultPreview) {
        if (resultPreview == null) {
            return false;
        }
        this.resultPreview = resultPreview;
        return true;
    }

    public static CalculatorResult getCurrentResultState() {
        return instance.currentResultState;
    }

    public static boolean setCurrentResultState(String currentResultState) {
        if (currentResultState == null) {
            return false;
        }
        instance.currentResultState = CalculatorResult.valueOf(currentResultState);
        return true;
    }

    public static String getExpression() {
        return instance.expression;
    }

    public static String getResultPreview() {
        return instance.resultPreview;
    }

    public static boolean setExpression(String newExpression) {
        if (newExpression == null) {
            return false;
        }
        instance.expression = newExpression;
        instance.updateResultPreview();
        return true;
    }

    public static void processButtonClick(int viewId, Context context) {
        switch (viewId) {
            case R.id.btn0:
                instance.expression = instance.expression + context.getString(R.string.btn0);
                break;
            case R.id.btn1:
                instance.expression = instance.expression + context.getString(R.string.btn1);
                break;
            case R.id.btn2:
                instance.expression = instance.expression + context.getString(R.string.btn2);
                break;
            case R.id.btn3:
                instance.expression = instance.expression + context.getString(R.string.btn3);
                break;
            case R.id.btn4:
                instance.expression = instance.expression + context.getString(R.string.btn4);
                break;
            case R.id.btn5:
                instance.expression = instance.expression + context.getString(R.string.btn5);
                break;
            case R.id.btn6:
                instance.expression = instance.expression + context.getString(R.string.btn6);
                break;
            case R.id.btn7:
                instance.expression = instance.expression + context.getString(R.string.btn7);
                break;
            case R.id.btn8:
                instance.expression = instance.expression + context.getString(R.string.btn8);
                break;
            case R.id.btn9:
                instance.expression = instance.expression + context.getString(R.string.btn9);
                break;
            case R.id.btnSeparator:
                instance.expression = instance.expression + context.getString(R.string.btnSeparator);
                break;
            case R.id.btnCalculate:
                instance.updateResultPreview();
                if (instance.currentResultState == CalculatorResult.RESULT_OK) {
                    instance.expression = instance.resultPreview;
                    instance.resultPreview = "";
                } else {
                    instance.resultPreview = context.getString(R.string.error);
                }
                return;
            case R.id.btnDelete:
                if (instance.expression.length() != 0) {
                    instance.expression = instance.expression.substring(0, instance.expression.length() - 1);
                }
                break;
            case R.id.btnAdd:
                instance.expression = instance.expression + context.getString(R.string.btnAdd);
                break;
            case R.id.btnSub:
                instance.expression = instance.expression + context.getString(R.string.btnSub);
                break;
            case R.id.btnMul:
                instance.expression = instance.expression + context.getString(R.string.btnMul);
                break;
            case R.id.btnDiv:
                instance.expression = instance.expression + context.getString(R.string.btnDiv);
                break;
            default:
                Log.d(MainActivity.LOG_TAG, "An unknown view with id = " + viewId + " calls onCalculatorButtonClick()");
                return;
        }
        instance.updateResultPreview();
        instance.currentResultState = CalculatorResult.RESULT_OK;
    }
}
