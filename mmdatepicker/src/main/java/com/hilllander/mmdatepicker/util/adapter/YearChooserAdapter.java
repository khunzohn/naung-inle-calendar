package com.hilllander.mmdatepicker.util.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hilllander.mmdatepicker.R;
import com.hilllander.mmdatepicker.ui.fragment.MyanmarDatePickerDialog;

import java.util.ArrayList;

import mm.technomation.mmtext.MMButtonView;

public class YearChooserAdapter extends RecyclerView.Adapter<YearChooserAdapter.ViewHolder> {
    private ArrayList<String> years;

    public YearChooserAdapter(MyanmarDatePickerDialog parent, ArrayList<String> years) {
        this.years = years;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.year_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String mYear = years.get(position);
        holder.year.setMyanmarText(mYear);
    }

    @Override
    public int getItemCount() {
        return years.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MMButtonView year;

        public ViewHolder(View itemView) {
            super(itemView);
            year = (MMButtonView) itemView.findViewById(R.id.year);
        }
    }
}
