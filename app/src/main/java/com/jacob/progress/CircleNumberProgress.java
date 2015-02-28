package com.jacob.progress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.TypedValue;

/**
 * Package : com.jacob.progress
 * Author : jacob
 * Date : 15-2-28
 * Description : 这个类是用来xxx
 */
public class CircleNumberProgress extends HorizontalNumProgressBar {

    public int radius = 160;
    private int progressWidth = 35;

    public CircleNumberProgress(Context context) {
        super(context);
    }

    public CircleNumberProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleNumberProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        radius = dpToPx(100);
        progressWidth = dpToPx(progressWidth);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        if (heightMode != MeasureSpec.EXACTLY) {
            int height = getPaddingTop() + getPaddingBottom() + radius * 2 + progressWidth * 2;
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        }

        if (widthMode != MeasureSpec.EXACTLY) {
            int width = getPaddingLeft() + getPaddingRight() + radius * 2 + progressWidth * 2;
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected synchronized void onDraw(@NonNull Canvas canvas) {
        String text = getProgress() + "%";
        float textWidth = mPaint.measureText(text);
        float textHeight = (mPaint.descent() + mPaint.ascent()) / 2;

        canvas.save();
        canvas.translate(getPaddingLeft(), getPaddingTop());
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));

        int sw = progressWidth;
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(progressUnReachedColor);
        mPaint.setStrokeWidth(sw);
        canvas.drawCircle(radius + sw / 2, radius + sw / 2, radius, mPaint);

        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setColor(progressReachedColor);
        float angle = getProgress() * 1.0f / getMax() * 360;
        canvas.drawArc(new RectF(sw / 2, sw / 2, radius * 2 + sw / 2, radius * 2 + sw / 2), 270, angle, false, mPaint);

        mPaint.setColor(progressTextColor);
        mPaint.setStrokeWidth(0);
        mPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 28, getResources().getDisplayMetrics()));
        canvas.drawText(text, (getMeasuredWidth() - textWidth-dpToPx(10)) / 2, (getMeasuredHeight() - textHeight-dpToPx(10)) / 2, mPaint);
        canvas.restore();
    }
}
