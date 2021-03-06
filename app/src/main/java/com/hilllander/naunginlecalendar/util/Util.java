package com.hilllander.naunginlecalendar.util;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.hilllander.naunginlecalendar.R;

import java.util.Random;

/**
 * Created by khunzohn on 12/4/15.
 */
public class Util {

    private static final String APP_ID = "com.hilllander.naunginlecalendar";

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

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = context.getResources().getDimensionPixelSize(resId);
        } else {
            Toast.makeText(context, "somthing wrong in getting status bar height", Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    public static void setStatusBarPaddingForKitkat(Context context, View mainLayout) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // only for lollipop and newer versions
            mainLayout.setPadding(0, getStatusBarHeight(context), 0, 0);
        }
    }

    public static void setSystemUiVisibilityForKitkat(Activity context) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // only for lollipop and newer versions
            context.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        }
    }

    public static void rateMe(Context context) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse("market://details?id=" + APP_ID));
        if (!startRateMeIntent(context, i)) {
            i.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + APP_ID));
            if (!startRateMeIntent(context, i)) {
                Toast.makeText(context,
                        "Can't start android market.Please install play store app", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private static boolean startRateMeIntent(Context context, Intent i) {
        try {
            context.startActivity(i);
            return true;
        } catch (ActivityNotFoundException e) {
            return false;
        }
    }
}
