package com.example.dadn.ui.home;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;

import com.example.dadn.ui.base.BaseViewModel;
import com.example.dadn.utils.PreferenceUtilities;
import com.example.dadn.utils.rx.SchedulerProvider;

public class HomeViewModel extends BaseViewModel<HomeNavigator> {

    private String soil_humidity;
    private String light;
    private String temperature;
    private String air_humidity;






    public String getSoil_humidity() {
        return soil_humidity;
    }

    public String getLight() {
        return light;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getAir_humidity() {
        return air_humidity;
    }

    public HomeViewModel(SchedulerProvider mSchedulerProvider) {
        super(mSchedulerProvider);
    }

    public void onIconAlertClick(){
        getNavigator().onIconAlertClick();
    }


}
