package com.example.dadn.ui.controlDevice.selectDevice;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dadn.R;
import com.example.dadn.utils.Constants;
import com.example.dadn.utils.mqtt.MqttService;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.Charset;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class SelectDeviceAdapter extends RecyclerView.Adapter<SelectDeviceAdapter.SelectDeviceViewHolder> {
    private Context context;
    MqttService mqttService;
    View v;
    String[] TOPICS = Constants.TOPICS;

    public void setMqttService(MqttService mqttService){ this.mqttService = mqttService; }
    public SelectDeviceAdapter(Context context) {
        this.context = context;
    }
    private ArrayList<SelectDeviceItem> deviceItemArrayList;

    public void setData(ArrayList<SelectDeviceItem> deviceItemArrayList){
        this.deviceItemArrayList = deviceItemArrayList;
    }
    @Override
    public SelectDeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_device_item, parent, false);
        SelectDeviceViewHolder selectDeviceViewHolder = new SelectDeviceViewHolder(v);
        return selectDeviceViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SelectDeviceViewHolder holder, int position) {
        SelectDeviceItem selectDeviceItem = deviceItemArrayList.get(position);
        if (selectDeviceItem.getName().equals("LED")) {
            holder.nameDevice.setText("ĐÈN");
        }
        else {
            holder.nameDevice.setText("MÁY BƠM");
        }
        holder.switchDevice.setChecked(selectDeviceItem.getData().equals("0") ? false : true);
        holder.position = position;
        holder.switchDevice.setId(parseInt(selectDeviceItem.getId()));

        holder.deviceItem.setId(selectDeviceItem.getId());
        holder.deviceItem.setName(selectDeviceItem.getName());
        holder.deviceItem.setData(selectDeviceItem.getData());
        holder.deviceItem.setUnit(selectDeviceItem.getUnit());

        Log.w("Switch", "id : "+holder.switchDevice.getId() + "/ " + selectDeviceItem.getData());
    }


    @Override
    public int getItemCount() {
        return deviceItemArrayList.size();
    }

    public class SelectDeviceViewHolder extends RecyclerView.ViewHolder {
        public TextView nameDevice;
        public Switch switchDevice;
        public int position;
        SelectDeviceItem deviceItem = new SelectDeviceItem("", "","","");

        public SelectDeviceViewHolder(@NonNull View itemView) {
            super(itemView);
            nameDevice = itemView.findViewById(R.id.nameDevice);
            switchDevice = itemView.findViewById(R.id.switchDevice);
//            switchDevice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    deviceItemArrayList.get(position).setData(isChecked? "1": "0");
//                    SelectDeviceItem device = deviceItemArrayList.get(position);
//                    String mes = "{\"id\":\""+ device.getId() +
//                            "\", \"name\":\"" + device.getName()+
//                            "\", \"data\":\"" + device.getData()+
//                            "\", \"unit\":\"" + device.getUnit()+
//                            "\"}";
//                    sendDataMqtt(mes, device.getName());
////                    Log.w("Switch", "id: " + switchDevice.getId());
//                }
//            });
            switchDevice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deviceItem.setData(deviceItem.getData().equals("0")? "1" : "0");
                    SelectDeviceItem device = deviceItem;
                    String mes = "{\"id\":\""+ device.getId() +
                            "\", \"name\":\"" + device.getName()+
                            "\", \"data\":\"" + device.getData()+
                            "\", \"unit\":\"" + device.getUnit()+
                            "\"}";
                    sendDataMqtt(mes, device.getName());
                }
            });
        }
    }

    public void sendDataMqtt(String data, String name){
        MqttMessage msg = new MqttMessage();
        msg.setId(1234);
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
            Log.w("MQTT", "publish: " + msg);

        } catch (MqttException e){
            Log.w("MQTT", "sendMqtt: cannot send message!", e);
        }
    }

    public SelectDeviceItem findDeviceById(String id){
        for (SelectDeviceItem device: deviceItemArrayList) {
            if (device.getId().equals(id)) return device;
        }
        return null;
    }
}
