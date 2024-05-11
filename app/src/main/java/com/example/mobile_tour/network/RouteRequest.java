package com.example.mobile_tour.network;

import com.google.gson.annotations.SerializedName;

public class RouteRequest {
    @SerializedName("coordinates")
    private double[][] coordinates;

    public RouteRequest(double[][] coordinates) {
        this.coordinates = coordinates;
    }
}
