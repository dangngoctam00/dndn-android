package com.example.dadn.ui.controlDevice;

import com.example.dadn.ui.base.BaseViewModel;
import com.example.dadn.ui.controlDevice.selectDevice.SelectDeviceFragment;
import com.example.dadn.ui.controlDevice.turnOffAll.TurnOffAllFragment;
import com.example.dadn.ui.controlDevice.turnOnAll.TurnOnAllFragment;
import com.example.dadn.utils.rx.SchedulerProvider;

public class ControlDeviceViewModel extends BaseViewModel<ControlDeviceNavigator> {
    public ControlDeviceViewModel(SchedulerProvider mSchedulerProvider) {
        super(mSchedulerProvider);
    }
    public void goSelectDevice(){
        getNavigator().ReplaceFragment(new SelectDeviceFragment());
    }

    public void goTurnOnAll() { getNavigator().ReplaceFragment(new TurnOnAllFragment()); }

    public void goTurnOffAll() { getNavigator().ReplaceFragment(new TurnOffAllFragment()); }

}
