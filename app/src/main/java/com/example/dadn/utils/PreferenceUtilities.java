package com.example.dadn.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceUtilities {
    public static final String KEY_ALERT = "Alert";
    public static final String KEY_IS_ALERT_PROCESSING = "isAlertProcessing";
    public static final String KEY_CAN_NOT_HANDLE = "cannotHandle";
    public static final String KEY_ALERT_ACTIVE = "alertactive";
    private static final Boolean DEFAULT_BOOLEAN = false;

    synchronized public static void SetAlertState(Context context, Boolean i) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(KEY_ALERT, i);
        editor.apply();
    }
    public static Boolean getAlertState(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Boolean i = prefs.getBoolean(KEY_ALERT, DEFAULT_BOOLEAN);
        return i;
    }
    synchronized public static void SetisAlertProcessing(Context context, Boolean i) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(KEY_IS_ALERT_PROCESSING, i);
        editor.apply();
    }
    public static Boolean getisAlertProcessing(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Boolean i = prefs.getBoolean(KEY_IS_ALERT_PROCESSING, DEFAULT_BOOLEAN);
        return i;
    }

    synchronized public static void SetcannotHandle(Context context, Boolean i) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(KEY_CAN_NOT_HANDLE, i);
        editor.apply();
    }
    public static Boolean getcannotHandle(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Boolean i = prefs.getBoolean(KEY_CAN_NOT_HANDLE, DEFAULT_BOOLEAN);
        return i;
    }

    synchronized public static void SetAlertActive(Context context, Boolean i) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(KEY_ALERT_ACTIVE, i);
        editor.apply();
    }
    public static Boolean getAlertActive(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Boolean i = prefs.getBoolean(KEY_ALERT_ACTIVE, true);
        return i;
    }


}
