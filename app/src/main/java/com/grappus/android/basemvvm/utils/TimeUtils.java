package com.grappus.android.basemvvm.utils;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;


import com.grappus.android.basemvvm.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeUtils implements Constants.TimeUnits, Constants.TimeFormats {


    public static SimpleDateFormat getCurrentTimeZoneFormat(String timeFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat, Locale.getDefault());

        Date currentDate = new Date();
        TimeZone tz = Calendar.getInstance().getTimeZone();

        //String name1 = tz.getDisplayName(tz.inDaylightTime(currentDate), TimeZone.SHORT);
        String name = TimeZone.getDefault().getDisplayName(tz.inDaylightTime(currentDate), TimeZone.SHORT);
        sdf.setTimeZone(TimeZone.getTimeZone("\"" + name + "\""));
        //Log.d("current time zone", sdf.getTimeZone().getDisplayName() + "::" + TimeZone.getDefault().getDisplayName() + ": " + TimeZone.getDefault().getID());

        return sdf;
    }


    public static String getTime(long timeInMilliSeconds) {
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_ONLY);
        Calendar calendar = Calendar.getInstance();
        if (timeInMilliSeconds > 0) calendar.setTimeInMillis(timeInMilliSeconds);
        return sdf.format(calendar.getTime());
    }

    public static String getTime(long timeInMilliSeconds, String timeFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
        Calendar calendar = Calendar.getInstance();
        if (timeInMilliSeconds > 0) calendar.setTimeInMillis(timeInMilliSeconds);
        return sdf.format(calendar.getTime());
    }

    public static String getTime(String time) {
        SimpleDateFormat sdf = getCurrentTimeZoneFormat(TIME_SERVER);
        SimpleDateFormat sdfT = new SimpleDateFormat(TIME_ONLY);
        Calendar calendar = Calendar.getInstance();
        try {
            if (TextUtils.isNotEmpty(time)) calendar.setTime(sdf.parse(time));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sdfT.format(calendar.getTime());
    }

    public static String getTimeInAgoFormat(String time) {

        SimpleDateFormat sdfT = getCurrentTimeZoneFormat(TIME_SERVER);
        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();

        /* date formatter in local timezone */
        SimpleDateFormat sdf = getCurrentTimeZoneFormat(DATE_TIME_1);
        sdf.setTimeZone(tz);

        /* print your timestamp and double check it's the date you expect */
        try {
            if (TextUtils.isNotEmpty(time)) cal.setTime(sdfT.parse(time));
        } catch (Exception e) {
            //e.printStackTrace();
        }
        long timestamp = cal.getTimeInMillis();
        String localTime = sdf.format(new Date(timestamp)); // I assume your timestamp is in seconds and you're converting to milliseconds?
        Log.d("Time: ", localTime);

        Log.d("Server time: ", timestamp + "");

        /* log the device timezone */
        Log.d("Time zone: ", tz.getDisplayName());

        /* log the system time */
        Log.d("System time: ", System.currentTimeMillis() + "");

        CharSequence relTime = DateUtils.getRelativeTimeSpanString(
                timestamp,
                System.currentTimeMillis(),
                DateUtils.MINUTE_IN_MILLIS);

        return relTime.toString();

        /*AppLog.i(TAG, "current milli seconds: " + System.currentTimeMillis());
        AppLog.i(TAG, "time for post: " + postedOn);

        CharSequence timeSpanString = DateUtils.getRelativeDateTimeString
                (context, Long.parseLong(postedOn), DateUtils.MINUTE_IN_MILLIS, DateUtils.DAY_IN_MILLIS, 0);
        AppLog.i(TAG, "formated date: " + timeSpanString);
        return timeSpanString.toString();*/
    }

    public static String getTimeInAgoFormat(long timestamp) {

        /* date formatter in local timezone */
        TimeZone tz = Calendar.getInstance().getTimeZone();
        SimpleDateFormat sdf = getCurrentTimeZoneFormat(DATE_TIME_1);
        sdf.setTimeZone(tz);

        String localTime = sdf.format(new Date(timestamp)); // I assume your timestamp is in seconds and you're converting to milliseconds?
        Log.d("Time: ", localTime);
        Log.d("Server time: ", timestamp + "");
        /* log the device timezone */
        Log.d("Time zone: ", tz.getDisplayName());
        /* log the system time */
        Log.d("System time: ", System.currentTimeMillis() + "");

        CharSequence relTime = DateUtils.getRelativeTimeSpanString(
                timestamp,
                System.currentTimeMillis(),
                DateUtils.MINUTE_IN_MILLIS);

        return relTime.toString();

        /*AppLog.i(TAG, "current milli seconds: " + System.currentTimeMillis());
        AppLog.i(TAG, "time for post: " + postedOn);

        CharSequence timeSpanString = DateUtils.getRelativeDateTimeString
                (context, Long.parseLong(postedOn), DateUtils.MINUTE_IN_MILLIS, DateUtils.DAY_IN_MILLIS, 0);
        AppLog.i(TAG, "formated date: " + timeSpanString);
        return timeSpanString.toString();*/
    }

    public static String getTime(String time, String inputTimeFormat, String outputTimeFormat) {
        SimpleDateFormat sdf = getCurrentTimeZoneFormat(inputTimeFormat);
        SimpleDateFormat sdfo = new SimpleDateFormat(outputTimeFormat);

        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(time));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sdfo.format(calendar.getTime());
    }

    public static String getTime(Date date, String timeFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sdf.format(calendar.getTime());
    }

    public static String convertDateFormat(String dateString, String oldFormat, String newFormat) {
        DateFormat originalFormat = new SimpleDateFormat(oldFormat, Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat(newFormat);
        Date date = null;
        try {
            date = originalFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return targetFormat.format(date);
    }

    public static String convertDateFormatNoZoneConversion(String dateString, String oldFormat, String newFormat) {
        DateFormat originalFormat = new SimpleDateFormat(oldFormat);
        DateFormat targetFormat = new SimpleDateFormat(newFormat);
        Date date = null;
        try {
            date = originalFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return targetFormat.format(date);
    }


    public static long getTime(String time, String inputTimeFormat) {
        SimpleDateFormat sdf = getCurrentTimeZoneFormat(inputTimeFormat);
        Calendar calendar = Calendar.getInstance();
        try {
            if (TextUtils.isNotEmpty(time)) calendar.setTime(sdf.parse(time));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return calendar.getTimeInMillis();
    }


    public static Date getDate(long timeInMilliSeconds, String outputTimeFormat, boolean convertToLocalTime) {
        SimpleDateFormat sdf = convertToLocalTime ? getCurrentTimeZoneFormat(outputTimeFormat)
                : new SimpleDateFormat(outputTimeFormat);

        Calendar calendar = Calendar.getInstance();
        if (timeInMilliSeconds > 0) calendar.setTimeInMillis(timeInMilliSeconds);

        try {
            return sdf.parse(sdf.format(calendar.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
            return new Date();
        }
    }

    public static Date getDate(long timeInMilliSeconds, String outputTimeFormat) {
        return getDate(timeInMilliSeconds, outputTimeFormat, true);
    }

    public static Date getDate(String time, String inputTimeFormat, String outputTimeFormat) {
        SimpleDateFormat sdf = getCurrentTimeZoneFormat(inputTimeFormat);
        SimpleDateFormat sdfo = new SimpleDateFormat(outputTimeFormat);

        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(time));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            return sdfo.parse(sdfo.format(calendar.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
            return new Date();
        }
    }

    public static Date getOnlyDate(String date, String format) throws ParseException {
        String sDate1 = date;
        Date date1 = new SimpleDateFormat(format).parse(date);
        System.out.println(sDate1 + "\t" + date1);
        return date1;
    }

    public static String convertToUTC(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TIME_SERVER_3);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return simpleDateFormat.format(date);
    }

    public static String setTimeDay(String timeOfDay, Context context) {
        /*This function return greeting of day i.e Morning, Afternoon and evening*/
        Date startTime = getDate(timeOfDay, TIME_24_HOUR, TIME_24_HOUR);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);
        String greeting = "";
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour <= 11) greeting = context.getString(R.string.text_morning);
        else if (hour < 16) greeting = context.getString(R.string.text_noon);
        else if (hour <= 23) greeting = context.getString(R.string.text_evening);
        return greeting;
    }

}

