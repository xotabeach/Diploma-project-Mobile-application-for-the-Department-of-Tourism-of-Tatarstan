package com.example.mobile_tour.ui.profile;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import android.content.res.Configuration;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mobile_tour.MainActivity;
import com.example.mobile_tour.R;
import com.example.mobile_tour.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    private String[] data;
    private ProfileViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        MainActivity mainActivity = (MainActivity) getActivity();

        if (mainActivity != null) {
            data = mainActivity.getMyName();
            System.out.println("ПРОФТИТТТТИИИЛЬ:" + data[0]);
            System.out.println("ПРОФТИТТТТИИИЛЬ:" + data[1]);
        }

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        viewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        TextView usernameTextView = root.findViewById(R.id.textViewName);
        TextView usermailTextView = root.findViewById(R.id.textViewEmail);
        TextView passwordTextView = root.findViewById(R.id.textViewPhone);
        ImageView avatarImageView = root.findViewById(R.id.imageViewAvatar);
        LinearLayout userInformationLayout = root.findViewById(R.id.userInfo);
        LinearLayout additionalInfoLayout = root.findViewById(R.id.dopData);
        Button editProfileButton = root.findViewById(R.id.buttonEditProfile);
        int buttonWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
        int buttonHeight = ViewGroup.LayoutParams.WRAP_CONTENT;

        int xPosition = (int)(width * 0.35); // 50% ширины экрана
        int yPosition = (int)(height * 0.40); // 80% высоты экрана

        editProfileButton.setX(xPosition);
        editProfileButton.setY(yPosition);

        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(buttonWidth, buttonHeight);
        editProfileButton.setLayoutParams(buttonParams);
        // Динамическое задание весов макетов для масштабирования
        LinearLayout.LayoutParams avatarParams = (LinearLayout.LayoutParams) avatarImageView.getLayoutParams();
        //avatarParams.weight = 1;
        avatarImageView.setLayoutParams(avatarParams);

        LinearLayout.LayoutParams userInfoParams = (LinearLayout.LayoutParams) userInformationLayout.getLayoutParams();
        //userInfoParams.weight = 3;
        userInformationLayout.setLayoutParams(userInfoParams);

        usermailTextView.setText(data[0]);
        passwordTextView.setText(data[1]);

        // Получение размеров экрана пользователя
        Configuration configuration = getResources().getConfiguration();
        int screenLayout = configuration.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;


        // Автоматическое размещение кнопки в нижней части экрана
        if (screenLayout == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            // Если экран очень большой
            editProfileButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            editProfileButton.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        }

        //final TextView textView = binding.textHome;

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}