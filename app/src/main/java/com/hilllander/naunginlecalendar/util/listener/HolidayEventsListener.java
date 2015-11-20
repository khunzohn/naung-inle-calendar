package com.hilllander.naunginlecalendar.util.listener;

import com.hilllander.naunginlecalendar.util.HolidayViewHolder;

/**
 * Created by khunzohn on 11/16/15.
 */
public interface HolidayEventsListener {
    void onClickMyaHolListItem(int mYear, int mMonth, int mType, int mStatus, int wanWaxDay);

    void onClickEngHolListItem(int year, int month, int day);

    void onHolidayListContextChange(int holContext);

    void onHolidayViewHolderCreated(HolidayViewHolder hh1, HolidayViewHolder hh2);
}
