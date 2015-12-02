package com.kinpa200296.android.labs.todolistmk2;

import android.content.Intent;
import android.os.Bundle;

public class ToDoLoader {

    public static final String ARG_ID = "argToDoId";
    public static final String ARG_TITLE = "argToDoTitle";
    public static final String ARG_DESCRIPTION = "argToDoDescription";
    public static final String ARG_IS_DONE = "argToDoIsDone";
    public static final String ARG_YEAR = "argToDoYear";
    public static final String ARG_MONTH = "argToDoMonth";
    public static final String ARG_DAY = "argToDoDay";
    public static final String ARG_HOUR = "argToDoHour";
    public static final String ARG_MINUTE = "argToDoMinute";

    public static ToDo loadFromBundle(Bundle bundle){
        ToDo toDo = ToDo.getNotInitializedToDo();
        toDo.setId(bundle.getLong(ARG_ID));
        toDo.setTitle(bundle.getString(ARG_TITLE));
        toDo.setDescription(bundle.getString(ARG_DESCRIPTION));
        toDo.setDone(bundle.getBoolean(ARG_IS_DONE));
        toDo.setYear(bundle.getInt(ARG_YEAR));
        toDo.setMonth(bundle.getInt(ARG_MONTH));
        toDo.setDay(bundle.getInt(ARG_DAY));
        toDo.setHour(bundle.getInt(ARG_HOUR));
        toDo.setMinute(bundle.getInt(ARG_MINUTE));
        return toDo;
    }

    public static void writeToBundle(Bundle bundle, ToDo toDo){
        bundle.putLong(ARG_ID, toDo.getId());
        bundle.putString(ARG_TITLE, toDo.getTitle());
        bundle.putString(ARG_DESCRIPTION, toDo.getDescription());
        bundle.putBoolean(ARG_IS_DONE, toDo.isDone());
        bundle.putInt(ARG_YEAR, toDo.getYear());
        bundle.putInt(ARG_MONTH, toDo.getMonth());
        bundle.putInt(ARG_DAY, toDo.getDay());
        bundle.putInt(ARG_HOUR, toDo.getHour());
        bundle.putInt(ARG_MINUTE, toDo.getMinute());
    }

    public static ToDo loadFromExtras(Intent intent){
        ToDo toDo = ToDo.getNotInitializedToDo();
        toDo.setId(intent.getLongExtra(ARG_ID, -1));
        toDo.setTitle(intent.getStringExtra(ARG_TITLE));
        toDo.setDescription(intent.getStringExtra(ARG_DESCRIPTION));
        toDo.setDone(intent.getBooleanExtra(ARG_IS_DONE, false));
        toDo.setYear(intent.getIntExtra(ARG_YEAR, 0));
        toDo.setMonth(intent.getIntExtra(ARG_MONTH, 0));
        toDo.setDay(intent.getIntExtra(ARG_DAY, 0));
        toDo.setHour(intent.getIntExtra(ARG_HOUR, 0));
        toDo.setMinute(intent.getIntExtra(ARG_MINUTE, 0));
        return toDo;
    }

    public static void writeToExtras(Intent intent, ToDo toDo){
        intent.putExtra(ARG_ID, toDo.getId());
        intent.putExtra(ARG_TITLE, toDo.getTitle());
        intent.putExtra(ARG_DESCRIPTION, toDo.getDescription());
        intent.putExtra(ARG_IS_DONE, toDo.isDone());
        intent.putExtra(ARG_YEAR, toDo.getYear());
        intent.putExtra(ARG_MONTH, toDo.getMonth());
        intent.putExtra(ARG_DAY, toDo.getDay());
        intent.putExtra(ARG_HOUR, toDo.getHour());
        intent.putExtra(ARG_MINUTE, toDo.getMinute());
    }
}
