package com.example.dadn.ui.alert;

import android.content.Context;
import android.util.Log;

import androidx.databinding.ObservableBoolean;

import com.example.dadn.Service.MyFirebaseService;
import com.example.dadn.utils.PreferenceUtilities;

public class AlertTasks {
    public static final String ACTION_ACCEPT = "accept";
    public static final String ACTION_CANCEL = "reject";
    public static final String ACTION_SHOW = "show";


    synchronized public static void executeTask(Context context, String action) {
        if (ACTION_ACCEPT.equals(action)) {
            PreferenceUtilities.SetisAlertProcessing(context,true);
            MyFirebaseService.clearAllNotifications(context);
            Log.d("action accept", "ok");
        } else if (ACTION_CANCEL.equals(action)) {
            PreferenceUtilities.SetisAlertProcessing(context,false);
            PreferenceUtilities.SetAlertState(context,false);
            MyFirebaseService.clearAllNotifications(context);
            Log.d("action reject", "ok");
        }


    }


}
