package com.hilllander.naunginlecalendar.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hilllander.naunginlecalendar.R;

/**
 * Created by khunzohn on 11/3/15.
 */
public class HolidaysFragment extends Fragment {
    public HolidaysFragment() {
    }

    public static Fragment getInstance() {
        return new HolidaysFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_holidays, container, false);
        return view;
    }
}
