package com.kinpa200296.android.labs.todolistmk2;

import java.util.HashMap;
import java.util.Map;

public class ToDo {

    private long id;
    private String title;
    private String description;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private boolean done;

    public static final String ATTRIBUTE_NAME_TITLE = "toDoTitle";
    public static final String ATTRIBUTE_NAME_DESCRIPTION = "toDoDescription";
    public static final String ATTRIBUTE_NAME_TIME = "toDoTime";
    public static final String ATTRIBUTE_NAME_DATE = "toDoDate";
    public static final String ATTRIBUTE_NAME_IS_DONE = "toDoIsDone";

    public Map<String, Object> toAdapterMap(){
        Map<String, Object> map = new HashMap<>();
        map.put(ATTRIBUTE_NAME_TITLE, getTitle());
        map.put(ATTRIBUTE_NAME_DESCRIPTION, getDescription());
        map.put(ATTRIBUTE_NAME_TIME, getTime());
        map.put(ATTRIBUTE_NAME_DATE, getDate());
        map.put(ATTRIBUTE_NAME_IS_DONE, isDone());
        return map;
    }

    public static ToDo getNotInitializedToDo(){
        return new ToDo();
    }

    private ToDo(){}

    public ToDo(String title, String description, int year, int month, int day, int hour, int minute) {
        this.title = title;
        this.description = description;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.done = false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getTime() {
        return String.format("%02d:%02d",getHour(), getMinute());
    }

    public String getDate() {
        return String.format("%02d.%02d.%04d", getDay(), getMonth(), getYear());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
