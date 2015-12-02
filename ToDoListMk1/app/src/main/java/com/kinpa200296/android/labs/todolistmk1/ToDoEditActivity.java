package com.kinpa200296.android.labs.todolistmk1;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Calendar;

public class ToDoEditActivity extends Activity implements ToDoEditFragment.Callback {

    private static final int DIALOG_TIME = 1;
    private static final int DIALOG_DATE = 2;

    private ToDoEditFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_edit);

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putInt(ToDoEditFragment.ARG_INDEX, getIntent().getIntExtra(ToDoEditFragment.ARG_INDEX, -1));
            ToDoLoader.writeToBundle(arguments, ToDoLoader.loadFromExtras(getIntent()));

            fragment = new ToDoEditFragment();
            fragment.setArguments(arguments);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.todo_edit_container, fragment);
            transaction.commit();
        }
    }

    @Override
    public void updateToDo(int index, ToDo newToDo) {
        Intent intent = new Intent();
        intent.putExtra(ToDoEditFragment.ARG_INDEX, index);
        ToDoLoader.writeToExtras(intent, newToDo);
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
