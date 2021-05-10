package com.example.dadn.ui.main;

import com.example.dadn.ui.base.BaseViewModel;
import com.example.dadn.utils.rx.SchedulerProvider;

public class MainViewModel extends BaseViewModel<MainNavigator> {
    public MainViewModel(SchedulerProvider mSchedulerProvider) {
        super(mSchedulerProvider);
    }
}
