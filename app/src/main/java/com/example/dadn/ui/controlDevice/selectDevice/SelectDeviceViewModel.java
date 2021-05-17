package com.example.dadn.ui.controlDevice.selectDevice;

import com.example.dadn.ui.base.BaseViewModel;
import com.example.dadn.utils.rx.SchedulerProvider;

public class SelectDeviceViewModel extends BaseViewModel<SelectDeviceNavigator> {

    private String text_test;

    public String getText_test(){return text_test;}

    public SelectDeviceViewModel(SchedulerProvider mSchedulerProvider) {
        super(mSchedulerProvider);
    }
}
