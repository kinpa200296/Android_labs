package com.kinpa200296.android.labs.todolistmk1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class ToDoLoader {

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
        intent.putExtra(ARG_TITLE, toDo.getTitle());
        intent.putExtra(ARG_DESCRIPTION, toDo.getDescription());
        intent.putExtra(ARG_IS_DONE, toDo.isDone());
        intent.putExtra(ARG_YEAR, toDo.getYear());
        intent.putExtra(ARG_MONTH, toDo.getMonth());
        intent.putExtra(ARG_DAY, toDo.getDay());
        intent.putExtra(ARG_HOUR, toDo.getHour());
        intent.putExtra(ARG_MINUTE, toDo.getMinute());
    }

    public static ToDo loadFromPreferences(SharedPreferences preferences, int index){
        ToDo toDo = ToDo.getNotInitializedToDo();
        toDo.setTitle(preferences.getString(ARG_TITLE + index, "No Title"));
        toDo.setDescription(preferences.getString(ARG_DESCRIPTION + index, "No Description"));
        toDo.setDone(preferences.getBoolean(ARG_IS_DONE + index, false));
        toDo.setYear(preferences.getInt(ARG_YEAR + index, 0));
        toDo.setMonth(preferences.getInt(ARG_MONTH + index, 0));
        toDo.setDay(preferences.getInt(ARG_DAY + index, 0));
        toDo.setHour(preferences.getInt(ARG_HOUR + index, 0));
        toDo.setMinute(preferences.getInt(ARG_MINUTE + index, 0));
        return toDo;
    }

    public static void writeToPreferences(SharedPreferences.Editor editor, int index, ToDo toDo){
        editor.putString(ARG_TITLE + index, toDo.getTitle());
        editor.putString(ARG_DESCRIPTION + index, toDo.getDescription());
        editor.putBoolean(ARG_IS_DONE + index, toDo.isDone());
        editor.putInt(ARG_YEAR + index, toDo.getYear());
        editor.putInt(ARG_MONTH + index, toDo.getMonth());
        editor.putInt(ARG_DAY + index, toDo.getDay());
        editor.putInt(ARG_HOUR + index, toDo.getHour());
        editor.putInt(ARG_MINUTE + index, toDo.getMinute());
    }
}
