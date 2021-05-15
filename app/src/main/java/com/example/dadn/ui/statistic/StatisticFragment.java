package com.example.dadn.ui.statistic;



import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;


import com.example.dadn.R;
import com.example.dadn.databinding.FragmentDeviceBinding;
import com.example.dadn.databinding.FragmentStatisticBinding;
import com.example.dadn.di.component.FragmentComponent;
import com.example.dadn.ui.base.BaseFragment;


public class StatisticFragment extends BaseFragment<FragmentStatisticBinding, StatisticViewModel> implements StatisticNavigator {

    FragmentStatisticBinding mFragmentStatisticBinding;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_statistic;
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
        mFragmentStatisticBinding = getViewDataBinding();
    }

}