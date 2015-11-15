package com.hilllander.naunginlecalendar.model;

import android.content.Context;

import com.hilllander.calendar_api.calendar.MyanmarCalendar;
import com.hilllander.naunginlecalendar.R;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by khunzohn on 11/9/15.
 */
public class MonthGridItem {
    private String[] shortMonths = {"ပဆို", "ခူး", "ဆုန်", "ယုန်", "ဆို", "ခေါင်", "လင်း", "ကျွတ်", "မုန်း"
            , "တော်", "သို", "တွဲ", "ပေါင်း"};

    private String myaMonth;
    private String myaDay;
    private String engDay;
    private String[] specialDay;
    private GregorianCalendar wCal;
    private MyanmarCalendar mCal;
    private Context context;
    private int monthStatus;
    private String simpleMonthName;
    private String myaYear;
    private int dateStatus;
    private int specialDayFlag;
    private boolean currentDay;
    private int yearType;


    private MonthGridItem(Context context, GregorianCalendar cal, int dateStatus, boolean currentDay) {
        this.context = context;
        this.wCal = cal;
        this.mCal = MyanmarCalendar.getInstance(wCal);
        specialDay = mCal.getHolidays(context);
        engDay = String.valueOf(wCal.get(Calendar.DAY_OF_MONTH));
        myaDay = getMDay();
        myaMonth = getShortMyaMonth();
        monthStatus = mCal.getMonthStatus();
        simpleMonthName = createSimpleMonthname();
        myaYear = mCal.getYearInMyanmar();
        this.dateStatus = dateStatus;
        specialDayFlag = specialDay != null && specialDay.length > 0 ? 1 : 0;
        this.currentDay = currentDay;
        this.yearType = mCal.getYearType();

    }

    public static MonthGridItem newInstance(Context context, GregorianCalendar mCal, int dateStatus, boolean currentDay) {
        return new MonthGridItem(context, mCal, dateStatus, currentDay);
    }

    public boolean isCurrentDay() {
        return currentDay;
    }

    private String getMDay() {
        String wanWaxDay = mCal.getDayInMyanmnar();
        int day = mCal.getDay();
        int mStatus = mCal.getMonthStatus();
        if (day == 1 || day == 16 || mStatus == 1 || mStatus == 3) {
            wanWaxDay = "";
        }
        return wanWaxDay;
    }

    private String getShortMyaMonth() {
        int mStatus = mCal.getMonthStatus(); //[0: waxing, 1: full moon, 2: waning, 3: new moon]
        int mType = mCal.getMonthType(); //[1=hnaung, 0= Oo]
        int month = mCal.getMonth();
        int yType = mCal.getYearType();
        boolean watat = yType == 1 || yType == 2;
        int day = mCal.getDay();
        String sMonth = "";
        if (day == 1 || day == 16 || mStatus == 1 || mStatus == 3) {
            // show only on 1[wax or wan] and full moon or new moon
            sMonth = shortMonths[month];
            if ((month == 1 || month == 2) && mType == 1) {  // hnaung tagu or kasone
                String temp = sMonth;
                sMonth = context.getString(R.string.naung);
                sMonth += temp;
            }
            if (month == 4 && watat) {  //second waso during watat
                String temp = sMonth;
                sMonth = context.getString(R.string.du);
                sMonth += temp;
            }
            sMonth += mStatus == 0 ? context.getString(R.string.san) :
                    mStatus == 1 ? context.getString(R.string.pyae) : mStatus == 2 ? context.getString(R.string.sote) : context.getString(R.string.kwae);
        }

        return sMonth;
    }

    public String getMyaMonth() {
        return myaMonth;
    }


    public String getMyaDay() {
        return myaDay;
    }


    public String getEngDay() {
        return this.engDay;
    }

    public String[] getSpecialDay() {
        return specialDay;
    }


    private String createSimpleMonthname() {
        String[] mMonths = context.getResources().getStringArray(com.hilllander.calendar_api.R.array.myan_months);
        int yType = mCal.getYearType();
        int mMonth = mCal.getMonth();
        String monthName = mMonths[mMonth];
        if (mMonth == 4 && (yType == 1 || yType == 2)) {
            String temp = monthName;
            monthName = context.getResources().getString(R.string.du);
            monthName += temp;
        }
        return monthName;
    }

    public String getMyaYear() {
        return myaYear;
    }

    public GregorianCalendar getGreDate() {
        return wCal;
    }

    public int getMonthStatus() {
        return monthStatus;
    }

    public String getSimpleMonthName() {
        return simpleMonthName;
    }

    /**
     * status shows day of previous , current or next month
     *
     * @return flag [0 - previous, 1 - current, 2 - next]
     */
    public int getDateStatus() {
        return dateStatus;
    }

    /**
     * indicates that the day is special day or not
     *
     * @return
     */
    public int getSpecialDayFlag() {
        return specialDayFlag;
    }

    public int getYearType() {
        return yearType;
    }
}
