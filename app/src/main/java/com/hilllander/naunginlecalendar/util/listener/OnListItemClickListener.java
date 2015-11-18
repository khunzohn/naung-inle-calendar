package com.hilllander.naunginlecalendar.util.listener;

/**
 * Created by khunzohn on 11/16/15.
 */
public interface OnListItemClickListener {
    void onClickMyaHolListItem(int mYear, int mMonth, int mType, int mStatus, int wanWaxDay);

    void onClickEngHolListItem(int year, int month, int day);

    void onHolidayListContextChange(int holContext);
}
