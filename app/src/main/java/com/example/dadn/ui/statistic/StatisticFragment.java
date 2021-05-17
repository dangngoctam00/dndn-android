package com.example.dadn.ui.statistic;



import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.library.baseAdapters.BR;

import com.example.dadn.R;
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

        AppCompatActivity parent = (AppCompatActivity)getActivity();
        Toolbar toolbar = mFragmentStatisticBinding.statisticToolbar;
        parent.setSupportActionBar(toolbar);
        parent.getSupportActionBar().setTitle("");
        mFragmentStatisticBinding.toolbarTitle.setText("THỐNG KÊ DỮ LIỆU");

        String[] specs = new String[] {"Ánh sáng", "Nhiệt độ", "Độ ẩm không khí", "Độ ẩm đất"};
        Spinner spinner = mFragmentStatisticBinding.specificationSpinner;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item_selected, specs);
        adapter.setDropDownViewResource(R.layout.spinner_item_dropdown);
        spinner.setAdapter(adapter);

        String[] time_durations = new String[] {"Giờ", "Ngày", "Tuần", "Tháng"};
        Spinner spinner_time_duration = mFragmentStatisticBinding.timeDurationSpinner;
        ArrayAdapter<String> adapter_time_duration = new ArrayAdapter<>(getActivity(), R.layout.spinner_item_selected, time_durations);
        adapter_time_duration.setDropDownViewResource(R.layout.spinner_item_dropdown);
        spinner_time_duration.setAdapter(adapter_time_duration);

    }

}
