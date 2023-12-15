package com.example.mobile_tour.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobile_tour.R;
import androidx.annotation.NonNull;
import androidx.compose.ui.platform.ComposeView;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    CarouselMainKt carouselMainKt = new CarouselMainKt();
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ComposeView composeView = root.findViewById(R.id.composeView);
       carouselMainKt.CarouselCard(composeView);
        return root; // Используйте root вместо composeView
    }
}
