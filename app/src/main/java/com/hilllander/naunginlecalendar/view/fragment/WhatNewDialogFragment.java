package com.hilllander.naunginlecalendar.view.fragment;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;

import com.hilllander.naunginlecalendar.R;

import mm.technomation.mmtext.MMButtonView;

public class WhatNewDialogFragment extends DialogFragment {


    public WhatNewDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.fragment_what_new_dialog);
        dialog.setTitle("What\'s new?");
        MMButtonView ok = (MMButtonView) dialog.findViewById(R.id.ok);
        MMButtonView feedback = (MMButtonView) dialog.findViewById(R.id.feedback);

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FeedbackDialogFragment feedback = new FeedbackDialogFragment();
                feedback.show(getChildFragmentManager(), "feedback");
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        return dialog;
    }
/*
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
    }*/

}
