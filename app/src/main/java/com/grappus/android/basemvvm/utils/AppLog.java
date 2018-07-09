package com.grappus.android.basemvvm.utils;

import android.util.Log;


public class AppLog {

    private static final String DEFAULT_TAG = "TAG";
    private static boolean showLogs;

    public static void setShowLogs(boolean show) {
        showLogs = show;
    }

    public static boolean getShowLogs() {
        return showLogs;
    }


    public static void i(String tag, String msg) {
        if (getShowLogs())
            Log.i(tag, msg);
    }

    public static void i(String msg) {
        if (getShowLogs())
            Log.i(DEFAULT_TAG, msg);
    }

    public static void d(String tag, String msg) {
        if (getShowLogs())
            Log.i(tag, msg);
    }

    public static void d(String msg) {
        if (getShowLogs())
            Log.i(DEFAULT_TAG, msg);
    }

    public static void e(String msg) {
        if (getShowLogs())
            Log.e(DEFAULT_TAG, msg);
    }

    public static void e(String tag, String msg) {
        if (getShowLogs())
            Log.e(tag, msg);
    }

    //what a terrible failure! To log stuff that defies logic of the android world :P
    public static void wtf(String tag, String msg) {
        if (getShowLogs())
            Log.wtf(tag, msg);
    }
}