package com.naver.smarteditor.lesssmarteditor;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Parcel;
import android.text.ParcelableSpan;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

/**
 * Created by NAVER on 2017. 6. 9..
 */


@SuppressLint("ParcelCreator")
public class ItalicSpan extends MetricAffectingSpan implements ParcelableSpan {


    private final int mStyle;

    /**
     *
     * @param style An integer constant describing the style for this span. Examples
     * include bold, italic, and normal. Values are constants defined
     * in {@link android.graphics.Typeface}.
     */
    public ItalicSpan(int style) {
        mStyle = style;
    }

    public ItalicSpan(Parcel src) {
        mStyle = src.readInt();
    }

    public int getSpanTypeId() {
        return getSpanTypeIdInternal();
    }

    /** @hide */
    public int getSpanTypeIdInternal() {
        return 7;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        writeToParcelInternal(dest, flags);
    }

    /** @hide */
    public void writeToParcelInternal(Parcel dest, int flags) {
        dest.writeInt(mStyle);
    }

    /**
     * Returns the style constant defined in {@link android.graphics.Typeface}.
     */
    public int getStyle() {
        return mStyle;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        apply(ds, mStyle);
    }

    @Override
    public void updateMeasureState(TextPaint paint) {
        apply(paint, mStyle);
    }

    private static void apply(Paint paint, int style) {
        int oldStyle;

        Typeface old = paint.getTypeface();
        if (old == null) {
            oldStyle = 0;
        } else {
            oldStyle = old.getStyle();
        }

        int want = oldStyle | style;

        Typeface tf;
        if (old == null) {
            tf = Typeface.defaultFromStyle(want);
        } else {
            tf = Typeface.create(old, want);
        }

        int fake = want & ~tf.getStyle();

        if ((fake & Typeface.BOLD) != 0) {
            paint.setFakeBoldText(false);
        }

        if ((fake & Typeface.ITALIC) != 0) {
            paint.setTextSkewX(-0.25f);
        }

        paint.setTypeface(tf);
    }
}
