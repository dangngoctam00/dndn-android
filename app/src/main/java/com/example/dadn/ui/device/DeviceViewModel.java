package com.example.dadn.ui.device;

import com.example.dadn.ui.base.BaseViewModel;
import com.example.dadn.ui.controlDevice.ControlDeviceFragment;
import com.example.dadn.ui.device.spec_limitation.SpecificationLimitationFragment;
import com.example.dadn.utils.rx.SchedulerProvider;


public class DeviceViewModel  extends BaseViewModel<DeviceNavigator> {

    public DeviceViewModel(SchedulerProvider mSchedulerProvider) {
        super(mSchedulerProvider);
    }

    public void goSpecificationLimitation() {
        getNavigator().ReplaceFragment(new SpecificationLimitationFragment());
    }

    public void goDeviceControl() {
        getNavigator().ReplaceFragment(new ControlDeviceFragment());
    }
}
