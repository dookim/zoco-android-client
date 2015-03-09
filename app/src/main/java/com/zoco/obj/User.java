package com.zoco.obj;

/**
 * Created by dookim on 2/8/15.
 */
public class User {

    public String nickName;
    public String provider;
    public String univ;
    public String chatId;

    public User(String nickName, String univ, String provider, String chatId) {
        this.nickName = nickName;
        this.univ = univ;
        this.provider = provider;
        this.chatId = chatId;
    }
}
