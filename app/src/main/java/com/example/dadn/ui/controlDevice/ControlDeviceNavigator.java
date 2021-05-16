package com.example.dadn.ui.controlDevice;

import androidx.fragment.app.Fragment;

public interface ControlDeviceNavigator {
    void ReplaceFragment(Fragment fragment);

    void turnOnAllDevice();

    void turnOffAllDevice();

    void goBack();
}
