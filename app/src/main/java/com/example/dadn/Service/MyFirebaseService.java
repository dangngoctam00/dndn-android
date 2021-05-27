package com.example.dadn.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.icu.text.UnicodeSetIterator;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.dadn.R;
import com.example.dadn.ui.alert.AlertActivity;
import com.example.dadn.ui.alert.AlertTasks;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import io.reactivex.rxjava3.internal.operators.parallel.ParallelRunOn;

import static androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC;

public class MyFirebaseService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseService";
    private static final int ACTION_ACCEPT_TASK = 1;
    private static final int ACTION_IGNORE_TASK = 14;
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
        String alert = remoteMessage.getData().get("alert");
        if(alert.equals("true")){
            //startActivity(AlertActivity.newIntent(this));
            Log.d(TAG, "onMessageReceived: ok");
        }
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        sendNotification(remoteMessage);
    }

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }

    /*
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }

     */
    private Intent createIntent(String actionName) {
        Intent intent = new Intent(this, AlertTasks.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setAction(actionName);
        return intent;
    }


    public static void clearAllNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    private void sendNotification(RemoteMessage remoteMessage) {
        Log.d(TAG, "sendNotification: " + remoteMessage.getData());
        Map<String, String> dataPayload = remoteMessage.getData();
        String title = dataPayload.get("title");
        String body = dataPayload.get("body");


        Intent accept = new Intent(this, AlertActivity.class);
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
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this ,NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_tomato)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background))
                .setContentTitle(title)
                .setContentText(body)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentIntent(contentIntent(this))
                .addAction(acceptAction(this))
                .addAction(ignoreAction(this))
                .setAutoCancel(true);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }
        notificationManager.notify(TASK_NOTIFICATION_ID, notificationBuilder.build());
    }

    private static NotificationCompat.Action ignoreAction(Context context) {
        Log.d(TAG, "ignoreAction");
        Intent ignoreTaskIntent = new Intent(context, AlertReceiver.class);
        ignoreTaskIntent.setAction(AlertTasks.ACTION_REJECT);
        PendingIntent ignoreReminderPendingIntent = PendingIntent.getBroadcast(
                context,
                ACTION_IGNORE_TASK,
                ignoreTaskIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action ignoreTaskAction = new NotificationCompat.Action(R.drawable.ic_tomato,
                "Hủy bỏ",
                ignoreReminderPendingIntent);

        return ignoreTaskAction;
    }

    private static NotificationCompat.Action acceptAction(Context context) {
        Log.d(TAG, "acceptAction");
        Intent acceptTaskIntent = new Intent(context, AlertReceiver.class);

        acceptTaskIntent.setAction(AlertTasks.ACTION_ACCEPT);
        PendingIntent incrementWaterPendingIntent = PendingIntent.getBroadcast(
                context,
                ACTION_ACCEPT_TASK,
                acceptTaskIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Action acceptTaskAction = new NotificationCompat.Action(R.drawable.ic_tomato,
                "Thực hiện",
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