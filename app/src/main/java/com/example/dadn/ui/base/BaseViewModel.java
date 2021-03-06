package com.example.dadn.ui.base;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.ViewModel;

import com.example.dadn.utils.rx.SchedulerProvider;

import java.lang.ref.WeakReference;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public abstract class BaseViewModel<N> extends ViewModel {
    private final ObservableBoolean mIsLoading = new ObservableBoolean();
    private final ObservableBoolean mIsAlertProcessing = new ObservableBoolean();
    private final ObservableBoolean mAlertState = new ObservableBoolean();
    private final ObservableBoolean mCannotHandleAlert = new ObservableBoolean();
    private final SchedulerProvider mSchedulerProvider;

    private CompositeDisposable mCompositeDisposable;

    private WeakReference<N> mNavigator;

    public BaseViewModel(SchedulerProvider mSchedulerProvider) {
        this.mSchedulerProvider = mSchedulerProvider;
        this.mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onCleared() {
        mCompositeDisposable.dispose();
        super.onCleared();
    }


    public ObservableBoolean getCannotHandleAlert() {
        return mCannotHandleAlert;
    }

    public void setCannotHandleAlert(boolean b) {
        mCannotHandleAlert.set(b);
    }


    public ObservableBoolean getIsAlertProcessing() {
        return mIsAlertProcessing;
    }

    public void setIsAlertProcessing(boolean b) {
        mIsAlertProcessing.set(b);
    }

    public ObservableBoolean getAlertState() {
        return mAlertState;
    }

    public void setAlertState(boolean b) {
        mAlertState.set(b);
    }

    public ObservableBoolean getIsLoading() {
        return mIsLoading;
    }

    public void setIsLoading(boolean isLoading) {
        mIsLoading.set(isLoading);
    }

    public N getNavigator() {
        return mNavigator.get();
    }

    public void setNavigator(N navigator) {
        this.mNavigator = new WeakReference<>(navigator);
    }

    public SchedulerProvider getSchedulerProvider() {
        return mSchedulerProvider;
    }
}
