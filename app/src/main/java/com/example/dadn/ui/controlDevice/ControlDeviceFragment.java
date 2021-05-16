package com.example.dadn.ui.controlDevice;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.dadn.R;
import com.example.dadn.databinding.FragmentControlDeviceBinding;

import com.example.dadn.di.component.FragmentComponent;
import com.example.dadn.ui.base.BaseFragment;


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
        buildComponent.inject(this);
    }

    @Override
    public void ReplaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment, fragment.toString());
        transaction.addToBackStack(fragment.toString());
        transaction.commit();
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
