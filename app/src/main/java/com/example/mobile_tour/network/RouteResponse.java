package com.example.mobile_tour.network;

import com.google.gson.annotations.SerializedName;

public class RouteResponse {
    @SerializedName("features")
    private Feature[] features;

    public Feature[] getFeatures() {
        return features;
    }

    public static class Feature {
        @SerializedName("properties")
        private Properties properties;

        public Properties getProperties() {
            return properties;
        }
    }

    public static class Properties {
        @SerializedName("segments")
        private Segment[] segments;

        public Segment[] getSegments() {
            return segments;
        }
    }

    public static class Segment {
        @SerializedName("duration")
        private double duration;

        public double getDuration() {
            return duration;
        }
    }
}
