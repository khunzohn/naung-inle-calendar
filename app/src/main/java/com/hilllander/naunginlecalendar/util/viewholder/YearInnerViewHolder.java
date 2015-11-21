package com.hilllander.naunginlecalendar.util.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hilllander.naunginlecalendar.R;

/**
 * Created by khunzohn on 11/20/15.
 */
public class YearInnerViewHolder extends RecyclerView.ViewHolder {
    public TextView innerGridDay;

    public YearInnerViewHolder(View view) {
        super(view);
        innerGridDay = (TextView) view.findViewById(R.id.innerGridDay);
    }
}
