package com.example.administrator.demo.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.demo.R;
import com.example.administrator.demo.adapter.CommonAdapter;
import com.example.administrator.demo.adapter.ViewHolder;
import com.example.administrator.demo.utils.ToastUtils;
import com.example.administrator.demo.utils.XmppConnectionUtils;
import com.example.administrator.demo.utils.XmppLoadThread;

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
    private CommonAdapter<String> adapter;
    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        toolbar.setTitle("添加好友");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        adapter = new CommonAdapter<String>(this, R.layout.search_user_item_layout, list) {
            @Override
            public void setData(ViewHolder holder, String s) {

                holder.setText(R.id.user_name, s);
            }
        };
        reSearchResult.setLayoutManager(new LinearLayoutManager(this));
        reSearchResult.setAdapter(adapter);
    }

    @OnClick(R.id.btn_search)
    public void onViewClicked() {
        if (TextUtils.isEmpty(searchTip.getText().toString())) {
            ToastUtils.showMessage(this, "名称不能为空");
            return;
        }
        loadData(searchTip.getText().toString());

    }

    private void loadData(final String tip) {
        new XmppLoadThread(this) {

            @Override
            protected Object load() {
                return XmppConnectionUtils.getInstance().searchUser(tip);
            }

            @Override
            protected void result(Object object) {
                List<String> listUser = (List<String>) object;
                list.clear();
                list.addAll(listUser);
                adapter.notifyDataSetChanged();
            }
        };
    }
}
