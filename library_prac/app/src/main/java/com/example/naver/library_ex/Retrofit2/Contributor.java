package com.example.naver.library_ex.Retrofit2;

/**
 * Created by NAVER on 2017. 5. 10..
 */

public class Contributor {
    String login;
    String html_url;

    int contributions;

    @Override
    public String toString() {
        return login + " (" + contributions + ")";
    }
}
