package com.hilllander.naunginlecalendar.util.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hilllander.naunginlecalendar.R;
import com.hilllander.naunginlecalendar.model.MonthGridItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import mm.technomation.mmtext.MMTextView;

/**
 * Created by khunzohn on 11/9/15.
 */
public class MonthRecyclerAdapter extends RecyclerView.Adapter<MonthRecyclerAdapter.ViewHolder> {
    private static final String TAG = MonthRecyclerAdapter.class.getSimpleName();
    private ArrayList<MonthGridItem> itemList;
    private Context context;

    public MonthRecyclerAdapter(Context context, ArrayList<MonthGridItem> itemList) {
        this.context = context;
        this.itemList = itemList;
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
        viewHolder.eDay.setText(item.getEngDay());
        viewHolder.mMonth.setMyanmarText(item.getMyaMonth());
        viewHolder.mDay.setMyanmarText(item.getMyaDay());

        if (i % 7 == 0 || i % 7 == 6) { //highlight weekend days
            viewHolder.eDay.setTextColor(context.getResources().getColor(R.color.red));
            viewHolder.mDay.setTextColor(context.getResources().getColor(R.color.red));
        }
        if (isEqual(item.getGreDate(), new GregorianCalendar())) { //highlight today
            viewHolder.eDay.setBackgroundColor(context.getResources().getColor(R.color.dark_blue));
            viewHolder.eDay.setTextColor(context.getResources().getColor(R.color.white));
        }
        if (item.getMonthStatus() == 1) { // highlight full
            viewHolder.specialDayImage.setImageDrawable(context.getResources().getDrawable(R.drawable.full_moon));
        } else if (item.getMonthStatus() == 3) { //highlight new moon
            viewHolder.specialDayImage.setImageDrawable(context.getResources().getDrawable(R.drawable.new_moon));
        }
        if (item.getDateStatus() == 0 || item.getDateStatus() == 2) { // highlight pre and next month's days
            viewHolder.rootView.setBackgroundColor(context.getResources().getColor(R.color.light_grey));
        }
        if (item.getSpecialDayFlag() == 1 &&    // highlight special day ,omit full and new moon day
                (item.getDateStatus() != 0 || item.getDateStatus() != 2)) {
            viewHolder.specialDayImage.setImageDrawable(context.getResources().getDrawable(R.drawable.special_day_image));
        }
    }

    private boolean isEqual(GregorianCalendar dateOne, GregorianCalendar dateTwo) {
        return dateOne.get(Calendar.YEAR) == dateTwo.get(Calendar.YEAR) &&
                dateOne.get(Calendar.MONTH) == dateTwo.get(Calendar.MONTH) &&
                dateOne.get(Calendar.DAY_OF_MONTH) == dateTwo.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MMTextView mMonth;
        MMTextView mDay;
        TextView eDay;
        ImageView specialDayImage;
        View rootView;

        public ViewHolder(View view) {
            super(view);
            rootView = view;
            mMonth = (MMTextView) view.findViewById(R.id.mMonth);
            mDay = (MMTextView) view.findViewById(R.id.mDay);
            eDay = (TextView) view.findViewById(R.id.eDay);
            specialDayImage = (ImageView) view.findViewById(R.id.specialDayImage);
        }
    }
}