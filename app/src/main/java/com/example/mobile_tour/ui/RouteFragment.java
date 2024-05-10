package com.example.mobile_tour.ui;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.example.mobile_tour.DataBaseHelper;
import com.example.mobile_tour.R;
import com.example.mobile_tour.databinding.FragmentRouteBinding;
import com.example.mobile_tour.ui.home.Landmark;

import java.util.List;
import java.util.Random;

public class RouteFragment extends Fragment {

    private FragmentRouteBinding binding;
    private boolean isHidden = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentRouteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Typeface customTypeface = ResourcesCompat.getFont(requireContext(), R.font.gothampro);

        // Получаем объект ContentValues из аргументов
        Bundle args = getArguments();

        // Извлекаем данные из ContentValues
        String preferCat = args.getString("autoCompleteText");
        int minThumbValue = args.getInt("leftThumbValue");
        int maxThumbValue = args.getInt("rightThumbValue");
        boolean freeTravelChecked = args.getBoolean("freeTravelChecked");
        boolean transportChecked = args.getBoolean("transportChecked");
        boolean noMoneyMattersChecked = args.getBoolean("noMoneyMattersChecked");

        // Выводим полученные данные в лог для проверки
        Log.d("RouteParams", "Prefer category: " + preferCat);
        Log.d("RouteParams", "Минимум достопримечательностей: " + minThumbValue);
        Log.d("RouteParams", "Максимум достопримечательностей: " + maxThumbValue);
        Log.d("RouteParams", "Free Travel Checked: " + freeTravelChecked);
        Log.d("RouteParams", "Transport Checked: " + transportChecked);
        Log.d("RouteParams", "No Money Matters Checked: " + noMoneyMattersChecked);

        // Создаем объект базы данных
        DataBaseHelper dataBaseHelper = new DataBaseHelper(requireContext());

        // Получаем список достопримечательностей по выбранной категории
        List<Landmark> landmarks = dataBaseHelper.getLandmarksByCategory(preferCat);

        // Выбираем случайное количество достопримечательностей в диапазоне от minThumbValue до maxThumbValue
        int numOfLandmarks = Math.min(landmarks.size(), minThumbValue + new Random().nextInt(maxThumbValue - minThumbValue + 1));

        // Выводим информацию о выбранных достопримечательностях в лог
        Log.d("RouteLandmarks", "Selected landmarks count: " + numOfLandmarks);

        LinearLayout stationsLayout = root.findViewById(R.id.stationsLayout);
        LayoutInflater layoutInflater = LayoutInflater.from(requireContext());

        // Создаем станции и точки
        for (int i = 0; i < numOfLandmarks; i++) {
            Landmark landmark = landmarks.get(i);
            LinearLayout stationCopy = createStationCopy(layoutInflater, stationsLayout, landmark, i, numOfLandmarks);
            stationsLayout.addView(stationCopy);

            LinearLayout dotLayout = (LinearLayout) layoutInflater.inflate(R.layout.station_dot_layout, stationsLayout, false);
            dotLayout.setId(View.generateViewId());
            if (numOfLandmarks > 4 && i == 0) { // Проверяем, больше ли четырех станций и индекс равен 0
                TextView dotText = dotLayout.findViewById(R.id.dotText);
                dotText.setText("2 мин"); // Устанавливаем текст вместо "10 мин"
            } else if (i == numOfLandmarks - 1) { // Если это последняя станция
                TextView dotText = dotLayout.findViewById(R.id.dotText);
                dotText.setText("1 час"); // Устанавливаем текст вместо "1 час"
                dotLayout.setVisibility(View.GONE);
            }
            stationsLayout.addView(dotLayout);
        }

        Log.d("RouteParams", "Количество: " + numOfLandmarks);

        // Скрываем ненужные станции и точки, если их больше 4
        if (numOfLandmarks > 4) {
            // Проходим по всем станциям и точкам, начиная со второй и заканчивая предпоследней
            for (int i = 2; i < numOfLandmarks * 2 - 2; i++) {
                View view = stationsLayout.getChildAt(i);
                if (view != null) {
                    view.setVisibility(View.GONE); // Устанавливаем невидимость
                }
            }
        }

        // Добавляем кнопку для показа остальных станций, если их больше четырех
        if (numOfLandmarks > 4) {
            // Добавляем кнопку для показа остальных станций
            TextView showMoreText = new TextView(requireContext());
            showMoreText.setText("Показать все");
            showMoreText.setTextColor(Color.BLACK);
            showMoreText.setTypeface(customTypeface);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 45);
            params.setMargins(0, 0, 0, 35); // Отступ снизу
            showMoreText.setLayoutParams(params);
            showMoreText.setGravity(Gravity.CENTER); // Выравнивание текста по центру

            showMoreText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isHidden = !isHidden;
                    // Проходим по всем станциям и точкам, начиная со второй и заканчивая предпоследней
                    for (int i = 2; i < numOfLandmarks * 2 - 2; i++) {
                        View view = stationsLayout.getChildAt(i);
                        if (view != null) {
                            view.setVisibility(isHidden ? View.GONE : View.VISIBLE); // Устанавливаем видимость
                        }
                    }
                    TextView firstDotText = (TextView) stationsLayout.getChildAt(1).findViewById(R.id.dotText);
                    firstDotText.setText(isHidden ? "2 мин" : "10 мин"); // Изменяем текст при нажатии
                    showMoreText.setText(isHidden ? "Показать все" : "Скрыть");

                    // Обновляем параметры ScrollView

                }
            });
            stationsLayout.addView(showMoreText);
        }

        // Возвращаем макет
        return root;
    }

    private LinearLayout createStationCopy(LayoutInflater layoutInflater, LinearLayout parentLayout, Landmark landmark, int index, int numOfLandmarks) {
        // Создаем копию LinearLayout с id "station"
        LinearLayout stationCopy = (LinearLayout) layoutInflater.inflate(R.layout.station_layout, parentLayout, false);
        // Присваиваем уникальный id к копии
        stationCopy.setId(View.generateViewId());
        // Находим в этой копии элементы
        ImageView stationIcon = stationCopy.findViewById(R.id.stationIcon1);
        TextView stationName = stationCopy.findViewById(R.id.stationName1);
        // Устанавливаем данные в найденные элементы
        stationIcon.setImageResource(R.drawable.circle_point);
        stationName.setText(landmark.getTitle());
        // Устанавливаем Tag, чтобы позже можно было определить, с какой станцией мы работаем
        stationCopy.setTag("station_" + index);

        return stationCopy;
    }
}
