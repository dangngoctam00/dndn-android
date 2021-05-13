package com.example.dadn.data.model.iotdevice;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.dadn.utils.Constants;
import com.example.dadn.utils.mqtt.MqttHelper;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DeviceRepository {
    private ArrayList<DeviceConfig> airHumidity = new ArrayList<DeviceConfig>();
    private ArrayList<DeviceConfig> solidHumidity = new ArrayList<DeviceConfig>();
    private ArrayList<DeviceConfig> light = new ArrayList<DeviceConfig>();
    private ArrayList<DeviceConfig> temperature = new ArrayList<DeviceConfig>();

    private MutableLiveData<List<DeviceConfig>> liveAirHumidity = new MutableLiveData<>();
    private MutableLiveData<List<DeviceConfig>> liveSolidHumidity = new MutableLiveData<>();
    private MutableLiveData<List<DeviceConfig>> liveLight = new MutableLiveData<>();
    private MutableLiveData<List<DeviceConfig>> liveTemperature = new MutableLiveData<>();
    MqttHelper mqttHelper;
    private Application application;

    public DeviceRepository(Application application){
        this.application = application;

    }

    public MutableLiveData<List<DeviceConfig>> getLiveDataAirHum(){

        return liveAirHumidity;
    }

    public MutableLiveData<List<DeviceConfig>> getLiveDataSolidHum(){
        startMqtt();
        return liveSolidHumidity;
    }

    public MutableLiveData<List<DeviceConfig>> getLiveDataLight(){
        return liveLight;

    }
    public MutableLiveData<List<DeviceConfig>> getLiveDataTemperature(){
        return liveTemperature;
    }

    public void insert(DeviceConfig device, String type){

    }

    private void startMqtt() {
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
                JSONObject jsonObject = new JSONObject(mqttMessage.toString());
                if (topic.equals(Constants.TOPICS[0])) {
                    String id = jsonObject.getString("unit");
                    String name = jsonObject.getString("name");
                    String data = jsonObject.getString("data");
                    String unit = jsonObject.getString("unit");
                    DeviceConfig newDevice = new DeviceConfig(id, name, data, unit);
                    airHumidity.add(newDevice);
                    liveAirHumidity.setValue(solidHumidity);

                }
                /*
                if (topic.equals(Constants.TOPICS[1])) {
                    String light = jsonObject.getString("data")  + jsonObject.getString("unit");
                    mFragmentHomeBinding.etLight.setText(light);
                }
                if (topic.equals(Constants.TOPICS[2])) {
                    String[] data = jsonObject.getString("data").split("-");
                    String[] unit = jsonObject.getString("unit").split("-");
                    String temp = data[0] + unit[0];
                    String humidity = data[1] + unit[1];
                    mFragmentHomeBinding.etTemperature.setText(temp);
                    mFragmentHomeBinding.etAirHumidity.setText(humidity);
                }

                 */
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        };
        mqttHelper = new MqttHelper(application, callbackExtended);
    }




}
