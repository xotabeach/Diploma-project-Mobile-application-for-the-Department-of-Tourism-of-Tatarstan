package com.example.mobile_tour.ui.home;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_tour.R;

public class ClickedLocationDialog{
    private Context context;
    private TravelLocation travelLocation;
    private Dialog dialog;


    public ClickedLocationDialog(Context context, TravelLocation travelLocation) {

        this.context = context;
        this.travelLocation = travelLocation;
    }

    public void show() {


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.clicked_location_dialog, null);
        ImageView imageView = dialogView.findViewById(R.id.imageView);
        TextView textView = dialogView.findViewById(R.id.textView);
        TextView textViewCat = dialogView.findViewById(R.id.textViewCategory);
        TextView textViewYear = dialogView.findViewById(R.id.textViewYear);
        TextView textView1 = dialogView.findViewById(R.id.textView2);

        // Задаем изображение и текст в соответствии с выбранной достопримечательностью
        imageView.setImageResource(travelLocation.imageUrl);
        textView.setText(travelLocation.title);
        textViewCat.setText(travelLocation.category);
        textViewYear.setText(travelLocation.year);
        textView1.setText(travelLocation.description);
        // Настройка внешнего вида диалогового окна
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
