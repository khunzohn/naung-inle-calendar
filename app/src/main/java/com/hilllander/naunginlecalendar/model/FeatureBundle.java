package com.hilllander.naunginlecalendar.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.hilllander.naunginlecalendar.R;

public class FeatureBundle {
    private String version;
    private String[] features;

    public FeatureBundle(String version, String[] features) {
        this.version = version;
        this.features = features;

    }

    public String getVersion() {
        return version;
    }

    public ListAdapter getAdapter() {
        return new FeatureListAdapter();
    }

    private class FeatureListAdapter extends BaseAdapter {
        private ViewHolder holder;

        @Override
        public int getCount() {
            return features.length;
        }

        @Override
        public Object getItem(int i) {
            return features[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View v = view;
            if (v == null) {
                v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.feature_item, viewGroup, false);
                holder = new ViewHolder(v);
                v.setTag(holder);
            } else {
                holder = (ViewHolder) v.getTag();
            }
            holder.tvFeature.setText(features[i]);
            return v;
        }

        class ViewHolder {
            TextView tvFeature;

            ViewHolder(View view) {
                tvFeature = (TextView) view.findViewById(R.id.tvFeature);
            }
        }
    }
}
