package com.example.dadn.ui.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.dadn.BR;
import com.example.dadn.R;
import com.example.dadn.databinding.ActivityRegisterBinding;
import com.example.dadn.di.component.ActivityComponent;
import com.example.dadn.ui.base.BaseActivity;
import com.example.dadn.ui.login.LoginActivity;


public class RegisterActivity extends BaseActivity<ActivityRegisterBinding, RegisterViewModel> implements RegisterNavigator {
    private ActivityRegisterBinding mActivityRegisterBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, RegisterActivity.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityRegisterBinding = getViewDataBinding();
        mViewModel.setNavigator(this);
    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }


    @Override
    public void handleError(Throwable throwable) {
        this.runOnUiThread(() -> {
            Toast.makeText(RegisterActivity.this,
                    "Bạn không thể sử dụng tên đăng nhập này.",
                    Toast.LENGTH_LONG).show();
        });
        Log.d("LOGIN", "failed: " + throwable.getMessage());
    }

    @Override
    public void register() {
        String username = mActivityRegisterBinding.registerUsername.getText().toString();
        String password = mActivityRegisterBinding.registerPassword.getText().toString();
        String retypePassword = mActivityRegisterBinding.registerConfirmPassword.getText().toString();
        if (!password.equals(retypePassword)) {
            Toast.makeText(this, "Xác nhận mật khẩu phải giống mật khẩu!", Toast.LENGTH_SHORT).show();
        }
        else {
            mViewModel.register(username, password);
        }
    }

    @Override
    public void openLoginActivity() {
        Intent intent = LoginActivity.newIntent(RegisterActivity.this);
        startActivity(intent);
        finish();
    }
}
