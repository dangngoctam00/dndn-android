package com.example.dadn.di.component;


import com.example.dadn.di.module.FragmentModule;
import com.example.dadn.di.scope.FragmentScope;
import com.example.dadn.ui.device.DeviceFragment;
import com.example.dadn.ui.home.HomeFragment;

import dagger.Component;



@FragmentScope
@Component(modules = FragmentModule.class, dependencies = AppComponent.class)
public interface FragmentComponent {

    void inject(HomeFragment fragment);

    void inject(DeviceFragment fragment);
}
