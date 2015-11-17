/*
 * Copyright (c) The code is developed  by Hilllander.
 * You can modify or use some or the whole piece of code with no limitation.
 * Feel free to do whatever you favour.Good luck!
 * Hilllander team.
 */

package com.hilllander.calendar_api.kernel;

import com.hilllander.calendar_api.model.AstroDetail;
import com.hilllander.calendar_api.model.MyanmarDate;
import com.hilllander.calendar_api.model.WesternDate;
import com.hilllander.calendar_api.util.Constant;

/**
 * Core kernel of Myanmar calendar apis based on the open sourced
 * Myanmar calendar algorithm by Ko Yan Naing Aye (http://www.cool-emerald.com).
 * Created by khunzohn on 10/18/15.
 * Modified by khunzohn on 10/25/15.
 */
public class CalendarKernel {

    /**
     * Time to Fraction of day starting from 12 noon
     *
     * @param h hour
     * @param m minute
     * @param s second
     * @return fraction of day
     */
    public double time2DayFraction(double h, double m, double s) {
        return (((h - 12) / 24) + (m / 1440) + (s / 86400));
    }

    /**
     * get Buddhist year of specified myanmar year
     *
     * @param my Myanmar year
     * @return Buddhist year
     */
    public int getBuddhaYear(int my) {
        return my + 1182;
    }

    public double getSecondWasoDay(final int my) {

        int era = my < 1100 ? 0 : my < 1217 ? 1 : my < 1312 ? 2 : 3;
        int[] numOfMonths = {-1, -1, 4, 8};
        int numOfMonth = numOfMonths[era];
        float wasoOffsets[] = {-1.1f, -0.85f, -1f, -0.5f};
        float wasoOffset = wasoOffsets[era];

        double threshold = (Constant.SY / 12 - Constant.LM) * (12 - numOfMonth);
        double excessDays = (Constant.SY * (my + 3739)) % Constant.LM;
        if (excessDays < threshold)
            excessDays += Constant.LM;
        double secondWaso = Math.round(Constant.SY * my + Constant.MO - excessDays + 4.5 * Constant.LM + wasoOffset);

        int offset = getUWYOffset(my, era);
        secondWaso += offset;
        return secondWaso;
    }

    private int getUWYOffset(int my, int era) {

        if (era == 0) { // Old time. use data based on cool emerald's findings
            switch (my) {
                case 205:
                    return 1;
                case 246:
                    return 1;
                case 572:
                    return -1;
                case 651:
                    return 1;
                case 653:
                    return 2;
                case 656:
                    return 1;
                case 672:
                    return 1;
                case 729:
                    return 1;
                case 767:
                    return -1;
                case 813:
                    return -1;
                case 849:
                    return -1;
                case 851:
                    return -1;
                case 854:
                    return -1;
                case 1039:
                    return -1;
                default:
                    return 0;
            }
        } else { // modern era
            switch (my) {
                case 1120:
                    return 1;
                case 1126:
                    return -1;
                case 1150:
                    return 1;
                case 1172:
                    return -1;
                case 1207:
                    return 1;
                case 1234:
                    return 1;
                case 1261:
                    return -1;
                case 1377:
                    return 1;
                default:
                    return 0;
            }

        }
    }

    private double getEd(int my) {
        return (Constant.SY * (my + 3739)) % Constant.LM;
    }

    /**
     * check for type of myanmar year
     *
     * @param my Myanmar year
     * @return year type [0-normal ,1-small watat, 2-big watat]
     */
    public int checkMYearType(int my) {
        return isWatat(my) ? isWatatGyee(my) ? 2 : 1 : 0;
    }

    /**
     * check if the specified Myanmar year is watat
     *
     * @param my Myanmar year
     * @return true if the specified year is watat
     */
    public boolean isWatat(int my) {
        double ed = getEd(my); // excess days
        if (my >= Constant.S3E) { //Third Myanmar Era
            if (ed < Constant.TA3) {
                ed = ed + Constant.LM;
            }
            if ((ed >= Constant.TW && my != 1345) || my == 1344)
                return true;
        } else if (my >= Constant.S2E && my < Constant.S3E) { //Second Myanmar era
            if (ed < Constant.TA2) {
                ed = ed + Constant.LM;
            }
            if ((ed >= Constant.TW && my != 1264 && my != 1260)) //TODO replace quick fix here 1260
                return true;
        } else if (my < Constant.S2E) { //First Myanmar Era

            int watat = (my * 7 + 2) % 19;
            if (watat < 0) {
                watat += 19;
            }
            watat = (int) Math.floor(watat / 12);
            if ((watat == 1 && my != 1202) || my == 1201)
                return true;
        }
        return false;
    }

    /**
     * check if the specified Myanmar year is watatgyee
     *
     * @param my Myanmar year
     * @return true if the specified year is watatgyee
     */
    private boolean isWatatGyee(final int my) {
        int y1;
        double diff;
        boolean watat = isWatat(my);
        double bigWatat = 0;
        int counter = 0;
        do {
            counter++;
            y1 = my - counter;
        } while (!isWatat(y1) && counter < 3);
        if (watat) {
            diff = (getSecondWasoDay(my) - getSecondWasoDay(y1)) % 354;
            bigWatat = Math.floor(diff / 31);
        }
        return bigWatat > 0 || my == 1299 || my == 1272; // the last two is my own exceptions
    }

    /**
     * calculate first day of tagu
     *
     * @param my Myanmar year
     * @return first day of tagu in julian date
     */
    public double getFirstTagu(final int my) {
        int prevWatatYear = my;
        int counter = 0;
        do {
            counter++;
            prevWatatYear--;
        } while (!isWatat(prevWatatYear) && counter < 3);
        return getSecondWasoDay(prevWatatYear) + 354 * counter - 102;
    }

    /**
     * change julian date to Myanmar date
     *
     * @param jd julian date
     * @return {@link MyanmarDate} object
     */
    public MyanmarDate J2M(double jd) {
        double jdn = Math.round(jd);//convert jd to jdn
        int year = (int) Math.floor((jdn - 0.5 - Constant.MO) / Constant.SY); // myanmar year
        int yearType = checkMYearType(year);
        double firstTagu = getFirstTagu(year);
        double dayCount = jdn - firstTagu + 1;
        int yearLength = 354 + (isWatat(year) ? 1 : 0) * 30 + (isWatatGyee(year) ? 1 : 0);
        int monthType = (int) Math.floor((dayCount - 1) / yearLength); // 1 : hnaung , 0 : Oo
        dayCount -= monthType * yearLength; //adjust day count
        double t = Math.floor(yearLength / (dayCount + 266));
        double s = 29.5 + t * (isWatatGyee(year) ? 1 : 0) / 5;
        double c = 117 + t * (isWatatGyee(year) ? 1 : 0) * 14 / 5; // get rate and offset
        dayCount += t * 266 - (1 - t) * (yearLength - 266); // modify day count
        int month = (int) Math.floor((dayCount + c) / s); //month
        int day = (int) (dayCount - Math.floor(s * month - c - 0.1)); //day
        month = (month % 16);
        month -= 12 * Math.floor(month / 13); //correct month number
        int monthLength = 30 - month % 2;
        if (month == 3) monthLength += isWatatGyee(year) ? 1 : 0; // adjust nayon in big watat
        int monthStatus = (int) (Math.floor((day + 1) / 16)   //waxing, 1: full moon, 2: waning, 3: new moon],
                + Math.floor(day / 16) + Math.floor(day / monthLength));
        int d = (int) (day - 15 * Math.floor(day / 16)); // waning or waxing day
        int weekday = (int) (jdn + 2) % 7;
        return MyanmarDate.buildDate()
                .setYear(year)
                .setMonth(month)
                .setDay(day)
                .setWanWaxDay(d)
                .setYearType(yearType)
                .setYearLength(yearLength)
                .setMonthType(monthType)
                .setMonthLength(monthLength)
                .setMonthStatus(monthStatus)
                .setWeekday(weekday);
    }

    /**
     * change myanmar date to julian date
     *
     * @param mYear   myanmar year
     * @param mMonth  myanmar month [sec_waso=0,tagu=1,...,tapaung=12]
     * @param mType   month type [1=hnaung, 0= Oo]
     * @param mStatus month status [0: waxing, 1: full moon, 2: waning, 3: new moon]
     * @param wanWaxDay     wanWaxDay [1-14||15]
     * @return julian date
     */
    public double M2J(int mYear, int mMonth, int mType, int mStatus, int wanWaxDay) {
        int monthLength = 30 - mMonth % 2;
        if (mMonth == 3)
            monthLength += (isWatatGyee(mYear) ? 1 : 0); // adjust if Nayon in big watat
        int m1 = mStatus % 2;
        int m2 = (int) Math.floor(mStatus / 2);
        int md = m1 * (15 + m2 * (monthLength - 15)) + (1 - m1) * (wanWaxDay + 15 * m2);
        mMonth += 4 * Math.floor((16 - mMonth) / 16) + 12 * Math.floor((15 - mMonth) / 12);
        double t = Math.floor(mMonth / 13);
        double s = 29.5 + t * (isWatatGyee(mYear) ? 1 : 0) / 5;
        double c = 117 + t * (isWatatGyee(mYear) ? 1 : 0) * 14 / 5;
        int numOfDays = (int) (md + Math.floor(s * mMonth - c - 0.1));
        int yearLength = 354 + (isWatat(mYear) ? 1 : 0) * 30 + (isWatatGyee(mYear) ? 1 : 0);
        numOfDays += (1 - t) * (yearLength - 266) - 266 * t;
        numOfDays += mType * yearLength;//adjust wanWaxDay count
        return numOfDays + getFirstTagu(mYear) - 1;
    }

    /**
     * change western date to julian date
     *
     * @param year    western year
     * @param month   month [Jan=1, ... , Dec=12]
     * @param day     day [0-31]
     * @param calType [0-English, 1-Gregorian, 2-Julian]
     * @return julian date
     */
    public double W2J(int year, int month, int day, int calType) {
        calType = calType > 0 ? calType : 0;
        double SG = 2361222; //Gregorian start in English calendar (1752/Sep/14)
        int a = (int) Math.floor((14 - month) / 12);
        year = year + 4800 - a;
        month = month + (12 * a) - 3;
        double jd = day + Math.floor((153 * month + 2) / 5) + (365 * year) + Math.floor(year / 4);
        if (calType == 1) jd = jd - Math.floor(year / 100) + Math.floor(year / 400) - 32045;
        else if (calType == 2) jd = jd - 32083;
        else {
            jd = jd - Math.floor(year / 100) + Math.floor(year / 400) - 32045;
            if (jd < SG) {
                jd = day + Math.floor((153 * month + 2) / 5) + (365 * year) + Math.floor(year / 4) - 32083;
                if (jd > SG) jd = SG;
            }
        }
        return jd;
    }

    /**
     * change julian date western date
     *
     * @param jd      julian date
     * @param caltype calendar type [0-English, 1-Gregorian, 2-Julian]
     * @return {@link WesternDate} object
     */
    public WesternDate J2W(double jd, int caltype) {
        caltype = caltype > 0 ? caltype : 0;
        double SG = 2361222;//Gregorian start in English calendar (1752/Sep/14)
        double j, jf;
        int y, m, d, h, n, s;
        if (caltype == 2 || (caltype == 0 && (jd < SG))) {
            double b, c, f, e;
            j = Math.floor(jd + 0.5);
            jf = jd + 0.5 - j;
            b = j + 1524;
            c = Math.floor((b - 122.1) / 365.25);
            f = Math.floor(365.25 * c);
            e = Math.floor((b - f) / 30.6001);
            m = (int) ((e > 13) ? (e - 13) : (e - 1));
            d = (int) (b - f - Math.floor(30.6001 * e));
            y = (int) (m < 3 ? (c - 4715) : (c - 4716));
        } else {
            j = Math.floor(jd + 0.5);
            jf = jd + 0.5 - j;
            j -= 1721119;
            y = (int) (Math.floor((4 * j - 1) / 146097));
            j = 4 * j - 1 - 146097 * y;
            d = (int) Math.floor(j / 4);
            j = Math.floor((4 * d + 3) / 1461);
            d = (int) (4 * d + 3 - 1461 * j);
            d = (int) Math.floor((d + 4) / 4);
            m = (int) Math.floor((5 * d - 3) / 153);
            d = 5 * d - 3 - 153 * m;
            d = (int) Math.floor((d + 5) / 5);
            y = (int) (100 * y + j);
            if (m < 10) {
                m += 3;
            } else {
                m -= 9;
                y = y + 1;
            }
        }
        jf *= 24;
        h = (int) Math.floor(jf);
        jf = (jf - h) * 60;
        n = (int) Math.floor(jf);
        s = (int) (jf - n) * 60;
        return WesternDate.buildDate()
                .setYear(y)
                .setMonth(m)
                .setDay(d)
                .setHour(h)
                .setMin(n)
                .setSec(s);
    }

    /**
     * Checking Astrological days
     *
     * @param month       myanmar month
     * @param monthLength month length
     * @param dayOfMonth  day of month [0-30]
     * @param weekday     weekday[sun=1,....,fri=6,sat=0]
     * @return {@link AstroDetail} that bundles all astrological info
     */
    public AstroDetail checkAstroDetail(int month, int monthLength, int dayOfMonth, int weekday) {
        int d, sabbath, sabbatheve, yatyaza, pyathada, thamanyo, amyeittasote;
        int warameittugyi, warameittunge, yatpote, thamaphyu, nagapor, yatyotema;
        int mahayatkyan, shanyat, nagahle, m1, wd1, wd2;
        int[] wda;
        int[] sya;
        if (month <= 0) month = 4;//first waso is considered waso
        d = (int) (dayOfMonth - 15 * Math.floor(dayOfMonth / 16));//waxing or waning day [0-15]
        sabbath = 0;
        if ((dayOfMonth == 8) || (dayOfMonth == 15) || (dayOfMonth == 23) || (dayOfMonth == monthLength))
            sabbath = 1;
        sabbatheve = 0;
        if ((dayOfMonth == 7) || (dayOfMonth == 14) || (dayOfMonth == 22) || (dayOfMonth == (monthLength - 1)))
            sabbatheve = 1;
        yatyaza = 0;
        m1 = month % 4;
        wd1 = (int) (Math.floor(m1 / 2) + 4);
        wd2 = (int) ((1 - Math.floor(m1 / 2)) + m1 % 2) * (1 + 2 * (m1 % 2));
        if ((weekday == wd1) || (weekday == wd2)) yatyaza = 1;
        pyathada = 0;
        wda = new int[]{1, 3, 3, 0, 2, 1, 2};
        if (m1 == wda[weekday]) pyathada = 1;
        if ((m1 == 0) && (weekday == 4)) pyathada = 2;//afternoon pyathada
        thamanyo = 0;
        m1 = (int) (month - 1 - Math.floor(month / 9));
        wd1 = (int) (m1 * 2 - Math.floor(m1 / 8)) % 7;
        wd2 = (weekday + 7 - wd1) % 7;
        if (wd2 <= 1) thamanyo = 1;
        amyeittasote = 0;
        wda = new int[]{5, 8, 3, 7, 2, 4, 1};
        if (d == wda[weekday]) amyeittasote = 1;
        warameittugyi = 0;
        wda = new int[]{7, 1, 4, 8, 9, 6, 3};
        if (d == wda[weekday]) warameittugyi = 1;
        warameittunge = 0;
        int wn = (weekday + 6) % 7;
        if ((12 - d) == wn) warameittunge = 1;
        yatpote = 0;
        wda = new int[]{8, 1, 4, 6, 9, 8, 7};
        if (d == wda[weekday]) yatpote = 1;
        thamaphyu = 0;
        wda = new int[]{1, 2, 6, 6, 5, 6, 7};
        if (d == wda[weekday]) thamaphyu = 1;
        wda = new int[]{0, 1, 0, 0, 0, 3, 3};
        if (d == wda[weekday]) thamaphyu = 1;
        if ((d == 4) && (weekday == 5)) thamaphyu = 1;
        nagapor = 0;
        wda = new int[]{26, 21, 2, 10, 18, 2, 21};
        if (dayOfMonth == wda[weekday]) nagapor = 1;
        wda = new int[]{17, 19, 1, 0, 9, 0, 0};
        if (dayOfMonth == wda[weekday]) nagapor = 1;
        if (((dayOfMonth == 2) && (weekday == 1)) || (((dayOfMonth == 12) || (dayOfMonth == 4) || (dayOfMonth == 18)) && (weekday == 2)))
            nagapor = 1;
        yatyotema = 0;
        m1 = (month % 2 > 0) ? month : ((month + 9) % 12);
        m1 = (m1 + 4) % 12 + 1;
        if (d == m1) yatyotema = 1;
        mahayatkyan = 0;
        m1 = (int) ((Math.floor((month % 12) / 2) + 4) % 6 + 1);
        if (d == m1) mahayatkyan = 1;
        shanyat = 0;
        sya = new int[]{8, 8, 2, 2, 9, 3, 3, 5, 1, 4, 7, 4};
        if (d == sya[month - 1]) shanyat = 1;
        nagahle = (int) Math.floor((month % 12) / 3);

        return AstroDetail.buildDate()
                .setSabbath(sabbath)
                .setSabbatheve(sabbatheve)
                .setYatyaza(yatyaza)
                .setPyathada(pyathada)
                .setThamanyo(thamanyo)
                .setAmyeittasote(amyeittasote)
                .setWarameittugyi(warameittugyi)
                .setWarameittunge(warameittunge)
                .setYatpote(yatpote)
                .setThamaphyu(thamaphyu)
                .setNagapor(nagapor)
                .setYatyotema(yatyotema)
                .setMahayatkyan(mahayatkyan)
                .setShanyat(shanyat)
                .setNagahle(nagahle);
    }

    /**
     * find length of western month
     *
     * @param year    western year
     * @param month   month [Jan=1, ... , Dec=12]
     * @param calType calender type [0-English, 1-Gregorian, 2-Julian])
     * @return length of the month
     */
    public int westMonthLength(int year, int month, int calType) {
        int leap = 0;
        int monthLength = (int) (30 + (month + Math.floor(month / 8)) % 2);//length of the current month
        if (month == 2) { //if  february
            if (calType == 1 || (calType == 0 && year > 1752)) {
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) leap = 1;
            } else if (year % 4 == 0) leap = 1;
            monthLength += leap - 2;
        }
        if (year == 1752 && month == 9 && calType == 0) monthLength = 19;
        return monthLength;
    }
}
