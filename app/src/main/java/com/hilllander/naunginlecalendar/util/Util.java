package com.hilllander.naunginlecalendar.util;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hilllander.naunginlecalendar.R;

import java.util.Random;

/**
 * Created by khunzohn on 12/4/15.
 */
public class Util {

    public static int getMainMarketDayBackgroundResId() {
        int backId = new Random().nextInt(4);
        switch (backId) {
            case 0:
                return R.drawable.mm1;
            case 1:
                return R.drawable.mm2;
            case 2:
                return R.drawable.mm3;
            case 3:
                return R.drawable.mm4;
            default:
                return R.drawable.mm1;
        }
    }

    public static void hideToolBarShadowForLollipop(Context context, Toolbar mToolbar, View shadowView) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            // only for lollipop and newer versions
            shadowView.setVisibility(View.GONE);
            mToolbar.setElevation(context.getResources().getDimension(R.dimen.toolbar_elevation_height));
        }
    }
}
