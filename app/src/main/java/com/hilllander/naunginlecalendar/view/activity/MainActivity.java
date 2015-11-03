package com.hilllander.naunginlecalendar.view.activity;

import android.os.Bundle;
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

import com.hilllander.naunginlecalendar.R;
import com.hilllander.naunginlecalendar.util.listener.SimpleGestureFilter;
import com.hilllander.naunginlecalendar.util.listener.SimpleGestureFilter.SimpleGestureListener;
import com.hilllander.naunginlecalendar.view.fragment.DayFragment;
import com.hilllander.naunginlecalendar.view.fragment.HolidaysFragment;
import com.hilllander.naunginlecalendar.view.fragment.MonthFragment;
import com.hilllander.naunginlecalendar.view.fragment.YearFragment;

public class MainActivity extends AppCompatActivity implements SimpleGestureListener {
    private SimpleGestureFilter detecter;
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
                .replace(R.id.main_content, YearFragment.getInstance())
                .commit();
    }

    private void inflateMonthFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content, MonthFragment.getInstance())
                .commit();
    }

    private void inflateDayFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content, DayFragment.getInstance())
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
                Toast.makeText(this, "right", Toast.LENGTH_SHORT).show();
                break;
            case SimpleGestureFilter.SWIPE_LEFT:
                Toast.makeText(this, "left", Toast.LENGTH_SHORT).show();
                break;
            case SimpleGestureFilter.SWIPE_DOWN:
                Toast.makeText(this, "down", Toast.LENGTH_SHORT).show();
                break;
            case SimpleGestureFilter.SWIPE_UP:
                Toast.makeText(this, "up", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onDoubleTap() {
        Toast.makeText(this, "double tap", Toast.LENGTH_SHORT).show();
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
                    inflateDayFragment();
                    break;
                case MONTH:
                    inflateMonthFragment();
                    break;
                case YEAR:
                    inflateYearFragment();
                    break;
                case HOLIDAYS:
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
