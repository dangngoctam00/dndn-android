package com.example.dadn.ui.register;

import android.util.Log;

import com.example.dadn.data.dto.RegisterRequest;
import com.example.dadn.network.APIClient;
import com.example.dadn.ui.base.BaseViewModel;
import com.example.dadn.utils.rx.SchedulerProvider;

public class RegisterViewModel extends BaseViewModel<RegisterNavigator> {

    public RegisterViewModel(SchedulerProvider mSchedulerProvider) {
        super(mSchedulerProvider);
    }

    public void onServerRegisterClick() {
        getNavigator().register();
    }

    public void onNavigateLoginClick() {
        getNavigator().openLoginActivity();
    }


    public void register(String username, String password) {
        setIsLoading(true);
        RegisterRequest payload = new RegisterRequest(username, password, password);
        APIClient.getRetrofit().register(payload)
                .subscribeOn(getSchedulerProvider().ui()) // subscribe on a thread which is different from Main thread
                .observeOn(getSchedulerProvider().io())
                .subscribe(response -> {
                    setIsLoading(false);
                    Log.d("response", "register: " + response.toString());
                    if (response.getMessage().equals("failed")) {
                        getNavigator().handleError(new Throwable("failed"));
                    }
                    getNavigator().openLoginActivity();
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                });
    }
}
