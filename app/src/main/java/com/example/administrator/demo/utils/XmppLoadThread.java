package com.example.administrator.demo.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

public abstract class XmppLoadThread {

    private Context mContext;

    @SuppressLint("NewApi")
    public XmppLoadThread(Context context) {
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
