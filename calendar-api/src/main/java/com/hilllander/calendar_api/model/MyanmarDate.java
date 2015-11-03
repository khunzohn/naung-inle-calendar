/*
 * Copyright (c) The code is developed  by Hilllander. You can modify or use some or the whole piece of code with no limitation. Feel free to do whatever you favour.Good luck!Hilllander team.
 */

package com.hilllander.calendar_api.model;

/**
 * Store Myanmar date detail.
 * Created by khunzohn on 10/24/15.
 */
public class MyanmarDate {
    private static MyanmarDate date;
    private int year;
    private int month;
    private int day;
    private int d;
    private int yearType;
    private int yearLength;
    private int monthType;
    private int monthLength;
    private int monthStatus;
    private int weekday;

    private MyanmarDate() {
        //prevent initializing by constructor
    }

    /**
     * build a date container to store
     * myanmar date detail by its object's chains
     *
     * @return its static instance
     */
    public static MyanmarDate buildDate() {
        if (date == null)
            date = new MyanmarDate();
        return date;
    }

    /**
     * get myanmar month
     *
     * @return month [sec_waso=0,Tagu=1, ... , Tabaung=12]
     */
    public int getMonth() {
        return month;
    }

    /**
     * set myanmar month
     *
     * @param month month [sec_waso=0,Tagu=1, ... , Tabaung=12]
     * @return itself for method chaining
     */
    public MyanmarDate setMonth(int month) {
        this.month = month;
        return this;
    }

    /**
     * get myanmar year
     *
     * @return myanmar year
     */
    public int getYear() {
        return year;
    }

    /**
     * set myanmar year
     *
     * @param year myanmar year
     * @return itself for method chaining
     */
    public MyanmarDate setYear(int year) {
        this.year = year;
        return this;
    }

    /**
     * get myanmar day
     *
     * @return myanmar day [0-30]
     */
    public int getDay() {
        return day;
    }

    /**
     * set myanmar day
     *
     * @param day myanmar day [0-30]
     * @return itself for method chaining
     */
    public MyanmarDate setDay(int day) {
        this.day = day;
        return this;
    }

    /**
     * get waning or waxing day
     *
     * @return waning or waxing day
     */
    public int getWanWaxDay() {
        return d;
    }

    /**
     * set waning or waxing day
     *
     * @param d waning or waxing day [0-14||15]
     * @return itself for method chaining
     */
    public MyanmarDate setWanWaxDay(int d) {
        this.d = d;
        return this;
    }

    /**
     * get myanmar year type
     *
     * @return year type [0=normal, 1=small watat, 2=big watat]
     */
    public int getYearType() {
        return yearType;
    }

    /**
     * set myanmar year type
     *
     * @param yearType year type [0=normal, 1=small watat, 2=big watat]
     * @return itself for method chaining
     */
    public MyanmarDate setYearType(int yearType) {
        this.yearType = yearType;
        return this;
    }

    /**
     * get myanmar year length
     *
     * @return year length
     */
    public int getYearLength() {
        return yearLength;
    }

    /**
     * set myanmar year length
     *
     * @param yearLength [normal=354, small watat=384, big watat=385]
     * @return itself for method chaining
     */
    public MyanmarDate setYearLength(int yearLength) {
        this.yearLength = yearLength;
        return this;
    }

    /**
     * get myanmar month type
     *
     * @return month type [1=hnaung, 0= Oo]
     */
    public int getMonthType() {
        return monthType;
    }

    /**
     * set myanmar month type
     *
     * @param monthType month type [[1=hnaung, 0= Oo]
     * @return itself for method chaining
     */
    public MyanmarDate setMonthType(int monthType) {
        this.monthType = monthType;
        return this;
    }

    /**
     * get myanmar month length
     *
     * @return month length [29||30]
     */
    public int getMonthLength() {
        return monthLength;
    }

    /**
     * set myanmar month length
     *
     * @param monthLength month length [29||30]
     * @return itself for method chaining
     */
    public MyanmarDate setMonthLength(int monthLength) {
        this.monthLength = monthLength;
        return this;
    }

    /**
     * get myanmar month status
     *
     * @return month status [0: waxing, 1: full moon, 2: waning, 3: new moon]
     */
    public int getMonthStatus() {
        return monthStatus;
    }

    /**
     * set myanmar month status
     *
     * @param monthStatus month status [0: waxing, 1: full moon, 2: waning, 3: new moon]
     * @return itself for method chaining
     */
    public MyanmarDate setMonthStatus(int monthStatus) {
        this.monthStatus = monthStatus;
        return this;
    }

    /**
     * get weekday
     *
     * @return weekday [sun=1, ... ,sat=0]
     */
    public int getWeekday() {
        return weekday;
    }

    /**
     * set weekday
     *
     * @param weekday weekday[sun=1, ... ,sat=0]
     * @return itself for method chaining
     */
    public MyanmarDate setWeekday(int weekday) {
        this.weekday = weekday;
        return this;
    }
}
