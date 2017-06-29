package com.example.administrator.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.demo.R;
import com.example.administrator.demo.utils.ToastUtils;
import com.example.administrator.demo.utils.XMPPConnUtils;
import com.example.administrator.demo.utils.XMPPLoadThread;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_id)
    EditText loginId;
    @BindView(R.id.login_pwd)
    EditText loginPwd;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_reg)
    Button btnReg;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        //初始化ToolBar
        toolbar.setTitle("用户登录");
    }

    @OnClick({R.id.btn_login, R.id.btn_reg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login://登录操作
                if (TextUtils.isEmpty(loginId.getText().toString())) {
                    ToastUtils.showMessage(LoginActivity.this, "用户名不能为空");
                    return;
                }
                if (TextUtils.isEmpty(loginPwd.getText().toString())) {
                    ToastUtils.showMessage(LoginActivity.this, "密码不能为空");
                    return;
                }
                //用户登录逻辑
                doLoginAction(loginId.getText().toString(), loginPwd.getText().toString());

                break;
            case R.id.btn_reg://跳转注册页面
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 用户登录
     *
     * @param userName
     * @param userPwd
     */
    private void doLoginAction(final String userName, final String userPwd) {
        new XMPPLoadThread(this) {
            @Override
            protected Object load() {
                //处理登录逻辑
                return XMPPConnUtils.getInstance().login(userName, userPwd);
            }

            @Override
            protected void result(Object object) {
                boolean isSuccess = (boolean) object;
                if (isSuccess) {//登录成功，跳转主页面
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }
}
