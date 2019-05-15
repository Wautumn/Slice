package com.example.slice.utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static String getCurrentTime(){
        Date currentTime = Calendar.getInstance().getTime();
        String timeStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currentTime);
        return timeStr;
    }

    public static String getCurrentDate(){
        Date currentDate = Calendar.getInstance().getTime();
        String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(currentDate);
        return dateStr;
    }

    public static Date stringToTime(String timeStr){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            Date date = simpleDateFormat.parse(timeStr);
            return date;
        }catch (ParseException exception){
            return null;
        }
    }

    public static Date stringToDate(String dateStr){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date date = simpleDateFormat.parse(dateStr);
            return date;
        }catch (ParseException exception){
            return null;
        }
    }

    public static int compareTime(String timeStr_1, String timeStr_2){
        Date time_1 = stringToDate(timeStr_1);
        Date time_2 = stringToDate(timeStr_2);

        try{
            if(time_1.getTime() > time_2.getTime()){
                return 1;
            }else if(time_1.getTime() < time_2.getTime()){
                return -1;
            }else{
                return 0;
            }
        }catch(Exception exception){
            return -2;
        }
    }

    public static int compareDate(String dateStr_1, String dateStr_2){
        Date date_1 = stringToDate(dateStr_1);
        Date date_2 = stringToDate(dateStr_2);

        try{
            if(date_1.getTime() > date_2.getTime()){
                return 1;
            }else if(date_1.getTime() < date_2.getTime()){
                return -1;
            }else{
                return 0;
            }
        }catch(Exception exception){
            return -2;
        }
    }
}
