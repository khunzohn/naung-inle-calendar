package com.hilllander.naunginlecalendar.view.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hilllander.naunginlecalendar.R;
import com.hilllander.naunginlecalendar.model.MonthGridItem;
import com.hilllander.naunginlecalendar.util.RecyclerItemDecorater;
import com.hilllander.naunginlecalendar.util.adapter.MonthRecyclerAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import mm.technomation.mmtext.MMTextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MonthFragment extends Fragment {
    private static final String YEAR = "year";
    private static final String MONTH = "month";
    private static final String DAY = "day";
    private static final String TAG = MonthFragment.class.getSimpleName();
    private MonthGridItem firstDayOfM, lastDayOfM;

    public MonthFragment() {
    }

    public static Fragment getInstance(GregorianCalendar currentDate) {
        Fragment fragment = new MonthFragment();
        Bundle args = new Bundle();
        args.putInt(YEAR, currentDate.get(Calendar.YEAR));
        args.putInt(MONTH, currentDate.get(Calendar.MONTH));
        args.putInt(DAY, currentDate.get(Calendar.DAY_OF_MONTH));
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_month, container, false);
        TextView engMonth = (TextView) view.findViewById(R.id.engMonth);
        TextView engYear = (TextView) view.findViewById(R.id.engYear);
        MMTextView myaYearRange = (MMTextView) view.findViewById(R.id.myaYearRange);
        MMTextView myaMonthRange = (MMTextView) view.findViewById(R.id.myaMonthRange);
        Bundle args = getArguments();
        int year = args.getInt(YEAR);
        int month = args.getInt(MONTH);
        int day = args.getInt(DAY);
        engMonth.setText(getMonthAsString(month));
        engYear.setText(String.valueOf(year));
        ArrayList<MonthGridItem> itemList = createGridItemList(year, month, day);

        myaMonthRange.setMyanmarText(getMRange(firstDayOfM, lastDayOfM));
        myaYearRange.setMyanmarText(getYRange(firstDayOfM, lastDayOfM));
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_month);
        int itemOffset = getResources().getDimensionPixelOffset(R.dimen.item_offset);
        recyclerView.addItemDecoration(new RecyclerItemDecorater(itemOffset));
        MonthRecyclerAdapter adapter = new MonthRecyclerAdapter(getContext(), itemList);
        recyclerView.setAdapter(adapter);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 7, LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    private String getYRange(MonthGridItem firstDayInGrid, MonthGridItem lastDayInGrid) {
        String yearOfFirstDay = firstDayInGrid.getMyaYear();
        Log.d("YearRange", "year of First day" + yearOfFirstDay);
        String yearOfLastDay = lastDayInGrid.getMyaYear();
        Log.d("YearRange", "year of last day" + yearOfLastDay);
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
        Log.d("Range", "month of first day" + monthOfFirstDay);
        String monthOfLastDay = lastDayInGrid.getSimpleMonthName();
        Log.d("Range", "month of last day" + monthOfLastDay);

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
        Log.d(TAG, cal.get(Calendar.YEAR) + " " + cal.get(Calendar.MONTH) + " " + cal.get(Calendar.DAY_OF_MONTH));
        ArrayList<MonthGridItem> itemList = new ArrayList<>();
        int weekday = cal.get(Calendar.DAY_OF_WEEK); // sun = 1 .... sat = 7
        Log.d(TAG, "weekday : " + weekday);
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
            Log.d(TAG, gridItem.getEngDay() + " " + gridItem.getMyaDay() + " " + gridItem.getMyaMonth());
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
            Log.d(TAG, item.getEngDay() + " " + item.getMyaDay() + " " + item.getMyaMonth());
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
            Log.d(TAG, item.getEngDay() + " " + item.getMyaDay() + " " + item.getMyaMonth());
            itemList.add(item);
        }
        for (MonthGridItem item : itemList)
            Log.d(TAG + " createGridItemList", item.getEngDay());
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
        Log.d(TAG + "createMonthGridItem", cal.get(Calendar.YEAR) + " " + cal.get(Calendar.MONTH) + " " + cal.get(Calendar.DAY_OF_MONTH));

        return MonthGridItem.newInstance(getContext(), cal, dateStatus, currentDay);
    }
}
