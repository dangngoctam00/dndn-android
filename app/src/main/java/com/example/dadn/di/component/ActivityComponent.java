package com.example.dadn.di.component;


import com.example.dadn.di.module.ActivityModule;
import com.example.dadn.di.scope.ActivityScope;
import com.example.dadn.ui.alert.AlertActivity;
import com.example.dadn.ui.alert.alertprocessing.AlertProcessingActivity;
import com.example.dadn.ui.login.LoginActivity;
import com.example.dadn.ui.register.RegisterActivity;

import dagger.Component;

@ActivityScope
@Component(modules = ActivityModule.class, dependencies = AppComponent.class)
public interface ActivityComponent {
    void inject(LoginActivity activity);

    void inject(AlertActivity activity);

    void inject(AlertProcessingActivity activity);

    void inject(RegisterActivity activity);
}
