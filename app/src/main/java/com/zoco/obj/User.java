package com.zoco.obj;

/**
 * Created by dookim on 2/8/15.
 */
public class User {
    public String email;
    public String provider;
    public String univ;
    public User(String email, String univ, String provider) {
        this.email = email;
        this.univ = univ;
        this.provider = provider;
    }
}
