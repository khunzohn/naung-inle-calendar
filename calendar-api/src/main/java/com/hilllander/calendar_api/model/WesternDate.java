/*
 * Copyright (c) The code is developed  by Hilllander.
 * You can modify or use some or the whole piece of code with no limitation.
 * Feel free to do whatever you favour.Good luck!
 *
 * Hilllander team.
 */

package com.hilllander.calendar_api.model;

/**
 * Store western date detail
 * Created by khunzohn on 10/19/15.
 */
public class WesternDate {
    private static WesternDate date;
    private int year;
    private int month;
    private int day;
    private int h;
    private int m;
    private int s;

    private WesternDate() {
        //prevent initializing by constructor
    }

    /**
     * build western date to start storing its details
     *
     * @return
     */
    public static WesternDate buildDate() {
        if (date == null)
            date = new WesternDate();
        return date;
    }

    /**
     * set hour
     *
     * @param h hour
     * @return itself for method chaining
     */
    public WesternDate setHour(int h) {
        this.h = h;
        return this;
    }

    /**
     * set minute
     *
     * @param m minute
     * @return itself for method chaining
     */
    public WesternDate setMin(int m) {
        this.m = m;
        return this;
    }

    /**
     * set second
     *
     * @param s second
     * @return itself for method chaining
     */
    public WesternDate setSec(int s) {
        this.s = s;
        return this;
    }

    /**
     * get western year
     *
     * @return year
     */
    public int getYear() {
        return year;
    }

    /**
     * set western year
     *
     * @param year year
     * @return itself for method chaining
     */
    public WesternDate setYear(int year) {
        this.year = year;
        return this;
    }

    /**
     * get western month
     *
     * @return month
     */
    public int getMonth() {
        return month;
    }

    /**
     * set western month
     *
     * @param month month
     * @return itself for method chaining
     */
    public WesternDate setMonth(int month) {
        this.month = month;
        return this;
    }

    /**
     * get western day
     *
     * @return day
     */
    public int getDay() {
        return day;
    }

    /**
     * set western day
     *
     * @param day day
     * @return itself for method chaining
     */
    public WesternDate setDay(int day) {
        this.day = day;
        return this;
    }

    /**
     * get hour
     *
     * @return hour
     */
    public int getH() {
        return h;
    }

    /**
     * get minute
     *
     * @return minute
     */
    public int getM() {
        return m;
    }

    /**
     * get second
     *
     * @return second
     */
    public int getS() {
        return s;
    }
}
