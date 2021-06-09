package com.example.dadn.ui.setting;

import com.example.dadn.ui.base.BaseViewModel;
import com.example.dadn.ui.setting.fragment.HelpFragment;
import com.example.dadn.ui.setting.fragment.IntroductionFragment;
import com.example.dadn.utils.rx.SchedulerProvider;

public class SettingViewModel extends BaseViewModel<SettingNavigator> {
    public SettingViewModel(SchedulerProvider mSchedulerProvider) {
        super(mSchedulerProvider);
    }
    public void openIntroductionFragment(){
        getNavigator().ReplaceFragment(new IntroductionFragment());
    }
    public void openHelpFragment(){
        getNavigator().ReplaceFragment(new HelpFragment());
    }

}
