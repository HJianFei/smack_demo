package com.example.administrator.demo.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

/**
 * 异步加载线程（工具类）
 */
public abstract class XMPPLoadThread {

    private Context mContext;

    @SuppressLint("NewApi")
    public XMPPLoadThread(Context context) {
        mContext = context;
        new AsyncTask<Void, Integer, Object>() {

            @Override
            protected Object doInBackground(Void... arg0) {
                return load();
            }

            @Override
            protected void onPostExecute(Object result) {
                try {

                    result(result);

                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void onPreExecute() {

            }

        }.execute();

    }

    protected abstract Object load();

    protected abstract void result(Object object);

}
