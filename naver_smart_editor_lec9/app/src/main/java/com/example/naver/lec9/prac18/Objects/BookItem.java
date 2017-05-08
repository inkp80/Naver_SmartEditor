package com.example.naver.lec9.prac18.Objects;

import android.graphics.drawable.Drawable;

/**
 * Created by NAVER on 2017. 5. 8..
 */

public class BookItem {

    private String title;
    private String author;
    private String description;
    private Drawable bookImg;

    public BookItem(String title, String author, String description, Drawable drawable){
        this.title = title;
        this.author = author;
        this.description = description;
        this.bookImg = drawable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Drawable getBookImg() {
        return bookImg;
    }

    public void setBookImg(Drawable bookImg) {
        this.bookImg = bookImg;
    }
}
