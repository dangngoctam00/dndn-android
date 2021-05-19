package com.example.dadn.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;

import android.view.MenuItem;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.dadn.R;
import com.example.dadn.ui.device.DeviceFragment;
import com.example.dadn.ui.home.HomeFragment;


import com.example.dadn.ui.instruction.InstructionFragment;
import com.example.dadn.ui.setting.SettingFragment;
import com.example.dadn.ui.statistic.StatisticFragment;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;


public class MainActivity extends AppCompatActivity {



    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_home);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
        }

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(
                task -> Log.d("FCM ", task.getResult())
        );
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.nav_device:
                        selectedFragment = new DeviceFragment();
                        break;
                    case R.id.nav_instruction:
                        selectedFragment = new InstructionFragment();
                        break;
                    case R.id.nav_setting:
                        selectedFragment = new SettingFragment();
                        break;
                    case R.id.nav_data:
                        selectedFragment = new StatisticFragment();
                        break;
                }
                if (getSupportFragmentManager().getBackStackEntryCount() > 0){
                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    Log.w("backtrack", "pop all");
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();
                return true;
            };
}
