package com.example.dadn.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceUtilities {
    public static final String KEY_ALERT = "Alert";
    public static final String KEY_IS_ALERT_PROCESSING = "isAlertProcessing";
    public static final String KEY_CAN_NOT_HANDLE = "cannotHandle";
    public static final String KEY_ALERT_ACTIVE = "alertactive";
    public static final String KEY_SOLID = "solid";
    public static final String KEY_TEMP = "temp";
    public static final String KEY_HUMID = "humid";
    public static final String KEY_LIGHT = "light";
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

    synchronized public static void SetSolid(Context context, String i) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_SOLID, i);
        editor.apply();
    }
    public static String getSolid (Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String i = prefs.getString(KEY_SOLID, "");
        return i;
    }

    synchronized public static void SetLight(Context context, String i) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_LIGHT, i);
        editor.apply();
    }
    public static String getLight (Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String i = prefs.getString(KEY_LIGHT, "");
        return i;
    }

    synchronized public static void SetHumid(Context context, String i) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_HUMID, i);
        editor.apply();
    }
    public static String getHumid (Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String i = prefs.getString(KEY_HUMID, "");
        return i;
    }

    synchronized public static void SetTemp(Context context, String i) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_TEMP, i);
        editor.apply();
    }
    public static String getTemp (Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String i = prefs.getString(KEY_TEMP, "");
        return i;
    }

    public static void resetState(Context context){
        SetTemp(context, "");
        SetSolid(context, "");
        SetLight(context, "");
        SetHumid(context, "");
    }


}
