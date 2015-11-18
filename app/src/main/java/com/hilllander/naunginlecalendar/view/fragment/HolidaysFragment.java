package com.hilllander.naunginlecalendar.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;

import com.hilllander.calendar_api.calendar.MyanmarCalendar;
import com.hilllander.calendar_api.kernel.HolidayKernel;
import com.hilllander.calendar_api.model.EngSDaysBundle;
import com.hilllander.calendar_api.model.MyaSDaysBundle;
import com.hilllander.naunginlecalendar.R;
import com.hilllander.naunginlecalendar.util.listener.OnListItemClickListener;
import com.nhaarman.listviewanimations.appearance.simple.SwingRightInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;

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
    String mYearString;
    private DynamicListView holidaylistview;
    private HolidayKernel holKernel;
    private OnListItemClickListener onListClickListener;
    private int holidayContext = 1; //0 = English, 1 = myanmar
    private int eYear, mYear;
    public HolidaysFragment() {
    }

    public static Fragment getInstance(GregorianCalendar curDate) {
        int eYear = curDate.get(Calendar.YEAR);
        MyanmarCalendar mCal = MyanmarCalendar.getInstance(curDate);
        String mYearString = mCal.getYearInMyanmar();
        int mYear = mCal.getYear();
        Fragment fragment = new HolidaysFragment();
        Bundle args = new Bundle();
        args.putInt(ENG_YEAR, eYear);
        args.putString(MYA_YEAR_STRING, mYearString);
        args.putInt(MYA_YEAR, mYear);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        holKernel = new HolidayKernel(getContext());
    }

    /**
     * Called when a fragment is first attached to its context.
     * {@link #onCreate(Bundle)} will be called after this.
     *
     * @param context main Activity
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onListClickListener = (OnListItemClickListener) context;
    }

    /**
     * Called when the fragment is no longer attached to its activity.  This
     * is called after {@link #onDestroy()}.
     */
    @Override
    public void onDetach() {
        super.onDetach();
        if (onListClickListener != null)
            onListClickListener = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_holidays, container, false);
        Spinner holidaySpinner = (Spinner) view.findViewById(R.id.holidaySpinner);
        holidaylistview = (DynamicListView) view.findViewById(R.id.holidaylistview);
        final MMTextView tvYear = (MMTextView) view.findViewById(R.id.tvYear);
        Bundle args = getArguments();
        eYear = args.getInt(ENG_YEAR);
        mYearString = args.getString(MYA_YEAR_STRING);
        mYear = args.getInt(MYA_YEAR);
        ArrayAdapter<CharSequence> spinnerAdapter =
                ArrayAdapter.createFromResource(getContext(),
                        R.array.holiday_spinner_item, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holidaySpinner.setAdapter(spinnerAdapter);
        holidaySpinner.setSelection(holidayContext);
        holidaySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                holidayContext = i;
                if (holidayContext == 0) { //English
                    tvYear.setMyanmarText(getContext().getString(R.string.eng_era) + " " + eYear + " " +
                            getContext().getString(R.string.holidays));
                } else { // Myanmar
                    tvYear.setMyanmarText(getContext().getString(R.string.mya_era) + " " + mYearString + " " +
                            getContext().getString(R.string.holidays));
                }
                MyAdapter adapter = new MyAdapter(holidayContext);
                SwingRightInAnimationAdapter animationAdapter = new SwingRightInAnimationAdapter(adapter);
                animationAdapter.setAbsListView(holidaylistview);
                holidaylistview.setAdapter(animationAdapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }

    private ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    static class ViewHolder {
        MMTextView holidayItem;
        View view;

        public ViewHolder(View view) {
            this.view = view;
            holidayItem = (MMTextView) view.findViewById(R.id.holidayItem);
        }
    }

    private class MyAdapter extends BaseAdapter {
        private ArrayList<MyaSDaysBundle> mHolidays;
        private ArrayList<EngSDaysBundle> eHolidays;
        private int holContext;

        public MyAdapter(int holContext) {
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
        public View getView(final int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.holiday_list_item, viewGroup, false);
            }
            ViewHolder holder = getViewHolder(view);
            if (holContext == 0) {//English
                final EngSDaysBundle eBundle = eHolidays.get(i);
                holder.holidayItem.setMyanmarText(eBundle.getName());
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onListClickListener.onClickEngHolListItem(eBundle.getYear(), eBundle.getMonth(), eBundle.getDay());
                    }
                });
            } else { // Myanmar
                final MyaSDaysBundle mBundle = mHolidays.get(i);
                holder.holidayItem.setMyanmarText(mBundle.getName());
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onListClickListener.onClickMyaHolListItem(mBundle.getmYear(), mBundle.getmMonth(),
                                mBundle.getmType(), mBundle.getmStatus(), mBundle.getWanWaxDay());
                    }
                });
            }

            return view;
        }
    }

}
