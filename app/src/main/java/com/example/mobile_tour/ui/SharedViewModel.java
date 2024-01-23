package com.example.mobile_tour.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;



import com.example.mobile_tour.ui.home.TravelLocation;

import java.util.List;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<List<ClickedTravelData>> travelLocations = new MutableLiveData<>();

    public void setTravelLocations(List<ClickedTravelData> locations) {
        travelLocations.setValue(locations);
    }


    public LiveData<List<ClickedTravelData>> getTravelLocations() {
        return travelLocations;
    }



}
