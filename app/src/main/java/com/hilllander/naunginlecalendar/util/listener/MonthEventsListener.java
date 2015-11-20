package com.hilllander.naunginlecalendar.util.listener;


import com.hilllander.naunginlecalendar.util.MonthViewHolder;

import java.util.GregorianCalendar;

/**
 * Created by khunzohn on 11/14/15.
 */
public interface MonthEventsListener {
    /**
     * all in one functio triggered once Grid items of month and year context is clicked
     *
     * @param monthDayFlag [flag [0 - previous, 1 - current, 2 - next , 3 - same item]]
     * @param date
     */
    void onGridItemClick(int monthDayFlag, GregorianCalendar date);

    void onViewHolderCreated(MonthViewHolder h1, MonthViewHolder h2);
}
