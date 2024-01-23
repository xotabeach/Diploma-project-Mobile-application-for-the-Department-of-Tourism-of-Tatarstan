package com.example.mobile_tour.ui.home;


import java.util.List;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    private MutableLiveData<List<TravelLocation>> travelLocations = new MutableLiveData<>();

    // Метод для установки списка travelLocation
    public void setTravelLocations(List<TravelLocation> locations) {
        travelLocations.setValue(locations);
    }
    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}