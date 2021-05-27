package com.example.dadn.ui.alert;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.dadn.Service.MyFirebaseService;

public class AlertTasks {
    public static final String ACTION_ACCEPT = "accept";
    public static final String ACTION_REJECT = "reject";
    public static final String ACTION_SHOW = "show";



    public static void executeTask(Context context, String action) {
        if (ACTION_ACCEPT.equals(action)) {
            MyFirebaseService.clearAllNotifications(context);
            Log.d("action accept", "ok");
        } else if (ACTION_REJECT.equals(action)) {
            MyFirebaseService.clearAllNotifications(context);
            Log.d("action reject", "ok");
        }


    }


}
