package com.urvesh.android_arch_mvp.domain.analytics;


import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ScreenNames {

    @Retention(RetentionPolicy.CLASS)
    @StringDef({ RequestPage})
    public @interface ScreenName {
    }

    public static final String RequestPage = "Request Page";
}