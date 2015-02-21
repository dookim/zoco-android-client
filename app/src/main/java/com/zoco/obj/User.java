package com.zoco.obj;

/**
 * Created by dookim on 2/8/15.
 */
public class User {
    public String email;
    public String univ;
    public String provider;
    public String password;

    public User(String email, String univ) {
        this.email = email;
        this.univ = univ;
    }

    public User(String email, String provider, String password) {
        this.email = email;
        this.provider = provider;
        this.password = password;
    }

    public User(String email, String univ, String provider, String password) {
        this.email = email;
        this.univ = univ;
        this.provider = provider;
        this.password = password;
    }
}
