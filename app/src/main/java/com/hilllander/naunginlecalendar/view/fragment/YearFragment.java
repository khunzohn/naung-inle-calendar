package com.hilllander.naunginlecalendar.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hilllander.naunginlecalendar.R;
import com.hilllander.naunginlecalendar.model.YearMainGridItem;
import com.hilllander.naunginlecalendar.util.adapter.YearMainAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by khunzohn on 11/3/15.
 */
public class YearFragment extends Fragment {
    private static final String CURRENT_YEAR = "current year";
    private static final String CURRENT_MONTH = "current month";
    private static final String CURRENT_DAY = "curent day";
    private RecyclerView mainRecy;

    public YearFragment() {
    }

    public static Fragment getInstance(GregorianCalendar currentDate) {
        Fragment fragment = new YearFragment();
        Bundle args = new Bundle();
        args.putInt(CURRENT_YEAR, currentDate.get(Calendar.YEAR));
        args.putInt(CURRENT_MONTH, currentDate.get(Calendar.MONTH));
        args.putInt(CURRENT_DAY, currentDate.get(Calendar.DAY_OF_MONTH));
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_year, container, false);
        TextView current = (TextView) view.findViewById(R.id.currentYear);
        Bundle args = getArguments();
        int year = args.getInt(CURRENT_YEAR);
        int cMonth = args.getInt(CURRENT_MONTH);
        int cDay = args.getInt(CURRENT_DAY);
        current.setText(String.valueOf(year));

        mainRecy = (RecyclerView) view.findViewById(R.id.main_recycler_year);
        mainRecy.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3, LinearLayoutManager.VERTICAL, false);
        mainRecy.setLayoutManager(layoutManager);
        ArrayList<YearMainGridItem> mainGridItems = createYearMainGridItems(year, cMonth, cDay);
        YearMainAdapter adapter = new YearMainAdapter(getContext(), mainGridItems);
        mainRecy.setAdapter(adapter);
        return view;
    }

    private ArrayList<YearMainGridItem> createYearMainGridItems(final int year, final int cMonth, final int cDay) {
        ArrayList<YearMainGridItem> items = new ArrayList<>();
        int firstDay = 1;
        for (int i = 0; i < 12; i++) {
            items.add(YearMainGridItem.newInstance(year, i, firstDay, cMonth, cDay));
        }
        return items;
    }
}
