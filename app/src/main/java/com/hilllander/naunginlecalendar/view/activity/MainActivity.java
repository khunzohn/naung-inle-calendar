package com.hilllander.naunginlecalendar.view.activity;


import android.app.DatePickerDialog;
import android.os.Bundle;
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
import android.widget.DatePicker;
import android.widget.Spinner;

import com.hilllander.calendar_api.kernel.CalendarKernel;
import com.hilllander.calendar_api.model.WesternDate;
import com.hilllander.naunginlecalendar.R;
import com.hilllander.naunginlecalendar.util.listener.SimpleGestureFilter;
import com.hilllander.naunginlecalendar.util.listener.SimpleGestureFilter.SimpleGestureListener;
import com.hilllander.naunginlecalendar.view.fragment.DayFragment;
import com.hilllander.naunginlecalendar.view.fragment.HolidaysFragment;
import com.hilllander.naunginlecalendar.view.fragment.MonthFragment;
import com.hilllander.naunginlecalendar.view.fragment.YearFragment;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity implements SimpleGestureListener {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        View tbShadow = findViewById(R.id.toolbar_shadow);
        hideToolBarShadowForLollipop(toolbar, tbShadow);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.date_picker) {
            DatePickerDialog builder = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
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
            },
                    currentDate.get(Calendar.YEAR),
                    currentDate.get(Calendar.MONTH),
                    currentDate.get(Calendar.DAY_OF_MONTH));
            builder.show();
        } else if (id == R.id.today) {
            GregorianCalendar cal = new GregorianCalendar();
            int
                    year = cal.get(Calendar.YEAR),
                    month = cal.get(Calendar.MONTH),
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
                .replace(R.id.main_content, HolidaysFragment.getInstance())
                .commit();
    }

    private void inflateYearFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content, YearFragment.getInstance(currentYear))
                .commit();
    }

    private void inflateMonthFragment(GregorianCalendar currentDate) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content, MonthFragment.getInstance(currentDate))
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
            case SimpleGestureFilter.SWIPE_DOWN:
                showPrev(direction);
                break;
            case SimpleGestureFilter.SWIPE_UP:
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
                return MonthFragment.getInstance(currentDate);
            case SpinnerListener.YEAR:
                return YearFragment.getInstance(currentYear);
            case SpinnerListener.HOLIDAYS:
                return HolidaysFragment.getInstance();
            default:
                return DayFragment.getInstance(currentDate, MainActivity.this);
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
                    inflateDayFragment();
                    break;
                case MONTH:
                    currentContext = MONTH;
                    inflateMonthFragment(currentDate);
                    break;
                case YEAR:
                    currentContext = YEAR;
                    inflateYearFragment();
                    break;
                case HOLIDAYS:
                    currentContext = HOLIDAYS;
                    inflateHolidaysFragment();
                    break;
                default:
                    inflateDayFragment();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }
}
