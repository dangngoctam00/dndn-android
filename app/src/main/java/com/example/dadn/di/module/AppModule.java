package com.example.dadn.di.module;

import com.example.dadn.utils.rx.AppSchedulerProvider;
import com.example.dadn.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }
}
