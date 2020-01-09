package com.govodrill.augusttoday.date;

import com.govodrill.augusttoday.date.ConstantsDate;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateCalculator {
    public static int getDaysFromAugust(){
//        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int currentYear = getYear();
        Date currentDate = new Date();

        if(getCurrentMonth() == Calendar.AUGUST){
            return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        }

        Date d1 = new GregorianCalendar(currentYear,
                ConstantsDate.MONTH, ConstantsDate.DAY,
                ConstantsDate.HOURS, ConstantsDate.MINUTES).getTime();

        // Get msec from each, and subtract.
        long diff = currentDate.getTime() - d1.getTime();
        long daysFromAugust = diff / (1000 * 60 * 60 * 24);

        return (int)daysFromAugust;
    }
    public static int getCurrentMonth(){
        return Calendar.getInstance().get(Calendar.MONTH);
    }
    //Calculates necessary year
    private static int getYear(){
        int month = getCurrentMonth();
        if(month >= Calendar.AUGUST){
            return Calendar.getInstance().get(Calendar.YEAR);
        }
        return Calendar.getInstance().get(Calendar.YEAR) - 1;
    }

}
