package com.example.dadn.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;

import com.example.dadn.R;
import com.example.dadn.databinding.FragmentSettingBinding;
import com.example.dadn.di.component.FragmentComponent;
import com.example.dadn.ui.base.BaseFragment;
import com.example.dadn.ui.login.LoginActivity;
import com.example.dadn.utils.SessionManager;

public class SettingFragment extends BaseFragment<FragmentSettingBinding, SettingViewModel> implements SettingNavigator {

    FragmentSettingBinding mFragmentSettingBinding;

    private SessionManager session;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_setting;
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel.setNavigator(this);
        session = new SessionManager(getActivity().getApplicationContext());
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentSettingBinding = getViewDataBinding();
    }

    @Override
    public void logout() {
        session.setLogin(false);
        Intent intent = LoginActivity.newIntent(getActivity());
        startActivity(intent);
    }
}
