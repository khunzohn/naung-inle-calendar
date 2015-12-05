package com.hilllander.naunginlecalendar.view.fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hilllander.naunginlecalendar.R;

import mm.technomation.mmtext.MMButtonView;

public class FeedbackDialogFragment extends DialogFragment {
    private static final String[] EMAIL = {"khunzohn@gmail.com"};
    EditText subject;
    private EditText body;

    public FeedbackDialogFragment() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.feedback_dialog);
        dialog.setTitle("Send feedback");
        MMButtonView send = (MMButtonView) dialog.findViewById(R.id.send);
        MMButtonView cancel = (MMButtonView) dialog.findViewById(R.id.cancel);
        subject = (EditText) dialog.findViewById(R.id.subject);
        body = (EditText) dialog.findViewById(R.id.bodyText);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendFeedBack(getContext(), dialog);
            }
        });
        return dialog;
    }

    private void sendFeedBack(Context context, Dialog dialog) {
        String sub = subject.getText().toString();
        String bod = body.getText().toString();
        if (!sub.equals("") && !bod.equals("")) {
            Intent i = new Intent(android.content.Intent.ACTION_SEND);
            i.setType("text/html");
            i.putExtra(android.content.Intent.EXTRA_EMAIL, EMAIL);
            i.putExtra(Intent.EXTRA_SUBJECT, sub);
            i.putExtra(Intent.EXTRA_TEXT, bod);
            context.startActivity(Intent.createChooser(i, "send feedback"));

        } else {
            Toast.makeText(context, "Please fill in both subject and body text.", Toast.LENGTH_SHORT).show();
        }

    }
}
