package com.example.mobile_tour.ui.home;

import android.view.View;

public class TravelCategory {

    public String title;

    public Integer imageResId;


    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public int getImageResId() {
        return imageResId;
    }

    @Override
    public String toString() {
        return title;
    }
}

