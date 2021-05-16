package com.example.dadn.network;


import com.example.dadn.data.dto.LoginRequest;
import com.example.dadn.data.dto.LoginResponse;
import com.example.dadn.data.dto.SpecificationDetailRequest;
import com.example.dadn.data.dto.SpecificationDetailResponse;
import com.example.dadn.data.dto.SpecificationResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitInterface {
    @POST("users/login")
    Observable<LoginResponse> login(@Body LoginRequest user);

    @GET("specifications")
    Observable<List<SpecificationResponse>> getSpecifications();

    @GET("specifications/{id}")
    Observable<SpecificationDetailResponse> getSpecificationDetail(@Path("id") Integer id);

    @POST("specifications/{id}")
    Observable<String> updateSpecificationDetail(@Path("id") Integer id, @Body SpecificationDetailRequest body);
}
