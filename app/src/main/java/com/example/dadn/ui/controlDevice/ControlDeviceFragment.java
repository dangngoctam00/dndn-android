package com.example.dadn.ui.controlDevice;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;

import com.example.dadn.R;
import com.example.dadn.databinding.FragmentControlDeviceBinding;
import com.example.dadn.databinding.FragmentDeviceBinding;
import com.example.dadn.databinding.FragmentHomeBinding;
import com.example.dadn.di.component.ActivityComponent;
import com.example.dadn.di.component.FragmentComponent;
import com.example.dadn.ui.base.BaseActivity;
import com.example.dadn.ui.base.BaseFragment;
import com.example.dadn.ui.device.DeviceFragment;
import com.example.dadn.ui.device.DeviceViewModel;

public class ControlDeviceFragment extends BaseFragment<FragmentControlDeviceBinding, ControlDeviceViewModel> implements ControlDeviceNavigator {

    FragmentControlDeviceBinding mFragmentControlDeviceBinding;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_control_device;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel.setNavigator(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentControlDeviceBinding = getViewDataBinding();
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {

    }

    @Override
    public void selectDevice() {

    }

    @Override
    public void turnOnAllDevice() {

    }

    @Override
    public void turnOffAllDevice() {

    }

    @Override
    public void goBack() {

    }
}
