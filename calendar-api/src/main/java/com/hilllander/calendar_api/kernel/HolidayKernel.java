/*
 * Copyright (c) The code is developed  by Hilllander. You can modify or use some or the whole piece of code with no limitation. Feel free to do whatever you favour.Good luck!Hilllander team.
 */

package com.hilllander.calendar_api.kernel;

import android.content.Context;

import com.hilllander.calendar_api.R;
import com.hilllander.calendar_api.model.EngSDaysBundle;
import com.hilllander.calendar_api.model.MyaSDaysBundle;
import com.hilllander.calendar_api.util.Constant;

import java.util.ArrayList;

/**
 * Kernel that calculates holidays based on the open sourced
 * Myanmar calendar algorithm by Ko Yan Naing Aye (http://www.cool-emerald.com).
 * Created by khunzohn on 10/25/15.
 */
public class HolidayKernel {
    private Context context;

    public HolidayKernel(Context context) {
        this.context = context;
    }

    /**
     * check for thingyan holidays
     *
     * @param jdn julian day number
     * @param my  Myanmar year
     * @param mmt myanmar month type[0 : Oo, 1 : hnaung]
     * @return thingyan day if exist
     */
    public String thingyan(double jdn, int my, int mmt) {
        double MO = 1954168.050623; //beginning of 0 ME
        int BGNTG = 1100;//start of Thingyan
        int flag = 0;
        String holiday = "";
        double akn, atn;
        int SE3 = 1312; //start of third era
        double ja = (Constant.SY * (my + mmt)) + MO;
        double jk;
        if (my >= SE3) jk = ja - 2.169918982;
        else jk = ja - 2.1675;
        akn = Math.round(jk);
        atn = Math.round(ja);
        if (jdn == (atn + 1)) {
            flag = 1;
            holiday = context.getString(R.string.myanmar_new_year_day);
        }
        if ((my + mmt) >= BGNTG) {
            if (jdn == atn) {
                flag = 1;
                holiday = context.getString(R.string.thingyan_atat);
            } else if ((jdn > akn) && (jdn < atn)) {
                flag = 1;
                holiday = context.getString(R.string.thingyan_akyat);
            } else if (jdn == akn) {
                flag = 1;
                holiday = context.getString(R.string.thingyan_akya);
            } else if (jdn == (akn - 1)) {
                flag = 1;
                holiday = context.getString(R.string.thingyan_akyo);
            } else if (((my + mmt) >= 1362) && ((jdn == (akn - 2)) ||
                    ((jdn >= (atn + 2)) && (jdn <= (akn + 7))))) {
                flag = 1;
                holiday = context.getString(R.string.official_day_off);
            }
        }
        if (flag == 1)
            return holiday;
        else return "";
    }

    /**
     * check for english holiday
     *
     * @param year  year
     * @param month month [Jan=1, ... , Dec=12]
     * @param day   day [0-31]
     * @return name of holiday if exist
     */
    public String engHoliday(int year, int month, int day) {
        int flag = 0;
        String holiday = "";
        if ((month == 1) && (day == 1)) {
            flag = 1;
            holiday = context.getString(R.string.new_year_day);
        } else if ((year >= 1948) && (month == 1) && (day == 4)) {
            flag = 1;
            holiday = context.getString(R.string.independence_day);
        } else if ((year >= 1947) && (month == 2) && (day == 12)) {
            flag = 1;
            holiday = context.getString(R.string.union_day);
        } else if ((year >= 1958) && (month == 3) && (day == 2)) {
            flag = 1;
            holiday = context.getString(R.string.pheasants_day);
        } else if ((year >= 1945) && (month == 3) && (day == 27)) {
            flag = 1;
            holiday = context.getString(R.string.resistance_day);
        } else if ((year >= 1923) && (month == 5) && (day == 1)) {
            flag = 1;
            holiday = context.getString(R.string.labour_day);
        } else if ((year >= 1947) && (month == 7) && (day == 19)) {
            flag = 1;
            holiday = context.getString(R.string.martyars_day);
        } else if ((month == 12) && (day == 25)) {
            flag = 1;
            holiday = context.getString(R.string.christmas_day);
        }
        if (flag == 1)
            return holiday;
        else
            return "";
    }

    /**
     * check for myanmar holidays
     *
     * @param my myanmar year
     * @param mm myanmar month [sec_waso=0,Tagu=1, ... , Tabaung=12]
     * @param md myanmar day [0-30]
     * @param ms month status [0: waxing, 1: full moon, 2: waning, 3: new moon]
     * @return name of myanmar holiday if exist
     */
    public String myaHoliday(int my, int mm, int md, int ms) {
        int flag = 0;
        String hs = "";
        if ((mm == 2) && (ms == 1)) {
            flag = 1;
            hs = context.getString(R.string.buddha_day);
        }//Vesak day
        else if ((mm == 4) && (ms == 1)) {
            flag = 1;
            hs = context.getString(R.string.start_of_buddha_lent);
        }//waso day
        else if ((mm == 7) && (ms == 1)) {
            flag = 1;
            hs = context.getString(R.string.end_of_buddha_lent);
        } else if ((mm == 8) && (ms == 1)) {
            flag = 1;
            hs = context.getString(R.string.tazaungdaing);
        } else if ((my >= 1282) && (mm == 8) && (md == 25)) {
            flag = 1;
            hs = context.getString(R.string.national_day);
        } else if ((mm == 10) && (md == 1)) {
            flag = 1;
            hs = context.getString(R.string.karen_new_year_day);
        } else if ((mm == 12) && (ms == 1)) {
            flag = 1;
            hs = context.getString(R.string.tabaung_bwe);
        }
        if (flag == 1)
            return hs;
        else
            return "";
    }

    /**
     * check for english c day (Don't know what c means :p)
     *
     * @param year  Western year
     * @param month month [Jan=1, ... , Dec=12]
     * @param day   day [0-31])
     * @return name of english c day (Don't know what c means :p)
     */
    public String ecd(int year, int month, int day) {
        int flag = 0;
        String holiday = "";
        if ((year >= 1915) && (month == 2) && (day == 13)) {
            flag = 1;
            holiday = context.getString(R.string.g_aungsan_birthday);
        } else if ((year >= 1969) && (month == 2) && (day == 14)) {
            flag = 1;
            holiday = context.getString(R.string.valentines_day);
        } else if ((year >= 1970) && (month == 4) && (day == 22)) {
            flag = 1;
            holiday = context.getString(R.string.earth_day);
        } else if ((year >= 1392) && (month == 4) && (day == 1)) {
            flag = 1;
            holiday = context.getString(R.string.april_fools_day);
        } else if ((year >= 1948) && (month == 5) && (day == 8)) {
            flag = 1;
            holiday = context.getString(R.string.red_cross_day);
        } else if ((year >= 1994) && (month == 10) && (day == 5)) {
            flag = 1;
            holiday = context.getString(R.string.world_teachers_day);
        } else if ((year >= 1947) && (month == 10) && (day == 24)) {
            flag = 1;
            holiday = context.getString(R.string.united_nations_day);
        } else if ((month == 10) && (day == 31)) {
            flag = 1;
            holiday = context.getString(R.string.halloween);
        }
        if (flag == 1)
            return holiday;
        else
            return "";
    }

    public ArrayList<EngSDaysBundle> getEngspecialDayBundle(final int year) {
        ArrayList<EngSDaysBundle> specialDays = new ArrayList<>();
        if (year >= 1915) {
            specialDays.add(new EngSDaysBundle(context.getString(R.string.g_aungsan_birthday)
                    , year, 2, 13));
        }
        if (year >= 1969) {
            specialDays.add(new EngSDaysBundle(context.getString(R.string.valentines_day),
                    year, 2, 14));
        }
        if (year >= 1970) {
            specialDays.add(new EngSDaysBundle(context.getString(R.string.earth_day),
                    year, 4, 22));
        }
        if (year >= 1392) {
            specialDays.add(new EngSDaysBundle(context.getString(R.string.april_fools_day),
                    year, 4, 1));
        }
        if (year >= 1948) {
            specialDays.add(new EngSDaysBundle(context.getString(R.string.red_cross_day),
                    year, 5, 8));
        }
        if (year >= 1994) {
            specialDays.add(new EngSDaysBundle(context.getString(R.string.world_teachers_day),
                    year, 10, 5));
        }
        if (year >= 1947) {
            specialDays.add(new EngSDaysBundle(context.getString(R.string.united_nations_day),
                    year, 10, 24));
        }
        specialDays.add(new EngSDaysBundle(context.getString(R.string.halloween),
                year, 10, 31));
        specialDays.add(new EngSDaysBundle(context.getString(R.string.new_year_day),
                year, 1, 1));

        if (year >= 1948) {
            specialDays.add(new EngSDaysBundle(context.getString(R.string.independence_day),
                    year, 1, 4));
        }
        if (year >= 1947) {
            specialDays.add(new EngSDaysBundle(context.getString(R.string.union_day),
                    year, 2, 12));
        }
        if (year >= 1958) {
            specialDays.add(new EngSDaysBundle(context.getString(R.string.pheasants_day),
                    year, 3, 2));
        }
        if (year >= 1945) {
            specialDays.add(new EngSDaysBundle(context.getString(R.string.resistance_day),
                    year, 3, 27));
        }
        if (year >= 1923) {
            specialDays.add(new EngSDaysBundle(context.getString(R.string.labour_day),
                    year, 5, 1));
        }
        if (year >= 1947) {
            specialDays.add(new EngSDaysBundle(context.getString(R.string.martyars_day),
                    year, 7, 19));
        }
        specialDays.add(new EngSDaysBundle(context.getString(R.string.christmas_day),
                year, 12, 25));

        return specialDays;
    }

    /**
     * check for myanmar c day (Don't know what c means :p)
     *
     * @param my Myanmar year
     * @param mm Myanmar month [sec_waso=0,Tagu=1, ... , Tabaung=12]
     * @param md Myanmar day [0-30]
     * @param ms month status [0: waxing, 1: full moon, 2: waning, 3: new moon]
     * @return array of myanmar c day (Don't know what c means :p) if exist and null otherwise.
     */
    public String[] mcd(int my, int mm, int md, int ms) {
        int flag = 0;
        String[] holidays = new String[2];
        if ((my >= 1309) && (mm == 11) && (md == 16)) {
            flag = 1;
            holidays[0] = context.getString(R.string.mon_national_day);
        }//the ancient founding of Hanthawady
        else if ((mm == 9) && (md == 1)) {
            flag = 1;
            holidays[0] = context.getString(R.string.shan_new_year_day);
            if (my >= 1306) {
                flag = 2;
                holidays[1] = context.getString(R.string.authors_day);
            }
        }//Nadaw waxing moon 1
        else if ((mm == 3) && (ms == 1)) {
            flag = 1;
            holidays[0] = context.getString(R.string.mahathamata_day);
        }//Nayon full moon
        else if ((mm == 6) && (ms == 1)) {
            flag = 1;
            holidays[0] = context.getString(R.string.garudhama_day);
        }//Tawthalin full moon
        else if ((my >= 1356) && (mm == 10) && (ms == 1)) {
            flag = 1;
            holidays[0] = context.getString(R.string.mothers_day);
        }//Pyatho full moon
        else if ((my >= 1370) && (mm == 12) && (ms == 1)) {
            flag = 1;
            holidays[0] = context.getString(R.string.fathers_day);
        }//Tabaung full moon
        else if ((mm == 5) && (ms == 1)) {
            flag = 1;
            holidays[0] = context.getString(R.string.metta_day);
            //if(my>=1324)  {flag=2; holidays[1]="Mon Revolution Day";}//Mon Revolution day
        }//Waguang full moon
        else if ((mm == 5) && (md == 10)) {
            flag = 1;
            holidays[0] = context.getString(R.string.taungpyone_pwe);
        }//Taung Pyone Pwe
        else if ((mm == 5) && (md == 23)) {
            flag = 1;
            holidays[0] = context.getString(R.string.yadanagu_pwe);
        }//Yadanagu Pwe
        /*else if ((my >= 1119) && (mm == 2) && (md == 23)) {
            flag = 1;
            holidays[0] = "Mon Fallen Day";
        } */
        else if ((mm == 12) && (md == 12)) {
            flag = 1;
            holidays[0] = context.getString(R.string.mon_women_day);
        }
        if (flag == 1 || flag == 2)
            return holidays;
        else
            return null;
    }

    /**
     * check for other holidays
     *
     * @param jd julian day number
     * @return name of other holiday if exist
     */
    public String otherHolidays(double jd) {
        long[] ghDiwali = new long[]{2456599, 2456953, 2457337};
        long[] ghEid = new long[]{2456513, 2456867, 2457221};
        int flag = 0;
        String holiday = "";
        if (bSearch1(jd, ghDiwali) >= 0) {
            flag = 1;
            holiday = context.getString(R.string.diwali);
        }
        if (bSearch1(jd, ghEid) >= 0) {
            flag = 1;
            holiday = context.getString(R.string.eid);
        }
        if (flag == 1)
            return holiday;
        else
            return "";
    }

    //Search a 1D array
    //input: (k=key,A=array)
    //output: (i= index)
    private int bSearch1(double k, long[] A) {
        int i;
        int l = 0;
        int u = A.length - 1;
        while (u >= l) {
            i = (int) Math.floor((l + u) / 2);
            if (A[i] > k) u = i - 1;
            else if (A[i] < k) l = i + 1;
            else return i;
        }
        return -1;
    }

    /**
     * get all myanmar date special days of specified year
     *
     * @param my myanmar year
     * @return array of special days
     *
     */
    public ArrayList<MyaSDaysBundle> getMyaSpecialDayBundle(final int my) {
        //TODO add more myanmar holidays
        ArrayList<MyaSDaysBundle> specialDays = new ArrayList<>();
        specialDays.add(new MyaSDaysBundle(context.getString(R.string.buddha_day), my, 2, 0, 1, 15));
        if (my >= 1309) {
            specialDays.add(new MyaSDaysBundle(context.getString(R.string.mon_national_day), my, 11, 0, 2, 1));
        }
        //the ancient founding of Hanthawady
        specialDays.add(new MyaSDaysBundle(context.getString(R.string.shan_new_year_day), my, 9, 0, 0, 1));
        if (my >= 1306) {
            specialDays.add(new MyaSDaysBundle(context.getString(R.string.authors_day), my, 9, 0, 0, 1));

        }//Nadaw waxing moon 1

        specialDays.add(new MyaSDaysBundle(context.getString(R.string.mahathamata_day), my, 3, 0, 1, 15));
        //Nayon full moon

        specialDays.add(new MyaSDaysBundle(context.getString(R.string.garudhama_day), my, 6, 0, 1, 15));
        //Tawthalin full moon
        if (my >= 1356) {
            specialDays.add(new MyaSDaysBundle(context.getString(R.string.mothers_day), my, 10, 0, 1, 15));
        }//Pyatho full moon
        if (my >= 1370) {
            specialDays.add(new MyaSDaysBundle(context.getString(R.string.fathers_day), my, 12, 0, 1, 15));
        }
        //Tabaung full moon

        specialDays.add(new MyaSDaysBundle(context.getString(R.string.metta_day), my, 5, 0, 1, 15));
        //if(my>=1324)  {flag=2; holidays[1]="Mon Revolution Day";}//Mon Revolution day
        //Waguang full moon

        specialDays.add(new MyaSDaysBundle(context.getString(R.string.taungpyone_pwe), my, 5, 0, 0, 10));
        //Taung Pyone Pwe

        specialDays.add(new MyaSDaysBundle(context.getString(R.string.yadanagu_pwe), my, 5, 0, 2, 8));
        //Yadanagu Pwe
        /*else if ((my >= 1119) && (mm == 2) && (md == 23)) {
            flag = 1;
            holidays[0] = "Mon Fallen Day";
        } */

        specialDays.add(new MyaSDaysBundle(context.getString(R.string.mon_women_day), my, 12, 0, 0, 12));

        return specialDays;
    }

}
