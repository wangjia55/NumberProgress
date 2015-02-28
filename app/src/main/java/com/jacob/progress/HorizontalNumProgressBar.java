package com.jacob.progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;

/**
 * Package : com.jacob.progress
 * Author : jacob
 * Date : 15-2-28
 * Description : 这个类是用来xxx
 */
public class HorizontalNumProgressBar extends ProgressBar {
    public static final int PROGRESS_REACHED_COLOR = 0xFF4587FF;
    public static final int PROGRESS_UNREACHED_COLOR = 0xFF696969;
    public static final int PROGRESS_HEIGHT =4;
    public static final int PROGRESS_TEXT_COLOR = 0XFFFC00D1;
    public static final int PROGRESS_TEXT_SIZE = 10;
    public static final int PROGRESS_TEXT_OFFSET = 5;

    protected int progressReachedColor = PROGRESS_REACHED_COLOR;
    protected int progressUnReachedColor = PROGRESS_UNREACHED_COLOR;
    protected int progressHeight = PROGRESS_HEIGHT;
    protected int progressTextColor = PROGRESS_TEXT_COLOR;
    protected int progressTextSize = PROGRESS_TEXT_SIZE;
    protected int progressTextOffset = PROGRESS_TEXT_OFFSET;

    protected Paint mPaint = new Paint();
    protected int realW;

    public HorizontalNumProgressBar(Context context) {
        this(context, null);
    }

    public HorizontalNumProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalNumProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.HorizontalNumProgressBar);
        progressHeight = (int) array.getDimension(R.styleable.HorizontalNumProgressBar_progress_height, dpToPx(PROGRESS_HEIGHT));
        progressReachedColor = array.getColor(R.styleable.HorizontalNumProgressBar_progress_reached_color, PROGRESS_REACHED_COLOR);
        progressUnReachedColor = array.getColor(R.styleable.HorizontalNumProgressBar_progress_unreached_color, PROGRESS_UNREACHED_COLOR);
        progressTextColor = array.getColor(R.styleable.HorizontalNumProgressBar_progress_text_color, PROGRESS_TEXT_COLOR);
        progressTextSize = (int) array.getDimension(R.styleable.HorizontalNumProgressBar_progress_text_size, dpToPx(PROGRESS_TEXT_SIZE));
        progressTextOffset = (int) array.getDimension(R.styleable.HorizontalNumProgressBar_progress_text_offset, dpToPx(PROGRESS_TEXT_OFFSET));
        array.recycle();

        mPaint.setAntiAlias(true);
        mPaint.setTextSize(progressTextSize);
    }


    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        realW = w - getPaddingLeft() - getPaddingRight();
    }

    @Override
    protected synchronized void onDraw(@NonNull Canvas canvas) {
        boolean drawUnreached = true;
        canvas.save();
        canvas.translate(getPaddingLeft(), getHeight() / 2);
        String text = getProgress() + "%";
        float textW = mPaint.measureText(text);
        float textH = (mPaint.descent() + mPaint.ascent()) / 2;

        float leftLineW = (float) (realW * (getProgress() * 1.0 / getMax())) - textW - progressTextOffset*2 ;
        float rightLineW = (float) (realW * (getProgress() * 1.0 / getMax()));
        float centerH = (getMeasuredHeight() - progressHeight) / 2 ;

        if (leftLineW + textW > realW) {
            leftLineW = realW - textW;
            drawUnreached = false;
        }

        int strokW = progressHeight;
        mPaint.setColor(progressReachedColor);
        mPaint.setStrokeWidth(strokW);
        mPaint.setAntiAlias(true);
        canvas.drawLine(0, centerH, leftLineW, centerH, mPaint);


        mPaint.reset();
        mPaint.setColor(progressTextColor);
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(progressTextSize);
        canvas.drawText(text, leftLineW , (getMeasuredHeight() - textH) / 2, mPaint);

        if (drawUnreached) {
            mPaint.reset();
            mPaint.setColor(progressUnReachedColor);
            mPaint.setStrokeWidth(strokW);
            mPaint.setAntiAlias(true);
            canvas.drawLine(rightLineW, centerH, realW, centerH, mPaint);
        }
        super.onDraw(canvas);
    }

    @Override
    public synchronized void setProgress(int progress) {
        super.setProgress(progress);
    }

    protected int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
//
//    private int spToPx(int sp) {
//        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
//    }
}
