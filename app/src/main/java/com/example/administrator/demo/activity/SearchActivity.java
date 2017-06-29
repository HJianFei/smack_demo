package com.example.administrator.demo.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.demo.R;
import com.example.administrator.demo.adapter.CommonAdapter;
import com.example.administrator.demo.adapter.OnItemClickListener;
import com.example.administrator.demo.adapter.ViewHolder;
import com.example.administrator.demo.entity.User;
import com.example.administrator.demo.utils.ImgConfig;
import com.example.administrator.demo.utils.ToastUtils;
import com.example.administrator.demo.utils.XMPPConnUtils;
import com.example.administrator.demo.utils.XMPPLoadThread;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.search_tip)
    EditText searchTip;
    @BindView(R.id.btn_search)
    Button btnSearch;
    @BindView(R.id.re_search_result)
    RecyclerView reSearchResult;
    private CommonAdapter<User> adapter;
    private List<User> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        //初始化ToolBar
        toolbar.setTitle("添加好友");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        //设配器
        adapter = new CommonAdapter<User>(this, R.layout.search_user_item_layout, list) {
            @Override
            public void setData(ViewHolder holder, User user) {

                holder.setText(R.id.user_name, user.username);
                holder.setText(R.id.user_nickname, user.nickname);
                Bitmap bitmap = ImgConfig.showHeadImg(user.username);
                if (bitmap == null) {
                    holder.setImageResource(R.id.user_avatar, R.drawable.default_avatar);
                } else {
                    holder.setImageBitmap(R.id.user_avatar, bitmap);
                }
            }
        };
        //item的点击事件
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                Intent intent = new Intent(SearchActivity.this, UserActivity.class);
                intent.putExtra("user", list.get(position));
                startActivity(intent);

            }
        });

        reSearchResult.setLayoutManager(new LinearLayoutManager(this));
        reSearchResult.setAdapter(adapter);
    }

    @OnClick(R.id.btn_search)
    public void onViewClicked() {
        if (TextUtils.isEmpty(searchTip.getText().toString())) {
            ToastUtils.showMessage(this, "名称不能为空");
            return;
        }
        //查询用户
        loadData(searchTip.getText().toString());

    }

    /**
     * 查询用户
     *
     * @param tip
     */
    private void loadData(final String tip) {
        new XMPPLoadThread(this) {

            @Override
            protected Object load() {//查询用户
                return XMPPConnUtils.getInstance().searchUser(tip);
            }

            @Override
            protected void result(Object object) {//数据填充
                List<User> listUser = (List<User>) object;
                list.clear();
                list.addAll(listUser);
                adapter.notifyDataSetChanged();
            }
        };
    }
}
