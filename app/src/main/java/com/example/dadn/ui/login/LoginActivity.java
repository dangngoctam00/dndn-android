package com.example.dadn.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.dadn.BR;
import com.example.dadn.R;
import com.example.dadn.databinding.ActivityLoginBinding;
import com.example.dadn.di.component.ActivityComponent;
import com.example.dadn.ui.base.BaseActivity;
import com.example.dadn.ui.main.MainActivity;
import com.example.dadn.ui.register.RegisterActivity;
import com.example.dadn.utils.SessionManager;


public class LoginActivity extends BaseActivity<ActivityLoginBinding ,LoginViewModel> implements LoginNavigator {

    private SessionManager session;
    private ActivityLoginBinding mActivityLoginBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityLoginBinding = getViewDataBinding();
        mViewModel.setNavigator(this);
        session = new SessionManager(getApplicationContext());
        if (session.isLoggedIn()) {
            openMainActivity();
        }
    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }


    @Override
    public void handleError(Throwable throwable) {
        this.runOnUiThread(() -> {
            Toast.makeText(LoginActivity.this,
                        "Username or password is wrong, please try again.",
                            Toast.LENGTH_LONG).show();
        });
        Log.d("LOGIN", "failed: " + throwable.getMessage());
    }

    @Override
    public void login() {
        String username = mActivityLoginBinding.etUsername.getText().toString();
        String password = mActivityLoginBinding.etPassword.getText().toString();
        if (mViewModel.isUsernameAndPasswordValid(username, password)) {
            hideKeyboard();
            mViewModel.login(username, password);
        }
        else {
            Toast.makeText(this, getString(R.string.invalid_email_password), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void openMainActivity() {
        Intent intent = MainActivity.newIntent(LoginActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void openRegisterActivity() {
        Intent intent = RegisterActivity.newIntent(LoginActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void setLogin(boolean isLoggedIn) {
        session.setLogin(isLoggedIn);
    }
}