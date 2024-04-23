package com.example.mobile_tour.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.mobile_tour.R;
import com.example.mobile_tour.databinding.FragmentRouteBinding;


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
        Log.d("RouteParams", "AutoCompleteTextView: " + preferCat);
        Log.d("RouteParams", "SeekBar Value1: " + minThumbValue);
        Log.d("RouteParams", "SeekBar Value2: " + maxThumbValue);
        Log.d("RouteParams", "Free Travel Checked: " + freeTravelChecked);
        Log.d("RouteParams", "Transport Checked: " + transportChecked);
        Log.d("RouteParams", "No Money Matters Checked: " + noMoneyMattersChecked);

        // Здесь вы можете выполнить действия для отображения данных на вашем фрагменте

        // Например, заполнить соответствующие поля в вашем макете фрагмента

        // Возвращаем макет
        return root;
    }
}

