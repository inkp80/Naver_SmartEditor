package com.example.naver.customlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by NAVER on 2017. 5. 4..
 */

public class CustomLayout extends ViewGroup {
    String TAG = "CUSTOM-LAYOUT";
    private float mWeightSum;
    private float mWeightTotal;

    boolean isWeightCalculated = false;
    boolean weightWasSetBefore = false;



    public CustomLayout(Context context){
        super(context);
        Log.d(TAG, "Constructor");
    }

    public CustomLayout(Context context, AttributeSet attrs){
        super(context, attrs);
        Log.d(TAG, "Constructor");
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomLayout);
        try {
            mWeightSum = a.getFloat(R.styleable.CustomLayout_weightSum, 0);
        } finally {
            a.recycle();
        }

    }

    public void addWeight(float val){
        mWeightTotal += val;
    }

    public void setmWeightSum(float val){
        mWeightSum = val;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){

        Log.d(TAG, "onMeasure(), width : "+String.valueOf(MeasureSpec.getSize(widthMeasureSpec)) +"; height : " + String.valueOf(MeasureSpec.getSize(heightMeasureSpec)) +";");
        //A MeasureSpec encapsulates the layout requirements passed from parent to child.
        //Each MeasureSpec represents a requirement for either the width or the height.
        // A MeasureSpec is comprised of a size and a mode. There are three possible modes:

        int widthSize = MeasureSpec.getSize(widthMeasureSpec)-getPaddingLeft()-getPaddingRight();
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        //뷰의 전체 크기
        int width = getPaddingLeft();
        int height = getPaddingTop();

        //현재 전개 중에 있는 상태에서의 크기
        int currentWidth = getPaddingLeft();
        int currentHeight = getPaddingTop();


        for(int i=0; i<getChildCount(); i++){
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            lp.width = child.getMeasuredWidth();
            lp.height = child.getMeasuredHeight();


            if((!isWeightCalculated) && mWeightSum ==0 ){
                addWeight(lp.weight);
            }

            if(currentWidth + child.getMeasuredWidth() >= widthSize){
                //뷰 전개 중 부모의 크기를 벗어나면 화면에서 지워버린다.
                //사이즈를 0, 0으로 주고 있는데, 좀 더 좋은 방법이 있을까?

                if(currentWidth < widthSize){
                    lp.width = widthSize - currentWidth;
                    child.setLayoutParams(lp);
                } else {
                    lp.width = 0;
                    lp.height = 0;
                    child.setVisibility(GONE);
                    child.setLayoutParams(lp);
                    continue;
                }
            }

            //뷰를 전개할 좌표 시작점 지정(Left-Top)
//            lp.x = currentWidth;
//            lp.y = height;

            //전개 중인 상태의 크기 갱신
            //height의 경우 Mode에 모드에 따라서 변경이 필요하다.
            //부모의 모드와 자식의 모드에 대해서 분기를 시킬 필요가 있다.
            //Wrap_content, Match_parent에 대해서 고려해볼 것
            currentWidth += child.getMeasuredWidth();
            currentHeight = Math.max(currentHeight, child.getMeasuredHeight());
        }


        //부모 뷰의 최종 크기 확정
        height = height + currentHeight + getPaddingBottom();
        width = width + currentWidth + getPaddingRight();

        //weight 값울 반영하여 자식 뷰의 재측정
        if(isWeightCalculated) {

            float denoWeight = mWeightSum == 0 ? mWeightTotal : mWeightSum;

            int exceedSpace = widthSize - width;
            if(widthMode != MeasureSpec.EXACTLY){
                //부모의 뷰가 만약 MATCH_PARENT의 속성을 가지고 있다면..
                exceedSpace = width;
            }

            int currentXpos = getPaddingLeft();

            for(int i=0; i<getChildCount(); i++){
                View child = getChildAt(i);
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                lp.x = currentXpos;
                if(lp.weight != 0){
                    lp.width = lp.width + (int) (exceedSpace * lp.weight / denoWeight);
                }
                currentXpos += lp.width;
                child.setLayoutParams(lp);
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
            }
            weightWasSetBefore = true;
        }isWeightCalculated = true;

        setMeasuredDimension(resolveSize(width, widthMeasureSpec), resolveSize(height, heightMeasureSpec));

    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(TAG, "onLayout()");
        for(int i=0; i<getChildCount(); i++){
            View child = getChildAt(i);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            child.layout(lp.x, lp.y, lp.x + lp.width, lp.y + lp.height);
        } isWeightCalculated = false;
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        Log.d(TAG, "onDraw()");
    }

    @Override
    public void requestLayout(){
        super.requestLayout();
        Log.d(TAG, "requestLayout()");
        if(weightWasSetBefore){
            //기존에 설정된 weight 값들을 갱신하여 재전개를 하기 위한 초기화
            mWeightTotal = 0;
            for(int i=0; i<getChildCount(); i++){
                View child = getChildAt(i);
                LayoutParams lp = new LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                LayoutParams olp =(LayoutParams) child.getLayoutParams();
                lp.weight = olp.weight;
                child.setLayoutParams(lp);
                measureChild(child, getWidth(), getHeight());
            }
            weightWasSetBefore = false;
        }
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p.width, p.height);
    }

    class LayoutParams extends ViewGroup.LayoutParams{
        int x, y;
        public float weight;
        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.CustomLayout_LayoutParams);
            try {
                weight = a.getFloat(R.styleable.CustomLayout_LayoutParams_layout_weight, 0);
            } finally {
                a.recycle();
            }
        }

        public LayoutParams(int width, int height){
            super(width, height);
        }


    }



    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, "onSizeChagned()");
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d(TAG, "onAttachedToWindow()");
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        Log.d(TAG, "dispatchDraw()");
    }

    @Override
    public void invalidate() {
        super.invalidate();
        Log.d(TAG, "Invalidate()");
    }


}
