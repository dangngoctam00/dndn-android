package com.example.dadn.ui.alert.alertprocessing;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dadn.R;
import com.example.dadn.ui.login.LoginActivity;
import com.example.dadn.ui.main.MainActivity;


public  class AlertProcessingActivity extends AppCompatActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_processing);
    }

    public void onClick(View v){
        Intent intent = MainActivity.newIntent(AlertProcessingActivity.this);
        startActivity(intent);
        finish();
    }
}
