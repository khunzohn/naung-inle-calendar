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
public class DayFragment extends Fragment {
    private static final String CURRENT = "current";

    public DayFragment() {
    }

    public static Fragment getInstance(int current) {
        Fragment fragment = new DayFragment();
        Bundle args = new Bundle();
        args.putInt(CURRENT, current);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day, container, false);
        TextView current = (TextView) view.findViewById(R.id.current);
        current.setText("day : " + getArguments().getInt(CURRENT));
        return view;
    }
}
