/*
 * Copyright (c) The code is developed  by Hilllander. You can modify or use some or the whole piece of code with no limitation. Feel free to do whatever you favour.Good luck!Hilllander team.
 */

package com.hilllander.calendar_api.kernel;

/**
 * kernel to calculate market days
 * Created by khunzohn on 10/26/15.
 */
public class MarketDayKernel {
    public static String[][] MARKET_DAYS = {
            new String[]{"တောင်ကြီးဈေး", "အောင်ပန်းဈေး", "ကျောက်တလုံးဈေး", "နမ့်ခုတ်ဈေး", "ရွာမရေပေါ်ဈေး",
                    "မိုင်းပျိုးဈေး", "ပင်ခွန်ဈေး", "လုံပိုးဈေး", "မြိုင်ဈေး", "ဆတ်သေဈေး"},

            new String[]{"မိုင်းသောက်ဈေး", "ပင်လောင်းဈေး", "နန်းတိုင်းဈေး", "တောင်နီဈေး", "ဖောင်တော်ဉီးဈေး",
                    "ဘန်းယဉ်ဈေး", "ပွေးလှဈေး", "ဘော်ဆိုင်းဈေး", "ကောင်းဘို့ဈေး"},

            new String[]{"ရွှေညောင်ဈေး", "အင်းတိမ်ဈေး", "ခေါင်တိုင်ဈေး", "မော်ဘီ(ဝါးတော)ဈေး",
                    "ဖယ်ခုံဈေး", "ပင်ဖြစ်ဈေး", "ဇလဲဈေး", "နောင်မွန်ဈေး",
                    "နောင်ကားဈေး", "ကလောဈေး", "ပင်လုံဈေး", "ထီယွန်ဈေး"},

            new String[]{"ညောင်ရွေဈေး", "ဟိုပုံးဈေး", "နန်းပန်ဈေး", "ရွာငံဈေး", "ရပ်စောက်ဈေး",
                    "ဘန်ကွဲဈေး", "ပင်းတယဈေး", "ဆီဆိုင်ဈေး", "ဆောင်းပြောင်းဈေး",
                    "တီကျစ်ဈေး", "ဟမ်းဆီးဈေး", "စံကားဈေး", "မိုးဗြဲဈေး", "မိုင်းပွန်ဈေး", "လဲချားဈေး"},

            new String[]{"ဟဲဟိုးဈေး", "နောင်ဝိုးဈေး", "ဆိုက်ခေါဝ်ဈေး", "နားဗောင်ဈေး", "ဆောင်းဖိုးဈေး",
                    "သန်းတောင်ဈေး", "တောင်တိုဈေး", "နမ့်ဆီးဈေး", "မြင်းကျဒိုးဈေး",
                    "ကျုံးဈေး", "လွိုင်ကော်ဈေး", "နမ့်စန်ဈေး"}

    };

    public String[] getMarketDayList(double curJd) {
        int marketDayIndex = (int) curJd % 5;
        return MARKET_DAYS[marketDayIndex];
    }
}
