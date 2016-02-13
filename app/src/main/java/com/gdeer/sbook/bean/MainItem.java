package com.gdeer.sbook.bean;

/**
 * Created by Gdeer on 2016/1/23.
 */

//由Book类代替，现在不再使用此类

public class MainItem {
    private String bookName;

    public MainItem(String bookName) {
        this.bookName = bookName;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
}
