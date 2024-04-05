package com.example.mobile_tour.ui.create_route;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mobile_tour.DataBaseHelper;
import com.example.mobile_tour.R;
import com.example.mobile_tour.databinding.FragmentCreateRouteBinding;


import com.example.mobile_tour.ui.ClickedTravelData;
import com.example.mobile_tour.ui.SharedViewModel;
import com.example.mobile_tour.ui.home.ClickedLocationDialog;
import com.example.mobile_tour.ui.home.TravelLocation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Create_routeFragment extends Fragment{

    private FragmentCreateRouteBinding binding;

    private TextView textyourchoise;

    private void handleDeleteAllButtonClick() {
        // Вызовите метод для удаления всех данных из базы данных
        DataBaseHelper dbHelper = new DataBaseHelper(requireContext());
        dbHelper.dropClickedLandmarksTable();
        dbHelper.getAllClickedLandmarks();
        // После удаления всех данных, обновите отображение, создав новые карточки
        LinearLayout linearLayout = requireView().findViewById(R.id.LinearCreate);
        linearLayout.removeAllViews(); // Очистите текущее содержимое

        // Получите обновленные данные, если они изменились с последнего вызова
        Create_routeViewModel createrouteViewModel =
                new ViewModelProvider(this).get(Create_routeViewModel.class);
        List<ClickedTravelData> clickedLandscapes = createrouteViewModel.loadClickedLandMarksFromDatabase(dbHelper);

        // Создайте новые карточки
        createCards(linearLayout, clickedLandscapes);
    }

    private void handleDeleteButtonClick(ClickedTravelData clickedLandmark) {

        DataBaseHelper dbHelper = new DataBaseHelper(requireContext());
        dbHelper.deleteClickedDataByTitle(clickedLandmark.getTitle());

        LinearLayout linearLayout = requireView().findViewById(R.id.LinearCreate);
        linearLayout.removeAllViews(); // Очистите текущее содержимое


        Create_routeViewModel createrouteViewModel =
                new ViewModelProvider(this).get(Create_routeViewModel.class);
        List<ClickedTravelData> clickedLandscapes = createrouteViewModel.loadClickedLandMarksFromDatabase(dbHelper);


        createCards(linearLayout, clickedLandscapes);
    }

    public void createCards(LinearLayout linearLayout, List<ClickedTravelData> clickedLandscapes){
        for (ClickedTravelData clickedLandmark : clickedLandscapes) {
            // Создание новой CardView
            CardView cardView = new CardView(requireContext());

            // Настройка параметров CardView
            LinearLayout.LayoutParams cardViewParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            cardViewParams.setMargins(25, 25, 25, 25); // Установите нужные отступы
            cardView.setLayoutParams(cardViewParams);
            cardView.setBackgroundResource(R.drawable.rounded_corner_back);
            // Создание макета для элементов внутри CardView
            LinearLayout cardLayout = new LinearLayout(requireContext());
            cardLayout.setOrientation(LinearLayout.HORIZONTAL);

            // Создание изображения
            ImageView imageView = new ImageView(requireContext());
            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(
                    250,
                    250
            );
            imageParams.setMargins(30, 10, 0, 0); // Установите нужные отступы
            imageView.setLayoutParams(imageParams);
            imageView.setImageResource(clickedLandmark.getImageUrl()); // Замените на ваш ресурс изображения

            // Создание макета для текстов
            LinearLayout textLayout = new LinearLayout(requireContext());
            textLayout.setOrientation(LinearLayout.VERTICAL);

            // Создание TextView для заголовка
            TextView titleTextView = new TextView(requireContext());
            LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(500,70);



            titleParams.setMargins(40, 35, 0, 0); // Установите нужные отступы
            titleTextView.setLayoutParams(titleParams);
            titleTextView.setTextSize(18);
            titleTextView.setText(clickedLandmark.getTitle());

            // Создание TextView для категории
            TextView categoryTextView = new TextView(requireContext());
            LinearLayout.LayoutParams categoryParams = new LinearLayout.LayoutParams(500,50);

            categoryParams.setMargins(40, 10, 0, 0); // Установите нужные отступы
            categoryTextView.setLayoutParams(categoryParams);
            categoryTextView.setText(clickedLandmark.getCategory());

            // Создание кнопки удаления
            AppCompatButton deleteButton = new AppCompatButton(requireContext());
            LinearLayout.LayoutParams deleteButtonParams = new LinearLayout.LayoutParams(
                    95,
                    95
            );
            deleteButtonParams.setMargins(50, 80, 10, 0);
            ConstraintLayout.LayoutParams constraintLayoutParams = new ConstraintLayout.LayoutParams(deleteButtonParams);
            constraintLayoutParams.endToEnd = R.id.LinearCreate;
            deleteButton.setLayoutParams(deleteButtonParams);
            deleteButton.setBackgroundResource(R.drawable.delete_button_style2);
            deleteButton.setForeground(ContextCompat.getDrawable(requireContext(), R.drawable.delete));
            deleteButton.setForegroundGravity(Gravity.CENTER);
            deleteButton.setPadding(0, 0, 0, 0);

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    handleDeleteButtonClick(clickedLandmark);
                }
            });


            // Добавление всех элементов в макет
            cardLayout.addView(imageView);
            textLayout.addView(titleTextView);
            textLayout.addView(categoryTextView);
            cardLayout.addView(textLayout);
            cardLayout.addView(deleteButton);
            cardView.addView(cardLayout);

            // Добавление CardView в LinearLayout
            linearLayout.addView(cardView);
        }



    }

    private LinearLayout linearLayoutSearch;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Create_routeViewModel createrouteViewModel =
                new ViewModelProvider(this).get(Create_routeViewModel.class);
        Bundle bundle = getArguments();

        DataBaseHelper dbHelper = new DataBaseHelper(getContext()); // Вам может потребоваться другой способ получения контекста

        List<ClickedTravelData> clickedLandscapes = createrouteViewModel.loadClickedLandMarksFromDatabase(dbHelper);

        binding = FragmentCreateRouteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNotifications;
        final LinearLayout linearLayout = root.findViewById(R.id.LinearCreate);
        final AppCompatButton deleteAllButton = root.findViewById(R.id.button_delete_all);
        textyourchoise = root.findViewById(R.id.textyourchoise);
        linearLayoutSearch = requireActivity().findViewById(R.id.linearLayoutSearch);
        linearLayoutSearch.setVisibility(View.GONE);

        if (clickedLandscapes.size() == 0) {
            textView.setText("Создание маршрута");
            deleteAllButton.setVisibility(View.GONE); // Скрыть кнопку удаления всех элементов
        } else {
            createrouteViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
            createCards(linearLayout, clickedLandscapes);
            textyourchoise.setVisibility(View.INVISIBLE);
            // Установка слушателя нажатия на кнопку удаления всех элементов
            deleteAllButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleDeleteAllButtonClick();
                }
            });
        }

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        linearLayoutSearch.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        linearLayoutSearch.setVisibility(View.GONE);
        // Очистите текущее содержимое, например, удалите все дочерние элементы из LinearLayout
        LinearLayout linearLayout = requireView().findViewById(R.id.LinearCreate);
        linearLayout.removeAllViews();

        // Получите обновленные данные, если они изменились с последнего вызова
        Create_routeViewModel createrouteViewModel =
                new ViewModelProvider(this).get(Create_routeViewModel.class);
        DataBaseHelper dbHelper = new DataBaseHelper(getContext());
        List<ClickedTravelData> clickedLandscapes = createrouteViewModel.loadClickedLandMarksFromDatabase(dbHelper);

        // Создайте новые карточки
        createCards(linearLayout, clickedLandscapes);
    }

}