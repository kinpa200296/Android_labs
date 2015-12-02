package com.kinpa200296.android.labs.todolistmk1;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

public class ToDoEditFragment extends Fragment implements OnDateTimeSetListener {

    private ToDo toDo;
    private int index;
    private Callback callbackTarget = dummyCallbackTarget;

    private EditText toDoTitle, toDoDescription;
    private TextView toDoTime, toDoDate;
    private Button btnSave, btnCancel;

    public static final String ARG_INDEX = "argToDoIndex";

    public interface Callback {
        void updateToDo(int index, ToDo newToDo);

        void cancelAction();
    }

    private static Callback dummyCallbackTarget = new Callback() {
        @Override
        public void updateToDo(int index, ToDo newToDo) {

        }

        @Override
        public void cancelAction() {

        }
    };

    public ToDoEditFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ToDoLoader.ARG_TITLE)) {
            toDo = ToDoLoader.loadFromBundle(getArguments());
            index = getArguments().getInt(ARG_INDEX);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_todo_edit, container, false);

        toDoTitle = (EditText) rootView.findViewById(R.id.toDoTitle);
        toDoDescription = (EditText) rootView.findViewById(R.id.toDoDescription);
        toDoTime = (TextView) rootView.findViewById(R.id.toDoTime);
        toDoDate = (TextView) rootView.findViewById(R.id.toDoDate);
        btnSave = (Button) rootView.findViewById(R.id.btnSave);
        btnCancel = (Button) rootView.findViewById(R.id.btnCancel);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btnSave:
                        toDo.setTitle(toDoTitle.getText().toString());
                        toDo.setDescription(toDoDescription.getText().toString());
                        callbackTarget.updateToDo(index, toDo);
                        break;
                    case R.id.btnCancel:
                        callbackTarget.cancelAction();
                }
            }
        };

        btnSave.setOnClickListener(listener);
        btnCancel.setOnClickListener(listener);

        if (toDo != null) {
            toDoTitle.setText(toDo.getTitle());
            toDoDescription.setText(toDo.getDescription());
            toDoTime.setText(toDo.getTime());
            toDoDate.setText(toDo.getDate());
        }

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (!(activity instanceof Callback)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        callbackTarget = (Callback) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        callbackTarget = dummyCallbackTarget;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        toDo.setYear(year);
        toDo.setMonth(monthOfYear);
        toDo.setDay(dayOfMonth);

        toDoDate.setText(toDo.getDate());
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        toDo.setHour(hourOfDay);
        toDo.setMinute(minute);

        toDoTime.setText(toDo.getTime());
    }
}
