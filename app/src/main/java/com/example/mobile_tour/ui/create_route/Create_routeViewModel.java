package com.example.mobile_tour.ui.create_route;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Create_routeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public Create_routeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Create route fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}