package com.grappus.android.basemvvm.utils;

import android.Manifest;

/**
 * Created by chandrapratapsingh on 6/7/18.
 */

public class Constants {

    public interface Global {

        String FILE_DIR_ROOT = "Grappus";
        String FILE_DIR_SUB_DOC = FILE_DIR_ROOT + "/Documents";
        String FILE_DIR_SUB_MEDIA = FILE_DIR_ROOT + "/Media";

        String PATTERN_USERNAME = "^(?=.*[a-z])[a-z0-9]{4,16}$"; //"^(?=.{3,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z][a-zA-Z0-9._]+(?<![_.])$"; //"^[a-zA-Z0-9._]{3,15}$";
        String PATTERN_EMAIL = "[a-zA-Z0-9._-]+@[a-z0-9]+\\.+[a-z]+";
        String PATTERN_PHONE_NUMBER = "^[6-9]\\d{9}$"; //"^[0-9]{10,15}$"; //
        String PATTERN_PHONE_NUMBERS_ONLY = "^[+]?[0-9]+$";
        String PATTERN_PASSWORD = "^(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])[a-zA-Z0-9!@#$%^&*]{8,16}$"; //"^(?=.*?[A-Z])(?=(.*[a-z]){1,})(?=(.*[\\d]){1,})(?!.*\\s).{8,}$"; //"^(?=.{4,})(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z0-9]).*$";
        String PATTERN_TEXT_ONLY = "[a-zA-Z ]+";
        String PATTERN_NUMBER_ONLY = "[0-9]+";

        String DEVICE_TYPE = "android";

        String COUNTRY_CODE = "+91";

        String IS_LOGGED_IN = "is_logged_in";
        String DOT = " • ";
    }

    public interface AnimationDuration {
        long ANIM_DURATION_SHORT = 250L;
        long ANIM_DURATION_REGULAR = 500L;
        long ANIM_DURATION_LONG = 700L;
    }

    public interface RequestAction {
        int ACTION_LOGIN = 101;
        int ACTION_SIGN_UP = 102;
    }

    public interface RequestArgs {
        String ARG_REQ_ACTION = "reqAction";
        String ARG_REQ_FRAGMENT = "reqAction";
        String ARG_EXTRA_OBJECT = "argExtraObject";
        String ARG_EXTRA_1 = "argExtra1";
        String ARG_EXTRA_2 = "argExtra2";
        String ARG_EXTRA_3 = "argExtra3";
    }

    public interface RequestFragment {
        int FRAG_LOGIN = 10001;
        int FRAG_SIGN_UP = 10002;
    }

    public interface LoginMode {
        String LOGIN_MODE = "login_mode";

        int LOGIN_MODE_NONE = 0;
        int LOGIN_MODE_EMAIL = 1;
        int LOGIN_MODE_PHONE = 2;
    }

    public interface TimeFormats {
        String TIME_ONLY = "hh:mm a";

        String DATE_TIME_1 = "dd-MMM-yyy hh:mm a";
        String DATE_TIME_2 = "dd/MM/yyyy hh:mm a";
        String DATE_TIME_3 = "MM/dd/yyyy HH:mm";

        String DATE_ONLY_1 = "dd MMMM yyyy";
        String DATE_ONLY_2 = "dd/MM/yyyy";
        String DATE_ONLY_3 = "dd-MMM-yyyy";
        String DATE_ONLY_4 = "MMMM dd, yyyy";
        String DATE_ONLY_5 = "dd MMM, yyyy";
        String DATE_ONLY_6 = "EEEE, MMM dd";
        String DATE_ONLY_7 = "EEEE, MMM dd";
        String DATE_ONLY_8 = "dd MMM yyyy";

        String DATE_DOB = "dd/MM/yyyy";
        String DATE_DOB_YEAR = "yyyy-MM-dd";
        String DATE_SLOT = "MM/dd/yyyy";
        String DATE_MONTH_SHORT = "MMM";
        String DATE_YEAR = "yyyy";
        String DATE_MONTH_YEAR = "MMM yyyy";
        String DATE_MONTH_YEAR_2 = "MM/yy";
        String DATE_DAY = "dd";
        String DATE_1 = "dd MMMM, yyyy";

        String DAY_ONLY = "EEEE";
        String DAY_DATE = "EEEE dd MMM, yyyy";

        String TIME_SERVER = "yyyy-MM-dd'T'HH:mm:ss"; //"yyyy-MM-dd'T'HH:mm:ss.SSSZ";
        String TIME_SERVER_2 = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
        String TIME_SERVER_3 = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        String TIME_SERVER_4 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

        String TIME_24_HOUR = "kk:mm";
        String TIME_12_HOUR_AMPM = "hh:mm a";
    }

    public interface TimeUnits {
        String TIME_FORMAT_AM = "AM";
        String TIME_FORMAT_PM = "PM";
        String TIME_IN_MIN = "mins";
        String TIME_IN_SECONDS = "secs";
        String TIME_IN_HOURS = "hours";
    }

    public interface LocalBroadcastAction {
        String ACTION_APP_UPDATE = "action_app_update";
        String ACTION_LOGOUT = "action_logout";
    }


    public interface APIRequestFragments {
        String HEADER_CONTENT_TYPE = "Content-Type";
        String HEADER_APP_VERSION = "app-version";
        String HEADER_DEVICE_TYPE = "device-type";

        String RESPONSE_TYPE_JSON = "application/json";
        String RESPONSE_TYPE_URL_ENCODED = "application/x-www-form-urlencoded";
        String RESPONSE_TYPE_MULTIPART = "multipart/form-data";

        int CONNECT_TIMEOUT_SECS = 10;
    }

    public interface APIResponseStatus {
        int STATUS_INVALID_CREDENTIALS = 401;

        int ERROR_INTERNET_CONNECTION = 40001;
    }

    public interface APIEndPoints {
        String API_LOGIN = "/login";
        String API_SIGN_UP = "/signup";
    }

    public interface Gender {
        String M = "m";
        String F = "f";
        String MALE = "Male";
        String FEMALE = "Female";
    }

    public interface RuntimePermissions {
        int REQUEST_CODE_RECORD_PERMISSION = 1;

        int REQUEST_CODE_EXTERNAL_STORAGE_PERMISSION = 2;
        String[] EXTERNAL_STORAGE_PERMISSIONS = new String[]
                {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

        int REQUEST_CODE_CAMERA_PERMISSION = 3;
        String[] CAMERA_PERMISSIONS = new String[]{Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};

        int REQUEST_CODE_READ_SMS = 4;
        String[] SMS_PERMISSIONS = new String[]{Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_SMS};

        int REQUEST_CODE_LOCATION_PERMISSION = 5;
        String[] LOCATION_PERMISSIONS = new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE};

        int REQUEST_CODE_CALENDAR_PERMISSION = 6;
        String[] CALENDAR_PERMISSIONS = new String[]
                {Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR};

        int REQUEST_CODE_READ_PHONE_STATE = 7;
        String[] READ_PHONE_STATE_PERMISSIONS = new String[]
                {Manifest.permission.READ_PHONE_STATE};

        int REQUEST_CODE_FILE_PICKER = 8;
        String[] FILE_PICKER_PERMISSIONS = new String[]
                {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE};

        int REQUEST_CODE_CALL_PHONE_PERMISSION = 9;
        String[] PHONE_CALL_PERMISSIONS = new String[]
                {Manifest.permission.CALL_PHONE};

        String[] FILE_PICKER_AUDIO_PERMISSIONS = new String[]
                {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO};

        int REQUEST_CODE_PLAY_SERVICES_AVAILABILITY_CHECK = 10;
    }

    public interface Broadcasts {
        String NOTIFICATION_BROADCAST = "notification_broadcast";
    }
}