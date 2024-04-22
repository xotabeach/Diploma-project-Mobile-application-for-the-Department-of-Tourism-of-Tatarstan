package com.example.mobile_tour.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mobile_tour.databinding.FragmentCreateRouteParamsBinding;

public class CreateRouteParamsFragment extends Fragment {

    private FragmentCreateRouteParamsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCreateRouteParamsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // В этом методе вы можете настроить отображение параметров маршрута




        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
