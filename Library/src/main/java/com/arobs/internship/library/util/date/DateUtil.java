package com.arobs.internship.library.util.date;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static Date addDays(Date d, int days){
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.DATE,days);
        return c.getTime();
    }

    public static Date addHours(Date d, int hours){
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.HOUR_OF_DAY,1);
        return c.getTime();
    }


    public static int getDaysBetween(Date d1, Date d2) {
        Date auxiliary1 = new Date(d1.getTime());
        Date auxiliary2 = new Date(d2.getTime());
        return Days.daysBetween(new LocalDate(auxiliary1.getTime()),
                                new LocalDate(auxiliary2.getTime())).getDays();
    }

    public static boolean compareDates(Date d1, Date d2) {
        Date auxiliary1 = new Date(d1.getTime());
        Date auxiliary2 = new Date(d2.getTime());
        return (new LocalDate(auxiliary1.getTime()).isEqual(new LocalDate(auxiliary2.getTime())));
    }
}
