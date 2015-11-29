package com.hilllander.mmdatepicker.dialog;

import android.app.Dialog;
import android.content.Context;

/**
 * Created by khunzohn on 11/29/15.
 */
public class MDialog extends Dialog {
    public MDialog(Context context) {
        super(context);
    }

    public MDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected MDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

}
