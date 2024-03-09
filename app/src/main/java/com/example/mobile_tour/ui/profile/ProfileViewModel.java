package com.example.mobile_tour.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mobile_tour.MainActivity;

public class ProfileViewModel extends ViewModel {






    private final MutableLiveData<String> email = new MutableLiveData<>();
    private String password;
    private String name;

    public void setEmail(String emailin) {
        email.setValue(emailin);

    }


    public LiveData<String> getEmail() {
        return email;
    }

    //public void check(){System.out.println("Email ОТ ОН: " + email.getValue());}
    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void clearData() {
        //this.email = null;
        this.password = null;
        this.name = null;
    }

}