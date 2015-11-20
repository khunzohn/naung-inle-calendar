package com.hilllander.naunginlecalendar.model;

import java.util.GregorianCalendar;

/**
 * Created by khunzohn on 11/12/15.
 */
public class YearInnerGridItem {
    private final int cDay;
    private final int cMonth;
    private final int month;
    private final int year;
    private final int day;
    private int flag;
    //TODO remove this comment after merging

    private YearInnerGridItem(int year, int month, int day, int cMonth, int cDay) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.cMonth = cMonth;
        this.cDay = cDay;
        flag = examineFlag();
    }

    public static YearInnerGridItem newInstance(int year, int month, int day, int cMonth, int cDay) {
        return new YearInnerGridItem(year, month, day, cMonth, cDay);
    }

    private int examineFlag() {
        return day == cDay && month == cMonth ? 1 : 0;
    }

    public String getDay() {
        return day != 0 ? String.valueOf(day) : "";
    }

    /**
     * indicates whether current date or not
     *
     * @return 1 true , 0 false
     */
    public int getFlag() {
        return this.flag;
    }

    public GregorianCalendar getDate() {
        return new GregorianCalendar(year, month, day);
    }
}
