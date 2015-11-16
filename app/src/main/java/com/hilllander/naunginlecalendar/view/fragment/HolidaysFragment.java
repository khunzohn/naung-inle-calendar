package com.hilllander.naunginlecalendar.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hilllander.calendar_api.kernel.HolidayKernel;
import com.hilllander.calendar_api.model.EngSDaysBundle;
import com.hilllander.calendar_api.model.MyaSDaysBundle;
import com.hilllander.naunginlecalendar.R;
import com.hilllander.naunginlecalendar.util.listener.OnListItemClickListener;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;

import java.util.ArrayList;

import mm.technomation.mmtext.MMTextView;

/**
 * Created by khunzohn on 11/3/15.
 */
public class HolidaysFragment extends Fragment {
    private DynamicListView holidaylistview;
    private ArrayList<MyaSDaysBundle> mHolidays;
    private ArrayList<EngSDaysBundle> eHolidays;
    private HolidayKernel holKernel;
    private OnListItemClickListener onListClickListener;
    public HolidaysFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        holKernel = new HolidayKernel(getContext());
        mHolidays = holKernel.getMyaSpecialDayBundle(1377);
        eHolidays = holKernel.getEngspecialDayBundle(2015);
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

    public static Fragment getInstance() {
        return new HolidaysFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_holidays, container, false);
        holidaylistview = (DynamicListView) view.findViewById(R.id.holidaylistview);
        MyAdapter adapter = new MyAdapter();
        SwingBottomInAnimationAdapter animationAdapter = new SwingBottomInAnimationAdapter(adapter);
        animationAdapter.setAbsListView(holidaylistview);

        holidaylistview.setAdapter(animationAdapter);
        return view;
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return eHolidays.size();
        }

        @Override
        public Object getItem(int i) {
            return eHolidays.get(i);
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
            MMTextView holidayItem = (MMTextView) view.findViewById(R.id.holidayItem);
//            final MyaSDaysBundle mBundle = mHolidays.get(i);
            final EngSDaysBundle eBundle = eHolidays.get(i);
            holidayItem.setMyanmarText(eBundle.getName());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*onListClickListener.onClickMyaHolListItem(mBundle.getmYear(), mBundle.getmMonth(),
                            mBundle.getmType(), mBundle.getmStatus(), mBundle.getWanWaxDay());*/
                    onListClickListener.onClickEngHolListItem(eBundle.getYear(), eBundle.getMonth(), eBundle.getDay());
                }
            });
            return view;
        }
    }

}
