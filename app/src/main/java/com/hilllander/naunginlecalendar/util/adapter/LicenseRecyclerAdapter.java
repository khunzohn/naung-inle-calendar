package com.hilllander.naunginlecalendar.util.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hilllander.naunginlecalendar.R;
import com.hilllander.naunginlecalendar.model.LicenseObject;

import java.util.ArrayList;

/**
 * Created by khunzohn on 11/21/15.
 */
public class LicenseRecyclerAdapter extends RecyclerView.Adapter<LicenseRecyclerAdapter.ViewHolder> {
    private Context context;
    private ArrayList<LicenseObject> items;

    public LicenseRecyclerAdapter(Context context, ArrayList<LicenseObject> items) {
        this.items = items;
        this.context = context;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.license_item, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        LicenseObject item = items.get(position);
        holder.licence.setText(item.getLicense());
        holder.dependency.setText(Html.fromHtml(item.getDependency()));

    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dependency;
        TextView licence;

        public ViewHolder(View itemView) {
            super(itemView);
            dependency = (TextView) itemView.findViewById(R.id.dependency);
            dependency.setMovementMethod(LinkMovementMethod.getInstance());
            licence = (TextView) itemView.findViewById(R.id.license);
        }
    }
}
