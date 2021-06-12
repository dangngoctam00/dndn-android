package com.example.dadn.ui.login;

import android.text.TextUtils;

import com.example.dadn.data.dto.LoginRequest;
import com.example.dadn.network.APIClient;
import com.example.dadn.ui.base.BaseViewModel;
import com.example.dadn.utils.rx.SchedulerProvider;

public class LoginViewModel extends BaseViewModel<LoginNavigator> {
    public LoginViewModel(SchedulerProvider mSchedulerProvider) {
        super(mSchedulerProvider);
    }

    public boolean isUsernameAndPasswordValid(String username, String password) {
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
            return false;
        }
        return true;
    }

    public void login(String username, String password) {
        setIsLoading(true);
        LoginRequest payload = new LoginRequest(username, password);
        APIClient.getRetrofit().login(payload)
                .subscribeOn(getSchedulerProvider().ui()) // subscribe on a thread which is different from Main thread
                .observeOn(getSchedulerProvider().io())
                .subscribe(response -> {
                    setIsLoading(false);
                    if (response.getMessage().equals("failed")) {
                        getNavigator().handleError(new Throwable("failed"));
                    }
                    getNavigator().setLogin(true);
                    getNavigator().openMainActivity();
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                });
    }

    public void onServerLoginClick() {
        getNavigator().login();
    }

    public void onNavigateRegisterClick() {
        getNavigator().openRegisterActivity();
    }
}
