package com.example.dadn.ui.controlDevice.turnOnAll;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;

import com.example.dadn.R;
import com.example.dadn.databinding.FragmentTurnOnAllBinding;
import com.example.dadn.di.component.FragmentComponent;
import com.example.dadn.ui.base.BaseFragment;
import com.example.dadn.ui.controlDevice.selectDevice.SelectDeviceItem;
import com.example.dadn.utils.Constants;
import com.example.dadn.utils.mqtt.MqttService;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.Charset;
import java.util.ArrayList;

public class TurnOnAllFragment extends BaseFragment<FragmentTurnOnAllBinding, TurnOnAllViewModel> implements TurnOnAllNavigator{

    FragmentTurnOnAllBinding mFragmentTurnOnAllBinding;
    private ArrayList<SelectDeviceItem> deviceItemArrayList = new ArrayList<SelectDeviceItem>();
    MqttService mqttService;
    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_turn_on_all;
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
        mFragmentTurnOnAllBinding = getViewDataBinding();
        onButtonCancel(view);
        onButtonConfirm(view);
    }
    public void onButtonCancel(@NonNull View view){
        Button back = (Button) view.findViewById(R.id.cancel_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getParentFragmentManager().getBackStackEntryCount() > 0){
                    boolean done = getParentFragmentManager().popBackStackImmediate();
                }
            }
        });
    }
    public void onButtonConfirm(@NonNull View view){
        Button confirm = (Button) view.findViewById(R.id.confirm_button);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setDeviceItemArrayList();
                updateDeviceItemArrayList();
                for (SelectDeviceItem device: deviceItemArrayList) {
                    String mes = "{\"id\":\""+ device.getId() +
                            "\", \"name\":\"" + device.getName()+
                            "\", \"data\":\"" + device.getData()+
                            "\", \"unit\":\"" + device.getUnit()+
                            "\"}";
                    sendDataMqtt(mes);
                }
                boolean done = getParentFragmentManager().popBackStackImmediate();
            }
        });
    }
    public void setDeviceItemArrayList() {
        deviceItemArrayList.add(new SelectDeviceItem("11", "RELAY", "0", ""));

        deviceItemArrayList.add(new SelectDeviceItem("1", "LED", "0", ""));

    }
    public void updateDeviceItemArrayList(){
        for (SelectDeviceItem device: deviceItemArrayList) {
            device.setData("1");
        }
    }
    public void sendDataMqtt(String data){
        MqttMessage msg = new MqttMessage();
        msg.setId(1);
        msg.setQos(0);
        msg.setRetained(true);
        byte[] b = data.getBytes(Charset.forName("UTF-8"));
        msg.setPayload(b);
        try {
            String topic = Constants.TOPICS[3];
            mqttService.mqttAndroidClient.publish(topic, msg);
            Log.w("MQTT", "publish: " + msg);

        } catch (MqttException e){
            Log.w("MQTT", "sendMqtt: cannot send message!");
        }
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

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        };
        mqttService = new MqttService(getActivity().getApplicationContext(), callbackExtended);

    }
}
