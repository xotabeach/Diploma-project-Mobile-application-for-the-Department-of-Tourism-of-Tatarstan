package com.example.mobile_tour.ui.right_bar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mobile_tour.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mobile_tour.databinding.FragmentRightBarBinding;
import com.example.mobile_tour.ui.home.Landmark;
import com.example.mobile_tour.ui.right_bar.RightBarViewModel;

import java.util.ArrayList;
import java.util.List;

public class RightBarFragment extends Fragment {
    private RightBarViewModel rightBarViewModel;
    private FragmentRightBarBinding binding;
    private TextView categoryTitleTextView;
    public void setCategoryTitle(String categoryTitle) {
        if (categoryTitleTextView != null) {
            categoryTitleTextView.setText(categoryTitle);
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRightBarBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        categoryTitleTextView = rootView.findViewById(R.id.text_notifications_cat);

        // Получение экземпляра ViewModel
        rightBarViewModel = new ViewModelProvider(requireActivity()).get(RightBarViewModel.class);

        // Наблюдение за изменениями в категории
        rightBarViewModel.getSelectedCategory().observe(getViewLifecycleOwner(), category -> {
            categoryTitleTextView.setText("Категория: " + category);
        });

        // Наблюдение за изменениями в списке landmarks
        rightBarViewModel.getLandmarks().observe(getViewLifecycleOwner(), landmarksList -> {
            System.out.println("Количество в RIGHT_BAR: " + landmarksList.size());
        });

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}