package com.example.mobile_tour.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.core.content.ContextCompat;

import com.example.mobile_tour.R;

public class RangeSeekBar extends View {
    private Paint paint;
    private int thumbRadius;
    private int thumbNormalColor;
    private int thumbSelectedColor;
    private int rangeColor;
    private int rangeWidth;
    private int min = 0;
    private int max = 25;
    private int leftThumb = 1;
    private int rightThumb = 16;
    private boolean isTouchingLeftThumb = false;
    private boolean isTouchingRightThumb = false;

    private int textColor;
    private int textSize;
    private int textPadding;
    private Paint textPaint;

    public RangeSeekBar(Context context) {
        super(context);
        init(context, null, 0);
    }

    public RangeSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public RangeSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        thumbRadius = getResources().getDimensionPixelSize(R.dimen.thumb_radius);
        thumbNormalColor = ContextCompat.getColor(context, R.color.seekbar_thumb_normal);
        thumbSelectedColor = ContextCompat.getColor(context, R.color.seekbar_thumb_selected);
        rangeColor = ContextCompat.getColor(context, R.color.seekbar_range);
        rangeWidth = getResources().getDimensionPixelSize(R.dimen.range_width);

        textColor = ContextCompat.getColor(context, R.color.black);
        textSize = getResources().getDimensionPixelSize(R.dimen.thumb_text_size);
        textPadding = getResources().getDimensionPixelSize(R.dimen.thumb_text_padding);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw range
        paint.setColor(rangeColor);
        paint.setStrokeWidth(rangeWidth);
        float startX = thumbRadius;
        float endX = getWidth() - thumbRadius;
        float startY = getHeight() / 2f;
        canvas.drawLine(startX, startY, endX, startY, paint);

        // Draw line between thumbs
        paint.setStrokeWidth(rangeWidth + 2); // Увеличиваем толщину линии
        paint.setColor(ContextCompat.getColor(getContext(), R.color.line_color)); // Указываем цвет линии
        float leftX = startX + (leftThumb / (float) max) * (endX - startX);
        float rightX = startX + (rightThumb / (float) max) * (endX - startX);
        canvas.drawLine(leftX, startY, rightX, startY, paint); // Линия между маркерами

        // Draw left thumb
        paint.setColor(isTouchingLeftThumb ? thumbSelectedColor : thumbNormalColor);
        Drawable customThumb = ContextCompat.getDrawable(getContext(), R.drawable.custom_thumb); // Загрузка пользовательского маркера
        customThumb.setBounds((int) (leftX - thumbRadius), (int) (startY - thumbRadius), (int) (leftX + thumbRadius), (int) (startY + thumbRadius));
        customThumb.draw(canvas);
        canvas.drawText(String.valueOf(leftThumb), leftX, startY - thumbRadius - textPadding, textPaint); // Отображение значения

        // Draw right thumb
        paint.setColor(isTouchingRightThumb ? thumbSelectedColor : thumbNormalColor);
        customThumb.setBounds((int) (rightX - thumbRadius), (int) (startY - thumbRadius), (int) (rightX + thumbRadius), (int) (startY + thumbRadius));
        customThumb.draw(canvas);
        canvas.drawText(String.valueOf(rightThumb), rightX, startY - thumbRadius - textPadding, textPaint); // Отображение значения
    }




    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event == null) return false;

        float x = event.getX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isTouchingLeftThumb = isInsideLeftThumb(x);
                isTouchingRightThumb = isInsideRightThumb(x);
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                if (isTouchingLeftThumb) {
                    leftThumb = (int) ((x - thumbRadius) / (getWidth() - 2 * thumbRadius) * max);
                    leftThumb = Math.min(Math.max(leftThumb, min), rightThumb);
                    invalidate();
                }
                if (isTouchingRightThumb) {
                    rightThumb = (int) ((x - thumbRadius) / (getWidth() - 2 * thumbRadius) * max);
                    rightThumb = Math.min(Math.max(rightThumb, leftThumb), max);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isTouchingLeftThumb = false;
                isTouchingRightThumb = false;
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }

        int leftThumbValue = leftThumb;
        int rightThumbValue = rightThumb;

        return true;
    }

    private boolean isInsideLeftThumb(float x) {
        float leftX = (leftThumb / (float) max) * (getWidth() - 2 * thumbRadius) + thumbRadius;
        return x >= leftX - thumbRadius && x <= leftX + thumbRadius;
    }

    private boolean isInsideRightThumb(float x) {
        float rightX = (rightThumb / (float) max) * (getWidth() - 2 * thumbRadius) + thumbRadius;
        return x >= rightX - thumbRadius && x <= rightX + thumbRadius;
    }
}
