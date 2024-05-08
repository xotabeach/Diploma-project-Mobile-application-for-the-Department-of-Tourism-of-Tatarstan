package com.example.mobile_tour.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobile_tour.DataBaseHelper;
import com.example.mobile_tour.R;
import com.example.mobile_tour.databinding.FragmentRouteBinding;
import com.example.mobile_tour.ui.home.Landmark;


import java.util.List;
import java.util.Random;

import androidx.fragment.app.Fragment;

public class RouteFragment extends Fragment {

    private FragmentRouteBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentRouteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

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
        int numOfLandmarks;
        if (landmarks.size() < minThumbValue) {
            numOfLandmarks = landmarks.size(); // Если достопримечательностей меньше минимума, выбираем все
        } else if (landmarks.size() < maxThumbValue) {
            numOfLandmarks = landmarks.size(); // Если достопримечательностей меньше максимума, выбираем все
        } else {
            numOfLandmarks = minThumbValue + new Random().nextInt(maxThumbValue - minThumbValue + 1);
        }

        // Выводим информацию о выбранных достопримечательностях в лог
        Log.d("RouteLandmarks", "Selected landmarks count: " + numOfLandmarks);
        for (int i = 0; i < numOfLandmarks; i++) {
            Landmark landmark = landmarks.get(i);
            Log.d("RouteLandmark", "Name: " + landmark.getTitle()+ " CoordX:" + landmark.getCoordX());
        }

        // Возвращаем макет
        return root;
    }
}
