package com.arobs.internship.library.util.date;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static Date addDays(int days){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE,days);
        return c.getTime();
    }

    public static Date addHours(Date d, int hours){
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.HOUR_OF_DAY,1);
        return c.getTime();
    }
}
