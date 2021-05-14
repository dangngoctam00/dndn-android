package com.example.dadn.ui.device;



import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.example.dadn.BR;
import com.example.dadn.R;
import com.example.dadn.databinding.FragmentDeviceBinding;
import com.example.dadn.di.component.FragmentComponent;
import com.example.dadn.generated.callback.OnClickListener;
import com.example.dadn.ui.base.BaseFragment;
import com.example.dadn.ui.controlDevice.ControlDeviceFragment;


public class DeviceFragment extends BaseFragment<FragmentDeviceBinding,DeviceViewModel> implements DeviceNavigator {

    FragmentDeviceBinding mFragmentDeviceBinding;

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
//        View v = inflater.inflate(R.layout.fragment_device, container, false);
////        mFragmentDeviceBinding = getViewDataBinding();
//
//        Button cont = v.findViewById(R.id.controlDevice);
//        cont.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                ControlDeviceFragment control = new ControlDeviceFragment();
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.replace(R.id.fragment_container, control);
//                transaction.commit();
//
//            }
//        });
//        return v;
//    }

}
