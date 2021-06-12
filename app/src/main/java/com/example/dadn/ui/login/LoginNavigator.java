package com.example.dadn.ui.login;

public interface LoginNavigator {
    void handleError(Throwable throwable);

    void login();

    void openMainActivity();

    void setLogin(boolean isLoggedIn);

    void openRegisterActivity();
}
