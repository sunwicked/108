
package com.one.assignment.utils;

import com.one.assignement.BuildConfig;

public class Log {
    private static final boolean isEnabled = BuildConfig.LOG_ENABLED;

    public static void d(String tag, String msg) {
        if (isEnabled)
            android.util.Log.d(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (isEnabled)
            android.util.Log.i(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (isEnabled)
            android.util.Log.e(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (isEnabled)
            android.util.Log.v(tag, msg);
    }

}
