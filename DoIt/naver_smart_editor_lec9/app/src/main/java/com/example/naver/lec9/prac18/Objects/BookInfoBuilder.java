package com.example.naver.lec9.prac18.Objects;

import android.graphics.drawable.Drawable;

/**
 * Created by NAVER on 2017. 5. 8..
 */

public class BookInfoBuilder {
    private String title;
    private String author;
    private String description;
    private int bookImg;

    public BookInfoBuilder setTitle(String title){
        this.title = title;
        return this;
    }

    public BookInfoBuilder setAuthor(String author){
        this.author = author;
        return this;
    }

    public BookInfoBuilder setDescriprion(String description){
        this.description = description;
        return this;
    }

    public BookInfoBuilder setBookImg(int drawableResId){
        this.bookImg = drawableResId;
        return this;
    }

    public BookItem Build(){
        BookItem item = new BookItem(title, author, description, bookImg);
        return item;
    }

}
