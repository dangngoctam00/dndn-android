package com.example.dadn.ui.alert;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.databinding.ObservableBoolean;

import com.example.dadn.Service.MyFirebaseService;
import com.example.dadn.data.dto.AlertReponse;
import com.example.dadn.data.dto.AlertRequest;
import com.example.dadn.network.APIClient;
import com.example.dadn.utils.PreferenceUtilities;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlertTasks {
    public static final String ACTION_ACCEPT = "accept";
    public static final String ACTION_CANCEL = "reject";



    synchronized public static void executeTask(Context context, String action) {
        MyFirebaseService.clearAllNotifications(context);
        if (ACTION_ACCEPT.equals(action)) {
            sendRequest(context, "dadn", "acceptTask");
            PreferenceUtilities.SetisAlertProcessing(context,true);
            Log.d("action accept", "ok");
        } else if (ACTION_CANCEL.equals(action)) {
            PreferenceUtilities.SetisAlertProcessing(context,false);
            PreferenceUtilities.SetAlertState(context,false);
            PreferenceUtilities.SetcannotHandle(context, false);
            PreferenceUtilities.resetState(context);
            sendRequest(context, "dadn", "cancelTask");
            Log.d("action reject", "ok");
        }

    }
    synchronized private static void sendRequest(Context context,String id, String action){
        AlertRequest payload = new AlertRequest(id, action);
        APIClient.getRetrofit().requestTask(payload).enqueue(new Callback<AlertReponse>() {
            @Override
            public void onResponse(Call<AlertReponse> call, Response<AlertReponse> response) {
                AlertReponse alertReponse = response.body();
                if(alertReponse.getStatus().equals("processing")){
                    PreferenceUtilities.SetisAlertProcessing(context,true);
                    Log.d("AlertRequest", "accept ok");
                }
                else if(alertReponse.getStatus().equals("rejected")){
                    Log.d("AlertRequest", "rejected ok");
                }
                else{
                    Log.d("AlertRequest", "not ok");
                    Toast.makeText(context, "try again", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AlertReponse> call, Throwable t) {
                Log.d("AlertRequest", "onFailure: "+ t);
                Log.d("AlertRequest", "error");
                Toast.makeText(context, "alert: send request error", Toast.LENGTH_LONG).show();
            }
        });

    }


}
