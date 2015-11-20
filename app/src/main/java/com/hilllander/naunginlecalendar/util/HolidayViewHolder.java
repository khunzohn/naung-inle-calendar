package com.hilllander.naunginlecalendar.util;

import android.view.View;
import android.widget.Spinner;

import com.hilllander.naunginlecalendar.R;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;

import mm.technomation.mmtext.MMTextView;

/**
 * Created by khunzohn on 11/20/15.
 */
public class HolidayViewHolder {
    private View rootView;
    private Spinner holidaySpinner;
    private DynamicListView holidaylistview;
    private MMTextView tvYear;

    public HolidayViewHolder(View view) {
        rootView = view;
        holidaySpinner = (Spinner) view.findViewById(R.id.holidaySpinner);
        holidaylistview = (DynamicListView) view.findViewById(R.id.holidaylistview);
        tvYear = (MMTextView) view.findViewById(R.id.tvYear);
    }

    public View getRootView() {
        return rootView;
    }

    public Spinner getHolidaySpinner() {
        return holidaySpinner;
    }

    public DynamicListView getHolidaylistview() {
        return holidaylistview;
    }

    public MMTextView getTvYear() {
        return tvYear;
    }
}
