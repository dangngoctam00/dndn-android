package com.example.dadn.Service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import com.example.dadn.ui.alert.AlertTasks;

public class AlertIntentService extends JobIntentService {

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        String action = intent.getAction();
        Log.d("action", action);
        AlertTasks.executeTask(this, action);
    }
}
