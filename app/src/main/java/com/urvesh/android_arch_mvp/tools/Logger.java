package com.urvesh.android_arch_mvp.tools;

import android.util.Log;


public class Logger {

    public static void i(String tag, String msg) {
        log(Log.INFO, tag, msg);
    }

    public static void d(String tag, String msg) {
        log(Log.DEBUG, tag, msg);
    }

    public static void v(String tag, String msg) {
        log(Log.VERBOSE, tag, msg);
    }

    public static void w(String tag, String msg) {
        log(Log.WARN, tag, msg);
    }

    public static void e(String tag, String msg) {
        log(Log.ERROR, tag, msg);
    }

    public static void log(int priority, String tag, String msg) {
        if (msg == null)
            msg = "no-MESSAGE";
        if (tag == null)
            tag = "empty-TAG";
        if (SettingsManager.isDebuggable()) {
            Log.println(priority, tag, msg);
            try {
                //Crashlytics.getInstance().core.log(priority, tag, msg);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        } else {
            try {
                //Crashlytics.getInstance().core.log(priority, tag, msg);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }

    public static void debugLog(String tag, String msg) {
        if (SettingsManager.isDebuggable()) {
            Log.d(tag, msg);
        }
    }

    public static void registerDevice(String deviceId) {
        //if (deviceId != null && Fabric.isInitialized())
            //Crashlytics.getInstance().core.setString(AnalyticKeys.DEVICE_ID, deviceId);
    }

    public static void registerUser(String userId, String name) {
        /*if (Fabric.isInitialized()) {
            if (userId != null) Crashlytics.getInstance().core.setUserIdentifier(userId);
            if (name != null) Crashlytics.getInstance().core.setUserName(name);
        }*/
    }

    public static void exception(String tag, Throwable e) {
        if (e == null) return;
        if (e.getMessage() != null)
            log(Log.ERROR, tag, e.getMessage());
        if (SettingsManager.isDebuggable()) {
            e.printStackTrace();
            try {
                //Crashlytics.getInstance().core.logException(e);
            } catch (IllegalStateException exception) {
                exception.printStackTrace();
            }
        } else {
            try {
                //Crashlytics.getInstance().core.logException(e);
            } catch (IllegalStateException exception) {
                exception.printStackTrace();
            }
        }
    }

}
