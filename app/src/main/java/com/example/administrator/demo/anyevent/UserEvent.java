package com.example.administrator.demo.anyevent;

/**
 * Created by HJF on 2017/6/28.
 * 描述：用户action事件总线(请求、删除、通过、拒绝、上线、离线)
 */

public class UserEvent {
    public String msg;
    public int flag;//0:表示好友请求；1：表示好友删除；2:通过；3：表示拒绝；4：表示上线；5：表示离线；

    public UserEvent(String msg, int flag) {
        this.msg = msg;
        this.flag = flag;
    }
}
