package com.grappus.android.basemvvm.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.grappus.android.basemvvm.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Pattern;


/**
 * Created by chandrapratapsingh on 6/8/18.
 */

public class Utils implements Constants.Global, Constants.LoginMode, Constants.TimeFormats {

    private static final String TAG = Utils.class.getSimpleName();

    private static Context context;
    private static Toast toast;
    private static Dialog dialog;


    //Internet Check
    public static boolean isInternetConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    //Toast
    public static void showToast(Context context, String message) {
        if (context != null) {
            if (toast == null) {
                toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP, 0, 250);
            } else toast.setText(message);
            toast.show();
        }
    }

    public static void showShortToast(Context context, String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP, 0, 250);
        toast.show();
    }


    //Loader
    public static Dialog getLoader(Context context, String message) {
        try {
            Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_app_loader);
            dialog.setCancelable(false);
            final Window window = dialog.getWindow();
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.setGravity(Gravity.CENTER);
            return dialog;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void showLoader(Context context, String message) {
        try {

            /*dialog = new ProgressDialog(context);
            dialog.setNonCancelable(false);
            dialog.setMessage(message);
            if (!dialog.isShowing()) {
                AppLog.i(TAG, "showing loader");
                dialog.show();*/

            dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_app_loader);
            dialog.setCancelable(false);
            final Window window = dialog.getWindow();
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.setGravity(Gravity.CENTER);
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
            showToast(context, message);
        }
    }

    public static void hideLoader() {
        try {
            if (dialog != null && dialog.isShowing()) dialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //Keyboard
    public static void showKeyboard(Activity activity, boolean show) {
        View view = activity.getCurrentFocus();
        try {
            InputMethodManager imgr = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (show)
                imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            else if (view != null) imgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showKeyboard(Activity activity, boolean show, View view) {
        try {
            InputMethodManager imgr = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (show)
                imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            else if (view != null) imgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //String Handling
    public static boolean isEmpty(String key) {
        if (key == null || key.trim().isEmpty() || key.trim().length() == 0 || key.toLowerCase().equals("null"))
            return true;
        else return false;
    }

    public static boolean isNotEmpty(String key) {
        if (key == null || key.trim().isEmpty() || key.trim().length() == 0 || key.toLowerCase().equals("null"))
            return false;
        else return true;
    }

    public static boolean containsNumberOnly(String key) {
        return key.matches(PATTERN_NUMBER_ONLY);
    }

    public static int parseInt(String key) {
        return isNotEmpty(key) && containsNumberOnly(key) ? Integer.parseInt(key) : 0;
    }

    public static String getCapitalizedString(String name) {
        String validName = "";

        if (Utils.isNotEmpty(name)) {
            String names[] = name.split(" ");
            if (names.length > 1) {
                for (int i = 0; i < names.length; i++) {
                    if (i == 0) {
                        String nm = names[i].toLowerCase().trim();
                        validName = nm.substring(0, 1).toUpperCase() +
                                (nm.length() > 1 ? nm.substring(1).toLowerCase() : "");
                    } else if (Utils.isNotEmpty(names[i])) {
                        String nm = names[i].toLowerCase();
                        validName += " " + nm.substring(0, 1).toUpperCase() +
                                (nm.length() > 1 ? nm.substring(1).toLowerCase() : "");
                    }
                }
            } else
                validName = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        }
        //AppLog.e("FORMATTED NAME - " + validName);
        return validName;
    }

    public static String getCombinedStringList(List<String> strings, String separator,
                                               boolean doCapitalize) {
        String str = null;

        if (strings != null && strings.size() > 0) {
            for (int i = 0; i < strings.size(); i++) {
                if (isNotEmpty(strings.get(i))) {
                    String st = doCapitalize ? getCapitalizedString(strings.get(i)) : strings.get(i);
                    if (Utils.isNotEmpty(str)) str = str + separator + st;
                    else str = st;
                }
            }
        }
        return str;
    }


    //Cred Validation
    public static boolean isValidEmail(String email) {
        //AppLog.e(TAG, "Email: " + email);
        return (Utils.isNotEmpty(email)
                && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
                && Pattern.matches(PATTERN_EMAIL, email));
    }

    public static boolean isValidPhoneNumber(String number) {
        //AppLog.e(TAG, "Email: " + email);
        return (Utils.isNotEmpty(number) && number.length() == 10 && Pattern.matches(PATTERN_PHONE_NUMBER, number));
    }

    public static boolean isValidPassword(String password) {
        return Pattern.matches(PATTERN_PASSWORD, password);
    }

    public static int getLoginMode(String username) {
        if (isValidEmail(username)) return LOGIN_MODE_EMAIL;
        else if (isValidPhoneNumber(username)) return LOGIN_MODE_PHONE;
        else return LOGIN_MODE_NONE;
    }


    //Time Formatting
    public static SimpleDateFormat getCurrentTimeZoneFormat(String timeFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat, Locale.getDefault());
        Date currentDate = new Date();
        TimeZone tz = Calendar.getInstance().getTimeZone();

        //String name = tz.getDisplayName(tz.inDaylightTime(now), TimeZone.SHORT);
        String name = TimeZone.getDefault().getDisplayName(tz.inDaylightTime(currentDate), TimeZone.SHORT);
        sdf.setTimeZone(TimeZone.getTimeZone("\"" + name + "\""));
        //Log.d("hack", "\"" + name + "\"");
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
            if (Utils.isNotEmpty(time)) calendar.setTime(sdf.parse(time));
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
            if (Utils.isNotEmpty(time)) cal.setTime(sdfT.parse(time));
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

        /*try {
            return sdfo.parse(sdfo.format(calendar.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
            return new Date();
        }*/
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

    public static long getTime(String time, String inputTimeFormat) {
        SimpleDateFormat sdf = getCurrentTimeZoneFormat(inputTimeFormat);
        Calendar calendar = Calendar.getInstance();
        try {
            if (Utils.isNotEmpty(time)) calendar.setTime(sdf.parse(time));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return calendar.getTimeInMillis();
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


    //Misc Utils
    public static boolean isInRange(long value, long start, long end) {
        if (value >= start && value <= end) return true;
        return false;
    }

    public static boolean isInRange(int value, int start, int end) {
        if (value >= start && value <= end) return true;
        return false;
    }

    public static String formatAmount(Object amount) {
        try {
            return new DecimalFormat("##,##,##0").format(amount);
        } catch (Exception e) {
            e.printStackTrace();
            return String.valueOf(amount);
        }
    }

    public static boolean isAlpha(String name) {
        return name.matches(PATTERN_TEXT_ONLY);
    }


    public static String getUniqueId() {
        return UUID.randomUUID().toString();
    }

    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }


    public static int getAge(String sDOB) {
        if (isNotEmpty(sDOB)) {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_DOB);
            try {
                Calendar today = Calendar.getInstance();
                Calendar dob = Calendar.getInstance();
                dob.setTime(sdf.parse(sDOB));

                int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
                if (today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)) {
                    age--;
                }
                return age;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
}