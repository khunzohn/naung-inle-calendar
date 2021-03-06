package com.hilllander.calendar_api.calendar;

import android.content.Context;
import android.util.Log;

import com.hilllander.calendar_api.R;
import com.hilllander.calendar_api.kernel.CalendarKernel;
import com.hilllander.calendar_api.kernel.HolidayKernel;
import com.hilllander.calendar_api.kernel.MarketDayKernel;
import com.hilllander.calendar_api.model.AstroDetail;
import com.hilllander.calendar_api.model.MyanmarDate;
import com.hilllander.calendar_api.model.WesternDate;
import com.hilllander.calendar_api.util.DateFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by khunzohn on 11/6/15.
 */
public class MyanmarCalendar {
    public static final int LONG_DAY = 100;
    public static final int SHORT_DAY = 50;
    private static final String TAG = MyanmarCalendar.class.getSimpleName();
    private static MyanmarCalendar mCalendar;
    private final int calType = 1; // Gregorian calendar
    private CalendarKernel calKernel;
    private double curJd;
    private MyanmarDate mDate;
    private WesternDate wDate;
    private int eYear, eMonth, eDay;

    private MyanmarCalendar(GregorianCalendar greCal) {
        Log.d(TAG, "year : " + greCal.get(Calendar.YEAR) + "month : "
                + greCal.get(Calendar.MONTH) + "day : " + greCal.get(Calendar.DAY_OF_MONTH));
        calKernel = new CalendarKernel();

        eYear = greCal.get(Calendar.YEAR);
        eMonth = greCal.get(Calendar.MONTH) + 1;
                eDay = greCal.get(Calendar.DAY_OF_MONTH);

        curJd = calKernel.W2J(eYear, eMonth, eDay, calType);
        mDate = calKernel.J2M(curJd);
        wDate = calKernel.J2W(curJd, calType);
        Log.d(TAG, "mYear :" + mDate.getYear() + "mMonth : " + mDate.getMonth() + "mDay : " + mDate.getWanWaxDay());
    }

    private MyanmarCalendar(double jd) {
        curJd = jd;
        calKernel = new CalendarKernel();
        mDate = calKernel.J2M(curJd);
        wDate = calKernel.J2W(curJd, calType);
        eYear = wDate.getYear();
        eMonth = wDate.getMonth();
        eDay = wDate.getDay();
    }

    private MyanmarCalendar() {
        GregorianCalendar greCal = new GregorianCalendar();
        calKernel = new CalendarKernel();
        eYear = greCal.get(Calendar.YEAR);
        eMonth = greCal.get(Calendar.MONTH) + 1;
                eDay = greCal.get(Calendar.DAY_OF_MONTH);

        curJd = calKernel.W2J(eYear, eMonth, eDay, calType);
        mDate = calKernel.J2M(curJd);
        wDate = calKernel.J2W(curJd, calType);
    }

    public static MyanmarCalendar getInstance() {
        mCalendar = new MyanmarCalendar();
        return mCalendar;
    }

    public static MyanmarCalendar getInstance(double jd) {
        mCalendar = new MyanmarCalendar(jd);
        return mCalendar;
    }

    public static MyanmarCalendar getInstance(GregorianCalendar greCal) {
        mCalendar = new MyanmarCalendar(greCal);
        return mCalendar;
    }

    public int getYear() {
        return mDate.getYear();
    }

    public int getMonth() {
        return mDate.getMonth();
    }

    public int getDay() {
        return mDate.getWanWaxDay();
    }

    public int getWeekDay() {
        return mDate.getWeekday();
    }

    public int getMonthLength() {
        return mDate.getMonthLength();
    }

    public int getMonthStatus() {
        return mDate.getMonthStatus();
    }

    public int getMonthType() {
        return mDate.getMonthType();
    }

    public int getYearLength() {
        return mDate.getYearLength();
    }

    /**
     * check for type of myanmar year
     *
     * @param my myanmar year
     * @return year type [0-normal ,1-small watat, 2-big watat]
     */
    public int checkYearType(int my) {
        return calKernel.checkMYearType(my);
    }

    /**
     * get type of myanmar year
     *
     * @return year type [0-normal ,1-small watat, 2-big watat]
     */
    public int getYearType() {
        return mDate.getYearType();
    }

    public int getWanWaxDay() {
        return mDate.getWanWaxDay();
    }

    public String getMonthInMyanmar(Context context) {
        int monthType = mDate.getMonthType();
        String mStatus = "";
        String mType = "";
        switch (mDate.getMonthStatus()) {
            case 0: // waxing
                mStatus = context.getString(R.string.lasan);
                break;
            case 1: // full moon
                mStatus = context.getString(R.string.lapyae);
                break;
            case 2: // waning
                mStatus = context.getString(R.string.lasote);
                break;
            case 3: // dark moon
                mStatus = context.getString(R.string.lakwae);
                break;
        }
        if (monthType == 1) { // Naung
            mType = context.getString(R.string.naung);
        }
        String[] mMonths = context.getResources().getStringArray(R.array.myan_months);
        String month = mMonths[mDate.getMonth()];
        if (calKernel.isWatat(mDate.getYearType())
                && mDate.getMonth() == 4) {    // waso becomes second waso in watat year
            String temp = month;
            month = context.getString(R.string.du) + temp;
        }
        if (mDate.getMonth() == 1 || mDate.getMonth() == 2) {
            if (monthType == 1) { // naung
                String temp = month;
                month = mType + temp;
            }
        }
        month += mStatus;
        return month;
    }

    public String getYearInMyanmar() {
        return eDigitToMDigit(mDate.getYear());
    }

    /**
     * get wan wax day in myanmar unicode
     * @return wan wax day string in unicode
     */
    public String getDayInMyanmnar() {
        return eDigitToMDigit(mDate.getWanWaxDay());
    }

    private String eDigitToMDigit(int eDigit) {
        StringBuilder builder = new StringBuilder();
        Map<Integer, String> digitMap = new HashMap<>();
        digitMap.put(0, "၀");
        digitMap.put(1, "၁");
        digitMap.put(2, "၂");
        digitMap.put(3, "၃");
        digitMap.put(4, "၄");
        digitMap.put(5, "၅");
        digitMap.put(6, "၆");
        digitMap.put(7, "၇");
        digitMap.put(8, "၈");
        digitMap.put(9, "၉");

        String[] digits = String.valueOf(eDigit).split("");
        for (int i = 1; i < digits.length; i++) {
            builder.append(digitMap.get(Integer.parseInt(digits[i])));
        }
        return builder.toString();
    }

    /**
     * get week day in myanmar
     *
     * @param type MyanmarCalendar.LONG_DAY or MyanmarCalendar.SHORT_DAY
     * @return weekday short or long in myanmar
     */
    public String getWeekDayInMyanmar(final int type, Context context) {
        String[] longWeekDay = context.getResources().getStringArray(R.array.long_week_day);
        String[] shortWeekDay = context.getResources().getStringArray(R.array.short_week_day);
        switch (type) {
            case MyanmarCalendar.LONG_DAY:
                return longWeekDay[mDate.getWeekday()];
            case MyanmarCalendar.SHORT_DAY:
                return shortWeekDay[mDate.getWeekday()];
            default:
                return longWeekDay[mDate.getWeekday()];
        }
    }

    public String getMyanmarDate(Context context) {
        return getYearInMyanmar() + " " + getMonthInMyanmar(context) + " " +
                (getMonthStatus() == 1 || getMonthStatus() == 3 ?
                        "" : getDayInMyanmnar() + " " + context.getString(R.string.yat));// no day expressed on full and dark moon day
    }

    public String getBuddhaYearInMyanmar() {
        return eDigitToMDigit(calKernel.getBuddhaYear(getYear()));
    }

    public AstroDetail getAstroDetail() {
        return calKernel.checkAstroDetail(mDate.getMonth(), mDate.getMonthLength(), mDate.getDay(), mDate.getWeekday());
    }

    public String[] getAstroDetialList(Context context) {
        return DateFormatter.formatAstroDetail(getAstroDetail(), context);
    }

    public ArrayList<String> getMarketDayList() {
        ArrayList<String> mList = new ArrayList<>();
        MarketDayKernel marKernel = new MarketDayKernel();
        String[] marlist = marKernel.getMarketDayList(curJd);
        for (String mar : marlist)
            mList.add(mar);
        return mList;
    }

    public String[] getHolidays(Context context) {
        HolidayKernel holKernel = new HolidayKernel(context);
        String thingyan = holKernel.thingyan(curJd, mDate.getYear(), mDate.getMonthType());
        String engHoliday = holKernel.engHoliday(wDate.getYear(), wDate.getMonth(), wDate.getDay());
        String myaHoliday = holKernel.myaHoliday(mDate.getYear(), mDate.getMonth(),
                mDate.getDay(), mDate.getMonthStatus());
        String ecd = holKernel.ecd(wDate.getYear(), wDate.getMonth(), wDate.getDay());
        String[] mcd = holKernel.mcd(mDate.getYear(), mDate.getMonth(),
                mDate.getDay(), mDate.getMonthStatus());
        String otherHol = holKernel.otherHolidays(curJd);
        String holidays = thingyan + "-" + engHoliday + "-" + myaHoliday + "-" + ecd + "-" + otherHol;
        if (mcd != null && mcd.length > 0) {
            for (String m : mcd) {
                if (m != null)
                holidays += "-" + m;
            }
        }
        return holidays.split("-");
    }

}
