package com.hilllander.naunginlecalendar.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by khunzohn on 11/12/15.
 */
public class YearMainGridItem {
    private static final String TAG = YearMainGridItem.class.getSimpleName();
    String[] months = {"January", "February", "March", "April",
            "May", "June", "July", "August",
            "September", "October", "November", "December"};
    int[] daysOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private int year;
    private int month;
    private int firstDay;
    private int cMonth;
    private int cDay;
    private GregorianCalendar cal;

    public YearMainGridItem(final int year, final int month, final int firstDay, final int cMonth, final int cDay) {
        this.year = year;
        this.month = month;
        this.firstDay = firstDay;
        this.cMonth = cMonth;
        this.cDay = cDay;
        cal = new GregorianCalendar(year, month, firstDay);
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getcMonth() {
        return cMonth;
    }

    public int getcDay() {
        return cDay;
    }

    public String getMonthHeader() {
        return months[month];
    }

    public ArrayList<YearInnerGridItem> getInnerGridItems(final int year, final int month, final int cMonth, final int cDay) {
        ArrayList<YearInnerGridItem> items = new ArrayList<>();
        int weekday = cal.get(Calendar.DAY_OF_WEEK); // 1 = sun...7 = sat
        int daysOfMonth = getDaysOfMonth();
        int trialingDays = weekday - 1;
        //add trialing days
        for (int i = 0; i < trialingDays; i++) {
            items.add(new YearInnerGridItem(year, month, 0, cMonth, cDay));
        }
        // add current month's days
        for (int i = 1; i <= daysOfMonth; i++)
            items.add(new YearInnerGridItem(year, month, i, cMonth, cDay));
        Log.d(TAG, "inner size : " + items.size());
        return items;
    }

    private int getDaysOfMonth() {
        int days = daysOfMonth[cal.get(Calendar.MONTH)];
        if (cal.isLeapYear(cal.get(Calendar.YEAR)) && cal.get(Calendar.MONTH) == 1) //feb 29 on leap year
            days++;
        return days;
    }
}
