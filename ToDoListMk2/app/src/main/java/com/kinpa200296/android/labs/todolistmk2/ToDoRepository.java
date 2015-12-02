package com.kinpa200296.android.labs.todolistmk2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Map;

public class ToDoRepository {

    private ArrayList<ToDo> data;
    private ArrayList<Map<String, Object>> dataMap;
    private ToDoListAdapter adapter;
    private ToDoDBHelper dbHelper;

    public ToDoRepository() {
        data = new ArrayList<>();
        adapter = null;
    }

    public void load(Context context) {
        data.clear();
        dataMap.clear();

        if (dbHelper != null) {
            dbHelper.close();
        }

        dbHelper = new ToDoDBHelper(context);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.query(ToDoDBHelper.TABLE, null, null, null, null, null, null);
        if (c.moveToFirst()) {
            int idColumnIndex = c.getColumnIndex(ToDoDBHelper.COLUMN_ID);
            int titleColumnIndex = c.getColumnIndex(ToDoDBHelper.COLUMN_TITLE);
            int descriptionColumnIndex = c.getColumnIndex(ToDoDBHelper.COLUMN_DESCRIPTION);
            int yearColumnIndex = c.getColumnIndex(ToDoDBHelper.COLUMN_YEAR);
            int monthColumnIndex = c.getColumnIndex(ToDoDBHelper.COLUMN_MONTH);
            int dayColumnIndex = c.getColumnIndex(ToDoDBHelper.COLUMN_DAY);
            int hourColumnIndex = c.getColumnIndex(ToDoDBHelper.COLUMN_HOUR);
            int minuteColumnIndex = c.getColumnIndex(ToDoDBHelper.COLUMN_MINUTE);
            int isDoneColumnIndex = c.getColumnIndex(ToDoDBHelper.COLUMN_IS_DONE);

            do {
                ToDo toDo = ToDo.getNotInitializedToDo();
                toDo.setId(c.getInt(idColumnIndex));
                toDo.setTitle(c.getString(titleColumnIndex));
                toDo.setDescription(c.getString(descriptionColumnIndex));
                toDo.setDone(c.getInt(isDoneColumnIndex) == 1);
                toDo.setYear(c.getInt(yearColumnIndex));
                toDo.setMonth(c.getInt(monthColumnIndex));
                toDo.setDay(c.getInt(dayColumnIndex));
                toDo.setHour(c.getInt(hourColumnIndex));
                toDo.setMinute(c.getInt(minuteColumnIndex));

                data.add(toDo);
                dataMap.add(toDo.toAdapterMap());
                adapter.notifyDataSetChanged();
            } while (c.moveToNext());
        } else {
            c.close();
        }
    }

    public void save() {
        dbHelper.close();
    }

    public ToDo getItem(int index) {
        return data.get(index);
    }

    public void add(ToDo toDo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ToDoDBHelper.COLUMN_TITLE, toDo.getTitle());
        contentValues.put(ToDoDBHelper.COLUMN_DESCRIPTION, toDo.getDescription());
        contentValues.put(ToDoDBHelper.COLUMN_IS_DONE, toDo.isDone() ? 1 : 0);
        contentValues.put(ToDoDBHelper.COLUMN_YEAR, toDo.getYear());
        contentValues.put(ToDoDBHelper.COLUMN_MONTH, toDo.getMonth());
        contentValues.put(ToDoDBHelper.COLUMN_DAY, toDo.getDay());
        contentValues.put(ToDoDBHelper.COLUMN_HOUR, toDo.getHour());
        contentValues.put(ToDoDBHelper.COLUMN_MINUTE, toDo.getMinute());
        long rowId = db.insert(ToDoDBHelper.TABLE, null, contentValues);
        toDo.setId(rowId);

        db.close();

        data.add(toDo);
        dataMap.add(toDo.toAdapterMap());
        adapter.notifyDataSetChanged();
    }

    public void remove(int index) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(ToDoDBHelper.TABLE, ToDoDBHelper.COLUMN_ID + " = ?",
                new String[]{"" + getItem(index).getId()});

        db.close();

        data.remove(index);
        dataMap.remove(index);
        adapter.notifyDataSetChanged();
    }

    public void update(int index, ToDo newToDo) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(ToDoDBHelper.COLUMN_TITLE, newToDo.getTitle());
        contentValues.put(ToDoDBHelper.COLUMN_DESCRIPTION, newToDo.getDescription());
        contentValues.put(ToDoDBHelper.COLUMN_IS_DONE, newToDo.isDone() ? 1 : 0);
        contentValues.put(ToDoDBHelper.COLUMN_YEAR, newToDo.getYear());
        contentValues.put(ToDoDBHelper.COLUMN_MONTH, newToDo.getMonth());
        contentValues.put(ToDoDBHelper.COLUMN_DAY, newToDo.getDay());
        contentValues.put(ToDoDBHelper.COLUMN_HOUR, newToDo.getHour());
        contentValues.put(ToDoDBHelper.COLUMN_MINUTE, newToDo.getMinute());

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.update(ToDoDBHelper.TABLE, contentValues, ToDoDBHelper.COLUMN_ID + " = ?",
                new String[]{"" + getItem(index).getId()});

        db.close();

        data.set(index, newToDo);
        dataMap.set(index, newToDo.toAdapterMap());
        adapter.notifyDataSetChanged();
    }

    public void createAdapter(Context context) {
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
