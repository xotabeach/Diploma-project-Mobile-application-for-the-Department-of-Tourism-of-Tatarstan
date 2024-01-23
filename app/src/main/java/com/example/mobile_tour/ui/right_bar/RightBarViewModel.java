package com.example.mobile_tour.ui.right_bar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mobile_tour.ui.home.Landmark;

import java.util.List;

public class RightBarViewModel extends ViewModel {

    private final MutableLiveData<String> selectedCategory = new MutableLiveData<>();
    private final MutableLiveData<List<Landmark>> landmarks = new MutableLiveData<>();

    public void setSelectedCategory(String category) {
        selectedCategory.setValue(category);
    }

    public LiveData<String> getSelectedCategory() {
        return selectedCategory;
    }

    public void setLandmarks(List<Landmark> landmarksList) {
        landmarks.setValue(landmarksList);
    }

    public LiveData<List<Landmark>> getLandmarks() {
        return landmarks;
    }
}