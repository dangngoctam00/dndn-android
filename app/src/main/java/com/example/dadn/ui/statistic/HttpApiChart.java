package com.example.dadn.ui.statistic;

import com.example.dadn.ui.controlDevice.selectDevice.ResultFeedData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HttpApiChart {
    static final String BASE_URL = "https://io.adafruit.com";
    @GET("/api/v2/{username}/feeds/{feed_key}/data")
    Call<List<ResultFeedChart>> getChartData(@Path("username") String username,
                                             @Path("feed_key") String feed_key,
                                             @Query("start_time") String start_time,
                                             @Query("end_time") String end_time);

}
