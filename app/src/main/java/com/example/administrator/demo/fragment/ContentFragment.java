package com.example.administrator.demo.fragment;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.demo.R;
import com.example.administrator.demo.adapter.CommonAdapter;
import com.example.administrator.demo.adapter.ViewHolder;
import com.example.administrator.demo.entity.Friend;
import com.example.administrator.demo.utils.ImgConfig;
import com.example.administrator.demo.utils.XMPPConnUtils;
import com.example.administrator.demo.utils.XMPPLoadThread;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class ContentFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Unbinder unbinder;
    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    private CommonAdapter<Friend> adapter;
    private List<Friend> friendList = new ArrayList<>();


    private String mParam1;
    private String mParam2;
    private Context mContext;


    public ContentFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public static ContentFragment newInstance(String param1, String param2) {
        ContentFragment fragment = new ContentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_content, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        initView();
        return view;
    }

    private void initView() {
        adapter = new CommonAdapter<Friend>(mContext, R.layout.search_user_item_layout, friendList) {
            @Override
            public void setData(ViewHolder holder, Friend friend) {
                holder.setText(R.id.user_name, friend.username);
                Bitmap bitmap = ImgConfig.showHeadImg(friend.username);
                if (bitmap == null) {
                    holder.setImageResource(R.id.user_avatar, R.drawable.default_avatar);
                } else {
                    holder.setImageBitmap(R.id.user_avatar, bitmap);
                }

            }
        };
        rvContent.setLayoutManager(new LinearLayoutManager(mContext));
        rvContent.setAdapter(adapter);
    }

    private void initData() {
        new XMPPLoadThread(getActivity()) {

            @Override
            protected Object load() {
                return XMPPConnUtils.getInstance().getFriends();
            }

            @Override
            protected void result(Object object) {

                friendList.clear();
                friendList.addAll((List<Friend>) object);
                adapter.notifyDataSetChanged();


            }
        };
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
