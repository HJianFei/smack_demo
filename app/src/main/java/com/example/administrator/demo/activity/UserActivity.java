package com.example.administrator.demo.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.demo.R;
import com.example.administrator.demo.entity.Friend;
import com.example.administrator.demo.entity.User;
import com.example.administrator.demo.utils.ImgConfig;
import com.example.administrator.demo.utils.XMPPConnUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.user_avatar)
    ImageView userAvatar;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.user_nickname)
    TextView userNickname;
    @BindView(R.id.user_email)
    TextView userEmail;
    @BindView(R.id.btn_add_or_del)
    Button btnAddOrDel;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        user = (User) getIntent().getSerializableExtra("user");
        ButterKnife.bind(this);
        //页面初始化
        initView();


    }

    /**
     * 初始化页面
     */
    private void initView() {
        toolbar.setTitle("用户详情");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        //按钮的文字显示
        if (isFriend(user.username)) {
            btnAddOrDel.setText("删除好友");
        } else {
            btnAddOrDel.setText("添加好友");
        }

        Bitmap bitmap = ImgConfig.showHeadImg(user.username);
        if (bitmap != null) {
            userAvatar.setImageBitmap(bitmap);
        } else {
            userAvatar.setImageResource(R.drawable.default_avatar);
        }
        userName.setText(user.username);
        userNickname.setText(user.nickname);
        userEmail.setText(user.email);
    }

    /**
     * 判断是否为好友关系
     *
     * @param user_name
     * @return
     */
    public boolean isFriend(String user_name) {

        return XMPPConnUtils.getInstance().getFriends().contains(new Friend(user_name));

    }

    //按钮点击事件
    @OnClick({R.id.btn_add_or_del, R.id.user_avatar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_add_or_del:
                if (!isFriend(user.username)) {//添加好友
                    XMPPConnUtils.getInstance().addUser(user.username);
                } else {//删除好友
                    XMPPConnUtils.getInstance().delFriend(user.username);
                }
                break;
            case R.id.user_avatar:

                break;
        }

    }
}
