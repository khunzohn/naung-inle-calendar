package com.hilllander.mmdatepicker.util.listener;

import mm.technomation.mmtext.MMButtonView;

/**
 * Created by khunzohn on 11/29/15.
 */
public interface OnDatePickedListener {
    void onYearPickedListener(MMButtonView selectedYear);

    void onMonthDayPickedListener(int month, int day);
}
