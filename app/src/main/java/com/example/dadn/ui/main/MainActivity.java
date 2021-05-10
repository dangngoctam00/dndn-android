package com.example.dadn.ui.main;

import android.content.Context;
import android.content.Intent;

import com.example.dadn.R;
import com.example.dadn.di.component.ActivityComponent;
import com.example.dadn.ui.base.BaseActivity;
import com.example.dadn.ui.login.LoginViewModel;
import com.example.dadn.utils.rx.AppSchedulerProvider;

public class MainActivity extends BaseActivity<MainViewModel> implements MainNavigator {


    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {

    }
}
