package com.example.dadn.ui.controlDevice.selectDevice;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HttpApi {
    static final String BASE_URL = "https://io.adafruit.com";
    @GET("/api/v2/{username}/feeds/{feed_key}/data")
    Call<List<ResultFeedData>> getFeedData(@Path("username") String username, @Path("feed_key") String feed_key, @Query("limit") String limit);

    @GET("/api/v2/{username}/feeds/bk-iot-led/data")
    Call<List<ResultFeedData>> getFeedLed(@Path("username") String username, @Query("limit") String limit);
}
