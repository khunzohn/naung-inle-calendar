package com.hilllander.naunginlecalendar.util.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.hilllander.naunginlecalendar.R;
import com.hilllander.naunginlecalendar.model.FeatureBundle;

import java.util.ArrayList;

/**
 * Created by khunzohn on 12/5/15.
 */
public class WhatNewAdapter extends RecyclerView.Adapter<WhatNewAdapter.ViewHolder> {
    private static final String TAG = WhatNewAdapter.class.getSimpleName();
    private ArrayList<FeatureBundle> bundles;

    public WhatNewAdapter(ArrayList<FeatureBundle> bundles) {
        this.bundles = bundles;
        Log.d(TAG, "bundles size " + bundles.size());
    }

    @Override
    public WhatNewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.what_new_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FeatureBundle bundle = bundles.get(position);
        Log.d(TAG, bundle.getVersion());
        holder.tvVersion.setText(bundle.getVersion());
        holder.lvFeatures.setAdapter(bundle.getAdapter());
    }

    @Override
    public int getItemCount() {
        return bundles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvVersion;
        ListView lvFeatures;

        public ViewHolder(View itemView) {
            super(itemView);
            tvVersion = (TextView) itemView.findViewById(R.id.tvVersion);
            lvFeatures = (ListView) itemView.findViewById(R.id.lvFeatures);
        }
    }
}
