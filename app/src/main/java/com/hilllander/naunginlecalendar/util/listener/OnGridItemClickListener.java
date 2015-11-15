package com.hilllander.naunginlecalendar.util.listener;


import java.util.GregorianCalendar;

/**
 * Created by khunzohn on 11/14/15.
 */
public interface OnGridItemClickListener {
    /**
     * all in one functio triggered once Grid items of month and year context is clicked
     *
     * @param monthDayFlag [flag [0 - previous, 1 - current, 2 - next , 3 - same item]]
     * @param date
     * @param context
     */
    void onGridItemClick(int monthDayFlag, GregorianCalendar date);
}
