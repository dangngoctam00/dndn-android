package com.example.dadn.ui.controlDevice;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.fragment.app.FragmentTransaction;

import com.example.dadn.R;
import com.example.dadn.databinding.FragmentControlDeviceBinding;
<<<<<<< HEAD

import com.example.dadn.di.component.FragmentComponent;

import com.example.dadn.ui.base.BaseFragment;
import com.example.dadn.ui.selectDevice.SelectDeviceFragment;

=======
import com.example.dadn.di.component.FragmentComponent;
import com.example.dadn.ui.base.BaseFragment;
>>>>>>> master

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
        //mViewModel.setNavigator(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentControlDeviceBinding = getViewDataBinding();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        mFragmentControlDeviceBinding = getViewDataBinding();

        Button control = v.findViewById(R.id.selectDevice);
        control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction tran = getChildFragmentManager().beginTransaction();
                tran.replace(R.id.container_fragment, new SelectDeviceFragment());
                tran.addToBackStack(null);
                tran.commit();
            }
        });
        return v;
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
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
