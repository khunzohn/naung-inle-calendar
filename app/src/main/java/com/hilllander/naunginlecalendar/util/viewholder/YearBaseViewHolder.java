package com.hilllander.naunginlecalendar.util.viewholder;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hilllander.naunginlecalendar.R;
import com.hilllander.naunginlecalendar.model.YearMainGridItem;
import com.hilllander.naunginlecalendar.util.adapter.YearOuterAdapter;
import com.hilllander.naunginlecalendar.util.listener.YearViewHolderListener;

import java.util.ArrayList;

/**
 * Created by khunzohn on 11/20/15.
 */
public class YearBaseViewHolder implements YearViewHolderListener {
    private View rootView;
    private YearOuterViewHolder oHolder;
    private TextView current;
    private RecyclerView mainRecy;
    private Context context;

    public YearBaseViewHolder(Context context, View view) {
        this.context = context;
        rootView = view;
        mainRecy = (RecyclerView) view.findViewById(R.id.main_recycler_year);
        mainRecy.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false);
        mainRecy.setLayoutManager(layoutManager);
        current = (TextView) view.findViewById(R.id.currentYear);
    }

    public View getRootView() {
        return rootView;
    }

    public TextView getCurrent() {
        return current;
    }

    public RecyclerView getMainRecy() {
        return mainRecy;
    }

    public void setMainGridItems(ArrayList<YearMainGridItem> mainGridItems) {
        YearOuterAdapter adapter = new YearOuterAdapter(context, this, mainGridItems, oHolder);
        mainRecy.setAdapter(adapter);
    }

    @Override
    public void onOuterViewHolderCreated(YearOuterViewHolder oHolder) {
        this.oHolder = oHolder;
    }

}
