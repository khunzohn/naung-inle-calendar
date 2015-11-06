package com.hilllander.calendar_api.calendar;

import android.content.Context;
import android.util.Log;

import com.hilllander.calendar_api.kernel.CalendarKernel;
import com.hilllander.calendar_api.model.AstroDetail;
import com.hilllander.calendar_api.model.MyanmarDate;
import com.hilllander.calendar_api.util.DateFormatter;

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
    private static final String[] MONTHS = {"ပဝါဆို", "တန်ခူး", "ကဆုန်", "နယုန်", "ဝါဆို", "ဝါခေါင်",
            "တော်သလင်း", "သီတင်းကျွတ်", "တန်ဆောင်မုန်း", "နတ်တော်", "ပြာသို", "တပို့တွဲ", "တပေါင်း"};
    private static final String[] LONG_WEEKDAY = {"စနေ", "တနင်္ဂနွေ", "တနင်္လာ", "အင်္ဂါ", "ဗုဒ္ဓဟူး", "ကြာသပတေး", "သောကြာ"};
    private static final String[] SHORT_WEEKDAY = {"နေ", "နွေ", "လာ", "ဂါ", "ဟူး", "တေး", "ကြာ"};
    private static final String TAG = MyanmarCalendar.class.getSimpleName();
    private static MyanmarCalendar mCalendar;
    private final int calType = 1; // Gregorian calendar
    private CalendarKernel calKernel;
    private double curJd;
    private MyanmarDate mDate;

    private MyanmarCalendar(GregorianCalendar greCal) {
        Log.d(TAG, "year : " + greCal.get(Calendar.YEAR) + "month : "
                + greCal.get(Calendar.MONTH) + "day : " + greCal.get(Calendar.DAY_OF_MONTH));
        calKernel = new CalendarKernel();
        int
                eYear = greCal.get(Calendar.YEAR),
                eMonth = greCal.get(Calendar.MONTH) + 1,
                eDay = greCal.get(Calendar.DAY_OF_MONTH);

        curJd = calKernel.W2J(eYear, eMonth, eDay, calType);
        mDate = calKernel.J2M(curJd);
        Log.d(TAG, "mYear :" + mDate.getYear() + "mMonth : " + mDate.getMonth() + "mDay : " + mDate.getWanWaxDay());
    }

    private MyanmarCalendar(double jd) {
        curJd = jd;
        calKernel = new CalendarKernel();
        mDate = calKernel.J2M(curJd);
    }

    private MyanmarCalendar() {
        GregorianCalendar greCal = new GregorianCalendar();
        calKernel = new CalendarKernel();
        int
                eYear = greCal.get(Calendar.YEAR),
                eMonth = greCal.get(Calendar.MONTH) + 1,
                eDay = greCal.get(Calendar.DAY_OF_MONTH);

        curJd = calKernel.W2J(eYear, eMonth, eDay, calType);
        mDate = calKernel.J2M(curJd);
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

    public int getYearType() {
        return mDate.getYearType();
    }

    public int getWanWaxDay() {
        return mDate.getWanWaxDay();
    }

    public String getMonthInMyanmar() {
        int day = mDate.getWanWaxDay();
        int monthType = mDate.getMonthType();
        String mStatus = "";
        String mType = "";
        switch (mDate.getMonthStatus()) {
            case 0: // waxing
                mStatus = "လဆန်း";
                break;
            case 1: // full moon
                mStatus = "လပြည့်";
                break;
            case 2: // waning
                mStatus = "လဆုတ်";
                break;
            case 3: // dark moon
                mStatus = "လကွယ်";
                break;
        }
        if (monthType == 1) { // Naung
            mType = "နှောင်း";
        }

        String month = MONTHS[mDate.getMonth()];
        if (calKernel.isWatat(mDate.getYearType())
                && mDate.getMonth() == 4) {    // waso becomes second waso in watat year
            String temp = month;
            month = "ဒု" + temp;
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
    public String getWeekDayInMyanmar(final int type) {
        switch (type) {
            case MyanmarCalendar.LONG_DAY:
                return LONG_WEEKDAY[mDate.getWeekday()];
            case MyanmarCalendar.SHORT_DAY:
                return SHORT_WEEKDAY[mDate.getWeekday()];
            default:
                return LONG_WEEKDAY[mDate.getWeekday()];
        }
    }

    public String getMyanmarDate() {
        return getMonthInMyanmar() + " " +
                (getMonthStatus() == 1 || getMonthStatus() == 3 ?
                        "" : getDayInMyanmnar() + " ရက္။");// no day expressed on full and dark moon day
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
}
