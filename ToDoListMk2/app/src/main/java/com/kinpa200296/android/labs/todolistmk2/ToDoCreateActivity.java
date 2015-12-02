package com.kinpa200296.android.labs.todolistmk2;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Calendar;

public class ToDoCreateActivity extends Activity implements ToDoCreateFragment.Callback {

    private static final int DIALOG_TIME = 1;
    private static final int DIALOG_DATE = 2;

    private ToDoCreateFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_create);

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            ToDoLoader.writeToBundle(arguments, ToDoLoader.loadFromExtras(getIntent()));

            fragment = new ToDoCreateFragment();
            fragment.setArguments(arguments);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.todo_create_container, fragment);
            transaction.commit();
        }
        else{
            fragment = (ToDoCreateFragment) getFragmentManager().findFragmentById(R.id.todo_create_container);
        }
    }

    @Override
    public void addToDo(ToDo toDo) {
        Intent intent = new Intent();
        ToDoLoader.writeToExtras(intent, toDo);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void cancelAction() {
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Calendar calendar = Calendar.getInstance();
        switch (id){
            case DIALOG_TIME:
                return new TimePickerDialog(this, fragment, calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE), true);
            case DIALOG_DATE:
                return new DatePickerDialog(this, fragment, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            default:
                return super.onCreateDialog(id);
        }
    }

    public void OnPickTime(View view) {
        if (view.getId() == R.id.toDoTime) {
            removeDialog(DIALOG_TIME);
            showDialog(DIALOG_TIME);
        }
    }

    public void OnPickDate(View view) {
        if (view.getId() == R.id.toDoDate) {
            removeDialog(DIALOG_DATE);
            showDialog(DIALOG_DATE);
        }
    }
}
