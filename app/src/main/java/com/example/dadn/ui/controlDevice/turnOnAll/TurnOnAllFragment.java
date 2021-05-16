package com.example.dadn.ui.controlDevice.turnOnAll;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;

import com.example.dadn.R;
import com.example.dadn.databinding.FragmentTurnOnAllBinding;
import com.example.dadn.di.component.FragmentComponent;
import com.example.dadn.ui.base.BaseFragment;

public class TurnOnAllFragment extends BaseFragment<FragmentTurnOnAllBinding, TurnOnAllViewModel> implements TurnOnAllNavigator{

    FragmentTurnOnAllBinding mFragmentTurnOnAllBinding;
    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_turn_on_all;
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
        mFragmentTurnOnAllBinding = getViewDataBinding();
    }
}
