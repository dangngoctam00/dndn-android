package com.example.dadn.ui.alert.alertprocessing;

import com.example.dadn.ui.alert.AlertNavigator;
import com.example.dadn.ui.base.BaseViewModel;
import com.example.dadn.utils.rx.SchedulerProvider;

public class AlertProcessingViewModel extends BaseViewModel<AlertProcessingNavigator>{

    public AlertProcessingViewModel(SchedulerProvider mSchedulerProvider) {
        super(mSchedulerProvider);
    }
    public  void openMainActivity(){
        getNavigator().openMainActivity();
    }

}
