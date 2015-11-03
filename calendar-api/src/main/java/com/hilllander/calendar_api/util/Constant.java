/*
 * Copyright (c) The code is developed  by Hilllander. You can modify or use some or the whole piece of code with no limitation. Feel free to do whatever you favour.Good luck!Hilllander team. 
 */

package com.hilllander.calendar_api.util;

/**
 * Created by khunzohn on 10/21/15.
 */
public class Constant {
    /**
     * solar year
     */
    public static final double SY = 365.2587565;
    /**
     * starting year of Myanmar era (year 0)
     */
    public static final double MO = 1954168.0506;
    /**
     * start of 2nd Era
     */
    public static final int S2E = 1217;
    /**
     * start of 3rd Era
     */
    public static final int S3E = 1312;

    /**
     * Lunar month
     */
    public static final double LM = 29.53058795;
    /**
     * number of excess days for Last four month
     * of years of 2nd myanmar era
     */
    public static final double TA2 = (12 - 4) * ((SY / 12) - LM);
    /**
     * number of excess days for Last four month
     * of years of 3rd myanmar era
     */
    public static final double TA3 = 3.630567; //(12-8)*((SY/12)-LM)
    /**
     * Wa Tat Kane for 3 myanmar era
     */
    public static final double TW = 22.2694539;
}
