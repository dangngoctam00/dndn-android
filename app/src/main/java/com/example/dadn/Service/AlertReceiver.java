package com.example.dadn.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlertReceiver extends BroadcastReceiver {

    private static Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        AlertIntentService.enqueueWork(context,intent);
    }
}
