package com.example.mobile_tour.ui.right_bar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mobile_tour.R;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
        LinearLayout linearLayout = rootView.findViewById(R.id.LinearRightBar);
        // Получение экземпляра ViewModel
        rightBarViewModel = new ViewModelProvider(requireActivity()).get(RightBarViewModel.class);

        // Наблюдение за изменениями в категории
        rightBarViewModel.getSelectedCategory().observe(getViewLifecycleOwner(), category -> {
            categoryTitleTextView.setText("Категория: " + category);
        });

        // Наблюдение за изменениями в списке landmarks
        rightBarViewModel.getLandmarks().observe(getViewLifecycleOwner(), landmarksList -> {
            System.out.println("Количество в RIGHT_BAR: " + landmarksList.size());
            createLandmarkCards(linearLayout, landmarksList);
        });

        return rootView;
    }
    private void createLandmarkCards(LinearLayout linearLayout, List<Landmark> landmarksList) {
        linearLayout.removeAllViews(); // Очищаем существующие вью перед созданием новых

        int landmarksCount = landmarksList.size();
        int cardsPerRow = 2; // Количество карточек в одном ряду


        for (int i = 0; i <= landmarksCount+1; i += cardsPerRow) {
            // Создаем новую горизонтальную LinearLayout
            LinearLayout rowLayout = new LinearLayout(requireContext());
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            rowParams.setMargins(5, 10, 5, 16); // Отступ между рядами
            rowLayout.setLayoutParams(rowParams);

            // Добавляем карточки в текущий ряд
            for (int j = 0; j < cardsPerRow && (i + j) < landmarksCount; j++) {
                Landmark landmark = landmarksList.get(i + j);
                CardView cardView = createLandmarkCard(landmark);
                rowLayout.addView(cardView);
            }

            // Добавляем текущий ряд в основной LinearLayout
            linearLayout.addView(rowLayout);
        }
    }

    private CardView createLandmarkCard(Landmark landmark) {
        CardView cardView = new CardView(requireContext());

        // Настройка параметров CardView
        LinearLayout.LayoutParams cardViewParams = new LinearLayout.LayoutParams(500,600);
        cardViewParams.setMargins(16, 0, 16, 0); // Отступы между карточками
        cardView.setLayoutParams(cardViewParams);
        cardView.setBackgroundResource(R.drawable.rounded_corner_back);

        // Создание макета для элементов внутри CardView
        LinearLayout cardLayout = new LinearLayout(requireContext());
        cardLayout.setOrientation(LinearLayout.VERTICAL);

        // Создание изображения
        ImageView imageView = new ImageView(requireContext());
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                375
        );
        imageView.setLayoutParams(imageParams);
        imageView.setImageResource(landmark.getImage()); // Замените на ваш ресурс изображения

        // Создание TextView для заголовка
        TextView titleTextView = new TextView(requireContext());
        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        titleTextView.setLayoutParams(titleParams);
        titleTextView.setTextSize(18);
        titleTextView.setText(landmark.getTitle());

        // Создание TextView для категории
        TextView categoryTextView = new TextView(requireContext());
        LinearLayout.LayoutParams categoryParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        categoryTextView.setLayoutParams(categoryParams);
        categoryTextView.setText(landmark.getCategory());

        // Добавление всех элементов в макет
        cardLayout.addView(imageView);
        cardLayout.addView(titleTextView);
        cardLayout.addView(categoryTextView);
        cardView.addView(cardLayout);

        // Ваш код для обработки нажатия на карточку

        return cardView;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}