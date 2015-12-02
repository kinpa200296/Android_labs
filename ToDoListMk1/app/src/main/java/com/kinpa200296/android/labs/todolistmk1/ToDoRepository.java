package com.kinpa200296.android.labs.todolistmk1;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Map;

public class ToDoRepository {

    public static final String PREFERENCES_NAME = "ToDoList";
    public static final String PREFERENCES_TODO_COUNT = "ToDoCount";

    private ArrayList<ToDo> data;
    private ArrayList<Map<String, Object>> dataMap;
    private ToDoListAdapter adapter;

    public ToDoRepository() {
        data = new ArrayList<>();
        adapter = null;
    }

    public void load(SharedPreferences preferences) {
        data.clear();
        dataMap.clear();

        int count = preferences.getInt(PREFERENCES_TODO_COUNT, 0);
        for (int i = 0; i < count; i++){
            add(ToDoLoader.loadFromPreferences(preferences, i));
        }

        /*add(new ToDo("ToDo 1", "Description 1", 1, 1, 1, 1, 1));
        add(new ToDo("ToDo 2", "Description 2", 2, 2, 2, 2, 2));
        add(new ToDo("ToDo 3", "Description 3", 3, 3, 3, 3, 3));
        add(new ToDo("ToDo 4", "Description 4", 4, 4, 4, 4, 4));
        add(new ToDo("ToDo 5", "Description 5", 5, 5, 5, 5, 5));
        add(new ToDo("ToDo 6", "Description 6", 6, 6, 6, 6, 6));*/
    }

    public void save(SharedPreferences preferences) {
        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt(PREFERENCES_TODO_COUNT, data.size());
        for (int i = 0; i < data.size(); i++)
        {
            ToDoLoader.writeToPreferences(editor, i, data.get(i));
        }

        editor.apply();
    }

    public ToDo getItem(int index){
        return data.get(index);
    }

    public void add(ToDo toDo) {
        data.add(toDo);
        dataMap.add(toDo.toAdapterMap());
        adapter.notifyDataSetChanged();
    }

    public void remove(int index) {
        data.remove(index);
        dataMap.remove(index);
        adapter.notifyDataSetChanged();
    }

    public void update(int index, ToDo newToDo) {
        data.set(index, newToDo);
        dataMap.set(index, newToDo.toAdapterMap());
        adapter.notifyDataSetChanged();
    }

    public void createAdapter(Context context){
        dataMap = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            dataMap.add(data.get(i).toAdapterMap());
        }
        String[] from = {ToDo.ATTRIBUTE_NAME_TITLE, ToDo.ATTRIBUTE_NAME_DESCRIPTION,
                ToDo.ATTRIBUTE_NAME_TIME, ToDo.ATTRIBUTE_NAME_DATE, ToDo.ATTRIBUTE_NAME_IS_DONE};
        int[] to = {R.id.toDoTitle, R.id.toDoDescription, R.id.toDoTime,
                R.id.toDoDate, R.id.toDoState};
        adapter = new ToDoListAdapter(context, dataMap, R.layout.todo_list_item, from, to);
    }

    public ToDoListAdapter getAdapter() {
        return adapter;
    }
}
