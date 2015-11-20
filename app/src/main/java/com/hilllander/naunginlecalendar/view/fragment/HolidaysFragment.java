package com.hilllander.naunginlecalendar.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import com.hilllander.calendar_api.calendar.MyanmarCalendar;
import com.hilllander.calendar_api.kernel.HolidayKernel;
import com.hilllander.calendar_api.model.EngSDaysBundle;
import com.hilllander.calendar_api.model.MyaSDaysBundle;
import com.hilllander.naunginlecalendar.R;
import com.hilllander.naunginlecalendar.util.HolidayViewHolder;
import com.hilllander.naunginlecalendar.util.listener.HolidayEventsListener;
import com.nhaarman.listviewanimations.appearance.simple.SwingRightInAnimationAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import mm.technomation.mmtext.MMTextView;

/**
 * Created by khunzohn on 11/3/15.
 */
public class HolidaysFragment extends Fragment {
    private static final String ENG_YEAR = "english year";
    private static final String MYA_YEAR_STRING = "myanmar year string";
    private static final String MYA_YEAR = "myanmar year";
    private static final String HOL_CONTEXT = "holiday context";
    private static HolidayViewHolder hHolder;
    String mYearString;
    private HolidayEventsListener holidayEventsListener;
    private int holidayContext; //0 = English, 1 = myanmar
    private int eYear, mYear;

    public HolidaysFragment() {
    }

    public static Fragment getInstance(HolidayViewHolder holder, GregorianCalendar curDate, int holContext) {
        hHolder = holder;
        int eYear = curDate.get(Calendar.YEAR);
        MyanmarCalendar mCal = MyanmarCalendar.getInstance(curDate);
        String mYearString = mCal.getYearInMyanmar();
        int mYear = mCal.getYear();
        Fragment fragment = new HolidaysFragment();
        Bundle args = new Bundle();
        args.putInt(ENG_YEAR, eYear);
        args.putString(MYA_YEAR_STRING, mYearString);
        args.putInt(MYA_YEAR, mYear);
        args.putInt(HOL_CONTEXT, holContext);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        holidayEventsListener = (HolidayEventsListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (holidayEventsListener != null)
            holidayEventsListener = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (hHolder == null) {
            View v1 = inflater.inflate(R.layout.fragment_holidays, container, false);
            View v2 = inflater.inflate(R.layout.fragment_holidays, container, false);
            HolidayViewHolder hh1 = new HolidayViewHolder(v1);
            HolidayViewHolder hh2 = new HolidayViewHolder(v2);
            holidayEventsListener.onHolidayViewHolderCreated(hh1, hh2);
            hHolder = hh2;
        }
        Bundle args = getArguments();
        eYear = args.getInt(ENG_YEAR);
        mYearString = args.getString(MYA_YEAR_STRING);
        mYear = args.getInt(MYA_YEAR);
        holidayContext = args.getInt(HOL_CONTEXT);
        ArrayAdapter<CharSequence> spinnerAdapter =
                ArrayAdapter.createFromResource(getContext(),
                        R.array.holiday_spinner_item, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hHolder.getHolidaySpinner().setAdapter(spinnerAdapter);
        hHolder.getHolidaySpinner().setSelection(holidayContext);
        hHolder.getHolidaySpinner().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                holidayContext = i;
                holidayEventsListener.onHolidayListContextChange(holidayContext);
                if (holidayContext == 0) { //English
                    hHolder.getTvYear().setMyanmarText(getContext().getString(R.string.eng_era) + " " + eYear + " " +
                            getContext().getString(R.string.holidays));
                } else { // Myanmar
                    hHolder.getTvYear().setMyanmarText(getContext().getString(R.string.mya_era) + " " + mYearString + " " +
                            getContext().getString(R.string.holidays));
                }
                MyAdapter adapter = new MyAdapter(holidayContext);
                SwingRightInAnimationAdapter animationAdapter = new SwingRightInAnimationAdapter(adapter);
                animationAdapter.setAbsListView(hHolder.getHolidaylistview());
                hHolder.getHolidaylistview().setAdapter(animationAdapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return hHolder.getRootView();
    }

    private class ViewHolder {
        MMTextView holidayItem;

        ViewHolder(View view) {
            holidayItem = (MMTextView) view.findViewById(R.id.holidayItem);
        }
    }

    private class MyAdapter extends BaseAdapter {
        private static final String TAG = "MyAdapter";
        ViewHolder holder;
        private ArrayList<MyaSDaysBundle> mHolidays;
        private ArrayList<EngSDaysBundle> eHolidays;
        private int holContext;
        private HolidayKernel holKernel;
        private int counter = 0;


        public MyAdapter(int holContext) {
            this.holKernel = new HolidayKernel(getContext());
            this.holContext = holContext;
            if (holContext == 0) { //English
                mHolidays = null;
                eHolidays = holKernel.getEngspecialDayBundle(eYear);
            } else {
                eHolidays = null;
                mHolidays = holKernel.getMyaSpecialDayBundle(mYear);
            }
        }

        @Override
        public int getCount() {
            switch (holContext) {
                case 0: // English
                    return eHolidays.size();
                case 1: //Myanmar
                    return mHolidays.size();
                default:
                    return eHolidays.size();
            }
        }

        @Override
        public Object getItem(int i) {
            switch (holContext) {
                case 0:
                    return eHolidays.get(i);
                case 1:
                    return mHolidays.get(i);
                default:
                    return eHolidays.get(i);
            }
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View convertView, ViewGroup viewGroup) {
            View mView = convertView;

            if (mView == null) {
                counter++;
                Log.d(TAG, "view created: " + counter);
                mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.holiday_list_item, viewGroup, false);
                holder = new ViewHolder(mView);
                mView.setTag(holder);
            } else {
                holder = (ViewHolder) mView.getTag();
            }

            if (holContext == 0) {//English
                final EngSDaysBundle eBundle = eHolidays.get(i);
                holder.holidayItem.setMyanmarText(eBundle.getName());
                mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        holidayEventsListener.onClickEngHolListItem(eBundle.getYear(), eBundle.getMonth(), eBundle.getDay());
                    }
                });
            } else { // Myanmar
                final MyaSDaysBundle mBundle = mHolidays.get(i);
                holder.holidayItem.setMyanmarText(mBundle.getName());
                mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        holidayEventsListener.onClickMyaHolListItem(mBundle.getmYear(), mBundle.getmMonth(),
                                mBundle.getmType(), mBundle.getmStatus(), mBundle.getWanWaxDay());
                    }
                });
            }

            mView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    int event = motionEvent.getAction();
                    switch (event) {
                        case MotionEvent.ACTION_DOWN:
                            view.setBackgroundColor(getContext().getResources().getColor(R.color.grey));
                            break;
                        case MotionEvent.ACTION_UP:
                            view.setBackgroundColor(getContext().getResources().getColor(R.color.white));
                            break;
                    }
                    return false;
                }
            });

            return mView;
        }
    }

}
