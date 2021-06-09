package com.example.dadn.ui.controlDevice.selectDevice;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient instance = null;
    private HttpApi api;

    private RetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(HttpApi.class);
    }

    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public HttpApi getApi(){
        return api;
    }
}
