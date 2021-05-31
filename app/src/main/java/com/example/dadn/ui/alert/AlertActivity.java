package com.example.dadn.ui.alert;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.library.baseAdapters.BR;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.dadn.R;
import com.example.dadn.Service.AlertIntentService;
import com.example.dadn.Service.AlertReceiver;
import com.example.dadn.databinding.ActivityAlertBinding;
import com.example.dadn.di.component.ActivityComponent;
import com.example.dadn.ui.alert.alertprocessing.AlertProcessingActivity;
import com.example.dadn.ui.base.BaseActivity;
import com.example.dadn.ui.login.LoginActivity;
import com.example.dadn.ui.main.MainActivity;


public  class AlertActivity extends BaseActivity<ActivityAlertBinding, AlertViewModel> implements AlertNavigator{



    private ActivityAlertBinding mActivityAlertBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, AlertActivity.class);
    }





    public void openAlertProcess() {
        Intent intent = MainActivity.newIntent(this);
        startActivity(intent);
        finish();
    }

    @Override
    public void acceptTask() {
        AlertTasks.executeTask(this, AlertTasks.ACTION_ACCEPT);
        Intent intent = new Intent(AlertActivity.this, AlertProcessingActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void cancelTask() {
        AlertTasks.executeTask(this, AlertTasks.ACTION_CANCEL);
        Intent intent = MainActivity.newIntent(AlertActivity.this);
        startActivity(intent);
        finish();
    }


    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_alert;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityAlertBinding = getViewDataBinding();
        mViewModel.setNavigator(this);
    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }


}
