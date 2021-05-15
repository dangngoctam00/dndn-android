package com.example.dadn.ui.selectDevice;

import androidx.databinding.library.baseAdapters.BR;

import com.example.dadn.R;
import com.example.dadn.databinding.FragmentSelectDeviceBinding;
import com.example.dadn.di.component.FragmentComponent;
import com.example.dadn.ui.base.BaseFragment;

public class SelectDeviceFragment extends BaseFragment<FragmentSelectDeviceBinding, SelectDeviceViewModel> {
    FragmentSelectDeviceBinding mFragmentSelectDeviceBinding;
    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_select_device;
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {

    }
}
