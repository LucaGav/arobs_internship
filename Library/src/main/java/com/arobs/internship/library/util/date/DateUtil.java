package com.arobs.internship.library.util.date;

import java.util.Date;

public class DateUtil {

    public static Date addDays(Date d, int days){
        d.setTime(d.getTime() + days * 1000 * 60 * 60 * 24);
        return d;
    }
}
