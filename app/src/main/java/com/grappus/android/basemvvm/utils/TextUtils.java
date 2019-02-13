package com.grappus.android.basemvvm.utils;

import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.grappus.android.basemvvm.utils.Constants.Gender.FEMALE;
import static com.grappus.android.basemvvm.utils.Constants.Gender.MALE;
import static com.grappus.android.basemvvm.utils.Constants.TimeFormats.DATE_DOB;

public class TextUtils implements Constants.Global {

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

        if (TextUtils.isNotEmpty(name)) {
            String names[] = name.split(" ");
            if (names.length > 1) {
                for (int i = 0; i < names.length; i++) {
                    if (i == 0) {
                        String nm = names[i].toLowerCase().trim();
                        validName = nm.substring(0, 1).toUpperCase() +
                                (nm.length() > 1 ? nm.substring(1).toLowerCase() : "");
                    } else if (TextUtils.isNotEmpty(names[i])) {
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

    public static String getCombinedStringList(List<String> strings, String separator, boolean doCapitalize) {
        String str = null;

        if (strings != null && strings.size() > 0) {
            for (int i = 0; i < strings.size(); i++) {
                if (isNotEmpty(strings.get(i))) {
                    String st = doCapitalize ? getCapitalizedString(strings.get(i)) : strings.get(i);
                    if (TextUtils.isNotEmpty(str)) str = str + separator + st;
                    else str = st;
                }
            }
        }
        return str;
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


    public static void setTextView(TextView textView, String value) {
        if (textView == null) return;
        if (isEmpty(value)) textView.setVisibility(View.GONE);
        else {
            if (textView.getVisibility() != View.VISIBLE) textView.setVisibility(View.VISIBLE);
            textView.setText(value);
        }
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

