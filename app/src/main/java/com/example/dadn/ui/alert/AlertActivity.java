package com.example.dadn.ui.alert;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.library.baseAdapters.BR;

import com.example.dadn.R;
import com.example.dadn.databinding.ActivityAlertBinding;
import com.example.dadn.di.component.ActivityComponent;
import com.example.dadn.ui.base.BaseActivity;



public  class AlertActivity extends BaseActivity<ActivityAlertBinding, AlertViewModel> implements AlertNavigator{


    private ActivityAlertBinding mActivityAlertBinding;
    //private ActivityAlertBinding mActivityAlertBinding;
    public static Intent newIntent(Context context) {
        return new Intent(context, AlertActivity.class);
    }

    @Override
    public void openMainActivity() {

    }

    @Override
    public void openProcess() {

    }

    @Override
    public void testNotification() {
        //NotificationUtils.remindUserBecauseCharging(this);
    }



    @Override
    public int getBindingVariable() {
        return BR.alertViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_alert;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityAlertBinding = getViewDataBinding();

    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }
}
