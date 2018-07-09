package com.grappus.android.basemvvm

import android.app.Application
import android.os.Build
import android.os.StrictMode
import com.grappus.android.basemvvm.BuildConfig
import com.grappus.android.basemvvm.utils.AppLog
import com.grappus.android.basemvvm.utils.Prefs

class BaseApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            // only for gingerbread and newer versions
            builder.detectFileUriExposure()
        }

        //Shared Preference
        Prefs.init(applicationContext)

        //App Logging
        AppLog.setShowLogs(BuildConfig.DEBUG)
    }
}