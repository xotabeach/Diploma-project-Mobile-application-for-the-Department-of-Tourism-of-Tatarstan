package com.example.mobile_tour.ui.splash_screen;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_tour.MainActivity;
import com.example.mobile_tour.R;
import com.example.mobile_tour.ui.login.LoginActivity;

public class SplashScreenActivity extends AppCompatActivity {
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, 4*1000);


    }


}
