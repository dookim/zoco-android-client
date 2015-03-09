package com.zoco.obj;

/**
 * Created by dookim on 2/8/15.
 */
public class User {

    public String email;
    public String nickName;
    public String provider;
    public String univ;
    public String password;
    public String id;


    /**
     * @param nickName
     * in order to check whether client is registered or not
     */
    public User(String nickName) {
        this.nickName = nickName;
    }

    /**
     * @param nickName
     * in order to login, if client login succeed, client will receive "user object"
     * u can use this object using gson
     * if fail, client will receive "cannot login" string
     */
    public User(String nickName, String password) {
        this.nickName = nickName;
        this.password = password;
    }

    /**
     *
     * @param email
     * @param nickName
     * @param provider
     * @param univ
     * @param password
     * in order to register user to server, client should this information below
     * whether client is registered or not, server always send "success"
     */
    public User(String email, String nickName, String provider, String univ, String password) {
        this.email = email;
        this.nickName = nickName;
        this.provider = provider;
        this.univ = univ;
        this.password = password;
    }




}
