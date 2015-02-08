package com.zoco.obj;

/**
 * Created by dookim on 2/8/15.
 */
public class BookInfo {
    public String email;
    public String isbn;
    public String author;
    public int origin_price;
    public int price;
    public boolean isScribble;
    public boolean isAnswer;
    public boolean hasAnswer;
    public String imgStr;

    public BookInfo(String email, String isbn, String author, int origin_price, int price, boolean isScribble, boolean isAnswer, boolean hasAnswer, String imgStr) {
        this.email = email;
        this.isbn = isbn;
        this.author = author;
        this.origin_price = origin_price;
        this.price = price;
        this.isScribble = isScribble;
        this.isAnswer = isAnswer;
        this.hasAnswer = hasAnswer;
        this.imgStr = imgStr;
    }
}
