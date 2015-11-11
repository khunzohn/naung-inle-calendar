package com.hilllander.naunginlecalendar.model;

/**
 * Created by khunzohn on 11/9/15.
 */
public class MonthGridItem {
    private static MonthGridItem item;
    private String myaMonth;
    private String myaDay;
    private String engDay;
    private String[] specialDay;


    private MonthGridItem() {

    }

    public static MonthGridItem newInstance() {
        return new MonthGridItem();
    }

    public String getMyaMonth() {
        return myaMonth;
    }

    public MonthGridItem setMyaMonth(String myaMonth) {
        this.myaMonth = myaMonth;
        return this;
    }

    public String getMyaDay() {
        return myaDay;
    }

    public MonthGridItem setMyaDay(String myaDay) {
        this.myaDay = myaDay;
        return this;
    }

    public String getEngDay() {
        return engDay;
    }

    public MonthGridItem setEngDay(String engDay) {
        this.engDay = engDay;
        return this;
    }

    public String[] getSpecialDay() {
        return specialDay;
    }

    public MonthGridItem setSpecialDay(String[] specialDay) {
        this.specialDay = specialDay;
        return this;
    }
}
