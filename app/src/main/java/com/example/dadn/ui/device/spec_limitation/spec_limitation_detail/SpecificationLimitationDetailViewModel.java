package com.example.dadn.ui.device.spec_limitation.spec_limitation_detail;

import com.example.dadn.ui.base.BaseViewModel;
import com.example.dadn.utils.rx.SchedulerProvider;

public class SpecificationLimitationDetailViewModel extends BaseViewModel<SpecificationLimitationDetailNavigator> {
    public SpecificationLimitationDetailViewModel(SchedulerProvider schedulerProvider) {
        super(schedulerProvider);
    }

    public void decreaseLowerBound() {
        getNavigator().decreaseLowerBound();
    }

    public void increaseLowerBound() {
        getNavigator().increaseLowerBound();
    }

    public void decreaseUpperBound() {
        getNavigator().decreaseUpperBound();
    }

    public void increaseUpperBound() {
        getNavigator().increaseUpperBound();
    }

    public void updateLimitation() {
        getNavigator().updateLimitation();
    }
}
