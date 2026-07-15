package com.autocrm.util

import android.util.Log

interface CrashLogger {
    fun logException(throwable: Throwable)
    fun logEvent(message: String)
}

class DefaultCrashLogger : CrashLogger {
    override fun logException(throwable: Throwable) {
        // TODO: In Phase 10+, replace with Sentry/Crashlytics
        Log.e("CrashLogger", "Exception caught: ", throwable)
    }

    override fun logEvent(message: String) {
        Log.d("CrashLogger", "Event: $message")
    }
}
