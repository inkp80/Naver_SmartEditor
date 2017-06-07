package com.naver.smarteditor.lesssmarteditor.data;

/**
 * Created by NAVER on 2017. 6. 7..
 */

public class SpanInfo {
    public int spanStart;
    public int spanEnd;
    public int spanType;

    public SpanInfo(int start, int end, int type){
        spanStart = start;
        spanEnd = end;
        spanType = type;
    }

    public int getSpanStart() {
        return spanStart;
    }

    public void setSpanStart(int spanStart) {
        this.spanStart = spanStart;
    }

    public int getSpanEnd() {
        return spanEnd;
    }

    public void setSpanEnd(int spanEnd) {
        this.spanEnd = spanEnd;
    }

    public int getSpanType() {
        return spanType;
    }

    public void setSpanType(int spanType) {
        this.spanType = spanType;
    }
}
