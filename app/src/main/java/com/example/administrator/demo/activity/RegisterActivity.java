package com.example.administrator.demo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.demo.R;
import com.example.administrator.demo.utils.ToastUtils;
import com.example.administrator.demo.utils.XMPPConnUtils;
import com.example.administrator.demo.utils.XMPPLoadThread;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.user_name)
    EditText userName;
    @BindView(R.id.user_pwd)
    EditText userPwd;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        //页面初始化
        initView();
    }

    /**
     * 页面初始化
     */
    private void initView() {
        //初始化ToolBar
        toolbar.setTitle("用户注册");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @OnClick(R.id.btn_register)
    public void onViewClicked() {
        if (TextUtils.isEmpty(userName.getText().toString())) {
            ToastUtils.showMessage(this, "用户名不能为空");
            return;
        }
        if (TextUtils.isEmpty(userPwd.getText().toString())) {
            ToastUtils.showMessage(this, "密码不能为空");
            return;
        }
        registerAction(userName.getText().toString(), userPwd.getText().toString());
    }

    private void registerAction(final String userName, final String userPwd) {
        new XMPPLoadThread(this) {

            @Override
            protected Object load() {//用户注册逻辑
                return XMPPConnUtils.getInstance().register(userName, userPwd);
            }

            @Override
            protected void result(Object object) {
                boolean isSuccess = (boolean) object;
                if (isSuccess) {
                    ToastUtils.showMessage(RegisterActivity.this, "注册成功");
                    finish();
                } else {
                    ToastUtils.showMessage(RegisterActivity.this, "注册失败");
                }

            }
        };


    }
}
