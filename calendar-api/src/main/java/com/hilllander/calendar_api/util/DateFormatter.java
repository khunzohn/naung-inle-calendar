/*
 * Copyright (c) The code is developed  by Hilllander. You can modify or use some or the whole piece of code with no limitation. Feel free to do whatever you favour.Good luck!Hilllander team.
 */

package com.hilllander.calendar_api.util;

import com.hilllander.calendar_api.model.AstroDate;
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

    private static String getMonthAsString(final double month) {
        return MONTHS[(int) month - 1];
    }

    public static String formatMDate(int year, int month, int day) {
        return year + " " + month + " " + day;
    }

    public static String formatAstroDate(AstroDate date) {
        String astro = "";
        astro += date.getYatyotema() == 1 ? "yatyotema " : "";
        astro += date.getAmyeittasote() == 1 ? "amyeittasote " : "";
        astro += date.getMahayatkyan() == 1 ? "mahayatkyan " : "";
        astro += "nagahle " +
                (date.getNagahle() == 0 ? "west " : date.getNagahle() == 1 ? "north " : date.getNagahle() == 2 ? "east " : "south ");
        astro += date.getNagapor() == 1 ? "nagapor " : "";
        astro += date.getPyathada() == 2 ? "afternoon pyathada " : date.getPyathada() == 1 ? "pyathada " : "";
        astro += date.getSabbath() == 1 ? "sabbath " : "";
        astro += date.getSabbatheve() == 1 ? "sabbatheve " : "";
        astro += date.getShanyat() == 1 ? "shanyat " : "";
        astro += date.getThamanyo() == 1 ? "thamanyo " : "";
        astro += date.getThamaphyu() == 1 ? "thamaphyu " : "";
        astro += date.getWarameittugyi() == 1 ? "warameittugyi " : "";
        astro += date.getWarameittunge() == 1 ? "warameittunge " : "";
        astro += date.getYatpote() == 1 ? "yatpote " : "";
        astro += date.getYatyaza() == 1 ? "yatyaza " : "";
        String[] astros = astro.split(" ");
        astro = "";
        for (String mastro : astros) {
            astro += mastro + "\n";
        }
        return astro;
    }
}
