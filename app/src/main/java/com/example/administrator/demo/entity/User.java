package com.example.administrator.demo.entity;

import java.io.Serializable;

/**
 * Created by HJF on 2017/6/29.
 * 描述：
 */

public class User implements Serializable {

    public String username;
    public String nickname;
    public String email;

    public User(String username, String nickname, String email) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
    }

    public User() {
    }
}
