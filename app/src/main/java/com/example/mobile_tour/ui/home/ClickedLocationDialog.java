package com.example.mobile_tour.ui.home;









import static androidx.core.content.ContentProviderCompat.requireContext;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;


import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.mobile_tour.R;
import com.example.mobile_tour.ui.ClickedTravelData;
import com.example.mobile_tour.ui.SharedViewModel;

import java.util.ArrayList;
import java.util.List;


public class ClickedLocationDialog{
    private Context context;
    private TravelLocation travelLocation;
    private Dialog dialog;

    private SharedViewModel sharedViewModel;

    public ClickedLocationDialog(Context context, TravelLocation travelLocation) {
        this.sharedViewModel = sharedViewModel;
        this.context = context;
        this.travelLocation = travelLocation;
    }

    public void dismiss(View overlay) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();

            // Удаление оверлея при закрытии окна
            if (overlay != null) {
                ViewGroup parentView = (ViewGroup) overlay.getParent();
                if (parentView != null) {
                    parentView.removeView(overlay);
                }
            }
        }
    }

    protected void hideSystemBars(Window window){
        View decorView = window.getDecorView();

        // Устанавливаем флаги для размещения контента под системными панелями
        int flags = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;

        decorView.setSystemUiVisibility(flags);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowInsetsController controller = decorView.getWindowInsetsController();
            if (controller != null) {
                controller.setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                        WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS);
                controller.setSystemBarsBehavior(WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
                controller.show(WindowInsets.Type.navigationBars());
            }
        }
        int navbarHeight=0;
        // Получение высоты нижней панели через decorView
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            navbarHeight = resources.getDimensionPixelSize(resourceId);
        }

        decorView.setPadding(0, 0, 0, -10); // применяем отступ снизу экрана, равный высоте нижней системной панели управления
    }

    public void show() {

        View overlay = new View(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        overlay.setLayoutParams(params);
        //hideSystemBars(((Activity) context).getWindow());
        Window window = ((Activity) context).getWindow();

        int previousColor = window.getNavigationBarColor();
        window.setNavigationBarColor(Color.parseColor("#99000000"));


        List<ClickedTravelData> clickedLocationData = new ArrayList<>();



        overlay.setBackgroundColor(Color.parseColor("#99000000")); // Здесь #99000000 - это полупрозрачный черный цвет
        ViewGroup decorView = ((Activity)context).getWindow().getDecorView().findViewById(android.R.id.content);
        decorView.addView(overlay);


        overlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Удаление затемнения
                //dismiss(overlay);
                decorView.removeView(overlay);
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.DialogStyle);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.clicked_location_dialog, null);
        ImageView imageView = dialogView.findViewById(R.id.imageView);
        TextView textView = dialogView.findViewById(R.id.textView);
        TextView textViewCat = dialogView.findViewById(R.id.textViewCategory);
        TextView textViewYear = dialogView.findViewById(R.id.textViewYear);
        TextView textView1 = dialogView.findViewById(R.id.textView2);
        View layoutview = dialogView.findViewById(R.id.layoutclickedId);
        Button addButton = dialogView.findViewById(R.id.button_clicked);
        Button deleteButton = dialogView.findViewById(R.id.button_delete);
        textView.setText(travelLocation.title);
        textViewCat.setText(travelLocation.category);
        textViewYear.setText(travelLocation.year);
        textView1.setText(travelLocation.description);




        int currentPaddingStart = addButton.getPaddingStart();
        int currentPaddingEnd = addButton.getPaddingEnd();
        addButton.setPaddingRelative(currentPaddingStart + 25, addButton.getPaddingTop(), currentPaddingEnd + 25, addButton.getPaddingBottom());

        deleteButton.setVisibility(View.INVISIBLE);
        final boolean[] isClicked = {false};

        addButton.setOnClickListener(new View.OnClickListener() {
            //private boolean isClicked = false; // флаг для отслеживания состояния нажатия
            @Override
            public void onClick(View v) {
                if (!isClicked[0]) { // проверка состояния нажатия кнопки
                    addButton.setBackgroundResource(R.drawable.button_clicked_dost_after);
                    ObjectAnimator textColorAnimator = ObjectAnimator.ofInt(addButton, "textColor", Color.parseColor("#FFFFFF"), Color.parseColor("#FF8642"));
                    textColorAnimator.setDuration(450);
                    textColorAnimator.setEvaluator(new ArgbEvaluator());
                    textColorAnimator.start();

                    ObjectAnimator translationXAnimator = ObjectAnimator.ofFloat(addButton, "translationX", 0f, -75f);
                    translationXAnimator.setDuration(500);
                    translationXAnimator.start();

                    isClicked[0] = true; // устанавливаем флаг нажатия в true

                    // Включение видимости кнопки "delete" и перемещение ее вправо
                    deleteButton.setVisibility(View.VISIBLE);
                    ObjectAnimator translationXDeleteAnimator = ObjectAnimator.ofFloat(deleteButton, "translationX", 0f, 15f);
                    translationXDeleteAnimator.setDuration(500);
                    translationXDeleteAnimator.start();

                    // Уменьшение padding start и end на 25 единиц
                    ValueAnimator paddingAnimator = ValueAnimator.ofInt(addButton.getPaddingStart(), addButton.getPaddingStart() - 25);
                    paddingAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            int animatedValue = (int) animation.getAnimatedValue();
                            addButton.setPaddingRelative(animatedValue, addButton.getPaddingTop(), animatedValue, addButton.getPaddingBottom());
                        }
                    });
                    paddingAnimator.setDuration(500);
                    paddingAnimator.start();
                    clickedLocationData.add(new ClickedTravelData(travelLocation.title, travelLocation.imageUrl, travelLocation.category));



                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Возвращение состояний кнопок в исходное состояние
                addButton.setBackgroundResource(R.drawable.button_clicked_dost_before);
                ObjectAnimator textColorAnimator = ObjectAnimator.ofInt(addButton, "textColor", Color.parseColor("#FF8642"), Color.parseColor("#FFFFFF"));
                textColorAnimator.setDuration(450);
                textColorAnimator.setEvaluator(new ArgbEvaluator());
                textColorAnimator.start();

                ObjectAnimator translationXAnimator = ObjectAnimator.ofFloat(addButton, "translationX", -75f, 0f);
                translationXAnimator.setDuration(500);
                translationXAnimator.start();

                deleteButton.setVisibility(View.INVISIBLE);
                ObjectAnimator translationXDeleteAnimator = ObjectAnimator.ofFloat(deleteButton, "translationX", 15f, 0f);
                translationXDeleteAnimator.setDuration(500);
                translationXDeleteAnimator.start();

                ValueAnimator paddingAnimator = ValueAnimator.ofInt(addButton.getPaddingStart(), addButton.getPaddingStart() + 25);
                paddingAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int animatedValue = (int) animation.getAnimatedValue();
                        addButton.setPaddingRelative(animatedValue, addButton.getPaddingTop(), animatedValue, addButton.getPaddingBottom());
                    }
                });
                paddingAnimator.setDuration(500);
                paddingAnimator.start();
                isClicked[0] = false; // возврат флага нажатия в исходное состояние
            }
        });

        // Задаем изображение
        //imageView.setImageResource(travelLocation.imageUrl);
        Glide.with(context)
                .load(travelLocation.imageUrl)
                .centerCrop()
                .into(imageView);

        // Настройка внешнего вида диалогового окна и его отображение
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        //alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                decorView.removeView(overlay); // Удаление оверлея при закрытии окна
                window.setNavigationBarColor(previousColor);
            }
        });




        alertDialog.show();
    }
}
