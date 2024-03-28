package com.example.mobile_tour.ui.profile;

import static androidx.fragment.app.FragmentManager.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mobile_tour.DataBaseHelper;
import com.example.mobile_tour.MainActivity;
import com.example.mobile_tour.R;
import com.example.mobile_tour.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;


    private String[] data;
    private ProfileViewModel viewModel;

    @SuppressLint("RestrictedApi")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        MainActivity mainActivity = (MainActivity) getActivity();

        DataBaseHelper dbHelper = new DataBaseHelper(requireContext());

        if (mainActivity != null) {
            data = mainActivity.getMyName();
            System.out.println("ПРОФТИТТТТИИИЛЬ:" + data[0]);
            System.out.println("ПРОФТИТТТТТТИИЛЬ:" + data[1]);
        }


        String[] userData = dbHelper.getUserDataByEmail(data[0]);



        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        viewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);

        if (userData != null) {
            // Используйте полученные данные о пользователе здесь, например:
            String email = userData[1];
            String name = userData[2];
            String password = userData[3];
            // Другие поля, если есть

            Log.d(TAG, "Email: " + email + ", Name: " + name + ", Password: " + password);
        } else {
            // Пользователь с указанным адресом электронной почты не найден
            Log.d(TAG, "Пользователь с указанным адресом электронной почты не найден");
        }


        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        TextView usernameTextView = root.findViewById(R.id.textViewName);
        TextView usermailTextView = root.findViewById(R.id.textViewEmail);
        TextView passwordTextView = root.findViewById(R.id.textViewPassword);
        ImageView avatarImageView = root.findViewById(R.id.imageViewAvatar);
        LinearLayout userInformationLayout = root.findViewById(R.id.userInfo);
        LinearLayout additionalInfoLayout = root.findViewById(R.id.dopData);
        Button editProfileButton = root.findViewById(R.id.buttonEditProfile);
        Button changeAvatarButton = root.findViewById(R.id.buttonChangeAvatar);
        Button togglePasswordVisibilityButton = root.findViewById(R.id.buttonTogglePasswordVisibility);


        // Скрываем пароль при начальной загрузке
        passwordTextView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        // Устанавливаем размер шрифта
        passwordTextView.setTextSize(16);



        int buttonWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
        int buttonHeight = ViewGroup.LayoutParams.WRAP_CONTENT;




// Проверка, есть ли данные о пользователе



        int xPosition = (int)(width * 0.15); // 50% ширины экрана
        int yPosition = (int)(height * 0.20); // 80% высоты экрана

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

        if(userData[2] != "")
            usernameTextView.setText(userData[2]);
        else
            usernameTextView.setText("Неизвестно");
        if(!userData[4].equals("0"))
            avatarImageView.setImageAlpha(Integer.parseInt(userData[4]));
        else
            avatarImageView.setImageResource(R.drawable.default_avatar);
        usermailTextView.setText(userData[1]);
        passwordTextView.setText(userData[3]);

        // Получение размеров экрана пользователя
        Configuration configuration = getResources().getConfiguration();
        int screenLayout = configuration.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;

        // Обработчик события для кнопки смены аватарки
        changeAvatarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Здесь нужно добавить код для открытия галереи и выбора новой фотографии
                // После выбора фотографии необходимо заменить текущую картинку на выбранную
            }
        });

        // Обработчик события для кнопки смены видимости пароля
        togglePasswordVisibilityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (passwordTextView.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {

                    passwordTextView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordTextView.setTextSize(16);
                    togglePasswordVisibilityButton.setText("Скрыть пароль");
                } else {

                    passwordTextView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordTextView.setTextSize(16);
                    togglePasswordVisibilityButton.setText("Показать пароль");
                }

            }
        });



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
