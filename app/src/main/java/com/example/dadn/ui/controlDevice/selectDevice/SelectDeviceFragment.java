package com.example.dadn.ui.controlDevice.selectDevice;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;

public class SelectDeviceFragment extends BaseFragment<FragmentSelectDeviceBinding, SelectDeviceViewModel> implements SelectDeviceNavigator{
    FragmentSelectDeviceBinding mFragmentSelectDeviceBinding;
    MqttService mqttService;
    private ArrayList<SelectDeviceItem> deviceItemArrayList = new ArrayList<SelectDeviceItem>();
    private RecyclerView mRecyclerView;
    private SelectDeviceAdapter mAdapter;

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
        setmRecyclerView(view);
        onButtonBack(view);
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
    public void setDeviceItemArrayList() {
        deviceItemArrayList.add(new SelectDeviceItem("1", "RELAY", "0", ""));
        deviceItemArrayList.add(new SelectDeviceItem("2", "RELAY", "0", ""));
        deviceItemArrayList.add(new SelectDeviceItem("3", "RELAY", "0", ""));
        deviceItemArrayList.add(new SelectDeviceItem("4", "RELAY", "0", ""));
        deviceItemArrayList.add(new SelectDeviceItem("5", "RELAY", "0", ""));
        deviceItemArrayList.add(new SelectDeviceItem("1", "LED", "0", ""));
        deviceItemArrayList.add(new SelectDeviceItem("2", "LED", "0", ""));
        deviceItemArrayList.add(new SelectDeviceItem("3", "LED", "0", ""));
        deviceItemArrayList.add(new SelectDeviceItem("4", "LED", "0", ""));
    }
    public void setmRecyclerView(@NonNull View view){
        mRecyclerView = view.findViewById(R.id.recyclerView_SelectDeviceFragment);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        setDeviceItemArrayList();
        mAdapter = new SelectDeviceAdapter(getContext());
        mAdapter.setData(deviceItemArrayList);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }
    public void onButtonBack(@NonNull View view){
        Button back = (Button) view.findViewById(R.id.goBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getParentFragmentManager().getBackStackEntryCount() > 0){
                    boolean done = getParentFragmentManager().popBackStackImmediate();
                }
            }
        });
    }
}
