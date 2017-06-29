package com.example.administrator.demo.application;

import android.app.Application;

import com.example.administrator.demo.utils.XMPPConnUtils;

/**
 * Created by HJF on 2017/6/28.
 * 描述：
 */

public class ImApp extends Application {

    private static ImApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;


        new Thread(new Runnable() {
            @Override
            public void run() {
                XMPPConnUtils.getInstance().getConnection();
            }
        }).start();

    }

    public static ImApp getInstance() {
        return instance;
    }
}
