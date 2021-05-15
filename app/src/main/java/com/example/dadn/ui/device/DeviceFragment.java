package com.example.dadn.ui.device;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.example.dadn.BR;
import com.example.dadn.R;
import com.example.dadn.databinding.FragmentDeviceBinding;
import com.example.dadn.di.component.FragmentComponent;
import com.example.dadn.di.component.ActivityComponent;
import com.example.dadn.generated.callback.OnClickListener;
import com.example.dadn.ui.base.BaseActivity;
import com.example.dadn.ui.base.BaseFragment;

import com.example.dadn.ui.controlDevice.ControlDeviceFragment;
import com.example.dadn.ui.login.LoginActivity;
import com.example.dadn.ui.main.MainActivity;


public class DeviceFragment extends BaseFragment<FragmentDeviceBinding,DeviceViewModel> implements DeviceNavigator {

    FragmentDeviceBinding mFragmentDeviceBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, DeviceFragment.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_device;
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel.setNavigator(this);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentDeviceBinding = getViewDataBinding();

    }

//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View v = super.onCreateView(inflater, container, savedInstanceState);
//        mFragmentDeviceBinding = getViewDataBinding();
//
//        Button control = v.findViewById(R.id.controlDevice);
//        control.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentTransaction tran = getChildFragmentManager().beginTransaction();
//                tran.replace(R.id.container_fragment, new ControlDeviceFragment());
//                tran.addToBackStack(tran.getClass().getSimpleName());
//                tran.commit();
//            }
//        });
//        return v;
//    }

    public void ReplaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment, fragment.toString());
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.commit();
    }

}
