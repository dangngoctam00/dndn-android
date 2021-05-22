package com.example.dadn.Service;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import com.example.dadn.ui.alert.AlertTasks;

public class AlertIntentService extends JobIntentService {

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        String action = intent.getAction();
        AlertTasks.executeTask(this, action);
    }
}
