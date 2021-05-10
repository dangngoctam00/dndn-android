package com.example.dadn.di.component;


import com.example.dadn.di.module.ActivityModule;
import com.example.dadn.di.module.AppModule;
import com.example.dadn.di.scope.ActivityScope;
import com.example.dadn.ui.login.LoginActivity;

import dagger.Component;

@ActivityScope
@Component(modules = ActivityModule.class, dependencies = AppComponent.class)
public interface ActivityComponent {
    void inject(LoginActivity activity);
}
