package com.airsaid.indicatedotview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.airsaid.indicatedotview.R;

/**
 * Created by zhouyou on 2016/9/30.
 * Class desc:
 *
 * 自定义圆点指示器View
 */
public class IndicateDotView extends View {

    /** 已选中指示点颜色 */
    private int mSelectColor = Color.parseColor("#2abb9b");
    /** 未选中指示点颜色 */
    private int mUnSelectColor = Color.parseColor("#e5e5e5");
    /** 指示点之间的间距 */
    private int mMargin = dp2px(5);
    /** 指示点半径 */
    private int mRadius = dp2px(10);
    /** 指示点选中个数 */
    private int mNumber = 0;
    /** 指示点最大个数 */
    private int mMaxNumber = 3;
    /** 是否最少保留一个指示点 */
    private boolean mIsLeastOne = false;
    /** 是否可以点击 */
    private boolean mClickable = true;
    /** 边框颜色 */
    private int mDivideColor = -1;
    /** 边框宽度 */
    private int mDivideWidth = dp2px(1);

    private Paint mPaint;


    public IndicateDotView(Context context) {
        this(context, null);
    }

    public IndicateDotView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public IndicateDotView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.IndicateDotView);
        mUnSelectColor = a.getColor(R.styleable.IndicateDotView_indicate_unselect_color, mUnSelectColor);
        mSelectColor = a.getColor(R.styleable.IndicateDotView_indicate_select_color, mSelectColor);
        mDivideWidth = (int) a.getDimension(R.styleable.IndicateDotView_indicate_divide_width, mDivideWidth);
        mDivideColor = a.getColor(R.styleable.IndicateDotView_indicate_divide_color, mDivideColor);
        mIsLeastOne = a.getBoolean(R.styleable.IndicateDotView_indicate_is_least_one, mIsLeastOne);
        mMargin = (int) a.getDimension(R.styleable.IndicateDotView_indicate_margin, mMargin);
        mRadius = (int) a.getDimension(R.styleable.IndicateDotView_indicate_radius, mRadius);
        mClickable = a.getBoolean(R.styleable.IndicateDotView_android_clickable, mClickable);
        mMaxNumber = a.getInt(R.styleable.IndicateDotView_indicate_max_number, mMaxNumber);
        mNumber = a.getInt(R.styleable.IndicateDotView_indicate_number, mNumber);
        a.recycle();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = 0;
        int height = 0;

        if(widthMode != MeasureSpec.EXACTLY){
            width = mMaxNumber * (mRadius * 2 + mDivideWidth * 2 + mMargin) + getPaddingLeft() + getPaddingRight();
        }

        if(heightMode != MeasureSpec.EXACTLY){
            height = mRadius * 2 + mDivideWidth * 2 + getPaddingTop() + getPaddingBottom();
        }

        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : width,
                heightMode == MeasureSpec.EXACTLY ? heightSize : height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(mNumber > mMaxNumber){
            if(mIsLeastOne){
                mNumber = 1;
            }else{
                mNumber = 0;
            }
        }

        for (int i = 0; i < mMaxNumber; i++) {
            if(i < mNumber){
                mPaint.setColor(mSelectColor);
                mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                drawCircle(canvas, i);
            }else{
                mPaint.setColor(mUnSelectColor);
                mPaint.setStyle(Paint.Style.FILL);
                drawCircle(canvas, i);

                if(mDivideColor != -1){
                    mPaint.setColor(mDivideColor);
                    mPaint.setStyle(Paint.Style.STROKE);
                    mPaint.setStrokeWidth(mDivideWidth);
                    drawCircle(canvas, i);
                }
            }
        }
    }

    private void drawCircle(Canvas canvas, int i){
        canvas.drawCircle(getPaddingLeft() + mRadius + mDivideWidth + (((mRadius * 2)
                + (mDivideWidth * 2) + mMargin) * i), getHeight() / 2, mRadius, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(mClickable){
            ++mNumber;
            invalidate();
            return false;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void setClickable(boolean clickable) {
        this.mClickable = clickable;
    }

    /**
     * 获取已选中指示点个数
     */
    public int getNubmer(){
        return mNumber;
    }

    /**
     * 设置已选中指示点个数
     */
    public void setNumber(int number) {
        this.mNumber = number;
        invalidate();
    }

    /**
     * 设置已选中指示点颜色
     */
    public void setSelectColor(int selectColor) {
        this.mSelectColor = selectColor;
    }

    /**
     * 设置未选中指示点颜色
     */
    public void setUnSelectColor(int unSelectColor) {
        this.mUnSelectColor = unSelectColor;
    }

    /**
     * 设置指示点之间的间距
     */
    public void setMargin(int margin) {
        this.mMargin = margin;
    }

    /**
     * 设置指示点半径
     */
    public void setRadius(int radius) {
        this.mRadius = radius;
    }

    /**
     * 设置最大指示点个数
     */
    public void setMaxNumber(int maxNumber) {
        this.mMaxNumber = maxNumber;
    }

    /**
     * 获取最大指示点个数
     */
    public int getMaxNumber(){
        return mMaxNumber;
    }

    /**
     * 是否最少保留一个指示点
     */
    public boolean isLeastOne() {
        return mIsLeastOne;
    }

    /**
     * 设置是否最少保留一个指示点
     */
    public void setLeastOne(boolean leastOne) {
        this.mIsLeastOne = leastOne;
    }

    /**
     * 设置边框颜色
     */
    public void setDivideColor(int divideColor){
        this.mDivideColor = divideColor;
    }

    /**
     * 设置边框宽度
     */
    public void setDivideWidth(int divideWidth){
        this.mDivideWidth = divideWidth;
    }

    private int dp2px(int dpVal){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }
}
