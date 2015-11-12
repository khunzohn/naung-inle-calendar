package com.hilllander.naunginlecalendar.util.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hilllander.naunginlecalendar.R;
import com.hilllander.naunginlecalendar.model.YearInnerGridItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by khunzohn on 11/12/15.
 */
public class YearInnerAdapter extends RecyclerView.Adapter<YearInnerAdapter.ViewHolder> {
    private Context context;
    private ArrayList<YearInnerGridItem> innerGridItems;

    public YearInnerAdapter(Context context, ArrayList<YearInnerGridItem> innerGridItems) {
        this.context = context;
        this.innerGridItems = innerGridItems;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.year_inner_grid_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        YearInnerGridItem item = innerGridItems.get(position);
        int flag = item.getFlag();
        holder.innerGridDay.setText(item.getDay());
        if (isEqual(item.getDate(), new GregorianCalendar())) { // highlight today
            holder.innerGridDay.setBackgroundColor(context.getResources().getColor(R.color.dark_blue));
            holder.innerGridDay.setTextColor(context.getResources().getColor(R.color.white));
        } else if (flag == 1) {// highlight current day
            holder.innerGridDay.setBackgroundColor(context.getResources().getColor(R.color.dark_blue_alpha));
            holder.innerGridDay.setTextColor(context.getResources().getColor(R.color.white));
        }
        if (position % 7 == 0 || position % 7 == 6) {
            holder.innerGridDay.setTextColor(context.getResources().getColor(R.color.red));
        }
    }

    private boolean isEqual(GregorianCalendar dateOne, GregorianCalendar dateTwo) {
        return dateOne.get(Calendar.YEAR) == dateTwo.get(Calendar.YEAR) &&
                dateOne.get(Calendar.MONTH) == dateTwo.get(Calendar.MONTH) &&
                dateOne.get(Calendar.DAY_OF_MONTH) == dateTwo.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Returns the total number of items in the data set hold by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return innerGridItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView innerGridDay;

        public ViewHolder(View view) {
            super(view);
            innerGridDay = (TextView) view.findViewById(R.id.innerGridDay);
        }
    }
}