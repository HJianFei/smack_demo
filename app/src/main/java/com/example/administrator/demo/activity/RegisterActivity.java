package com.example.administrator.demo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.demo.R;
import com.example.administrator.demo.utils.ToastUtils;
import com.example.administrator.demo.utils.XmppConnectionUtils;
import com.example.administrator.demo.utils.XmppLoadThread;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
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
        new XmppLoadThread(this) {

            @Override
            protected Object load() {
                return XmppConnectionUtils.getInstance().register(userName, userPwd);
            }

            @Override
            protected void result(Object object) {
                boolean isSucccess = (boolean) object;
                if (isSucccess) {
                    ToastUtils.showMessage(RegisterActivity.this, "注册成功");
                    finish();
                } else {
                    ToastUtils.showMessage(RegisterActivity.this, "注册失败");
                }

            }
        };


    }
}
