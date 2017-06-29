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
import com.example.administrator.demo.anyevent.UserEvent;
import com.example.administrator.demo.utils.ImageUtil;
import com.example.administrator.demo.utils.ToastUtils;
import com.example.administrator.demo.utils.XMPPConnUtils;

import org.greenrobot.eventbus.EventBus;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FriendActivity extends AppCompatActivity {

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
    @BindView(R.id.btn_del)
    Button btnDel;
    private String user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        user_name = getIntent().getStringExtra("user_name");
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        VCard userInfo = XMPPConnUtils.getInstance().getUserInfo(user_name);
        String nickname = userInfo.getField("nickname");
        String phone = userInfo.getField("phone");
        String email = userInfo.getField("email");
        String avatar = userInfo.getField("avatar");
        userName.setText(user_name);
        userNickname.setText(nickname);
        userPhone.setText(phone);
        userEmail.setText(email);
        Bitmap bitmap = ImageUtil.getBitmapFromBase64String(avatar);
        if (bitmap != null) {
            userAvatar.setImageBitmap(bitmap);
        } else {
            userAvatar.setImageResource(R.drawable.default_avatar);
        }

    }

    private void initView() {

        toolbar.setTitle("好友详情");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @OnClick(R.id.btn_del)
    public void onViewClicked() {
        boolean delFriend = XMPPConnUtils.getInstance().delFriend(user_name);
        if (delFriend) {
            ToastUtils.showMessage(this, "删除成功");
            EventBus.getDefault().post(new UserEvent("删除好友成功", 2));
            this.finish();
        } else {
            ToastUtils.showMessage(this, "删除失败，稍后再试");
        }
    }
}
