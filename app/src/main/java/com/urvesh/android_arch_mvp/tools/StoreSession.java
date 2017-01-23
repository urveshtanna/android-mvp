package com.urvesh.android_arch_mvp.tools;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class StoreSession {

    public static final String TOKEN = "token";
    public static final String MY_PREF = "android-arch-mvp";

    private Context mContext;
    SharedPreferences mSharedPreference;
    SharedPreferences.Editor mEditor;

    public StoreSession(Context context) {
        this.mContext = context;
        if (context != null) {
            mSharedPreference = context.getSharedPreferences(MY_PREF, Context.MODE_PRIVATE); // 0 - for private mode
            mEditor = mSharedPreference.edit();
            mEditor.commit();
        }
    }

    public StoreSession(Context context, String sessioToken) {
        this(context);
        mEditor.putString(TOKEN, sessioToken);
        mEditor.apply();
    }

    public void storeVariable(String key, String value) {
        mEditor.putString(key, value);
        mEditor.apply();
    }

    public void storeVariable(String key, int value) {
        mEditor.putInt(key, value);
        mEditor.apply();
    }

    public void storeVariable(String key, boolean value) {
        mEditor.putBoolean(key, value);
        mEditor.apply();
    }

    public void storeVariable(String key, Set<String> value) {
        mEditor.putStringSet(key, value);
        mEditor.apply();
    }

    public void clearData() {
        mEditor.clear();
        mEditor.apply();
    }

    public void deleteVariable(String key) {
        mEditor.remove(key);
        mEditor.apply();
    }

    public void clearSessionToken() {
        mEditor.remove(TOKEN);
        mEditor.apply();
    }
}
