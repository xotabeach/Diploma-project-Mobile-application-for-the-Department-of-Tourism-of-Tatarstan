package com.example.mobile_tour.ui.question;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.widget.NestedScrollView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mobile_tour.R;
import com.example.mobile_tour.databinding.FragmentQuestionBinding;

public class QuestionFragment extends Fragment {

    private FragmentQuestionBinding binding;
    private LinearLayout faqCardHolder;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentQuestionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        faqCardHolder = root.findViewById(R.id.faqCardHolder);
        LinearLayout linearLayoutSearch = requireActivity().findViewById(R.id.linearLayoutSearch);
        linearLayoutSearch.setVisibility(View.GONE);
        ScrollView scrollView = root.findViewById(R.id.scrollView);

// Включим скроллинг для ScrollView
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

// Установим стиль полосы прокрутки
        scrollView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        String[] texts = {
                "Как сменить язык?",
                "Как создать маршрут?",
                "Я не могу создавать маршрут",
                "Как сменить данные профиля?"
        };


        createCardView(texts);

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void createCardView(String[] texts) {
        for (int i = 0; i < texts.length; i++) {
            final int cardIndex = i; // Идентификатор карточки

            // Создание макета для карточки и ее содержимого
            FrameLayout cardContainer = new FrameLayout(requireContext());
            LinearLayout.LayoutParams cardContainerParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            cardContainerParams.setMargins(22, -3, 22, 0);
            cardContainer.setLayoutParams(cardContainerParams);

            final Boolean[] icon_state = {true};
            // Создание карточки
            CardView cardView = new CardView(requireContext());
            FrameLayout.LayoutParams cardParams = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
            );
            cardParams.gravity = Gravity.TOP;
            cardView.setLayoutParams(cardParams);
            cardView.setCardBackgroundColor(Color.WHITE);
            cardView.setCardElevation(10);
            cardView.setUseCompatPadding(true);
            cardView.setRadius(70);
            cardView.setTranslationZ(1); // Установка z для карточки выше, чем у FrameLayout

            // Создание макета для элементов внутри карточки
            LinearLayout innerLayout = new LinearLayout(requireContext());
            innerLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            innerLayout.setOrientation(LinearLayout.HORIZONTAL);
            innerLayout.setGravity(Gravity.CENTER_VERTICAL | Gravity.END); // Выравнивание по вертикали и прижатие к правому краю

            // Создание текстового представления
            TextView textView = new TextView(requireContext());
            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1
            );
            textParams.setMargins(55, 65, 0, 65); // Отступы
            textView.setLayoutParams(textParams);
            textView.setTextSize(16);
            Typeface customTypeface = ResourcesCompat.getFont(requireContext(), R.font.gothampro);
            textView.setTypeface(customTypeface);
            textView.setText(texts[i]); // Установка текста из массива

            // Создание иконки "+"
            ImageView plusIcon = new ImageView(requireContext());
            LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(70, 70);
            iconParams.setMargins(0, 15, 55, 15);
            plusIcon.setLayoutParams(iconParams);
            plusIcon.setImageResource(R.drawable.ic_plus);
            plusIcon.setPadding(1, 1, 1, 1);

            // Создание вложенного FrameLayout
            FrameLayout nestedFrameLayout = new FrameLayout(requireContext());
            FrameLayout.LayoutParams nestedParams = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
            );
            nestedParams.setMargins(25, 29, 25, 15); // Увеличение маргина сверху и слева, уменьшение снизу
            nestedParams.gravity = Gravity.BOTTOM; // Расположение внизу
            nestedFrameLayout.setBackgroundResource(R.drawable.one_more_corner_back);
            nestedFrameLayout.setLayoutParams(nestedParams);
            nestedFrameLayout.setVisibility(View.GONE); // Начально скрываем вложенный FrameLayout
            TextView nestedTextView = new TextView(requireContext());
            FrameLayout.LayoutParams nestedTextParams = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
            );
            nestedTextView.setTextSize(15);
            nestedTextView.setTypeface(customTypeface);
            nestedTextParams.setMargins(55, 175, 55, 45);
            nestedTextParams.gravity = Gravity.BOTTOM; // Текст прижимается к низу
            nestedTextView.setLayoutParams(nestedTextParams);
            nestedTextView.setText("Для того, чтобы сменить данные в вашем профиле, перейдите в раздел Профиль и выберете пункт меню.\n" +
                    "После этого не забудьте нажать кнопку сохранить, чтобы ваши новые данные отобразились у нас");

// Установка параметра междустрочного интервала
            float lineSpacingExtra = 8f; // Пример значения междустрочного интервала
            nestedTextView.setLineSpacing(lineSpacingExtra, 1.0f); // 1.0f - масштаб междустрочного интервала

            nestedFrameLayout.addView(nestedTextView);


// Добавление всех элементов во вложенный FrameLayout
            cardContainer.addView(nestedFrameLayout); // Добавление вложенного FrameLayout в контейнер карточки


            innerLayout.addView(textView);
            innerLayout.addView(plusIcon);
            cardView.addView(innerLayout);
            cardContainer.addView(cardView); // Добавление карточки в контейнер
            faqCardHolder.addView(cardContainer); // Добавление контейнера карточки в общий контейнер

            // Обработчик нажатия на иконку
            plusIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Вывод уникального номера карточки
                    //Toast.makeText(requireContext(), "Нажата иконка на карточке №" + (cardIndex + 1), Toast.LENGTH_SHORT).show();
                    if (icon_state[0]){
                        nestedFrameLayout.setVisibility(View.VISIBLE);
                        plusIcon.setImageResource(R.drawable.ic_minus);
                        icon_state[0] = false;
                    }

                    else
                    {
                        nestedFrameLayout.setVisibility(View.GONE);
                        plusIcon.setImageResource(R.drawable.ic_plus);
                        icon_state[0] = true;
                    }



                }
            });
        }
    }






}
