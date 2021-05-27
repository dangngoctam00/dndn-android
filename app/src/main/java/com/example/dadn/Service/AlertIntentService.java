package com.example.dadn.Service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import com.example.dadn.ui.alert.AlertTasks;

public class AlertIntentService extends JobIntentService {
    static final int JOB_ID = 1001;
    
    static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, AlertIntentService.class, JOB_ID, work);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        String action = intent.getAction();
        //Log.d("action", action);
        AlertTasks.executeTask(this, action);
    }
}
