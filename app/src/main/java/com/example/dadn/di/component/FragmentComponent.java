package com.example.dadn.di.component;


import com.example.dadn.di.module.FragmentModule;
import com.example.dadn.di.scope.FragmentScope;
import com.example.dadn.ui.controlDevice.ControlDeviceFragment;
import com.example.dadn.ui.device.DeviceFragment;
import com.example.dadn.ui.device.spec_limitation.SpecificationLimitationFragment;
import com.example.dadn.ui.home.HomeFragment;
import com.example.dadn.ui.instruction.InstructionFragment;
import com.example.dadn.ui.setting.SettingFragment;
import com.example.dadn.ui.statistic.StatisticFragment;

import dagger.Component;



@FragmentScope
@Component(modules = FragmentModule.class, dependencies = AppComponent.class)
public interface FragmentComponent {

    void inject(HomeFragment fragment);

    void inject(DeviceFragment fragment);

    void inject(SettingFragment fragment);

    void inject(InstructionFragment fragment);

    void inject(StatisticFragment fragment);

    void inject(SpecificationLimitationFragment fragment);

    void inject(ControlDeviceFragment fragment);
}
