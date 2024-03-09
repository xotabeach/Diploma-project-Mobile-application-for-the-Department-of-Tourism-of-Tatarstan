package com.example.mobile_tour.ui.profile;

import static android.content.Intent.getIntent;
import static android.content.Intent.parseUri;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mobile_tour.MainActivity;
import com.example.mobile_tour.databinding.FragmentProfileBinding;
import com.example.mobile_tour.ui.right_bar.RightBarViewModel;

import java.util.Objects;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    private String email;
    private ProfileViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        MainActivity mainActivity = (MainActivity) getActivity();

        if (mainActivity != null) {
            email = mainActivity.getMyName();
            System.out.println("ПРОФТИТТТТИИИЛЬ:"+ email);
        }

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        viewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        System.out.println("Категория:DOFGJFIOUSDNFJNDS");

        //final TextView textView = binding.textHome;
        viewModel.getEmail().observe(getViewLifecycleOwner(), email -> {
            System.out.println("Профиль:"+ email);
        });



        //String email = viewModel.getEmail();

        //String name = sendUserData.getString("username");
        //String password = sendUserData.getString("password");
        //Toast.makeText(getContext(), "ИМЯЯЯЯ"+email, Toast.LENGTH_SHORT).show();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}