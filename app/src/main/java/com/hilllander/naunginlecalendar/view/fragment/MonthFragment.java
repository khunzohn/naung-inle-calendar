package com.hilllander.naunginlecalendar.view.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hilllander.naunginlecalendar.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MonthFragment extends Fragment {


    public MonthFragment() {
    }

    public static Fragment getInstance() {
        return new MonthFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_month, container, false);
        return view;
    }


}
