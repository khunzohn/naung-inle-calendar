package com.hilllander.naunginlecalendar.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by khunzohn on 10/9/15.
 */
public class FacebookOpener {
    public static Intent getOpenFacebookIntent(Context context) {
        /*hilllander page id = 405267716349059 */
        try {
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/746688995463780")); // naung inle calendar page id
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/NicheOfHilllander"));
        }
    }
}
