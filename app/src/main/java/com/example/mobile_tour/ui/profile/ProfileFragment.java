package com.example.mobile_tour.ui.profile;

import static android.app.Activity.RESULT_OK;
import static androidx.fragment.app.FragmentManager.TAG;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mobile_tour.DataBaseHelper;
import com.example.mobile_tour.MainActivity;
import com.example.mobile_tour.R;
import com.example.mobile_tour.databinding.FragmentProfileBinding;

import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ProfileFragment extends Fragment {


    private View.OnClickListener editButtonClickListener;
    private FragmentProfileBinding binding;

    private int height;

    private boolean shakeEnabled = false;

    private void setShakeEnabled(boolean enabled) {
        shakeEnabled = enabled;
    }
    private boolean scrollstate;
    private int initialProfileLayoutHeight;
    private ImageView avatarImageView;
    private String[] data;

    private ImageView location_icon;
    private ProfileViewModel viewModel;

    private LinearLayout profileTitle;
    private ImageView editButton;

    AutoCompleteTextView autoCompleteTextView;
    private ImageView confirmButton;
    private CardView imageCard;
    private ImageView imageViewAvatar;
    private ImageView imageViewChangeAvatar;
    private ImageView buttonDeleteAvatar;
    private CardView cityCard;
    private TextView textViewCity;
    private CardView nameCard;
    private TextView textViewName;
    private CardView mailCard;
    private TextView textViewMail;
    private CardView aboutCard;
    private TextView textViewTitle;
    private ImageView editButton_About;
    private CardView routesCard;
    private CardView cardViewRoutes;
    private ImageView imageRoutes;
    private TextView textViewRoutesDescr;
    private TextView textViewRoutes;


    private TextView textViewHello;


    ContentValues values = new ContentValues();

    private void setTextFieldStyle(TextView textView) {
        textView.setBackground(getResources().getDrawable(R.drawable.rounded_corner)); // Устанавливаем рамку
        textView.setPadding(10, 5, 10, 5); // Устанавливаем отступы внутри текстового поля
        textView.setInputType(InputType.TYPE_CLASS_TEXT); // Устанавливаем тип ввода
    }




    private void moveProfileLayoutUpWithoutAnimation(LinearLayout layout, float k) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) layout.getLayoutParams();
        int currentTopMargin = params.topMargin;
        int newTopMargin = (int) (currentTopMargin - height * k); // Уменьшаем маргин на k*100% высоты экрана
        params.topMargin = newTopMargin;
        layout.setLayoutParams(params);
    }




    private String[] getAllCities() {
        String[] cities = {
                // Российские города
                "Москва", "Санкт-Петербург", "Новосибирск", "Екатеринбург", "Нижний Новгород", "Казань",
                "Челябинск", "Омск", "Самара", "Ростов-на-Дону", "Уфа", "Красноярск", "Пермь", "Воронеж",
                "Волгоград", "Краснодар", "Саратов", "Тюмень", "Тольятти", "Ижевск", "Барнаул", "Ульяновск",
                "Иркутск", "Хабаровск", "Ярославль", "Владивосток", "Махачкала", "Томск", "Оренбург", "Кемерово",
                // Китайские города (переведенные на русский)
                "Пекин", "Шанхай", "Гуанчжоу", "Шэньчжэнь", "Тяньцзинь", "Хунчун", "Чунцин", "Чэнду", "Чанчжоу",
                "Сучжоу", "Хэфэй", "Нанкин", "Ухань", "Чанчунь", "Харбин",
                // Украинские города
                "Киев", "Харьков", "Одесса", "Днепр", "Донецк"
        };
        return cities;
    }



    private boolean isViewInBounds(View view, int x, int y) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int viewX = location[0];
        int viewY = location[1];
        int viewWidth = view.getWidth();
        int viewHeight = view.getHeight();
        return (x >= viewX && x <= (viewX + viewWidth) && y >= viewY && y <= (viewY + viewHeight));
    }


    @SuppressLint({"RestrictedApi", "ClickableViewAccessibility"})
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        MainActivity mainActivity = (MainActivity) getActivity();

        DataBaseHelper dbHelper = new DataBaseHelper(requireContext());

        if (mainActivity != null) {
            data = mainActivity.getMyName();
            System.out.println("ПРОФТИТТТТИИИЛЬ:" + data[0]);
            System.out.println("ПРОФТИТТТТТТИИЛЬ:" + data[1]);
        }

        scrollstate = false;
        String[] userData = dbHelper.getUserDataByEmail(data[0]);



        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        viewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);

        LinearLayout linearLayoutSearch = requireActivity().findViewById(R.id.linearLayoutSearch);
        linearLayoutSearch.setVisibility(View.VISIBLE);

        if (userData != null) {
            // Используйте полученные данные о пользователе здесь, например:
            String email = userData[1];
            String name = userData[2];
            String password = userData[3];
            String image = userData[4];
            String city = userData[6];
            String routes = userData[5];
            // Другие поля, если есть

            Log.d(TAG, "Email: " + email + ", Name: " + name + ", Password: " + password + ", Image " + image+ ", City: " + city+ ", Routes: " + routes);
        } else {
            // Пользователь с указанным адресом электронной почты не найден
            Log.d(TAG, "Пользователь с указанным адресом электронной почты не найден");
        }




        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        // TextView
        profileTitle = root.findViewById(R.id.profileTitle);
        textViewCity = root.findViewById(R.id.textViewCity);
        textViewName = root.findViewById(R.id.textViewName);
        textViewMail = root.findViewById(R.id.textViewMail);
        textViewTitle = root.findViewById(R.id.textViewTitle);
        textViewRoutesDescr = root.findViewById(R.id.textViewRoutesDescr);
        textViewRoutes = root.findViewById(R.id.textViewRoutes);
        textViewHello = root.findViewById(R.id.textHello);

// CardView
        imageCard = root.findViewById(R.id.imageCard);
        cityCard = root.findViewById(R.id.cityCard);
        nameCard = root.findViewById(R.id.nameCard);
        mailCard = root.findViewById(R.id.mailCard);
        aboutCard = root.findViewById(R.id.aboutCard);
        routesCard = root.findViewById(R.id.routesCard);

// ImageView
        imageViewAvatar = root.findViewById(R.id.imageViewAvatar);
        location_icon = root.findViewById(R.id.location_icon);
        imageViewChangeAvatar = root.findViewById(R.id.imageViewChangeAvatar);
        buttonDeleteAvatar = root.findViewById(R.id.buttonDeleteAvatar);
        imageRoutes = root.findViewById(R.id.imageRoutes);
        avatarImageView = root.findViewById(R.id.imageViewAvatar);

// Button
        editButton = root.findViewById(R.id.editButton);
        confirmButton = root.findViewById(R.id.confirmButton);
        editButton_About = root.findViewById(R.id.editButton_About);

        LinearLayout userInformationLayout = root.findViewById(R.id.userInfo);


        textViewMail.setEnabled(true); // Делаем textViewMail кликабельным


        confirmButton.setVisibility(View.GONE);

        imageCard.setEnabled(false);
        imageViewChangeAvatar.setEnabled(false);
        buttonDeleteAvatar.setEnabled(false);
        cityCard.setEnabled(false);
        nameCard.setEnabled(false);
        mailCard.setEnabled(false);
        aboutCard.setEnabled(false);
        routesCard.setEnabled(false);
        profileTitle.setEnabled(true);

        int buttonWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
        int buttonHeight = ViewGroup.LayoutParams.WRAP_CONTENT;


        //moveProfileLayoutUpWithoutAnimation(imageButtonLayout,0.25F);

// Проверка, есть ли данные о пользователе



        int xPosition = (int)(width * 0.15); // 50% ширины экрана
        int yPosition = (int)(height * 0.20); // 80% высоты экрана

        textViewRoutesDescr.setShadowLayer(1, 0, 0, Color.GRAY);




        // Динамическое задание весов макетов для масштабирования




        if (userData[2] != "") {
            String name = userData[2];
            if (Character.isLowerCase(name.charAt(0))) { // Проверяем, начинается ли строка с маленькой буквы
                name = Character.toUpperCase(name.charAt(0)) + name.substring(1); // Преобразуем первую букву в заглавную
            }
            textViewName.setText(name);
            textViewHello.setText("Привет, " + name);
        }

        else
            textViewName.setText("Неизвестно");
        if(!userData[4].equals("0"))
            avatarImageView.setImageURI(Uri.parse(userData[4]));
        else
            avatarImageView.setImageResource(R.drawable.default_avatar);
        textViewRoutes.setText(userData[5]);
        if(userData[6] != null)
            textViewCity.setText(userData[6]);
        else
            textViewCity.setText("Не указан");

        if(userData[1] != null)
            textViewMail.setText(userData[1]);
        else
            textViewMail.setText("Не указан");




        Configuration configuration = getResources().getConfiguration();
        int screenLayout = configuration.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;






        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shakeEnabled = !shakeEnabled;

                if (shakeEnabled) {
                    // Включаем режим редактирования
                    confirmButton.setVisibility(View.VISIBLE);

                    imageCard.setEnabled(true);
                    imageViewChangeAvatar.setEnabled(true);
                    buttonDeleteAvatar.setEnabled(true);
                    cityCard.setEnabled(true);
                    nameCard.setEnabled(true);
                    mailCard.setEnabled(true);
                    aboutCard.setEnabled(true);
                    routesCard.setEnabled(true);

                    // Если тряска включена, запускаем анимацию для каждого CardView
                    shakeView(imageCard, -2, 2, -2, 2);
                    shakeView(cityCard, 2, 2, -2, 2);
                    shakeView(nameCard, 2, -2, 2, -2);
                    shakeView(mailCard, -3, 3, 3, -3);
                    shakeView(aboutCard, -3, 3, -3, 3);

                    // Заменяем TextView на AutoCompleteTextView
                    location_icon.setVisibility(View.GONE);
                    textViewCity.setVisibility(View.GONE);
                    autoCompleteTextView = new AutoCompleteTextView(requireContext());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.setMargins(25, 0, 5, 0); // Отступы слева и справа
                    autoCompleteTextView.setLayoutParams(params);
                    autoCompleteTextView.setText("");
                    autoCompleteTextView.setThreshold(1);
                    autoCompleteTextView.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, getAllCities()));
                    autoCompleteTextView.setSingleLine(true);
                    cityCard.addView(autoCompleteTextView);

                    // Растягиваем cityCard до правого края

                } else {
                    // Выключаем режим редактирования
                    confirmButton.setVisibility(View.GONE);
                    location_icon.setVisibility(View.VISIBLE);
                    imageCard.setEnabled(false);
                    imageViewChangeAvatar.setEnabled(false);
                    buttonDeleteAvatar.setEnabled(false);
                    cityCard.setEnabled(false);
                    nameCard.setEnabled(false);
                    mailCard.setEnabled(false);
                    aboutCard.setEnabled(false);
                    routesCard.setEnabled(false);

                    imageCard.clearAnimation();
                    cityCard.clearAnimation();
                    nameCard.clearAnimation();
                    mailCard.clearAnimation();
                    aboutCard.clearAnimation();

                    // Удаляем AutoCompleteTextView и возвращаем TextView
                    cityCard.removeViewAt(cityCard.getChildCount() - 1);
                    textViewCity.setVisibility(View.VISIBLE);



                }
            }

            private void shakeView(View view, float fromXDelta, float toXDelta, float fromYDelta, float toYDelta) {
                TranslateAnimation shake = new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta);
                shake.setDuration(100);
                shake.setInterpolator(new DecelerateInterpolator());
                shake.setRepeatCount(Animation.INFINITE);
                shake.setRepeatMode(Animation.REVERSE);
                view.startAnimation(shake);
            }
        });



        cityCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Разрешаем редактирование текста в поле города
                textViewCity.setEnabled(true);
                // После нажатия устанавливаем фокус на поле, чтобы пользователь мог сразу вводить текст
                textViewCity.requestFocus();
            }
        });



        textViewCity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {


                    textViewCity.setEnabled(false);
                }
            }
        });




        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newCity = autoCompleteTextView.getText().toString();
                // Выводим текст в консоль
                Log.d("New City", newCity);

                // Удаляем AutoCompleteTextView и возвращаем TextView
                cityCard.removeViewAt(cityCard.getChildCount() - 1);
                textViewCity.setVisibility(View.VISIBLE);
                textViewCity.setText(newCity);
                if(!newCity.equals(""))
                {
                    values.put("city", newCity);
                }
                else{
                    textViewCity.setText(userData[6]);
                }
                if (values.size() > 0) {
                    // Здесь вызываем метод из базы данных для сохранения изменений

                    // Получаем новое значение города из AutoCompleteTextView, если он был отображен


                    // Помещаем новое значение города в ContentValues


                    // Вызываем метод из базы данных для сохранения изменений
                    boolean result = dbHelper.updateUserData(values, userData[1]);
                    if (result) {
                        // Успешное обновление данных в базе данных
                        Toast.makeText(requireContext(), "Данные успешно обновлены", Toast.LENGTH_SHORT).show();
                    } else {
                        // Что-то пошло не так при обновлении данных
                        Toast.makeText(requireContext(), "Ошибка при обновлении данных", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Выводим сообщение об ошибке, если values пустой
                    Toast.makeText(requireContext(), "Нет данных для обновления", Toast.LENGTH_SHORT).show();
                }


                // После завершения обновления данных выключаем редактирование
                confirmButton.setVisibility(View.GONE);
                location_icon.setVisibility(View.VISIBLE);
                //textViewCity.setVisibility(View.VISIBLE);
                imageCard.setEnabled(false);
                imageViewChangeAvatar.setEnabled(false);
                buttonDeleteAvatar.setEnabled(false);
                cityCard.setEnabled(false);
                nameCard.setEnabled(false);
                mailCard.setEnabled(false);
                aboutCard.setEnabled(false);
                routesCard.setEnabled(false);

                // Остановка анимации, если она была включена
                imageCard.clearAnimation();
                cityCard.clearAnimation();
                nameCard.clearAnimation();
                mailCard.clearAnimation();
                aboutCard.clearAnimation();
            }
        });



// Обработчик события клика вне области cityCard
        root.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Проверяем, был ли клик вне области cityCard
                    if (!isViewInBounds(cityCard, (int) event.getRawX(), (int) event.getRawY())) {
                        // Принимаем текст из textViewCity
                        String newCity = textViewCity.getText().toString();
                        // Вызываем метод для сохранения текста

                        // Скрываем клавиатуру
                        InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(textViewCity.getWindowToken(), 0);

                    }
                }
                return false;
            }
        });



        textViewMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Получаем текст из TextView
                String mailText = textViewMail.getText().toString();
                // Копируем текст в буфер обмена
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Email", mailText);
                if (clipboard != null) {
                    clipboard.setPrimaryClip(clip);
                    // Выводим сообщение об успешном копировании
                    Toast.makeText(getActivity(), "Email скопирован в буфер обмена", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Обработчик события для кнопки смены аватарки
        imageViewChangeAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 1);
            }
        });



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        scrollstate = false;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            if (data != null) {
                Uri selectedImage = data.getData();
                // Преобразуйте URI изображения в строку и сохраните его в базу данных или в другое место хранения
                String imagePath = selectedImage.toString();
                // Далее обновите отображаемое изображение
                //avatarImageView.setImageURI(selectedImage);

                // Используйте ContentResolver для загрузки изображения из URI
                try {
                    InputStream inputStream = getActivity().getContentResolver().openInputStream(selectedImage);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    avatarImageView.setImageBitmap(bitmap);
                    values.put("image", imagePath); // Если вам нужно сохранить путь к изображению
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
