package com.example.mobile_tour.ui;

import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.mobile_tour.DataBaseHelper;
import com.example.mobile_tour.R;
import com.example.mobile_tour.databinding.FragmentCreateRouteParamsBinding;
import com.example.mobile_tour.ui.home.TravelCategory;
import com.example.mobile_tour.ui.home.TravelCategoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class CreateRouteParamsFragment extends Fragment {

    private FragmentCreateRouteParamsBinding binding;

    private TextView text_nameapp;
    private TextView textViewTitle;
    private TextView textViewGroup1Title;
    private CardView seekBarCard;
    private RangeSeekBar seekBar;
    private TextView textViewGroup2Title;
    private CardView group2Card;
    private AutoCompleteTextView autoCompleteTextView;
    private CardView autocompleteCard;
    private TextView textViewLeft;
    private TextView textViewRight;
    private LinearLayout linearLayout;
    private CardView group3;
    private CheckBox checkBoxFreeTravel;
    private CheckBox checkBoxTransport;
    private CheckBox checkBoxNoMoneyMatters;
    private CardView buttonCreateRoute;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCreateRouteParamsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        DataBaseHelper dataBaseHelper = new DataBaseHelper(requireContext());

        List<TravelCategory> categoryList = dataBaseHelper.getAllCategories();

        List<String> catStringList = new ArrayList<>();
        for (TravelCategory category : categoryList) {
            catStringList.add(category.toString());
        }
        // Выводим категории в консоль
        for (TravelCategory category : categoryList) {
            Log.d("Category", category.getTitle());
        }

        // Получаем ссылки на все элементы из XML
        text_nameapp = root.findViewById(R.id.text_nameapp);
        textViewTitle = root.findViewById(R.id.textViewTitle);
        textViewGroup1Title = root.findViewById(R.id.textViewGroup1Title);
        seekBarCard = root.findViewById(R.id.seekBarCard);
        seekBar = root.findViewById(R.id.seekBar);
        textViewGroup2Title = root.findViewById(R.id.textViewGroup2Title);
        group2Card = root.findViewById(R.id.group2Card);
        autoCompleteTextView = root.findViewById(R.id.autoCompleteTextView);
        autocompleteCard = root.findViewById(R.id.autocompleteCard);
        textViewLeft = root.findViewById(R.id.textViewLeft);
        textViewRight = root.findViewById(R.id.textViewRight);
        group3 = root.findViewById(R.id.group3);
        checkBoxFreeTravel = root.findViewById(R.id.checkBoxFreeTravel);
        checkBoxTransport = root.findViewById(R.id.checkBoxTransport);
        checkBoxNoMoneyMatters = root.findViewById(R.id.checkBoxNoMoneyMatters);
        buttonCreateRoute = root.findViewById(R.id.buttonCreateRoute);

        // Создаем адаптер для автоподстановки текста

        autoCompleteTextView.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, catStringList));

        autoCompleteTextView.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                autoCompleteTextView.setText("");
                autoCompleteTextView.setTextSize(14); // Установка размера шрифта 14
                autoCompleteTextView.setTextColor(getResources().getColor(android.R.color.black)); // Установка цвета текста черным
            } else {
                autoCompleteTextView.setTextSize(12); // Установка размера шрифта 12
                autoCompleteTextView.setTextColor(getResources().getColor(R.color.gray_text)); // Установка цвета текста #535353
            }
        });

        autoCompleteTextView.setText("Например: кафе");

        // Добавляем обработчик нажатия на кнопку buttonCreateRoute
        buttonCreateRoute.setOnClickListener(v -> {
            // Получаем значения со всех элементов
            String autoCompleteText = autoCompleteTextView.getText().toString();
            int[] thumbValues = seekBar.getThumbValues();
            int leftThumbValue = thumbValues[0];
            int rightThumbValue = thumbValues[1];
            boolean freeTravelChecked = checkBoxFreeTravel.isChecked();
            boolean transportChecked = checkBoxTransport.isChecked();
            boolean noMoneyMattersChecked = checkBoxNoMoneyMatters.isChecked();

            // Выводим полученные данные в лог
            Log.d("RouteParams", "AutoCompleteTextView: " + autoCompleteText);
            Log.d("RouteParams", "SeekBar Value1: " + leftThumbValue);
            Log.d("RouteParams", "SeekBar Value2: " + rightThumbValue);
            Log.d("RouteParams", "Free Travel Checked: " + freeTravelChecked);
            Log.d("RouteParams", "Transport Checked: " + transportChecked);
            Log.d("RouteParams", "No Money Matters Checked: " + noMoneyMattersChecked);

            // Здесь вы можете выполнить другие действия с полученными данными

            // Например, можно вызвать другой метод для их обработки
            processRouteParams(autoCompleteText, leftThumbValue, rightThumbValue, freeTravelChecked, transportChecked, noMoneyMattersChecked);
        });

        return root;
    }

    // Метод для обработки полученных данных
    private void processRouteParams(String autoCompleteText, int leftThumbValue, int rightThumbValue, boolean freeTravelChecked, boolean transportChecked, boolean noMoneyMattersChecked) {
        Bundle args = new Bundle();
        args.putString("autoCompleteText", autoCompleteText);
        args.putInt("leftThumbValue", leftThumbValue);
        args.putInt("rightThumbValue", rightThumbValue);
        args.putBoolean("freeTravelChecked", freeTravelChecked);
        args.putBoolean("transportChecked", transportChecked);
        args.putBoolean("noMoneyMattersChecked", noMoneyMattersChecked);

        // Вызываем метод для перехода в новый фрагмент с передачей объекта ContentValues
        navigateToRouteFragment(args);
    }

    // Метод для перехода в новый фрагмент с передачей объекта ContentValues
    private void navigateToRouteFragment(Bundle values) {
        // Создаем NavController
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);

        // Передаем данные во фрагмент через аргументы
        Bundle args = new Bundle();
        args.putAll(values);

        // Навигация к фрагменту RouteFragment с передачей аргументов
        navController.navigate(R.id.route_fragment, args);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
