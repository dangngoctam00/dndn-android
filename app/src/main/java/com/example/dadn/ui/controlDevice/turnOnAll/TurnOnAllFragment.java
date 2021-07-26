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
import com.example.dadn.ui.controlDevice.selectDevice.ResultFeedData;
import com.example.dadn.ui.controlDevice.selectDevice.RetrofitClient;
import com.example.dadn.ui.controlDevice.selectDevice.SelectDeviceItem;
import com.example.dadn.utils.Constants;
import com.example.dadn.utils.mqtt.MqttService;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.dadn.utils.Constants.USERNAME1;

public class TurnOnAllFragment extends BaseFragment<FragmentTurnOnAllBinding, TurnOnAllViewModel> implements TurnOnAllNavigator{

    FragmentTurnOnAllBinding mFragmentTurnOnAllBinding;
    private ArrayList<SelectDeviceItem> deviceItemArrayList = new ArrayList<SelectDeviceItem>();
    MqttService mqttService;
    String[] TOPICS = Constants.TOPICS;
    String USERNAME = Constants.USERNAME;
    String LIMIT = Constants.LIMIT;
    final String TAG = "TurnOnAllFragment TAG";

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


    @Override
    public void onDestroy() {
        super.onDestroy();
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
                setDeviceItemArrayList("bk-iot-relay");
                setDeviceItemArrayList("bk-iot-led");
                boolean done = getParentFragmentManager().popBackStackImmediate();
            }
        });
    }
    public void setDeviceItemArrayList(String feed_key) {
//        deviceItemArrayList.add(new SelectDeviceItem("11", "RELAY", "0", ""));
//        deviceItemArrayList.add(new SelectDeviceItem("1", "LED", "0", ""));
        Call<List<ResultFeedData>> call;
        if (feed_key.equals("bk-iot-led")) {
            call = RetrofitClient.getInstance().getApi()
                    .getFeedData(USERNAME, feed_key, LIMIT);
        }
        else {
            call = RetrofitClient.getInstance().getApi()
                    .getFeedData(USERNAME1, feed_key, LIMIT);
        }
        call.enqueue(new Callback<List<ResultFeedData>>() {
            @Override
            public void onResponse(Call<List<ResultFeedData>> call, Response<List<ResultFeedData>> response) {
                List<ResultFeedData> values = response.body();
                for (ResultFeedData value: values) {
                    Log.w(TAG, "Http Response" + value.getValue());
                    try {
                        JSONObject jsonObject = new JSONObject(value.getValue());
                        if (!inDeviceItemArrayList(jsonObject)){
                            deviceItemArrayList.add(new SelectDeviceItem(
                                    jsonObject.getString("id"),
                                    jsonObject.getString("name"),
                                    jsonObject.getString("data"),
                                    jsonObject.getString("unit")
                            ));
                            Log.w(TAG, "array list add one " + deviceItemArrayList.toString());
                        }
                    } catch (Exception e){
                        Log.w(TAG, "Debug http request:/ json fail "+ e.toString());
                    }
                }
                updateDeviceItemArrayList();
            }

            @Override
            public void onFailure(Call<List<ResultFeedData>> call, Throwable t) {
                Log.w(TAG, "get http request fail" + t.toString());
            }
        });

    }
    public void updateDeviceItemArrayList(){
        for (SelectDeviceItem device: deviceItemArrayList) {
            String mes = "{\"id\":\""+ device.getId() +
                    "\", \"name\":\"" + device.getName()+
                    "\", \"data\":\"" + "1"+
                    "\", \"unit\":\"" + device.getUnit()+
                    "\"}";
            sendDataMqtt(mes, device.getName());
        }
        deviceItemArrayList = new ArrayList<SelectDeviceItem>();
    }
    public void sendDataMqtt(String data, String name){
        MqttMessage msg = new MqttMessage();
        msg.setId(1);
        msg.setQos(0);
        msg.setRetained(true);
        byte[] b = data.getBytes(Charset.forName("UTF-8"));
        msg.setPayload(b);
        try {
            if (name.equals("RELAY")) {
                String topic = TOPICS[3];
                mqttService.mqttAndroidClient1.publish(topic, msg);
            }
            if (name.equals("LED")){
                String topic = TOPICS[4];
                mqttService.mqttAndroidClient.publish(topic, msg);
            }
            Log.w(TAG, "publish: " + msg);

        } catch (MqttException e){
            Log.w(TAG, "sendMqtt: cannot send message!");
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
//                Log.w("Debug", topic + "/:" + mqttMessage.toString());
//                JSONObject jsonObject = new JSONObject(mqttMessage.toString());

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        };
        mqttService = new MqttService(getActivity().getApplicationContext(), callbackExtended, false);

    }
    public boolean inDeviceItemArrayList(JSONObject jsonObject) throws JSONException {
        if (deviceItemArrayList.isEmpty()) return false;
        for (SelectDeviceItem device: deviceItemArrayList) {
            if (device.getName().equals(jsonObject.getString("name"))
                    && device.getId().equals(jsonObject.getString("id"))) {
                return true;
            }
        }
        return false;
    }
}
