package com.example.dadn.ui.setting.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.example.dadn.R;
import com.example.dadn.data.dto.AlertActiveRequest;
import com.example.dadn.data.dto.AlertActiveRespone;
import com.example.dadn.data.dto.AlertReponse;
import com.example.dadn.data.dto.AlertRequest;
import com.example.dadn.network.APIClient;
import com.example.dadn.utils.PreferenceUtilities;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Setting2Fragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    SwitchCompat mSwitch;

    @Override
    public void onResume() {
        super.onResume();
        sendGetRequest(this.getActivity());

    }

    TextView mAlertState;
    static Boolean defaultvalue;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_settings_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSwitch = (SwitchCompat) getView().findViewById(R.id.switch_alert);
        mAlertState = (TextView) getView().findViewById(R.id.alert_state);
        Log.d("onViewCreated", "onViewCreated: "+ defaultvalue);
        onButtonBack(view);
        sendGetRequest(this.getActivity());
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sendPostRequest(Setting2Fragment.this.getActivity(), Boolean.toString(isChecked));
                mAlertState.setText(isChecked ? "Cảnh báo đã bật" : "Cảnh báo đã tắt");
            }
        });
    }


    public void onButtonBack(@NonNull View view){
        ImageView back = (ImageView) view.findViewById(R.id.btnBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getParentFragmentManager().getBackStackEntryCount() > 0){
                    boolean done = getParentFragmentManager().popBackStackImmediate();
                    Log.w("Button back", "/:" + done);
                }
            }
        });
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }

    private void sendPostRequest(Context context, String activate){
        AlertActiveRequest payload = new AlertActiveRequest(activate);
        APIClient.getRetrofit().postactivateAlert(payload).enqueue(new Callback<AlertActiveRespone>() {
            @Override
            public void onResponse(Call<AlertActiveRespone> call, Response<AlertActiveRespone> response) {
                AlertActiveRespone alertActiveResponeReponse = response.body();
                if(alertActiveResponeReponse.getActive().equals("true")){
                    mSwitch.setChecked(true);
                    mAlertState.setText("Cảnh báo đã bật");
                    Log.d("AlertActiveRequest", "accept ok");
                }
                else if(alertActiveResponeReponse.getActive().equals("false")){
                    Log.d("AlertActiveRequest", "rejected ok");
                    mSwitch.setChecked(false);
                    mAlertState.setText("Cảnh báo đã tắt");
                }
            }

            @Override
            public void onFailure(Call<AlertActiveRespone> call, Throwable t) {
                Log.d("AlertActiveRequest", "onFailure: "+ t);

            }
        });

    }

    private void sendGetRequest(Context context){
        APIClient.getRetrofit().getactivateAlert().enqueue(new Callback<AlertActiveRespone>() {
            @Override
            public void onResponse(Call<AlertActiveRespone> call, Response<AlertActiveRespone> response) {
                AlertActiveRespone alertActiveResponeReponse = response.body();
                if(alertActiveResponeReponse.getActive().equals("true")){
                    mSwitch.setChecked(true);
                    mAlertState.setText("Cảnh báo đã bật");

                }
                else if(alertActiveResponeReponse.getActive().equals("false")){
                    mSwitch.setChecked(false);
                    mAlertState.setText("Cảnh báo đã tắt");
                }
            }
            @Override
            public void onFailure(Call<AlertActiveRespone> call, Throwable t) {
                Log.d("AlertActiveRequest", "onFailure: "+ t);

            }
        });

    }
}
