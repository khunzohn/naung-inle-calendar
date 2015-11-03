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
 * A simple {@link Fragment} subclass.
 */
public class MonthFragment extends Fragment {


    private static final String CURRENT_MONTH = "current month";

    public MonthFragment() {
    }

    public static Fragment getInstance(int currentMonth) {
        Fragment fragment = new MonthFragment();
        Bundle args = new Bundle();
        args.putInt(CURRENT_MONTH, currentMonth);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_month, container, false);
        TextView current = (TextView) view.findViewById(R.id.current_month);
        current.setText("month : " + getArguments().getInt(CURRENT_MONTH));
        return view;
    }


}
