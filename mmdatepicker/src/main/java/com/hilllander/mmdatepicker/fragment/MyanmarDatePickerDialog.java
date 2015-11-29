package com.hilllander.mmdatepicker.fragment;


import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.hilllander.mmdatepicker.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyanmarDatePickerDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyanmarDatePickerDialog extends DialogFragment {

    private static final String TITLE = "title";
    private String title;

    public MyanmarDatePickerDialog() {
        // Required empty public constructor
    }

    public static MyanmarDatePickerDialog newInstance(String title) {
        MyanmarDatePickerDialog fragment = new MyanmarDatePickerDialog();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(TITLE);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getContext());
        dialog.setTitle(title);
        dialog.setContentView(R.layout.dialog_mdate_picker);
        return dialog;
    }
}
