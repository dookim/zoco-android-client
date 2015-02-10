package com.zoco.obj;

/**
 * Created by dookim on 2/8/15.
 */
public class BookInfo {
    public String email;
    public String isbn;
    public String author;
    public int ori_price;
    public int price;
    public boolean scribble;
    public boolean check_answer;
    public boolean have_answer;
    public String img_str;

    public BookInfo(String email, String isbn, String author, int ori_price, int price, boolean scribble, boolean have_answer, boolean check_answer, String img_str) {
        this.email = email;
        this.isbn = isbn;
        this.author = author;
        this.ori_price = ori_price;
        this.price = price;
        this.scribble = scribble;
        this.check_answer = check_answer;
        this.have_answer = have_answer;
        this.img_str = img_str;
    }
}
