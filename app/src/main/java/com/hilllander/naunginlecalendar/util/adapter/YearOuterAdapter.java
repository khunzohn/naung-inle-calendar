package com.hilllander.naunginlecalendar.util.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hilllander.naunginlecalendar.R;
import com.hilllander.naunginlecalendar.model.YearInnerGridItem;
import com.hilllander.naunginlecalendar.model.YearMainGridItem;
import com.hilllander.naunginlecalendar.util.listener.YearViewHolderListener;
import com.hilllander.naunginlecalendar.util.viewholder.YearOuterViewHolder;

import java.util.ArrayList;

/**
 * Created by khunzohn on 11/12/15.
 */
public class YearOuterAdapter extends RecyclerView.Adapter<YearOuterViewHolder> {
    private static final String TAG = YearOuterAdapter.class.getSimpleName();
    private ArrayList<YearMainGridItem> items;
    private Context context;
    private YearOuterViewHolder ovHolder;
    private YearViewHolderListener vhListener;

    public YearOuterAdapter(Context context, YearViewHolderListener vlistener,
                            ArrayList<YearMainGridItem> mainGridItems, YearOuterViewHolder oHolder) {
        ovHolder = oHolder;
        vhListener = vlistener;
        items = mainGridItems;
        this.context = context;

    }


    @Override
    public YearOuterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (ovHolder == null) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.year_grid_item, parent, false);
            ovHolder = new YearOuterViewHolder(context, view);
            vhListener.onOuterViewHolderCreated(ovHolder);
        }
        return ovHolder;
    }


    @Override
    public void onBindViewHolder(YearOuterViewHolder holder, int position) {
        YearMainGridItem item = items.get(position);
        holder.month.setText(item.getMonthHeader());
        ArrayList<YearInnerGridItem> innerItems = item.getInnerGridItems();
        YearInnerAdapter innerAdapter = new YearInnerAdapter(context, innerItems);
        holder.innerRecy.setAdapter(innerAdapter);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
