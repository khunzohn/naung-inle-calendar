package com.hilllander.naunginlecalendar.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hilllander.naunginlecalendar.R;

/**
 * Created by khunzohn on 11/3/15.
 */
public class YearFragment extends Fragment {
    private static final String CURRENT_YEAR = "current year";

    public YearFragment() {
    }

    public static Fragment getInstance(int currentYear) {
        Fragment fragment = new YearFragment();
        Bundle args = new Bundle();
        args.putInt(CURRENT_YEAR, currentYear);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_year, container, false);
        TextView current = (TextView) view.findViewById(R.id.current_year);
        current.setText("year : " + getArguments().getInt(CURRENT_YEAR));
        return view;
    }
}
