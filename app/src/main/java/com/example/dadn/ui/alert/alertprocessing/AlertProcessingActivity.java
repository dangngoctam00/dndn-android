package com.example.dadn.ui.alert.alertprocessing;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import com.example.dadn.BR;
import com.example.dadn.R;
import com.example.dadn.databinding.ActivityAlertProcessingBinding;
import com.example.dadn.di.component.ActivityComponent;
import com.example.dadn.ui.base.BaseActivity;
import com.example.dadn.ui.login.LoginActivity;
import com.example.dadn.ui.main.MainActivity;
import com.example.dadn.utils.PreferenceUtilities;


public  class AlertProcessingActivity extends BaseActivity<ActivityAlertProcessingBinding, AlertProcessingViewModel> implements AlertProcessingNavigator, SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.unregisterOnSharedPreferenceChangeListener(this);
    }

    private ActivityAlertProcessingBinding mActivityAlertProcessingBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_alert_processing;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityAlertProcessingBinding = getViewDataBinding();
        mViewModel.setNavigator(this);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);
        mViewModel.setIsAlertProcessing(PreferenceUtilities.getisAlertProcessing(this));
        mViewModel.setAlertState(PreferenceUtilities.getAlertState(this));
        mViewModel.setCannotHandleAlert(PreferenceUtilities.getcannotHandle(this));
    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void openMainActivity() {
        Intent intent = MainActivity.newIntent(AlertProcessingActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (PreferenceUtilities.KEY_ALERT.equals(key)) {
            mViewModel.setAlertState(PreferenceUtilities.getAlertState(this));
        } else if (PreferenceUtilities.KEY_IS_ALERT_PROCESSING.equals(key)) {
            mViewModel.setIsAlertProcessing(PreferenceUtilities.getisAlertProcessing(this));
        }
        else if (PreferenceUtilities.KEY_CAN_NOT_HANDLE.equals(key)) {
            mViewModel.setCannotHandleAlert(PreferenceUtilities.getcannotHandle(this));
        }
    }
}
