package com.example.dadn.ui.device.spec_limitation;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.library.baseAdapters.BR;

import com.example.dadn.R;
import com.example.dadn.databinding.FragmentSpecificationLimitationBinding;
import com.example.dadn.di.component.FragmentComponent;
import com.example.dadn.ui.base.BaseFragment;


public class SpecificationLimitationFragment extends BaseFragment<FragmentSpecificationLimitationBinding, SpecificationLimitationViewModel> {

    FragmentSpecificationLimitationBinding mFragmentSpecificationLimitationBinding;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_specification_limitation;
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentSpecificationLimitationBinding = getViewDataBinding();
        ((AppCompatActivity)getActivity()).setSupportActionBar(mFragmentSpecificationLimitationBinding.specLimitationToolbar);
    }
}
