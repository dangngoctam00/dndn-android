package com.example.dadn.ui.base;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.example.dadn.DadnApp;
import com.example.dadn.di.component.ActivityComponent;
import com.example.dadn.di.component.DaggerActivityComponent;
import com.example.dadn.di.module.ActivityModule;

import javax.inject.Inject;

import dagger.internal.DaggerCollections;

//import javax.inject.Inject;

public abstract class BaseActivity<T extends ViewDataBinding,V extends BaseViewModel> extends AppCompatActivity {

    private T mViewDataBinding;

    @Inject
    protected V mViewModel;

    public abstract int getBindingVariable();

    public abstract
    @LayoutRes
    int getLayoutId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        performDependencyInjection(getBuildComponent());
        super.onCreate(savedInstanceState);
        performDataBinding();
    }

    public abstract void performDependencyInjection(ActivityComponent buildComponent);

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    private ActivityComponent getBuildComponent() {
        return DaggerActivityComponent.builder()
                .appComponent(((DadnApp)getApplication()).appComponent)
                .activityModule(new ActivityModule(this))
                .build();
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }

    public T getViewDataBinding() {
        return mViewDataBinding;
    }

    private void performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        mViewDataBinding.executePendingBindings();
    }
}
