package com.example.administrator.demo.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.demo.R;
import com.example.administrator.demo.entity.Friend;
import com.example.administrator.demo.utils.ImgConfig;
import com.example.administrator.demo.utils.XMPPConnUtils;
import com.example.administrator.demo.utils.XMPPLoadThread;

import org.jivesoftware.smackx.vcardtemp.packet.VCard;

import java.io.File;

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
    @BindView(R.id.user_phone)
    TextView userPhone;
    @BindView(R.id.user_email)
    TextView userEmail;
    @BindView(R.id.btn_add_or_del)
    Button btnAddOrDel;
    private String user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        user_name = getIntent().getStringExtra("user_name");
        ButterKnife.bind(this);
        //数据初始化
        initData();
        //页面初始化
        initView();
    }

    /**
     * 数据初始化
     */
    private void initData() {
        new XMPPLoadThread(this) {

            @Override
            protected Object load() {
                return XMPPConnUtils.getInstance().getUserInfo(user_name);
            }

            @Override
            protected void result(Object object) {
                VCard vCard = (VCard) object;
                userName.setText(vCard.getField("Name"));
                userNickname.setText(vCard.getField("nickName"));
                userPhone.setText(vCard.getField("mobile"));
                userEmail.setText(vCard.getField("email"));
                userAvatar.setImageBitmap(ImgConfig.showHeadImg(user_name));

            }
        };

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
        if (isFriend(user_name)) {
            btnAddOrDel.setText("删除好友");
        } else {
            btnAddOrDel.setText("添加好友");
        }
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
    @OnClick(R.id.btn_add_or_del)
    public void onViewClicked() {
//        if (!isFriend(user_name)) {//添加好友
//            XMPPConnUtils.getInstance().addUser(user_name);
//        } else {//删除好友
//            XMPPConnUtils.getInstance().delFriend(user_name);
//        }
        Bitmap bitmap = XMPPConnUtils.getInstance().changeImage(new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "bitmap.png"));


    }
}
