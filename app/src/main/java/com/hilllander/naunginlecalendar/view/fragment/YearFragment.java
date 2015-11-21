package com.hilllander.naunginlecalendar.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hilllander.naunginlecalendar.R;
import com.hilllander.naunginlecalendar.model.YearMainGridItem;
import com.hilllander.naunginlecalendar.util.listener.YearEventsListener;
import com.hilllander.naunginlecalendar.util.viewholder.YearBaseViewHolder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * Created by khunzohn on 11/3/15.
 */
public class YearFragment extends Fragment {
    private static final String CURRENT_YEAR = "current year";
    private static final String CURRENT_MONTH = "current month";
    private static final String CURRENT_DAY = "curent day";
    private static YearBaseViewHolder yHolder;
    private YearEventsListener yearEventsListener;

    public YearFragment() {
    }

    public static Fragment getInstance(YearBaseViewHolder holder, GregorianCalendar currentDate) {
        yHolder = holder;
        Fragment fragment = new YearFragment();
        Bundle args = new Bundle();
        args.putInt(CURRENT_YEAR, currentDate.get(Calendar.YEAR));
        args.putInt(CURRENT_MONTH, currentDate.get(Calendar.MONTH));
        args.putInt(CURRENT_DAY, currentDate.get(Calendar.DAY_OF_MONTH));
        fragment.setArguments(args);
        return fragment;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        yearEventsListener = (YearEventsListener) context;
    }

    public void onDetach() {
        super.onDetach();
        yearEventsListener = null;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (yHolder == null) {
            View v1 = inflater.inflate(R.layout.fragment_year, container, false);
            View v2 = inflater.inflate(R.layout.fragment_year, container, false);
            YearBaseViewHolder yh1 = new YearBaseViewHolder(getContext(), v1);
            YearBaseViewHolder yh2 = new YearBaseViewHolder(getContext(), v2);
            yearEventsListener.onYearViewHolderCreated(yh1, yh2);
            yHolder = yh2;
        }

        Bundle args = getArguments();
        int year = args.getInt(CURRENT_YEAR);
        int cMonth = args.getInt(CURRENT_MONTH);
        int cDay = args.getInt(CURRENT_DAY);
        yHolder.getCurrent().setText(String.valueOf(year));


        ArrayList<YearMainGridItem> mainGridItems = createYearMainGridItems(year, cMonth, cDay);
        yHolder.setMainGridItems(mainGridItems);
        return yHolder.getRootView();
    }

    private ArrayList<YearMainGridItem> createYearMainGridItems(final int year, final int cMonth, final int cDay) {
        ArrayList<YearMainGridItem> items = new ArrayList<>();
        int firstDay = 1;
        for (int i = 0; i < 12; i++) {
            items.add(YearMainGridItem.newInstance(year, i, firstDay, cMonth, cDay));
        }
        return items;
    }
}
