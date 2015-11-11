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

import com.hilllander.calendar_api.calendar.MyanmarCalendar;
import com.hilllander.naunginlecalendar.R;
import com.hilllander.naunginlecalendar.model.MonthGridItem;
import com.hilllander.naunginlecalendar.util.RecyclerItemDecorater;
import com.hilllander.naunginlecalendar.util.adapter.MonthRecyclerAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class MonthFragment extends Fragment {
    private static final String YEAR = "year";
    private static final String MONTH = "month";
    private static final String DAY = "day";
    private static final String CURRENT_MONTH = "current month";
    private static final String TAG = MonthFragment.class.getSimpleName();
    private RecyclerView recyclerView;

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
        TextView current = (TextView) view.findViewById(R.id.current_month);
        Bundle args = getArguments();
        int year = args.getInt(YEAR);
        int month = args.getInt(MONTH);
        int day = args.getInt(DAY);
        ArrayList<MonthGridItem> itemList = getGridItemList(year, month, day);
        for (MonthGridItem item : itemList)
            Log.d(TAG + " onCreateView", item.getEngDay());
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_month);
        int itemOffset = getResources().getDimensionPixelOffset(R.dimen.item_offset);
        Log.d(TAG, "itemOffset : " + itemOffset);
        recyclerView.addItemDecoration(new RecyclerItemDecorater(itemOffset));
        int recH = recyclerView.getMeasuredHeight();
        MonthRecyclerAdapter adapter = new MonthRecyclerAdapter(itemList, recH);
        recyclerView.setAdapter(adapter);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 7, LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        Log.d(TAG, "recH : " + recH);


        current.setText("month : " + args.getInt(CURRENT_MONTH));

        return view;
    }

    private ArrayList<MonthGridItem> getGridItemList(final int year, final int month, final int day) {
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

        //add trialing days to grid list
        for (int i = 0; i < trialingDays; i++) {
            mDay = prevMBaseDay++;
            mMonth = month == 0 ? 11 : month - 1; //if current month is jan then prevMonth will be Dec
            mYear = month == 0 ? year - 1 : year; // if current month is jan then preYear is decreased by 1
            MonthGridItem gridItem = createMonthGridItem(mYear, mMonth, mDay);
            Log.d(TAG, gridItem.getEngDay() + " " + gridItem.getMyaDay() + " " + gridItem.getMyaMonth());
            itemList.add(gridItem);
        }
        //add current months day to grid list
        for (int i = 1; i <= daysOfCurM; i++) {
            mDay = i;
            mMonth = month;
            mYear = year;
            MonthGridItem item = createMonthGridItem(mYear, mMonth, mDay);
            Log.d(TAG, item.getEngDay() + " " + item.getMyaDay() + " " + item.getMyaMonth());
            itemList.add(item);
        }
        int i = 1;
        //add next month days to grid list
        while (itemList.size() < 42) { //7*6 7 col and 6 row
            mDay = i++;
            mMonth = month == 11 ? 0 : month + 1; //if current month is Dec ,next month is jan
            mYear = month == 11 ? year + 1 : year; //if current month is Dec ,next year needs increment by 1
            MonthGridItem item = createMonthGridItem(mYear, mMonth, mDay);
            Log.d(TAG, item.getEngDay() + " " + item.getMyaDay() + " " + item.getMyaMonth());
            itemList.add(item);
        }
        for (MonthGridItem item : itemList)
            Log.d(TAG + " getGridItemList", item.getEngDay());
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

    private MonthGridItem createMonthGridItem(int mYear, int mMonth, int mDay) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(mYear, mMonth, mDay);
        Log.d(TAG + "createMonthGridItem", cal.get(Calendar.YEAR) + " " + cal.get(Calendar.MONTH) + " " + cal.get(Calendar.DAY_OF_MONTH));
        MyanmarCalendar mCal = MyanmarCalendar.getInstance(cal);
        MonthGridItem item = MonthGridItem.newInstance(mCal)
                .setEngDay(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)))
                .setMyaDay(mCal.getDayInMyanmnar())
                .setMyaMonth(mCal.getMonthInMyanmar(getContext()))
                .setSpecialDay(mCal.getHolidays(getContext()));

        return item;
    }
}
