package com.example.dadn.ui.controlDevice.selectDevice;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;

import com.example.dadn.R;
import com.example.dadn.databinding.FragmentSelectDeviceBinding;
import com.example.dadn.di.component.FragmentComponent;
import com.example.dadn.ui.base.BaseFragment;
import com.example.dadn.utils.Constants;
import com.example.dadn.utils.mqtt.MqttService;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

public class SelectDeviceFragment extends BaseFragment<FragmentSelectDeviceBinding, SelectDeviceViewModel> implements SelectDeviceNavigator{
    FragmentSelectDeviceBinding mFragmentSelectDeviceBinding;
    MqttService mqttService;
    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_select_device;
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel.setNavigator(this);
        startMqtt();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentSelectDeviceBinding = getViewDataBinding();
    }

    private void startMqtt(){
        MqttCallbackExtended callbackExtended = new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {

            }

            @Override
            public void connectionLost(Throwable throwable) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                Log.w("Debug", topic + "/:" + mqttMessage.toString());
//                JSONObject jsonObject = new JSONObject(mqttMessage.toString());
                String status = mqttMessage.toString();
                mFragmentSelectDeviceBinding.textView3.setText(status);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        };
        mqttService = new MqttService(getActivity().getApplicationContext(), callbackExtended);
    }
}
