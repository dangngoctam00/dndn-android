package com.example.dadn.network;


import com.example.dadn.data.dto.AlertActiveRequest;
import com.example.dadn.data.dto.AlertActiveRespone;
import com.example.dadn.data.dto.AlertReponse;
import com.example.dadn.data.dto.AlertRequest;
import com.example.dadn.data.dto.LoginRequest;
import com.example.dadn.data.dto.LoginResponse;
import com.example.dadn.data.dto.RegisterRequest;
import com.example.dadn.data.dto.RegisterResponse;
import com.example.dadn.data.dto.SpecificationDetailResponse;
import com.example.dadn.data.dto.SpecificationResponse;
import com.example.dadn.data.dto.UpdateSpecificationRequest;
import com.example.dadn.data.dto.UpdateSpecificationResponse;
import com.example.dadn.ui.statistic.ResultChartDB;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitInterface {
    @Headers("Content-Type: application/json")
    @POST("api/login")
    Observable<LoginResponse> login(@Body LoginRequest user);

    @Headers("Content-Type: application/json")
    @POST("api/register")
    Observable<RegisterResponse> register(@Body RegisterRequest user);

    @GET("constrain")
    Observable<List<SpecificationResponse>> getSpecifications();

    @GET("constrain/{type}")
    Observable<List<SpecificationDetailResponse>> getSpecificationDetail(@Path("type") String type);

    @Headers("Content-Type: application/json")
    @POST("setConstrain")
    Observable<UpdateSpecificationResponse> updateSpecificationDetail(@Body UpdateSpecificationRequest body);

    @POST("api/receiveresponefromapp")
    Call<AlertReponse> requestTask(@Body AlertRequest body);

    @POST("api/activatealert")
    Call<AlertActiveRespone> postactivateAlert(@Body AlertActiveRequest body);

    @GET("api/activatealert")
    Call<AlertActiveRespone> getactivateAlert();

    @GET("statistic/{type}")
    Observable<List<ResultChartDB>> getStatisticData(@Path("type") String type,
                                                     @Query("from") String from,
                                                     @Query("to") String to);
}
