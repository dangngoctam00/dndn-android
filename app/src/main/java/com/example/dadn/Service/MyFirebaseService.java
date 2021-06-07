package com.example.dadn.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.dadn.R;
import com.example.dadn.ui.alert.AlertActivity;
import com.example.dadn.ui.alert.AlertTasks;
import com.example.dadn.utils.PreferenceUtilities;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import static androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC;

public class MyFirebaseService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseService";
    private static final int ACTION_ACCEPT_TASK = 1;
    private static final int ACTION_CANCEL_TASK = 14;
    private static final int ALERT_PENDING_INTENT_ID = 3417;
    private static final int TASK_NOTIFICATION_ID = 1138;
    private static final String NOTIFICATION_CHANNEL_ID = "smarttomamtofarmchannel";


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // handle a notification payload.
        /*
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

            sendNotification(remoteMessage.getNotification().getBody());
        }

         */
        Log.d(TAG, "onMessageReceived");
        String alert = remoteMessage.getData().get("alert");
        if(alert.equals("alert")){
            Log.d(TAG, "onMessageReceived: ok");
            PreferenceUtilities.SetAlertState(this,true);
            sendNotification(remoteMessage);
        }
        else if (alert.equals("taskCompleted")){
            Log.d(TAG, "onMessageReceived: completed");
            clearAllNotifications(this);
            PreferenceUtilities.SetAlertState(this,false);
            PreferenceUtilities.SetisAlertProcessing(this,false);
            PreferenceUtilities.SetcannotHandle(this, false);
        }
        else if(alert.equals("cannotHandle")){
            Log.d(TAG, "onMessageReceived: cannotHandle");
            clearAllNotifications(this);
            PreferenceUtilities.SetAlertState(this,true);
            PreferenceUtilities.SetcannotHandle(this, true);
            sendNotificationCannotHandle(remoteMessage);
        }


    }

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }


    public static void clearAllNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }
    private void sendNotificationCannotHandle(RemoteMessage remoteMessage){
        Log.d(TAG, "sendNotification: " + remoteMessage.getData());
        Map<String, String> dataPayload = remoteMessage.getData();
        String title = dataPayload.get("title");
        String body = dataPayload.get("body");
        String detail = dataPayload.get("detail");
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    getString(R.string.main_notification_channel_name),
                    NotificationManager.IMPORTANCE_HIGH);
            mChannel.enableLights(true);
            mChannel.setVibrationPattern(new long[]{0,1000,500,1000});
            mChannel.enableVibration(true);
            mChannel.setLockscreenVisibility(VISIBILITY_PUBLIC);
            notificationManager.createNotificationChannel(mChannel);
        }
        SpannableString string = new SpannableString(title);
        string.setSpan(new StyleSpan(Typeface.BOLD), 0, string.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this ,NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_tomato)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_siren))
                .setContentTitle(string)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setColor(ContextCompat.getColor(this, R.color.red_dark))
                .setColorized(true)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(detail))
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentIntent(contentIntent(this))
                .addAction(ignoreAction(this, "Đã hiểu"))
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }
        notificationManager.notify(TASK_NOTIFICATION_ID, notificationBuilder.build());
    }
    private void sendNotification(RemoteMessage remoteMessage) {
        Log.d(TAG, "sendNotification: " + remoteMessage.getData());
        Map<String, String> dataPayload = remoteMessage.getData();
        String title = dataPayload.get("title");
        String body = dataPayload.get("body");
        String detail = dataPayload.get("detail");




        //Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //.setSound(defaultSoundUri)
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    getString(R.string.main_notification_channel_name),
                    NotificationManager.IMPORTANCE_HIGH);
            mChannel.enableLights(true);
            mChannel.setVibrationPattern(new long[]{0,1000,500,1000});
            mChannel.enableVibration(true);
            mChannel.setLockscreenVisibility(VISIBILITY_PUBLIC);
            notificationManager.createNotificationChannel(mChannel);
        }
        SpannableString string = new SpannableString(title);
        string.setSpan(new StyleSpan(Typeface.BOLD), 0, string.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this ,NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_tomato)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_siren))
                .setContentTitle(string)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setColor(ContextCompat.getColor(this, R.color.red_dark))
                .setColorized(true)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(detail))
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentIntent(contentIntent(this))
                .addAction(acceptAction(this))
                .addAction(ignoreAction(this, "Hủy bỏ"))
                .setAutoCancel(true);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }
        notificationManager.notify(TASK_NOTIFICATION_ID, notificationBuilder.build());
    }

    private static NotificationCompat.Action ignoreAction(Context context, String str) {
        Log.d(TAG, "ignoreAction");
        SpannableString string = new SpannableString(str);
        string.setSpan(new StyleSpan(Typeface.BOLD), 0, string.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        Intent cancelTaskIntent = new Intent(context, AlertReceiver.class);
        cancelTaskIntent.setAction(AlertTasks.ACTION_CANCEL);
        PendingIntent ignoreReminderPendingIntent = PendingIntent.getBroadcast(
                context,
                ACTION_CANCEL_TASK,
                cancelTaskIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action ignoreTaskAction = new NotificationCompat.Action(R.drawable.ic_tomato,
                string,
                ignoreReminderPendingIntent);

        return ignoreTaskAction;
    }

    private static NotificationCompat.Action acceptAction(Context context) {
        SpannableString string = new SpannableString("Thực hiện");
        string.setSpan(new StyleSpan(Typeface.BOLD), 0, string.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        Log.d(TAG, "acceptAction");
        Intent acceptTaskIntent = new Intent(context, AlertReceiver.class);

        acceptTaskIntent.setAction(AlertTasks.ACTION_ACCEPT);
        PendingIntent incrementWaterPendingIntent = PendingIntent.getBroadcast(
                context,
                ACTION_ACCEPT_TASK,
                acceptTaskIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Action acceptTaskAction = new NotificationCompat.Action(R.drawable.ic_tomato,
                string,
                incrementWaterPendingIntent);
        return acceptTaskAction;
    }
    private static PendingIntent contentIntent(Context context) {
        Intent startActivityIntent = new Intent(context, AlertActivity.class);
        return PendingIntent.getActivity(
                context,
                ALERT_PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
