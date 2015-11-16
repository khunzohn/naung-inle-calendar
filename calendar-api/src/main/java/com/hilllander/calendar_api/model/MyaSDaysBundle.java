package com.hilllander.calendar_api.model;

/**
 * Created by khunzohn on 11/16/15.
 */
public class MyaSDaysBundle {
    private String name;
    private int mYear;
    private int mMonth;
    private int mType;
    private int mStatus;
    private int wanWaxDay;

    public MyaSDaysBundle(String name, int mYear, int mMonth, int mType, int mStatus, int wanWaxDay) {
        this.name = name;
        this.mYear = mYear;
        this.mMonth = mMonth;
        this.mType = mType;
        this.mStatus = mStatus;
        this.wanWaxDay = wanWaxDay;
    }

    public int getWanWaxDay() {
        return wanWaxDay;
    }

    public String getName() {
        return name;
    }

    public int getmYear() {
        return mYear;
    }

    public int getmMonth() {
        return mMonth;
    }

    public int getmType() {
        return mType;
    }

    public int getmStatus() {
        return mStatus;
    }
}
