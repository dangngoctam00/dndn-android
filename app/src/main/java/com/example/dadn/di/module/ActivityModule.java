package com.example.dadn.di.module;

import androidx.core.util.Supplier;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.dadn.ViewModelProviderFactory;
import com.example.dadn.ui.base.BaseActivity;
import com.example.dadn.ui.base.BaseViewModel;
import com.example.dadn.ui.login.LoginViewModel;
import com.example.dadn.ui.main.MainViewModel;
import com.example.dadn.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private BaseActivity<?> activity;

    public ActivityModule(BaseActivity<?> activity) {
        this.activity = activity;
    }

    @Provides
    LoginViewModel provideLoginViewModel(SchedulerProvider schedulerProvider) {
        Supplier<LoginViewModel> supplier = () -> new LoginViewModel(schedulerProvider);
        ViewModelProviderFactory<LoginViewModel> factory = new ViewModelProviderFactory<>(LoginViewModel.class, supplier);
        return new ViewModelProvider((ViewModelStoreOwner) activity, factory).get(LoginViewModel.class);
    }

    @Provides
    MainViewModel provideMainViewModel(SchedulerProvider schedulerProvider) {
        Supplier<MainViewModel> supplier = () -> new MainViewModel( schedulerProvider);
        ViewModelProviderFactory<MainViewModel> factory = new ViewModelProviderFactory<>(MainViewModel.class, supplier);
        return new ViewModelProvider((ViewModelStoreOwner) activity, factory).get(MainViewModel.class);
    }
}
