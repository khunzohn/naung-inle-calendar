package com.hilllander.naunginlecalendar.view.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.hilllander.calendar_api.kernel.CalendarKernel;
import com.hilllander.naunginlecalendar.R;
import com.hilllander.naunginlecalendar.util.listener.SimpleGestureFilter;
import com.hilllander.naunginlecalendar.util.listener.SimpleGestureFilter.SimpleGestureListener;
import com.hilllander.naunginlecalendar.view.fragment.DayFragment;
import com.hilllander.naunginlecalendar.view.fragment.HolidaysFragment;
import com.hilllander.naunginlecalendar.view.fragment.MonthFragment;
import com.hilllander.naunginlecalendar.view.fragment.YearFragment;

public class MainActivity extends AppCompatActivity implements SimpleGestureListener {
    private SimpleGestureFilter detecter;
    private int currentDay = 0;
    private int currentMonth = 0;
    private int currentYear = 0;
    private int currentContext = SpinnerListener.DAY;
    private CalendarKernel kernel = new CalendarKernel();
    private boolean watat = kernel.isWatat(1377);
    private double currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter
                .createFromResource(this, R.array.spinner_item, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new SpinnerListener());
        detecter = new SimpleGestureFilter(this, this);
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

    private void inflateMonthFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content, MonthFragment.getInstance(currentMonth))
                .commit();
    }

    private void inflateDayFragment(int current) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content, DayFragment.getInstance(current))
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
                currentDay--;
                break;
            case SpinnerListener.MONTH:
                currentMonth--;
                break;
            case SpinnerListener.YEAR:
                currentYear--;
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
                currentDay++;
                break;
            case SpinnerListener.MONTH:
                currentMonth++;
                break;
            case SpinnerListener.YEAR:
                currentYear++;
                break;
        }
    }

    private Fragment getCurrentFragment() {
        switch (currentContext) {
            case SpinnerListener.DAY:
                return DayFragment.getInstance(currentDay);
            case SpinnerListener.MONTH:
                return MonthFragment.getInstance(currentMonth);
            case SpinnerListener.YEAR:
                return YearFragment.getInstance(currentYear);
            case SpinnerListener.HOLIDAYS:
                return HolidaysFragment.getInstance();
            default:
                return DayFragment.getInstance(currentDay);
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
        Toast.makeText(this, "watat " + watat, Toast.LENGTH_SHORT).show();
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
                    inflateDayFragment(currentDay);
                    break;
                case MONTH:
                    currentContext = MONTH;
                    inflateMonthFragment();
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
                    inflateDayFragment(currentDay);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }
}
