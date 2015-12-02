package com.kinpa200296.android.labs.todolistmk2;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class ToDoListFragment extends ListFragment {

    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    private Callback callbackTarget = dummyCallbackTarget;

    public int activatedPosition = ListView.INVALID_POSITION;

    public ToDoRepository repository = new ToDoRepository();

    public interface Callback {
        void onItemSelected(ToDo toDo);
    }

    private static Callback dummyCallbackTarget = new Callback() {
        @Override
        public void onItemSelected(ToDo toDo) {
        }
    };

    public ToDoListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        repository.load(getActivity());
        repository.getAdapter().setViewBinder(new ToDoViewBinder());
        setListAdapter(repository.getAdapter());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        repository.createAdapter(activity);

        if (!(activity instanceof Callback)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        callbackTarget = (Callback) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        callbackTarget = dummyCallbackTarget;

        repository.save();
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);

        callbackTarget.onItemSelected(repository.getItem(position));

        setActivatedPosition(position);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (activatedPosition != ListView.INVALID_POSITION) {
            outState.putInt(STATE_ACTIVATED_POSITION, activatedPosition);
        }
    }

    public void setActivateOnItemClick(boolean activateOnItemClick) {
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    public void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(activatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        activatedPosition = position;
    }
}
