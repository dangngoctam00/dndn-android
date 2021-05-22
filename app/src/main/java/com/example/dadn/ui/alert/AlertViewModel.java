package com.example.dadn.ui.alert;

import com.example.dadn.ui.base.BaseViewModel;
import com.example.dadn.utils.rx.SchedulerProvider;

public class AlertViewModel extends BaseViewModel<AlertNavigator>{

    public AlertViewModel(SchedulerProvider mSchedulerProvider) {
        super(mSchedulerProvider);
    }

    public void testNotification(){getNavigator().testNotification();}
}
