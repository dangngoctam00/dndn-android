package com.example.dadn.ui.alert;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.library.baseAdapters.BR;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.dadn.R;
import com.example.dadn.Service.AlertIntentService;
import com.example.dadn.Service.AlertReceiver;
import com.example.dadn.databinding.ActivityAlertBinding;
import com.example.dadn.di.component.ActivityComponent;
import com.example.dadn.ui.alert.alertprocessing.AlertProcessingActivity;
import com.example.dadn.ui.base.BaseActivity;
import com.example.dadn.ui.login.LoginActivity;
import com.example.dadn.ui.main.MainActivity;
import com.example.dadn.utils.PreferenceUtilities;


public  class AlertActivity extends BaseActivity<ActivityAlertBinding, AlertViewModel> implements AlertNavigator, SharedPreferences.OnSharedPreferenceChangeListener{


    String TAG = "AlertActivity";
    private ActivityAlertBinding mActivityAlertBinding;
    TextView mAlertNotice;
    Button mThucHien;
    Button mHuyBo;
    Button mDaHieu;
    TextView mLight;
    TextView mSolid;
    TextView mTemp;
    TextView mHumid;
    public static Intent newIntent(Context context) {
        return new Intent(context, AlertActivity.class);
    }





    public void openAlertProcess() {
        Intent intent = MainActivity.newIntent(this);
        startActivity(intent);
        finish();
    }

    @Override
    public void acceptTask() {
        AlertTasks.executeTask(this, AlertTasks.ACTION_ACCEPT);
        Intent intent = new Intent(AlertActivity.this, AlertProcessingActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void cancelTask() {
        AlertTasks.executeTask(this, AlertTasks.ACTION_CANCEL);
        Intent intent = MainActivity.newIntent(AlertActivity.this);
        startActivity(intent);
        finish();
    }


    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_alert;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityAlertBinding = getViewDataBinding();
        mViewModel.setNavigator(this);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);
        mViewModel.setIsAlertProcessing(PreferenceUtilities.getisAlertProcessing(this));
        mViewModel.setAlertState(PreferenceUtilities.getAlertState(this));
        mViewModel.setCannotHandleAlert(PreferenceUtilities.getcannotHandle(this));
        mAlertNotice = (TextView) findViewById(R.id.tv_alert_notice);
        mDaHieu = (Button) findViewById(R.id.btn_dahieu);
        mHuyBo = (Button) findViewById(R.id.btn_huybo);
        mThucHien = (Button) findViewById(R.id.btn_thuchien);

        mDaHieu.setVisibility(mViewModel.getCannotHandleAlert().get() ? View.VISIBLE : View.GONE);
        mThucHien.setVisibility(mViewModel.getCannotHandleAlert().get() ? View.GONE : View.VISIBLE);
        mHuyBo.setVisibility(mViewModel.getCannotHandleAlert().get() ? View.GONE : View.VISIBLE);
        if(mViewModel.getCannotHandleAlert().get()){
            mAlertNotice.setText("Hiện tại chúng tôi không thể cải thiện tình trạng của khu vườn. \nHãy kiểm tra khu vườn ngay");
        }

        mHumid = (TextView) findViewById(R.id.tv_alert_humid);
        mSolid = (TextView) findViewById(R.id.tv_alert_solid);
        mTemp = (TextView) findViewById(R.id.tv_alert_temperature);
        mLight = (TextView) findViewById(R.id.tv_alert_light);

        String solid = PreferenceUtilities.getSolid(this);
        String humid = PreferenceUtilities.getHumid(this);
        String light = PreferenceUtilities.getLight(this);
        String temp = PreferenceUtilities.getTemp(this);
        Log.d(TAG, "onCreate: " + "solid " + solid + " humid " + humid + " light " + light + " temp " + temp);
        if(humid.equals("lower_bound")){

            mHumid.setText("Độ ẩm không khí thấp");
            mHumid.setVisibility(View.VISIBLE);
        }
        else if(humid.equals("upper_bound")){

            mHumid.setText("Độ ẩm không khí cao");
            mHumid.setVisibility(View.VISIBLE);
        }
        else mHumid.setVisibility(View.GONE);

        if(solid.equals("lower_bound")){

            mSolid.setText("Độ ẩm đất thấp");
            mSolid.setVisibility(View.VISIBLE);
        }
        else if(solid.equals("upper_bound")){
            mSolid.setText("Độ ẩm đất cao");
            mSolid.setVisibility(View.VISIBLE);
        }
        else mSolid.setVisibility(View.GONE);

        if(light.equals("lower_bound")){
            mLight.setText("Ánh sáng thấp");
            mLight.setVisibility(View.VISIBLE);
        }
        else if(light.equals("upper_bound")){
            mLight.setText("Ánh sáng cao");
            mLight.setVisibility(View.VISIBLE);
        }
        else mLight.setVisibility(View.GONE);

        if(temp.equals("lower_bound")){
            mTemp.setText("Nhiệt độ thấp");
            mTemp.setVisibility(View.VISIBLE);
        }
        else if(temp.equals("upper_bound")){
            mTemp.setText("Nhiệt độ cao");
            mTemp.setVisibility(View.VISIBLE);
        }
        else mTemp.setVisibility(View.GONE);


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.unregisterOnSharedPreferenceChangeListener(this);
    }
    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (PreferenceUtilities.KEY_ALERT.equals(key)) {
            mViewModel.setAlertState(PreferenceUtilities.getAlertState(this));
        } else if (PreferenceUtilities.KEY_IS_ALERT_PROCESSING.equals(key)) {
            mViewModel.setIsAlertProcessing(PreferenceUtilities.getisAlertProcessing(this));
        }
        else if (PreferenceUtilities.KEY_CAN_NOT_HANDLE.equals(key)) {
            mViewModel.setCannotHandleAlert(PreferenceUtilities.getcannotHandle(this));
        }


    }
}
