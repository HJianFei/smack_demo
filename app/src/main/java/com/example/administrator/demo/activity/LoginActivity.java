package com.example.administrator.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.demo.R;
import com.example.administrator.demo.utils.LogUtil;
import com.example.administrator.demo.utils.ToastUtils;
import com.example.administrator.demo.utils.XmppConnectionUtils;
import com.example.administrator.demo.utils.XmppLoadThread;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_login, R.id.btn_reg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                if (TextUtils.isEmpty(loginId.getText().toString())) {
                    ToastUtils.showMessage(LoginActivity.this, "用户名不能为空");
                    return;
                }
                if (TextUtils.isEmpty(loginPwd.getText().toString())) {
                    ToastUtils.showMessage(LoginActivity.this, "密码不能为空");
                    return;
                }
                doLoginAction(loginId.getText().toString(), loginPwd.getText().toString());

                break;
            case R.id.btn_reg:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void doLoginAction(final String userName, final String userPwd) {
        new XmppLoadThread(this) {
            @Override
            protected Object load() {
                return XmppConnectionUtils.getInstance().login(userName, userPwd);
            }

            @Override
            protected void result(Object object) {
                boolean isSuccess = (boolean) object;
                LogUtil.d("onResponse", isSuccess + ":");
                if (isSuccess) {
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
