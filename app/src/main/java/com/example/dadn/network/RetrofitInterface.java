package com.example.dadn.network;


import com.example.dadn.data.dto.LoginRequest;
import com.example.dadn.data.dto.LoginResponse;


import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitInterface {
    @POST("login")
    Observable<LoginResponse> login(@Body LoginRequest user);
}
