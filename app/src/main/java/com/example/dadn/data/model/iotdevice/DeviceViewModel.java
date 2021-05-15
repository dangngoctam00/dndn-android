package com.example.dadn.data.model.iotdevice;

import android.app.Application;
import android.bluetooth.BluetoothClass;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class DeviceViewModel extends AndroidViewModel {
    private DeviceRepository deviceRepository;


    public  DeviceViewModel(@NonNull Application application){
        super(application);
        deviceRepository = new DeviceRepository(application);

    }

    public LiveData<List<DeviceConfig>> getSolidHumidity(){
        return deviceRepository.getLiveDataSolidHum();
    }

}
