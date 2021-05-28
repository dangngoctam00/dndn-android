package com.example.dadn.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;

import com.example.dadn.R;
import com.example.dadn.databinding.FragmentHomeBinding;
import com.example.dadn.di.component.FragmentComponent;
import com.example.dadn.ui.alert.AlertActivity;
import com.example.dadn.ui.base.BaseFragment;
import com.example.dadn.ui.main.MainActivity;
import com.example.dadn.utils.mqtt.MqttService;
import com.example.dadn.utils.Constants;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

public class HomeFragment extends BaseFragment<FragmentHomeBinding ,HomeViewModel> implements HomeNavigator {

    FragmentHomeBinding mFragmentHomeBinding;
    MqttService mqttService;

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
        Log.d("HOME FRAGMENT", "onCreate function");
        super.onCreate(savedInstanceState);
        mViewModel.setNavigator(this);
        startMqtt();
    }


    private void startMqtt() {
        //mViewModel.setIsLoading(true);
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
                Log.w("Debug", topic + "/:" + mqttMessage.toString());
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
        mqttService = new MqttService(getActivity().getApplicationContext(), callbackExtended);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentHomeBinding = getViewDataBinding();
    }

    @Override
    public void openAlertActivity() {
        Intent intent = new Intent(getContext(), AlertActivity.class);
        getContext().startActivity(intent);
        //startActivity(AlertActivity.newIntent(this.getActivity()));

    }
}
