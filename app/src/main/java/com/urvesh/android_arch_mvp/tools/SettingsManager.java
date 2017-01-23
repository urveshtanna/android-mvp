package com.urvesh.android_arch_mvp.tools;


import com.urvesh.android_arch_mvp.BuildConfig;

public class SettingsManager {

    public static boolean isDebuggable() {
        return BuildConfig.FLAVOR.equals("develop");
    }
}