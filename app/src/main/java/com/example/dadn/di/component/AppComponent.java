package com.example.dadn.di.component;

import android.app.Application;

import com.example.dadn.DadnApp;
import com.example.dadn.di.module.AppModule;
import com.example.dadn.utils.rx.SchedulerProvider;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(DadnApp app);

    SchedulerProvider getSchedulerProvider();

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
