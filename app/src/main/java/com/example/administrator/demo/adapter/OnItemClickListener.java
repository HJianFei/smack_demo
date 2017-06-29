package com.example.administrator.demo.adapter;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/6/26.
 * 描述：RecyclerView 的点击事件
 */

public interface OnItemClickListener<T> {

    void onItemClick(ViewGroup parent, View view, T t, int position);

//    boolean onItemLongClick(ViewGroup parent, View view, T t, int position);
}
