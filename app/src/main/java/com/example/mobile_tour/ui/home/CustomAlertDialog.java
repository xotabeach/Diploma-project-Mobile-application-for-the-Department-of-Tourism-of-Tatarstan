package com.example.mobile_tour.ui.home;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;

public class CustomAlertDialog extends Dialog {

    private View overlay;

    public CustomAlertDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public void show() {
        super.show();

        // Создание затемнения
        overlay = new View(getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        overlay.setLayoutParams(params);
        overlay.setBackgroundColor(Color.parseColor("#99000000")); // Здесь #99000000 - это полупрозрачный черный цвет
        ((Activity)getContext()).addContentView(overlay, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        overlay.setOnClickListener(view -> dismiss());
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (overlay != null) {
            ((ViewGroup) overlay.getParent()).removeView(overlay);
            overlay = null;
        }
    }
}

