package com.example.mobile_tour.ui.right_bar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mobile_tour.databinding.FragmentRightBarBinding;
import com.example.mobile_tour.ui.right_bar.RightBarViewModel;

public class RightBarFragment extends Fragment {

    private FragmentRightBarBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RightBarViewModel rightBarViewModel =
                new ViewModelProvider(this).get(RightBarViewModel.class);

        binding = FragmentRightBarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        rightBarViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}