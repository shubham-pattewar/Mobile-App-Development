package com.example.foodsafe.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.foodsafe.R;

public class CustomProgressBar extends View {

    private int progress = 0;
    private Paint backgroundPaint;
    private Paint foregroundPaint;
    private RectF rectF;
    private float strokeWidth = 20f;

    public CustomProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(getResources().getColor(R.color.surface_dark));
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeWidth(strokeWidth);

        foregroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        foregroundPaint.setStyle(Paint.Style.STROKE);
        foregroundPaint.setStrokeWidth(strokeWidth);
        foregroundPaint.setStrokeCap(Paint.Cap.ROUND);

        rectF = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float width = getWidth();
        float height = getHeight();
        float radius = Math.min(width, height) / 2 - strokeWidth;

        rectF.set(width / 2 - radius, height / 2 - radius, width / 2 + radius, height / 2 + radius);

        canvas.drawCircle(width / 2, height / 2, radius, backgroundPaint);

        // Color coding based on progress
        if (progress >= 80) {
            foregroundPaint.setColor(getResources().getColor(R.color.safe_green));
        } else if (progress >= 50) {
            foregroundPaint.setColor(getResources().getColor(R.color.warning_yellow));
        } else {
            foregroundPaint.setColor(getResources().getColor(R.color.danger_red));
        }

        float angle = 360 * progress / 100f;
        canvas.drawArc(rectF, -90, angle, false, foregroundPaint);
    }

    public void setProgress(int progress) {
        this.progress = Math.max(0, Math.min(progress, 100));
        invalidate();
    }
}
