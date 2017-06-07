package com.naver.smarteditor.lesssmarteditor.views.edit.utils;

import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.Toast;

/**
 * Created by NAVER on 2017. 5. 30..
 */

public class TitleFilter implements InputFilter {
    private final int mMax;
    private final Context mContext;

    public TitleFilter(Context c, int max) {
        mMax = max;
        mContext = c;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest,
                               int dstart, int dend) {
        int keep = mMax - (dest.length() - (dend - dstart));
        if (keep <= 0) {
            Toast.makeText(mContext, String.valueOf(mMax) + "자를 초과하였습니다.", Toast.LENGTH_SHORT).show();
            return "";
        } else if (keep >= end - start) {
            return null; // keep original
        } else {
            keep += start;
            if (Character.isHighSurrogate(source.charAt(keep - 1))) {
                --keep;
                if (keep == start) {
                    return "";
                }
            }
            return source.subSequence(start, keep);
        }
    }
    public int getMax() {
        return mMax;
    }
}
