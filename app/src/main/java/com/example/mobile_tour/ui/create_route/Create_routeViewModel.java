package com.example.mobile_tour.ui.create_route;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mobile_tour.DataBaseHelper;
import com.example.mobile_tour.ui.ClickedTravelData;
import com.example.mobile_tour.ui.SharedViewModel;
import com.example.mobile_tour.ui.home.TravelLocation;
import java.util.List;

public class Create_routeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private MutableLiveData<List<TravelLocation>> travelLocations = new MutableLiveData<>();

    public Create_routeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Ваши достопримечательности:");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void setTravelLocations(List<TravelLocation> locations) {
        travelLocations.setValue(locations);
    }

    public List<ClickedTravelData> loadClickedLandMarksFromDatabase(DataBaseHelper dbHelper) {
        return dbHelper.getAllClickedLandmarks();
    }


    public LiveData<List<TravelLocation>> getTravelLocations() {
        return travelLocations;
    }

    public LiveData<List<ClickedTravelData>> getTravelLocationsFromSharedViewModel(SharedViewModel sharedViewModel) {
        return sharedViewModel.getTravelLocations();
    }
}