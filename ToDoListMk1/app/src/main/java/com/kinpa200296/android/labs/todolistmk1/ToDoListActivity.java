package com.kinpa200296.android.labs.todolistmk1;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Calendar;


public class ToDoListActivity extends Activity
        implements ToDoListFragment.Callback, ToDoCreateFragment.Callback, ToDoEditFragment.Callback {

    private static final int DIALOG_TIME = 1;
    private static final int DIALOG_DATE = 2;

    private static final int ACTIVITY_EDIT = 1;
    private static final int ACTIVITY_CREATE = 2;

    private static final String IS_PREVIOUS_TWO_PANE = "isPreviousTwoPane";

    private ToDoListFragment listFragment;
    private Fragment currentFragment;
    private boolean isTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        listFragment = (ToDoListFragment) getFragmentManager().findFragmentById(R.id.todo_list);

        registerForContextMenu(listFragment.getListView());

        if (findViewById(R.id.todo_container) != null) {
            isTwoPane = true;

            listFragment.setActivateOnItemClick(true);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(IS_PREVIOUS_TWO_PANE, isTwoPane);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (!savedInstanceState.getBoolean(IS_PREVIOUS_TWO_PANE) && isTwoPane) {
            listFragment.setActivatedPosition(ListView.INVALID_POSITION);
            View fragmentView = findViewById(R.id.todo_container).findViewById(R.id.todo_fragment);
            if (fragmentView != null) {
                fragmentView.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void onItemSelected(ToDo toDo) {
        if (isTwoPane) {
            Bundle arguments = new Bundle();
            ToDoLoader.writeToBundle(arguments, toDo);
            ToDoDetailFragment fragment = new ToDoDetailFragment();
            currentFragment = fragment;

            fragment.setArguments(arguments);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.todo_container, fragment);
            transaction.commit();
        } else {
            Intent detailIntent = new Intent(this, ToDoDetailActivity.class);
            ToDoLoader.writeToExtras(detailIntent, toDo);
            startActivity(detailIntent);
        }
    }

    public void onChangeToDoState(View view) {
        int index = (int) view.getTag();
        ToDo toDo = listFragment.repository.getItem(index);
        toDo.setDone(!toDo.isDone());
        listFragment.repository.update(index, toDo);
        if (isTwoPane && listFragment.activatedPosition == index) {
            onItemSelected(toDo);
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

        if (id == R.id.action_add_item) {
            if (isTwoPane) {
                listFragment.setActivatedPosition(ListView.INVALID_POSITION);
                ToDoCreateFragment fragment = new ToDoCreateFragment();
                currentFragment = fragment;

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.todo_container, fragment);
                transaction.commit();
            } else {
                Intent createIntent = new Intent(this, ToDoCreateActivity.class);
                startActivityForResult(createIntent, ACTIVITY_CREATE);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == listFragment.getListView().getId()) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            ToDo toDo = listFragment.repository.getItem(info.position);
            getMenuInflater().inflate(R.menu.menu_item_context, menu);
            menu.setHeaderTitle(toDo.getTitle());
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.action_edit_item:
                ToDo toDo = listFragment.repository.getItem(info.position);
                if (isTwoPane) {
                    listFragment.setActivatedPosition(info.position);
                    Bundle arguments = new Bundle();
                    arguments.putInt(ToDoEditFragment.ARG_INDEX, info.position);
                    ToDoLoader.writeToBundle(arguments, toDo);
                    ToDoEditFragment fragment = new ToDoEditFragment();
                    currentFragment = fragment;

                    fragment.setArguments(arguments);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.todo_container, fragment);
                    transaction.commit();
                } else {
                    Intent editIntent = new Intent(this, ToDoEditActivity.class);
                    editIntent.putExtra(ToDoEditFragment.ARG_INDEX, info.position);
                    ToDoLoader.writeToExtras(editIntent, toDo);
                    startActivityForResult(editIntent, ACTIVITY_EDIT);
                }
                break;
            case R.id.action_remove_item:
                listFragment.repository.remove(info.position);
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ACTIVITY_CREATE:
                if (resultCode == RESULT_OK) {
                    ToDo toDo = ToDoLoader.loadFromExtras(data);
                    listFragment.repository.add(toDo);
                }
                break;
            case ACTIVITY_EDIT:
                if (resultCode == RESULT_OK) {
                    ToDo newToDo = ToDoLoader.loadFromExtras(data);
                    int index = data.getIntExtra(ToDoEditFragment.ARG_INDEX, -1);
                    listFragment.repository.update(index, newToDo);
                }
                break;
        }
    }

    @Override
    public void addToDo(ToDo toDo) {
        listFragment.repository.add(toDo);
        listFragment.setActivatedPosition(listFragment.repository.getAdapter().getCount() - 1);
        onItemSelected(toDo);
    }

    @Override
    public void updateToDo(int index, ToDo newToDo) {
        listFragment.repository.update(index, newToDo);
        listFragment.setActivatedPosition(index);
        onItemSelected(listFragment.repository.getItem(index));
    }

    @Override
    public void cancelAction() {
        getFragmentManager().beginTransaction().remove(currentFragment).commit();
        listFragment.setActivatedPosition(ListView.INVALID_POSITION);
        currentFragment = null;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Calendar calendar = Calendar.getInstance();
        switch (id) {
            case DIALOG_TIME:
                return new TimePickerDialog(this, (TimePickerDialog.OnTimeSetListener) currentFragment,
                        calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
            case DIALOG_DATE:
                return new DatePickerDialog(this, (DatePickerDialog.OnDateSetListener) currentFragment,
                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
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
