package com.hilllander.naunginlecalendar.util.viewholder;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hilllander.naunginlecalendar.R;

/**
 * Created by khunzohn on 11/20/15.
 */
public class YearOuterViewHolder extends RecyclerView.ViewHolder {
    public TextView month;
    public RecyclerView innerRecy;

    public YearOuterViewHolder(Context context, View view) {
        super(view);
        month = (TextView) view.findViewById(R.id.year_item_month_header);
        innerRecy = (RecyclerView) view.findViewById(R.id.year_inner_recycler);
        GridLayoutManager innerLayout = new GridLayoutManager(context, 7, LinearLayoutManager.VERTICAL, false);
        innerRecy.setHasFixedSize(true);
        innerRecy.setLayoutManager(innerLayout);
    }
}
