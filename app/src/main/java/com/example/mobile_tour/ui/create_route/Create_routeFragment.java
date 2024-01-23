package com.example.mobile_tour.ui.create_route;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mobile_tour.databinding.FragmentCreateRouteBinding;


import com.example.mobile_tour.ui.ClickedTravelData;
import com.example.mobile_tour.ui.SharedViewModel;
import com.example.mobile_tour.ui.home.ClickedLocationDialog;
import com.example.mobile_tour.ui.home.TravelLocation;

import java.util.ArrayList;
import java.util.List;

public class Create_routeFragment extends Fragment{

    private FragmentCreateRouteBinding binding;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Create_routeViewModel createrouteViewModel =
                new ViewModelProvider(this).get(Create_routeViewModel.class);
        Bundle bundle = getArguments();




        binding = FragmentCreateRouteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        final TextView textView = binding.textNotifications;
        createrouteViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


        SharedViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        Create_routeViewModel createRouteViewModel = new ViewModelProvider(requireActivity()).get(Create_routeViewModel.class);

        LiveData<List<ClickedTravelData>> travelLocations = createRouteViewModel.getTravelLocationsFromSharedViewModel(sharedViewModel);
        createRouteViewModel.getTravelLocations().observe(getViewLifecycleOwner(), new Observer<List<TravelLocation>>() {
            @Override
            public void onChanged(List<TravelLocation> travelLocations) {
                // Получаем количество элементов в списке travelLocations
                int itemCount = travelLocations.size();

                // Показываем уведомление Toast с количеством элементов
                Toast.makeText(requireContext(), "Количество элементов: " + itemCount, Toast.LENGTH_SHORT).show();
            }
        });
        List<ClickedTravelData> locationList = travelLocations.getValue(); // Получение значения из LiveData

        if (locationList != null) {
            int itemCount = locationList.size(); // Вызов метода size() на списке List
            System.out.println("Количество элементов: " + itemCount);
        } else {
            System.out.println("Список locationList пуст");
        }
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}