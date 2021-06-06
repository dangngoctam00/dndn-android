package com.example.dadn.ui.statistic;

import com.example.dadn.ui.controlDevice.selectDevice.HttpApi;
import com.example.dadn.ui.controlDevice.selectDevice.RetrofitClient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientChart {
    private static RetrofitClientChart instance = null;
    private HttpApiChart api;

    private RetrofitClientChart() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(HttpApiChart.class);
    }

    public static synchronized RetrofitClientChart getInstance() {
        if (instance == null) {
            instance = new RetrofitClientChart();
        }
        return instance;
    }

    public HttpApiChart getApi(){
        return api;
    }
}
