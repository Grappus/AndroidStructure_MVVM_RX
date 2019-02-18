package com.grappus.android.basemvvm.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.grappus.android.basemvvm.R;

import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;

public class RuntimePermissions implements Constants.RuntimePermissions {

    private static final String TAG = RuntimePermissions.class.getSimpleName();
    private Activity activity;


    public RuntimePermissions(Activity activity) {
        this.activity = activity;
    }


    public boolean checkPermissionForCallPhone() {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE);
        return result == PackageManager.PERMISSION_GRANTED;
    }


    public List<String> checkMissingPermissions(String[] permissionsList) {
        final List<String> missingPermissions = new ArrayList<String>();
        // check all required dynamic permissions
        for (final String permission : permissionsList) {
            final int result = ContextCompat.checkSelfPermission(activity, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(permission);
            }
        }
        return missingPermissions;
    }

    public String[] getMissingPermissions(List<String> missingPermissions) {
        final String[] permissions = missingPermissions
                .toArray(new String[missingPermissions.size()]);
        return permissions;
    }


    //Permission
    public boolean isPlayServiceAvailable() {
        try {
            GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
            int resultCode = apiAvailability.isGooglePlayServicesAvailable(activity);

            if (resultCode != ConnectionResult.SUCCESS) {
                if (apiAvailability.isUserResolvableError(resultCode)) {
                    apiAvailability.getErrorDialog
                            (activity, resultCode, REQUEST_CODE_PLAY_SERVICES_AVAILABILITY_CHECK).show();
                } else {
                    AlertDialogUtils.showCustomAlertDialog(activity,
                            activity.getString(R.string.error_play_services),
                            null,
                            "Ok");
                }
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}