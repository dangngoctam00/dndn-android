package com.example.dadn.ui.controlDevice.selectDevice;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dadn.R;
import com.example.dadn.utils.mqtt.MqttService;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.Charset;
import java.util.ArrayList;

public class SelectDeviceAdapter extends RecyclerView.Adapter<SelectDeviceAdapter.SelectDeviceViewHolder> {
    private Context context;
    MqttService mqttService;

    public SelectDeviceAdapter(Context context) {
        this.context = context;
    }
    private ArrayList<SelectDeviceItem> deviceItemArrayList;

    public void setData(ArrayList<SelectDeviceItem> deviceItemArrayList){
        this.deviceItemArrayList = deviceItemArrayList;
    }
    @Override
    public SelectDeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_device_item, parent, false);
        SelectDeviceViewHolder selectDeviceViewHolder = new SelectDeviceViewHolder(v);
        startMqtt();
        return selectDeviceViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SelectDeviceViewHolder holder, int position) {
        SelectDeviceItem selectDeviceItem = deviceItemArrayList.get(position);
        holder.nameDevice.setText(selectDeviceItem.getName());
        holder.switchDevice.setChecked(selectDeviceItem.getData() == "0" ? false : true);
        holder.position = position;
    }


    @Override
    public int getItemCount() {
        return deviceItemArrayList.size();
    }

    public class SelectDeviceViewHolder extends RecyclerView.ViewHolder {
        public TextView nameDevice;
        public Switch switchDevice;
        public int position;

        public SelectDeviceViewHolder(@NonNull View itemView) {
            super(itemView);
            nameDevice = itemView.findViewById(R.id.nameDevice);
            switchDevice = itemView.findViewById(R.id.switchDevice);
            switchDevice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    deviceItemArrayList.get(position).setData(isChecked? "1": "0");
                    SelectDeviceItem device = deviceItemArrayList.get(position);
                    String mes = "{id:"+ device.getId() +
                            ", name:" + device.getName()+
                            ", data:" + device.getData()+
                            ", unit:" + device.getUnit()+
                            "}";
                    sendDataMqtt(mes);
                }
            });
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
            mqttService.mqttAndroidClient.publish("pdt95/feeds/test-relay-device", msg);
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
//                Log.w("Debug", topic + "/:" + mqttMessage.toString());
//                JSONObject jsonObject = new JSONObject(mqttMessage.toString());

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        };
        mqttService = new MqttService(context, callbackExtended);

    }
    public void updateDeviceItemArrayList(String data, int position){
        deviceItemArrayList.get(position).setData(data);
    }
}
