package com.hilllander.naunginlecalendar.util.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hilllander.naunginlecalendar.R;
import com.hilllander.naunginlecalendar.util.RecyclerItemDecorater;

import mm.technomation.mmtext.MMTextView;

/**
 * Created by khunzohn on 11/19/15.
 */
public class MonthViewHolder {
    private TextView engMonth;
    private TextView engYear;
    private MMTextView myaYearRange;
    private MMTextView myaMonthRange;
    private RecyclerView recyclerView;
    private View rootView;

    public MonthViewHolder(Context context, View view) {
        rootView = view;
        engMonth = (TextView) view.findViewById(R.id.engMonth);
        engYear = (TextView) view.findViewById(R.id.engYear);
        myaYearRange = (MMTextView) view.findViewById(R.id.myaYearRange);
        myaMonthRange = (MMTextView) view.findViewById(R.id.myaMonthRange);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_month);
        int itemOffset = context.getResources().getDimensionPixelOffset(R.dimen.item_offset);
        recyclerView.addItemDecoration(new RecyclerItemDecorater(itemOffset));
    }

    public TextView getEngMonth() {
        return engMonth;
    }

    public TextView getEngYear() {
        return engYear;
    }

    public MMTextView getMyaYearRange() {
        return myaYearRange;
    }

    public MMTextView getMyaMonthRange() {
        return myaMonthRange;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public View getRootView() {
        return rootView;
    }
}
