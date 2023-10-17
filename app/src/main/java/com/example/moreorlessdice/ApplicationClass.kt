package com.example.moreorlessdice

import android.app.Application
import com.onesignal.OneSignal

const val ONESIGNAL_APP_ID = "7a9437ab-55e6-48c3-b18a-cb0ad6f07c3d"

class ApplicationClass : Application() {
    override fun onCreate() {
        super.onCreate()

        // Logging set to help debug issues, remove before releasing your app.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

        // OneSignal Initialization
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)
    }
}