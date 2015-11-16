package com.hilllander.calendar_api.model;

/**
 * Created by khunzohn on 11/16/15.
 */
public class EngSDaysBundle {
    private String name;
    private int year;
    private int month;
    private int day;

    public EngSDaysBundle(String name, int year, int month, int day) {
        this.name = name;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getDay() {
        return day;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }
}
