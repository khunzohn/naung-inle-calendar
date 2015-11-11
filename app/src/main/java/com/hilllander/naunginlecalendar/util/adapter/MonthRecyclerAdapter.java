package com.hilllander.naunginlecalendar.util.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hilllander.naunginlecalendar.R;
import com.hilllander.naunginlecalendar.model.MonthGridItem;

import java.util.ArrayList;

import mm.technomation.mmtext.MMTextView;

/**
 * Created by khunzohn on 11/9/15.
 */
public class MonthRecyclerAdapter extends RecyclerView.Adapter<MonthRecyclerAdapter.ViewHolder> {
    private static final String TAG = MonthRecyclerAdapter.class.getSimpleName();
    private final int recH;
    private ArrayList<MonthGridItem> itemList;

    public MonthRecyclerAdapter(ArrayList<MonthGridItem> itemList, int recH) {
        this.itemList = itemList;
        this.recH = recH;
        for (MonthGridItem item : itemList)
            Log.d(TAG, item.getEngDay() + " " + item.getMyaDay());
    }

    @Override
    public MonthRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.month_grid_item, viewGroup, false);

//        view.setMinimumHeight(80);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final MonthGridItem item = itemList.get(i);
        Log.d(TAG + " onBind", item.getEngDay());
        String[] specialDay = item.getSpecialDay();
        viewHolder.specialDay.setMyanmarText(specialDay != null && specialDay.length > 0 ? specialDay[0] : "");
        viewHolder.eDay.setText(item.getEngDay());
        viewHolder.mMonth.setMyanmarText("လ");
        viewHolder.mDay.setMyanmarText(item.getMyaDay());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MMTextView mMonth;
        MMTextView mDay;
        TextView eDay;
        MMTextView specialDay;

        public ViewHolder(View view) {
            super(view);

            mMonth = (MMTextView) view.findViewById(R.id.mMonth);
            mDay = (MMTextView) view.findViewById(R.id.mDay);
            eDay = (TextView) view.findViewById(R.id.eDay);
            specialDay = (MMTextView) view.findViewById(R.id.specialDay);
        }
    }
}
