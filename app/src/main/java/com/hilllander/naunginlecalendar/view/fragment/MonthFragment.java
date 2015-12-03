package com.hilllander.naunginlecalendar.view.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hilllander.naunginlecalendar.R;
import com.hilllander.naunginlecalendar.model.MonthGridItem;
import com.hilllander.naunginlecalendar.util.MonthViewHolder;
import com.hilllander.naunginlecalendar.util.adapter.MonthRecyclerAdapter;
import com.hilllander.naunginlecalendar.util.listener.MonthEventsListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MonthFragment extends Fragment {
    private static final String YEAR = "year";
    private static final String MONTH = "month";
    private static final String DAY = "day";
    private static final String TAG = MonthFragment.class.getSimpleName();
    private static MonthViewHolder mholder;
    private MonthGridItem firstDayOfM, lastDayOfM;
    private MonthEventsListener meListener;

    public MonthFragment() {
    }

    public static Fragment getInstance(MonthViewHolder holder, GregorianCalendar currentDate) {
        mholder = holder;

        Fragment fragment = new MonthFragment();
        Bundle args = new Bundle();
        args.putInt(YEAR, currentDate.get(Calendar.YEAR));
        args.putInt(MONTH, currentDate.get(Calendar.MONTH));
        args.putInt(DAY, currentDate.get(Calendar.DAY_OF_MONTH));
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Called when a fragment is first attached to its context.
     * {@link #onCreate(Bundle)} will be called after this.
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        meListener = (MonthEventsListener) context;
    }

    /**
     * Called when the fragment is no longer attached to its activity.  This
     * is called after {@link #onDestroy()}.
     */
    @Override
    public void onDetach() {
        super.onDetach();
        meListener = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mholder == null) {
            View v1 = inflater.inflate(R.layout.fragment_month, container, false);
            View v2 = inflater.inflate(R.layout.fragment_month, container, false);
            MonthViewHolder h1 = new MonthViewHolder(getContext(), v1);
            MonthViewHolder h2 = new MonthViewHolder(getContext(), v2);

            meListener.onViewHolderCreated(h1, h2);
            mholder = h2;
        }
        Bundle args = getArguments();
        int year = args.getInt(YEAR);
        int month = args.getInt(MONTH);
        int day = args.getInt(DAY);
        mholder.getEngMonth().setText(getMonthAsString(month));
        mholder.getEngYear().setText(String.valueOf(year));
        ArrayList<MonthGridItem> itemList = createGridItemList(year, month, day);

        mholder.getMyaMonthRange().setMyanmarText(getMRange(firstDayOfM, lastDayOfM));
        mholder.getMyaYearRange().setMyanmarText(getYRange(firstDayOfM, lastDayOfM));

        MonthRecyclerAdapter adapter = new MonthRecyclerAdapter(getContext(), itemList);
        mholder.getRecyclerView().setAdapter(adapter);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 7, LinearLayoutManager.VERTICAL, false);
        mholder.getRecyclerView().setHasFixedSize(true);
        mholder.getRecyclerView().setLayoutManager(layoutManager);

        return mholder.getRootView();
    }

    private String getYRange(MonthGridItem firstDayInGrid, MonthGridItem lastDayInGrid) {
        String yearOfFirstDay = firstDayInGrid.getMyaYear();
        String yearOfLastDay = lastDayInGrid.getMyaYear();
        String formattedRange = yearOfFirstDay.equals(yearOfLastDay) ?
                yearOfFirstDay : yearOfFirstDay + " - " + yearOfLastDay;
        if (formattedRange.equals(yearOfFirstDay)) {
            formattedRange = firstDayInGrid.getYearType() == 2 ? formattedRange + "-" + getContext().getString(R.string.big_watat_year) :
                    firstDayInGrid.getYearType() == 1 ? formattedRange + "-" + getContext().getString(R.string.small_watat_year) : formattedRange;
        }
        return formattedRange;
    }

    private String getMRange(MonthGridItem fristDayInGrid, MonthGridItem lastDayInGrid) {
        String monthOfFirstDay = fristDayInGrid.getSimpleMonthName();
        String monthOfLastDay = lastDayInGrid.getSimpleMonthName();

        return monthOfFirstDay.equals(monthOfLastDay) ? monthOfFirstDay : monthOfFirstDay + " - " + monthOfLastDay;
    }

    private String getMonthAsString(int month) {
        String[] months = {"January", "February", "March", "April", "May", "June", "July",
                "August", "September", "October", "November", "December"};
        return months[month];
    }

    private ArrayList<MonthGridItem> createGridItemList(final int year, final int month, final int curDay) {
        final int firstDay = 1;
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(year, month, firstDay);
        ArrayList<MonthGridItem> itemList = new ArrayList<>();
        int weekday = cal.get(Calendar.DAY_OF_WEEK); // sun = 1 .... sat = 7
        int daysOfPrevM = getDaysOfPrevM(month, year);
        int daysOfCurM = numOfDayInMonth(month, year);
        int trialingDays = weekday - 1; //days of prev month to be included int the grid
        int DAY_OFF_SET = 1;
        int prevMBaseDay = daysOfPrevM - trialingDays + DAY_OFF_SET;
        int mYear, mMonth, mDay;
        int dateStatus; // 0 = prevMonth 1= curMonth 2 = nextMonth

        //add trialing days to grid list
        for (int i = 0; i < trialingDays; i++) {
            mDay = prevMBaseDay++;
            mMonth = month == 0 ? 11 : month - 1; //if current month is jan then prevMonth will be Dec
            mYear = month == 0 ? year - 1 : year; // if current month is jan then preYear is decreased by 1
            dateStatus = 0;
            MonthGridItem gridItem = createMonthGridItem(mYear, mMonth, mDay, dateStatus, false);
            itemList.add(gridItem);
        }
        //add current months day to grid list
        for (int i = 1; i <= daysOfCurM; i++) {
            mDay = i;
            mMonth = month;
            mYear = year;
            dateStatus = 1;
            boolean currentDay = false;
            if (mDay == curDay)
                currentDay = true;
            MonthGridItem item = createMonthGridItem(mYear, mMonth, mDay, dateStatus, currentDay);
            itemList.add(item);
            if (i == 1) {
                firstDayOfM = item;
            }
            if (i == daysOfCurM) {
                lastDayOfM = item;
            }
        }
        int i = 1;
        //add next month days to grid list
        while (itemList.size() < 42) { //7*6 7 col and 6 row
            mDay = i++;
            mMonth = month == 11 ? 0 : month + 1; //if current month is Dec ,next month is jan
            mYear = month == 11 ? year + 1 : year; //if current month is Dec ,next year needs increment by 1
            dateStatus = 2;
            MonthGridItem item = createMonthGridItem(mYear, mMonth, mDay, dateStatus, false);
            itemList.add(item);
        }

        return itemList;
    }

    private int numOfDayInMonth(final int month, final int year) {
        int[] daysOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int days = daysOfMonth[month];
        if (new GregorianCalendar().isLeapYear(year) && month == 1) //leap year's feb
            days++;
        return days;
    }

    private int getDaysOfPrevM(int month, int year) {
        int prevMonth = month == 0 ? 11 : month - 1;
        int prevYear = month == 0 ? year - 1 : year;
        return numOfDayInMonth(prevMonth, prevYear);
    }

    private MonthGridItem createMonthGridItem(int mYear, int mMonth, int mDay, int dateStatus, boolean currentDay) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(mYear, mMonth, mDay);

        return MonthGridItem.newInstance(getContext(), cal, dateStatus, currentDay);
    }
}
