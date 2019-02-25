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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Pattern;

import static com.grappus.android.basemvvm.utils.Constants.Gender.FEMALE;
import static com.grappus.android.basemvvm.utils.Constants.Gender.MALE;
import static com.grappus.android.basemvvm.utils.TextUtils.getCombinedStringList;


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


    //Cred Validation
    public static boolean isValidEmail(String email) {
        //AppLog.e(TAG, "Email: " + email);
        return (TextUtils.isNotEmpty(email)
                && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
                && Pattern.matches(PATTERN_EMAIL, email));
    }

    public static boolean isValidPhoneNumber(String number) {
        //AppLog.e(TAG, "Email: " + email);
        return (TextUtils.isNotEmpty(number) && number.length() == 10 && Pattern.matches(PATTERN_PHONE_NUMBER, number));
    }

    public static boolean isValidPassword(String password) {
        return Pattern.matches(PATTERN_PASSWORD, password);
    }

    public static int getLoginMode(String username) {
        if (isValidEmail(username)) return LOGIN_MODE_EMAIL;
        else if (isValidPhoneNumber(username)) return LOGIN_MODE_PHONE;
        else return LOGIN_MODE_NONE;
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



    public static String getAgeAndGender(String dob, String gender) {
        ArrayList<String> list = new ArrayList<>();

        if (TextUtils.isNotEmpty(dob))
            list.add("Age " + getAge(dob));

        if (TextUtils.isNotEmpty(gender))
            switch (gender) {
                case Constants.Gender.M: {
                    list.add(MALE);
                    break;
                }
                case Constants.Gender.F: {
                    list.add(FEMALE);
                    break;
                }
            }
        return getCombinedStringList(list, Constants.Global.DOT, true);
    }

    public static int getAge(String sDOB) {
        if (TextUtils.isNotEmpty(sDOB)) {
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