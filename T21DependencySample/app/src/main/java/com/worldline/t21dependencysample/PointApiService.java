package com.worldline.t21dependencysample;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PointApiService {

    @GET("points/{pointId}")
    Call<Point> getPointById(@Path("pointId") String pointId);
}
