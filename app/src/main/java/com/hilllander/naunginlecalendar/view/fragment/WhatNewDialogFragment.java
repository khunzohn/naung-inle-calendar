package com.hilllander.naunginlecalendar.view.fragment;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.hilllander.naunginlecalendar.R;
import com.hilllander.naunginlecalendar.model.FeatureBundle;
import com.hilllander.naunginlecalendar.util.adapter.WhatNewAdapter;

import java.util.ArrayList;

import mm.technomation.mmtext.MMButtonView;

public class WhatNewDialogFragment extends DialogFragment {


    public WhatNewDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.fragment_what_new_dialog);
        RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.whatIsNewRecycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        WhatNewAdapter adapter = new WhatNewAdapter(getFeatureBundles());
        recyclerView.setAdapter(adapter);
        dialog.setTitle("What\'s new in " + getString(R.string.version) + "?");
        MMButtonView ok = (MMButtonView) dialog.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        return dialog;
    }

    private ArrayList<FeatureBundle> getFeatureBundles() {
        ArrayList<FeatureBundle> bundles = new ArrayList<>();
        String[] versions = getResources().getStringArray(R.array.version);
        Toast.makeText(getContext(), "versions size" + versions.length, Toast.LENGTH_SHORT).show();
        String[] feature_0_1_1 = getResources().getStringArray(R.array.feature_list_0_1_1);
        String[] feature_0_1 = getResources().getStringArray(R.array.feature_list_0_1);
        String[][] featureLists = {feature_0_1_1, feature_0_1};
        for (int i = 0; i < versions.length; i++) {
            bundles.add(new FeatureBundle(versions[i], featureLists[i]));
        }
        return bundles;
    }

}
