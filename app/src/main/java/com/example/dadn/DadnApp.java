package com.example.dadn;

import android.app.Application;


import com.example.dadn.di.component.AppComponent;
import com.example.dadn.di.component.DaggerAppComponent;

import javax.inject.Inject;



public class DadnApp extends Application {

    public AppComponent appComponent;


    @Override
    public void onCreate() {
        super.onCreate();

        this.appComponent = DaggerAppComponent.builder()
                .application(this)
                .build();

        this.appComponent.inject(this);
    }
}
