package com.example.dadn.di.module;

import androidx.core.util.Supplier;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.dadn.ViewModelProviderFactory;
import com.example.dadn.ui.alert.AlertViewModel;
import com.example.dadn.ui.alert.alertprocessing.AlertProcessingViewModel;
import com.example.dadn.ui.base.BaseActivity;
import com.example.dadn.ui.login.LoginViewModel;
import com.example.dadn.ui.register.RegisterViewModel;
import com.example.dadn.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private BaseActivity<?,?> activity;

    public ActivityModule(BaseActivity<?,?> activity) {
        this.activity = activity;
    }

    @Provides
    LoginViewModel provideLoginViewModel(SchedulerProvider schedulerProvider) {
        Supplier<LoginViewModel> supplier = () -> new LoginViewModel(schedulerProvider);
        ViewModelProviderFactory<LoginViewModel> factory = new ViewModelProviderFactory<>(LoginViewModel.class, supplier);
        return new ViewModelProvider((ViewModelStoreOwner) activity, factory).get(LoginViewModel.class);
    }

    @Provides
    AlertViewModel provideAlertViewModel(SchedulerProvider schedulerProvider) {
        Supplier<AlertViewModel> supplier = () -> new AlertViewModel(schedulerProvider);
        ViewModelProviderFactory<AlertViewModel> factory = new ViewModelProviderFactory<>(AlertViewModel.class, supplier);
        return new ViewModelProvider((ViewModelStoreOwner) activity, factory).get(AlertViewModel.class);
    }
    @Provides
    AlertProcessingViewModel provideAlertProcessingViewModel(SchedulerProvider schedulerProvider) {
        Supplier<AlertProcessingViewModel> supplier = () -> new AlertProcessingViewModel(schedulerProvider);
        ViewModelProviderFactory<AlertProcessingViewModel> factory = new ViewModelProviderFactory<>(AlertProcessingViewModel.class, supplier);
        return new ViewModelProvider((ViewModelStoreOwner) activity, factory).get(AlertProcessingViewModel.class);
    }

    @Provides
    RegisterViewModel provideRegisterViewModel(SchedulerProvider schedulerProvider) {
        Supplier<RegisterViewModel> supplier = () -> new RegisterViewModel(schedulerProvider);
        ViewModelProviderFactory<RegisterViewModel> factory = new ViewModelProviderFactory<>(RegisterViewModel.class, supplier);
        return new ViewModelProvider((ViewModelStoreOwner) activity, factory).get(RegisterViewModel.class);
    }
}
