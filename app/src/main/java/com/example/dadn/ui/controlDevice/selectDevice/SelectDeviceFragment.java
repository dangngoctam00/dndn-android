package com.example.dadn.ui.controlDevice.selectDevice;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

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
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Integer.parseInt;

public class SelectDeviceFragment extends BaseFragment<FragmentSelectDeviceBinding, SelectDeviceViewModel> implements SelectDeviceNavigator{
    FragmentSelectDeviceBinding mFragmentSelectDeviceBinding;
    MqttService mqttService;
    private ArrayList<SelectDeviceItem> deviceItemArrayList = new ArrayList<SelectDeviceItem>();
    private RecyclerView mRecyclerView;
    private SelectDeviceAdapter mAdapter;
    View viewForSwitch;
    String[] TOPICS = Constants.TOPICS;
    String USERNAME = Constants.USERNAME;
    String LIMIT = Constants.LIMIT;
    final String TAG = "SelectDeviceFrg TAG";

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
        mViewModel.setIsLoading(true);
        startMqtt();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentSelectDeviceBinding = getViewDataBinding();

        setDeviceItemArrayList("bk-iot-relay");
        setDeviceItemArrayList("bk-iot-led");
        onButtonBack(view);
        viewForSwitch = view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            mqttService.mqttAndroidClient.unsubscribe(Constants.CONSTRAINT_TOPICS);
            mqttService.mqttAndroidClient.disconnect();
            Log.d(TAG, "Unsubscribe successfully");
        } catch (MqttException e) {
            e.printStackTrace();
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
                try {
                    JSONObject jsonObject = new JSONObject(mqttMessage.toString());
                    if (topic.equals(TOPICS[3])){
                        String data = jsonObject.getString("data");
                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        updateDeviceItemArrayList(data, id, name);
                    }
                    if (topic.equals(TOPICS[4])){
                        String data = jsonObject.getString("data");
                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        updateDeviceItemArrayList(data, id, name);
                    }
                } catch (Exception e){
                    Log.w(TAG, "exception: " + e.toString());
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        };
        mqttService = new MqttService(getActivity().getApplicationContext(), callbackExtended, Constants.DEVICE_TOPICS);

    }
    public void setDeviceItemArrayList(String feed_key) {
//        deviceItemArrayList.add(new SelectDeviceItem("11", "RELAY", "0", ""));
//        deviceItemArrayList.add(new SelectDeviceItem("1", "LED", "0", ""));

        Call<List<ResultFeedData>> call = RetrofitClient.getInstance().getApi()
                .getFeedData(USERNAME, feed_key, LIMIT);
        Log.w(TAG, "Http Response: " +  feed_key);

        call.enqueue(new Callback<List<ResultFeedData>>() {
            @Override
            public void onResponse(Call<List<ResultFeedData>> call, Response<List<ResultFeedData>> response) {
                List<ResultFeedData> values = response.body();
                Log.w(TAG, "response: " + response.toString());
                for (ResultFeedData value: values) {
                    Log.w("Http Response", value.getValue());
                    try {
                        JSONObject jsonObject = new JSONObject(value.getValue());
                        if (!inDeviceItemArrayList(jsonObject)){
                            deviceItemArrayList.add(new SelectDeviceItem(
                                    jsonObject.getString("id"),
                                    jsonObject.getString("name"),
                                    jsonObject.getString("data"),
                                    jsonObject.getString("unit")
                            ));
                            Log.w(TAG, "array list: " + "add one " + deviceItemArrayList.toString());
                        }
                    } catch (Exception e){
                        Log.w(TAG, "Debug http request:/ " + "json fail "+ e.toString());
                    }
                }
                setmRecyclerView(viewForSwitch);
                if (feed_key.equals("bk-iot-led")) {
                    mViewModel.setIsLoading(false);
                }
            }

            @Override
            public void onFailure(Call<List<ResultFeedData>> call, Throwable t) {
                Log.w(TAG, "get http request fail" + t.toString());

            }
        });
    }
    public void setmRecyclerView(@NonNull View view){
        mRecyclerView = view.findViewById(R.id.recyclerView_SelectDeviceFragment);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        Log.w(TAG, deviceItemArrayList.toString());
        mAdapter = new SelectDeviceAdapter(getContext());
        mAdapter.setData(deviceItemArrayList);
        mAdapter.setMqttService(this.mqttService);
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
    public void updateDeviceItemArrayList(String data, String id, String name){
        for (SelectDeviceItem device: deviceItemArrayList) {
            if (device.getName().equals(name) && device.getId().equals(id)){
                device.setData(data);
                Switch switchDevice = (Switch) viewForSwitch.findViewById(parseInt(device.getId()));
                switchDevice.setChecked(data.equals("0") ? false : true);
                Log.w(TAG, "Switch update finish id " + switchDevice.getId());
                break;
            }
        }

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
