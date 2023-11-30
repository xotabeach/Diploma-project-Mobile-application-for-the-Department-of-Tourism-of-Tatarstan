package com.example.mobile_tour.ui.right_bar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RightBarViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public RightBarViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is question fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}