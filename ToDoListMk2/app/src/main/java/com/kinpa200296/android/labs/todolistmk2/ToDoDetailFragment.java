package com.kinpa200296.android.labs.todolistmk2;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ToDoDetailFragment extends Fragment {

    private ToDo toDo;

    public ToDoDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ToDoLoader.ARG_TITLE)) {
            toDo = ToDoLoader.loadFromBundle(getArguments());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_todo_detail, container, false);


        if (toDo != null) {
            ((TextView) rootView.findViewById(R.id.toDoTitle)).setText(toDo.getTitle());
            ((TextView) rootView.findViewById(R.id.toDoDescription)).setText(toDo.getDescription());
            ((TextView) rootView.findViewById(R.id.toDoTime)).setText(toDo.getTime());
            ((TextView) rootView.findViewById(R.id.toDoDate)).setText(toDo.getDate());
            if (toDo.isDone()) {
                ((TextView) rootView.findViewById(R.id.toDoState)).setText(R.string.stateDone);
            } else {
                ((TextView) rootView.findViewById(R.id.toDoState)).setText(R.string.stateNotDone);
            }
        }

        return rootView;
    }
}
