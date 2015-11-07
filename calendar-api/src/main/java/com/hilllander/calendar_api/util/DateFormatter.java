/*
 * Copyright (c) The code is developed  by Hilllander. You can modify or use some or the whole piece of code with no limitation. Feel free to do whatever you favour.Good luck!Hilllander team.
 */

package com.hilllander.calendar_api.util;

import android.content.Context;

import com.hilllander.calendar_api.R;
import com.hilllander.calendar_api.model.AstroDetail;
import com.hilllander.calendar_api.model.WesternDate;

/**
 * Created by khunzohn on 10/19/15.
 */
public class DateFormatter {
    private static final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    public static String format(WesternDate date) {
        if (date != null)
            return date.getYear() + "-" +
                    getMonthAsString(date.getMonth()) + "-" +
                    date.getDay() + " " +
                    date.getH() + ":" +
                    date.getM() + ":" +
                    date.getS();
        else
            return null;
    }

    public static String getMonthAsString(final int month) {
        return MONTHS[month - 1];
    }

    public static String formatMDate(int year, int month, int day) {
        return year + " " + month + " " + day;
    }

    public static String[] formatAstroDetail(AstroDetail detail, Context context) {
        String astro = "";
        astro += context.getString(R.string.nagakhaung) +
                (detail.getNagahle() == 0 ? context.getString(R.string.west) : detail.getNagahle() == 1 ?
                        context.getString(R.string.north) : detail.getNagahle() == 2 ?
                        context.getString(R.string.east) : context.getString(R.string.south))
                + context.getString(R.string.turns) + " "; //at index 0 for easy handling
        astro += detail.getYatyotema() == 1 ? context.getString(R.string.yatyotema) + " " : "";
        astro += detail.getAmyeittasote() == 1 ? context.getString(R.string.amyeittasote) + " " : "";
        astro += detail.getMahayatkyan() == 1 ? context.getString(R.string.mahayatkyan) + " " : "";
        astro += detail.getNagapor() == 1 ? context.getString(R.string.nagapor) + " " : "";
        astro += detail.getPyathada() == 2 ? context.getString(R.string.afternoon_pyathada) + " " :
                detail.getPyathada() == 1 ? context.getString(R.string.pyathada) + " " : "";
        astro += detail.getSabbath() == 1 ? context.getString(R.string.sabbath) + " " : "";
        astro += detail.getSabbatheve() == 1 ? context.getString(R.string.sabbatheve) + " " : "";
        astro += detail.getShanyat() == 1 ? context.getString(R.string.shanyat) + " " : "";
        astro += detail.getThamanyo() == 1 ? context.getString(R.string.thamanyo) + " " : "";
        astro += detail.getThamaphyu() == 1 ? context.getString(R.string.thamaphyu) + " " : "";
        astro += detail.getWarameittugyi() == 1 ? context.getString(R.string.warameitthugyi) + " " : "";
        astro += detail.getWarameittunge() == 1 ? context.getString(R.string.warameitthunge) + " " : "";
        astro += detail.getYatpote() == 1 ? context.getString(R.string.yatpote) + " " : "";
        astro += detail.getYatyaza() == 1 ? context.getString(R.string.yatyaza) + " " : "";

        return astro.split(" ");
    }
}
