package com.example.dadn.ui.register;

public interface RegisterNavigator {
    void openLoginActivity();

    void register();

    void handleError(Throwable throwable);
}
