package com.example.naver.lec5_1.GridView;

/**
 * Created by NAVER on 2017. 4. 25..
 */

public class SingleItem {
    String name;
    String mobile;
    int age;
    int resId;

    public SingleItem(String name, String mobile){
        this.name=name;
        this.mobile=mobile;
    }

    public SingleItem(String name, String mobile, int age, int resId){
        this.name = name;
        this.mobile = mobile;
        this.age = age;

        this.resId = resId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

}
