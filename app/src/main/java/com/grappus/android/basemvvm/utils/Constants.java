package com.grappus.android.basemvvm.utils;

/**
 * Created by chandrapratapsingh on 6/7/18.
 */

public class Constants {

    public interface Global {

        String FILE_DIR_ROOT = "BajajCapital";
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

        String DATE_ONLY_1 = "dd MMMM yyyy";
        String DATE_ONLY_2 = "dd/MM/yyyy";
        String DATE_ONLY_3 = "dd-MMM-yyyy";
        String DATE_ONLY_4 = "MMMM dd, yyyy";

        String DATE_DOB = "dd/MM/yyyy";
        String DATE_MONTH_SHORT = "MMM";
        String DATE_YEAR = "yyyy";
        String DATE_MONTH_YEAR = "MMM yyyy";

        String DAY_ONLY = "EEEE";
        String DAY_DATE = "EEEE dd MMM, yyyy";

        String TIME_SERVER = "yyyy-MM-dd'T'HH:mm:ss"; //"yyyy-MM-dd'T'HH:mm:ss.SSSZ";
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
}