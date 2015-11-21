package com.hilllander.naunginlecalendar.view.activity;


import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.hilllander.calendar_api.kernel.CalendarKernel;
import com.hilllander.calendar_api.model.WesternDate;
import com.hilllander.naunginlecalendar.R;
import com.hilllander.naunginlecalendar.util.listener.HolidayEventsListener;
import com.hilllander.naunginlecalendar.util.listener.MonthEventsListener;
import com.hilllander.naunginlecalendar.util.listener.SimpleGestureFilter;
import com.hilllander.naunginlecalendar.util.listener.SimpleGestureFilter.SimpleGestureListener;
import com.hilllander.naunginlecalendar.util.listener.YearEventsListener;
import com.hilllander.naunginlecalendar.util.viewholder.HolidayViewHolder;
import com.hilllander.naunginlecalendar.util.viewholder.MonthViewHolder;
import com.hilllander.naunginlecalendar.util.viewholder.YearBaseViewHolder;
import com.hilllander.naunginlecalendar.view.fragment.DayFragment;
import com.hilllander.naunginlecalendar.view.fragment.HolidaysFragment;
import com.hilllander.naunginlecalendar.view.fragment.MonthFragment;
import com.hilllander.naunginlecalendar.view.fragment.YearFragment;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity implements
        SimpleGestureListener, MonthEventsListener,
        HolidayEventsListener, YearEventsListener,
        com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private final int caltype = 1; //gregorian calendar
    private SimpleGestureFilter detecter;
    private int currentDay = 0;
    private int currentMonth = 0;
    private int currentYear = 0;
    private int currentContext = SpinnerListener.DAY;
    private CalendarKernel kernel = new CalendarKernel();
    private GregorianCalendar currentDate;
    private double curJd;
    private boolean firstBackPress = true;
    private LinearLayout mainLayout;
    private Spinner spinner;
    private int holContext = 0;
    private MonthViewHolder mh1, mh2;
    private HolidayViewHolder hh1, hh2;
    private boolean viewHolderFlag = false;
    private YearBaseViewHolder yh2, yh1;

    private void toggleHolderFlag() {
        viewHolderFlag = !viewHolderFlag;
    }

    private void resetHolderFlag(boolean val) {
        viewHolderFlag = val;
    }

    private MonthViewHolder getMHolder() {
        if (viewHolderFlag) {
            toggleHolderFlag();
            return mh1;
        } else {
            toggleHolderFlag();
            return mh2;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        View tbShadow = findViewById(R.id.toolbar_shadow);
        hideToolBarShadowForLollipop(toolbar, tbShadow);
        mainLayout = (LinearLayout) findViewById(R.id.main_layout);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter
                .createFromResource(this, R.array.spinner_item, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new SpinnerListener());
        GregorianCalendar today = new GregorianCalendar();
        setCurrentDate(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH));
        detecter = new SimpleGestureFilter(this, this);


    }

    private void setCurrentDate(int year, int month, int day) {
        if (currentDate == null)
            currentDate = new GregorianCalendar();
        currentDate.set(year, month, day);
        currentYear = year;
        currentMonth = month;
        currentDay = day;
        curJd = kernel.W2J(currentDate.get(Calendar.YEAR),
                currentDate.get(Calendar.MONTH) + 1, // MyanmarCalendar's month starts from 1
                currentDate.get(Calendar.DAY_OF_MONTH), caltype);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.date_picker) {

            DatePickerDialog datePicker = DatePickerDialog.newInstance(this, currentYear, currentMonth, currentDay);
            datePicker.show(getFragmentManager(), "Datepicker dialog");
        } else if (id == R.id.today) {
            GregorianCalendar cal = new GregorianCalendar();
            int
                    year = cal.get(Calendar.YEAR),
                    month = cal.get(Calendar.MONTH), // Gre month starts from 0
                    day = cal.get(Calendar.DAY_OF_MONTH);
            setCurrentDate(year, month, day);
            curJd = kernel.W2J(year, month + 1, day, caltype); // MyanmarCalendar's month starts from 1
            Log.d(TAG, "curJd : today " + curJd);
            int[] directions = animDirections(SimpleGestureFilter.SWIPE_UP);
            Fragment currentFragment = getCurrentFragment();
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(directions[0], directions[1])
                    .replace(R.id.main_content, currentFragment)
                    .commit();
        }

        return super.onOptionsItemSelected(item);
    }

    private void inflateHolidaysFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content, HolidaysFragment.getInstance(getHHolder(), currentDate, holContext))
                .commit();
    }

    private void inflateYearFragment(GregorianCalendar currentDate) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content, YearFragment.getInstance(getYHolder(), currentDate))
                .commit();
    }

    private YearBaseViewHolder getYHolder() {
        if (viewHolderFlag) {
            toggleHolderFlag();
            return yh1;
        } else {
            toggleHolderFlag();
            return yh2;
        }
    }

    private void inflateMonthFragment(GregorianCalendar currentDate) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content, MonthFragment.getInstance(getMHolder(), currentDate))
                .commit();
    }


    private void inflateDayFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content, DayFragment.getInstance(currentDate, MainActivity.this))
                .commit();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        this.detecter.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onSwipe(int direction) {
        switch (direction) {
            case SimpleGestureFilter.SWIPE_RIGHT:
                showPrev(direction);
                break;
            case SimpleGestureFilter.SWIPE_LEFT:
                showNext(direction);
                break;
        }
    }

    private void showPrev(int direction) {
        decreDate();
        int[] directions = animDirections(direction);
        Fragment currentFragment = getCurrentFragment();
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(directions[0], directions[1])
                .replace(R.id.main_content, currentFragment)
                .commit();
    }

    @Override
    public void onBackPressed() {

        if (firstBackPress) {
            firstBackPress = false;
            Snackbar.make(mainLayout, "Press one more time to exit", Snackbar.LENGTH_SHORT)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            view.setVisibility(View.INVISIBLE);
                        }
                    }).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    firstBackPress = true;
                }
            }, 2000);

        } else {
            super.onBackPressed();
        }
    }

    private void decreDate() {
        switch (currentContext) {
            case SpinnerListener.DAY:
                curJd -= 1;
                Log.d(TAG, "curJd : decre " + curJd);
                WesternDate wDate = kernel.J2W(curJd, caltype);
                setCurrentDate(wDate.getYear(), wDate.getMonth() - 1, wDate.getDay()); // WesternDate's month starts from 1
                break;
            case SpinnerListener.MONTH:
                currentMonth--;
                if (currentMonth < 0) {
                    currentMonth = 11;
                    currentYear--;
                }
                currentDay = currentDay > numOfDayInCurMonth(currentMonth, currentYear) ?
                        numOfDayInCurMonth(currentMonth, currentYear) : currentDay;
                setCurrentDate(currentYear, currentMonth, currentDay);
                break;
            case SpinnerListener.YEAR:
                currentYear--;
                currentDay = currentDay > numOfDayInCurMonth(currentMonth, currentYear) ?
                        numOfDayInCurMonth(currentMonth, currentYear) : currentDay;
                setCurrentDate(currentYear, currentMonth, currentDay);
                break;
            case SpinnerListener.HOLIDAYS:
                currentYear--;
                currentDay = currentDay > numOfDayInCurMonth(currentMonth, currentYear) ?
                        numOfDayInCurMonth(currentMonth, currentYear) : currentDay;
                setCurrentDate(currentYear, currentMonth, currentDay);
                break;
        }
    }

    private void showNext(int direction) {
        increDate();
        int[] directions = animDirections(direction);
        Fragment currentFragment = getCurrentFragment();
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(directions[0], directions[1])
                .replace(R.id.main_content, currentFragment)
                .commit();
    }

    private void increDate() {
        switch (currentContext) {
            case SpinnerListener.DAY:
                curJd += 1;
                Log.d(TAG, "curJd : incre" + curJd);
                WesternDate wDate = kernel.J2W(curJd, caltype);
                setCurrentDate(wDate.getYear(), wDate.getMonth() - 1, wDate.getDay()); // WesternDate's month starts from 1
                break;
            case SpinnerListener.MONTH:
                currentMonth++;
                if (currentMonth > 11) {
                    currentMonth = 0;
                    currentYear++;
                }
                currentDay = currentDay > numOfDayInCurMonth(currentMonth, currentYear) ?
                        numOfDayInCurMonth(currentMonth, currentYear) : currentDay;
                setCurrentDate(currentYear, currentMonth, currentDay);
                break;
            case SpinnerListener.YEAR:
                currentYear++;
                currentDay = currentDay > numOfDayInCurMonth(currentMonth, currentYear) ?
                        numOfDayInCurMonth(currentMonth, currentYear) : currentDay;
                setCurrentDate(currentYear, currentMonth, currentDay);
                break;

            case SpinnerListener.HOLIDAYS:
                currentYear++;
                currentDay = currentDay > numOfDayInCurMonth(currentMonth, currentYear) ?
                        numOfDayInCurMonth(currentMonth, currentYear) : currentDay;
                setCurrentDate(currentYear, currentMonth, currentDay);
                break;
        }
    }

    private int numOfDayInCurMonth(final int curMonth, final int curYear) {
        int[] daysOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int days = daysOfMonth[curMonth];
        if (new GregorianCalendar().isLeapYear(curYear) && curMonth == 1) //leap year's feb
            days++;
        return days;
    }

    private Fragment getCurrentFragment() {
        switch (currentContext) {
            case SpinnerListener.DAY:
                return DayFragment.getInstance(currentDate, MainActivity.this);
            case SpinnerListener.MONTH:
                return MonthFragment.getInstance(getMHolder(), currentDate);
            case SpinnerListener.YEAR:
                return YearFragment.getInstance(getYHolder(), currentDate);
            case SpinnerListener.HOLIDAYS:
                return HolidaysFragment.getInstance(getHHolder(), currentDate, holContext);
            default:
                return DayFragment.getInstance(currentDate, MainActivity.this);
        }
    }

    private HolidayViewHolder getHHolder() {
        if (viewHolderFlag) {
            toggleHolderFlag();
            return hh1;
        } else {
            toggleHolderFlag();
            return hh2;
        }
    }

    private int[] animDirections(int direction) {
        int enter = 0, exit = 0;
        switch (direction) {
            case SimpleGestureFilter.SWIPE_RIGHT:
                enter = R.anim.slide_in_left;
                exit = R.anim.slide_out_right;
                break;
            case SimpleGestureFilter.SWIPE_LEFT:
                enter = R.anim.slide_in_right;
                exit = R.anim.slide_out_left;
                break;
            case SimpleGestureFilter.SWIPE_DOWN:
                enter = R.anim.slide_in_top;
                exit = R.anim.slide_out_bottom;
                break;
            case SimpleGestureFilter.SWIPE_UP:
                enter = R.anim.slide_in_bottom;
                exit = R.anim.slide_out_top;
                break;
        }
        return new int[]{enter, exit};
    }


    @Override
    public void onDoubleTap() {

    }

    protected void hideToolBarShadowForLollipop(Toolbar mToolbar, View shadowView) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            // only for lollipop and newer versions
            shadowView.setVisibility(View.GONE);
            mToolbar.setElevation(getResources().getDimension(R.dimen.toolbar_elevation_height));
        }
    }

    @Override
    public void onMonthGridItemClick(int monthDayFlag, GregorianCalendar date) {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH);
        int day = date.get(Calendar.DAY_OF_MONTH);
        setCurrentDate(year, month, day);

        switch (monthDayFlag) {
            case 0: //click on previous month days
                showCur(SimpleGestureFilter.SWIPE_DOWN);
                break;
            case 1: //click on current month days
                break;
            case 2: //click on next month days
                showCur(SimpleGestureFilter.SWIPE_UP);
                break;
            case 3: // click on selected day
                spinner.setSelection(0, true);
                break;
        }

    }

    @Override
    public void onMonthViewHolderCreated(MonthViewHolder h1, MonthViewHolder h2) {
        this.mh1 = h1;
        this.mh2 = h2;
    }

    private void showCur(int direction) {
        int[] directions = animDirections(direction);
        Fragment currentFragment = getCurrentFragment();
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(directions[0], directions[1])
                .replace(R.id.main_content, currentFragment)
                .commit();
    }

    @Override
    public void onClickMyaHolListItem(int mYear, int mMonth, int mType, int mStatus, int wanWaxDay) {
        double jd = kernel.M2J(mYear, mMonth, mType, mStatus, wanWaxDay);
        WesternDate wDate = kernel.J2W(jd, 1);
        setCurrentDate(wDate.getYear(), wDate.getMonth() - 1, wDate.getDay());
        spinner.setSelection(0, true);
    }

    @Override
    public void onClickEngHolListItem(int year, int month, int day) {
        setCurrentDate(year, month - 1, day);
        spinner.setSelection(0);
    }

    @Override
    public void onHolidayListContextChange(int holContext) {
        this.holContext = holContext;
    }

    @Override
    public void onHolidayViewHolderCreated(HolidayViewHolder hh1, HolidayViewHolder hh2) {
        this.hh1 = hh1;
        this.hh2 = hh2;
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        setCurrentDate(year, month, day);
        curJd = kernel.W2J(year, month + 1, day, caltype); // MyanmarCalendar's month starts from 1
        Log.d(TAG, "curJd : dp " + curJd);
        int[] directions = animDirections(SimpleGestureFilter.SWIPE_UP);
        Fragment currentFragment = getCurrentFragment();
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(directions[0], directions[1])
                .replace(R.id.main_content, currentFragment)
                .commit();
    }

    @Override
    public void onYearViewHolderCreated(YearBaseViewHolder yh1, YearBaseViewHolder yh2) {
        this.yh1 = yh1;
        this.yh2 = yh2;
    }

    private class SpinnerListener implements android.widget.AdapterView.OnItemSelectedListener {
        private static final int DAY = 0;
        private static final int MONTH = 1;
        private static final int YEAR = 2;
        private static final int HOLIDAYS = 3;

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            switch (i) {
                case DAY:
                    currentContext = DAY;
                    resetHolderFlag(false);
                    inflateDayFragment();
                    break;
                case MONTH:
                    resetHolderFlag(false);
                    currentContext = MONTH;
                    inflateMonthFragment(currentDate);
                    break;
                case YEAR:
                    resetHolderFlag(false);
                    currentContext = YEAR;
                    inflateYearFragment(currentDate);
                    break;
                case HOLIDAYS:
                    resetHolderFlag(false);
                    currentContext = HOLIDAYS;
                    inflateHolidaysFragment();
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

}
