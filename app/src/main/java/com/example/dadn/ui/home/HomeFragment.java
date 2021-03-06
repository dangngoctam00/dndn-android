package com.example.dadn.ui.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;

import com.example.dadn.R;
import com.example.dadn.databinding.FragmentHomeBinding;
import com.example.dadn.di.component.FragmentComponent;
import com.example.dadn.ui.alert.AlertActivity;
import com.example.dadn.ui.alert.alertprocessing.AlertProcessingActivity;
import com.example.dadn.ui.base.BaseFragment;
import com.example.dadn.utils.Constants;
import com.example.dadn.utils.PreferenceUtilities;
import com.example.dadn.utils.mqtt.MqttService;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

public class HomeFragment extends BaseFragment<FragmentHomeBinding ,HomeViewModel> implements HomeNavigator, SharedPreferences.OnSharedPreferenceChangeListener {

    FragmentHomeBinding mFragmentHomeBinding;
    MqttService mqttService;
    final String TAG = "HomeFragment TAG";

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("onCreate", "create");
        super.onCreate(savedInstanceState);
        mViewModel.setNavigator(this);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        prefs.registerOnSharedPreferenceChangeListener(this);
        mViewModel.setIsAlertProcessing(PreferenceUtilities.getisAlertProcessing(this.getActivity()));
        mViewModel.setAlertState(PreferenceUtilities.getAlertState(this.getActivity()));
        mViewModel.setCannotHandleAlert(PreferenceUtilities.getcannotHandle(this.getActivity()));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("onViewCreated", "create view");
        mFragmentHomeBinding = getViewDataBinding();
        startMqtt();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("onDestroy", "destroy");
//        try {
//            for (String topic : Constants.TOPICS) {
//                if (topic.split("/").equals(Constants.USERNAME)) {
//                    if (mqttService.mqttAndroidClient != null) {
//                        mqttService.mqttAndroidClient.unsubscribe(new String[] {topic});
//                    }
//                }
//                else {
//                    if (mqttService.mqttAndroidClient1 != null) {
//                        mqttService.mqttAndroidClient1.unsubscribe(new String[] {topic});
//                    }
//                }
//            }
//            mqttService.mqttAndroidClient.disconnect();
//            mqttService.mqttAndroidClient1.disconnect();
//            Log.d(TAG, "Unsubscribe successfully");
//        } catch (MqttException e) {
//            e.printStackTrace();
//        }
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        prefs.unregisterOnSharedPreferenceChangeListener(this);
    }


    private void startMqtt() {
        mViewModel.setIsLoading(true);
        MqttCallbackExtended callbackExtended = new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {

            }

            @Override
            public void connectionLost(Throwable throwable) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                mViewModel.setIsLoading(false);
                Log.w(TAG, "messageArrived to: " + topic + "/:" + mqttMessage.toString());
                JSONObject jsonObject = new JSONObject(mqttMessage.toString());
                if (topic.equals(Constants.TOPICS[0])) {
                    String soil = jsonObject.getString("data") + jsonObject.getString("unit");
                    mFragmentHomeBinding.etSoilHumidity.setText(soil);
                }
                if (topic.equals(Constants.TOPICS[1])) {
                    String light = jsonObject.getString("data")  + jsonObject.getString("unit");
                    mFragmentHomeBinding.etLight.setText(light);
                }
                if (topic.equals(Constants.TOPICS[2])) {
                    String[] data = jsonObject.getString("data").split("-");
                    String[] unit = jsonObject.getString("unit").split("-");
                    String temp = data[0] + "\u2103";
                    String humidity = data[1] + unit[1];
                    mFragmentHomeBinding.etTemperature.setText(temp);
                    mFragmentHomeBinding.etAirHumidity.setText(humidity);
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        };
        mqttService = new MqttService(getActivity().getApplicationContext(), callbackExtended, Constants.CONSTRAINT_TOPICS);
    }

    @Override
    public void onIconAlertClick() {
        Boolean b = mViewModel.getIsAlertProcessing().get();

        if(b){
            Intent intent = new Intent(getContext(), AlertProcessingActivity.class);
            getContext().startActivity(intent);
        }
        else{
            Intent intent = new Intent(getContext(), AlertActivity.class);
            getContext().startActivity(intent);
        }
    }



    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (PreferenceUtilities.KEY_ALERT.equals(key)) {
            mViewModel.setAlertState(PreferenceUtilities.getAlertState(this.getActivity()));
        } else if (PreferenceUtilities.KEY_IS_ALERT_PROCESSING.equals(key)) {
            mViewModel.setIsAlertProcessing(PreferenceUtilities.getisAlertProcessing(this.getActivity()));
        }
        else if (PreferenceUtilities.KEY_CAN_NOT_HANDLE.equals(key)) {
            mViewModel.setCannotHandleAlert(PreferenceUtilities.getcannotHandle(this.getActivity()));
        }

    }
}