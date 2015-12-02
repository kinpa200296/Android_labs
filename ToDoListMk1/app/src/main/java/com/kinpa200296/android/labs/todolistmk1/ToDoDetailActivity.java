package com.kinpa200296.android.labs.todolistmk1;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class ToDoDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_detail);

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            ToDoLoader.writeToBundle(arguments, ToDoLoader.loadFromExtras(getIntent()));

            ToDoDetailFragment fragment = new ToDoDetailFragment();
            fragment.setArguments(arguments);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.todo_container, fragment);
            transaction.commit();
        }
    }
}
