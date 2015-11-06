/*
 * Copyright (c) The code is developed  by Hilllander. You can modify or use some or the whole piece of code with no limitation. Feel free to do whatever you favour.Good luck!Hilllander team.
 */

package com.hilllander.calendar_api.kernel;

import android.content.Context;

/**
 * kernel to calculate market days
 * Created by khunzohn on 10/26/15.
 */
public class MarketDayKernel {
    public static String[][] MARKET_DAYS = {
            new String[]{"ေတာင္ျကီးေစ်း", "ေအာင္ပန္းေစ်း", "ေက်ာက္တလံုးေစ်း", "နမ့္ခုတ္ေစ်း", "ရြာမေရေပါေစ်း",
                    "မိုင္းပ်ိးေစ်း", "ပင္ခြန္ေစ်း", "လံုပိုးေစ်း", "ျမိုင္ေစ်း", "ဆတ္ေသေစ်း"},

            new String[]{"မိုင္းေသာက္ေစ်း", "ပင္ေလာင္းေစ်း", "နန္းတိုင္းေစ်း", "ေတာင္နီေစ်း", "ေဖာင္ေတာ္ဦးေစ်း",
                    "ဘန္းယဥ္ေစ်း", "ေပြးလွေစ်း", "ေဘာ္ဆိုင္းေစ်း", "ေကာင္းဘို့ေစ်း"},

            new String[]{"ေရြေညာင္ေစ်း", "အင္းတိမ္ေစ်း", "ေခါင္တိုင္ေစ်း", "ေမာ္ဘီ(ဝါးေတာ)ေစ်း",
                    "ဖယ္ခံုေစ်း", "ပင္ျဖစ္ေစ်း", "ဇလဲေစ်း", "ေနာင္မြန္ေစ်း",
                    "ေနာင္ကားေစ်း", "ကေလာေစ်း", "ပင္လံုေစ်း", "ထီယြန္ေစ်း"},

            new String[]{"ေညာင္ေရြေစ်း", "ဟိုပံုးေစ်း", "နန္းပန္ေစ်း", "ရြာငံေစ်း", "ရပ္ေစာက္ေစ်း",
                    "ဘန္ကဲြေစ်း", "ပင္းတယေစ်း", "ဆီဆိုင္ေစ်း", "ေဆာင္းေျပာင္းေစ်း",
                    "တီက်စ္ေစ်း", "ဟမ္းဆီးေစ်း", "စံကားေစ်း", "မိုးျဗဲေစ်း", "မိုင္းပြန္ေစ်း", "လဲခ်ားေစ်း"},

            new String[]{"ဟဲဟိုးေစ်း", "ေနာင္ဝိုးေစ်း", "ဆိုက္ေခါဝ္ေစ်း", "နားေဗာင္ေစ်း", "ေဆာင္းဖိုးေစ်း",
                    "သန္းေတာင္ေစ်း", "ေတာင္တိုေစ်း", "နမ့္ဆီးေစ်း", "ျမင္းက်ဒိုးေစ်း",
                    "က်ံးေစ်း", "လိြုင္ေကာ္ေစ်း", "နမ့္စန္ေစ်း"}

    };

    public String[] getMarketDayList(double curJd, Context context) {
        int marketDayIndex = (int) curJd % 5;
        return MARKET_DAYS[marketDayIndex];
    }
}
