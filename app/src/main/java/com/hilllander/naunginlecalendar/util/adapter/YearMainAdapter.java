package com.hilllander.naunginlecalendar.util.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hilllander.naunginlecalendar.R;
import com.hilllander.naunginlecalendar.model.YearInnerGridItem;
import com.hilllander.naunginlecalendar.model.YearMainGridItem;

import java.util.ArrayList;

/**
 * Created by khunzohn on 11/12/15.
 */
public class YearMainAdapter extends RecyclerView.Adapter<YearMainAdapter.ViewHolder> {
    private static final String TAG = YearMainAdapter.class.getSimpleName();
    private ArrayList<YearMainGridItem> items;
    private Context context;

    public YearMainAdapter(Context context, ArrayList<YearMainGridItem> mainGridItems) {
        items = mainGridItems;
        this.context = context;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.year_grid_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        YearMainGridItem item = items.get(position);
        holder.month.setText(item.getMonthHeader());
        ArrayList<YearInnerGridItem> innerItems = item.getInnerGridItems();
        YearInnerAdapter innerAdapter = new YearInnerAdapter(context, innerItems);
        GridLayoutManager innerLayout = new GridLayoutManager(context, 7, LinearLayoutManager.VERTICAL, false);
        holder.innerRecy.setHasFixedSize(true);
        holder.innerRecy.setLayoutManager(innerLayout);
        holder.innerRecy.setAdapter(innerAdapter);
    }

    /**
     * Returns the total number of items in the data set hold by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView month;
        public RecyclerView innerRecy;

        public ViewHolder(View view) {
            super(view);
            month = (TextView) view.findViewById(R.id.year_item_month_header);
            innerRecy = (RecyclerView) view.findViewById(R.id.year_inner_recycler);
        }
    }
}
