package com.example.dadn.ui.home;

import androidx.lifecycle.MutableLiveData;

import com.example.dadn.ui.base.BaseViewModel;
import com.example.dadn.utils.rx.SchedulerProvider;

public class HomeViewModel extends BaseViewModel<HomeNavigator> {

    private String soil_humidity;
    private String light;
    private String temperature;
    private String air_humidity;


    private MutableLiveData<Boolean>  mIsAlertProcessing = new MutableLiveData<Boolean>(false);
    private MutableLiveData<Boolean>  mAlert = new MutableLiveData<Boolean>(false);

    public MutableLiveData<Boolean> getmIsAlertProcessing(){
        return mIsAlertProcessing;
    }

    public MutableLiveData<Boolean> getmAlert(){
        return mAlert;
    }

    public void setmIsAlertProcessing(Boolean b){
        mIsAlertProcessing.setValue(b);
    }

    public void postmIsAlertProcessing(Boolean b){
        mIsAlertProcessing.postValue(b);
    }

    public void setmAlert(Boolean b){
        mAlert.setValue(b);
    }

    public void postmAlert(Boolean b){
        mAlert.postValue(b);
    }




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

    public void onIconAlertClick(){getNavigator().openAlertActivity(); }


}
