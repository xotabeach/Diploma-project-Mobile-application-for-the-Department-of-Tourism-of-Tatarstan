package com.example.mobile_tour.network;

import com.example.mobile_tour.network.RouteResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface OpenRouteServiceAPI {
    @POST("directions/foot-walking/geojson")
    Call<RouteResponse> getWalkingRoute(@Header("Authorization") String apiKey, @Body RouteRequest request);
}
