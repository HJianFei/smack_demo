package com.example.administrator.demo.utils;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.XMPPConnection;

/**
 * 连接监听
 */
public class XMPPConnListener implements ConnectionListener {
    @Override
    public void connected(XMPPConnection xmppConnection) {//连接成功回调

    }

    @Override
    public void authenticated(XMPPConnection xmppConnection, boolean b) {// 登录鉴权回调

    }

    @Override
    public void connectionClosed() {//连接被关闭回调
        XMPPConnUtils.getInstance().setNull();

    }

    @Override
    public void connectionClosedOnError(Exception e) {//连接被关闭出现异常回调

        LogUtil.d("onResponse", e.toString());

        if (e.getMessage().contains("conflict")) {//登录冲突

            LogUtil.d("onResponse", "登录冲突");

        }

    }

    @Override
    public void reconnectionSuccessful() {// 重新连接成功回调

    }

    @Override
    public void reconnectingIn(int i) {// 重新登录成功回调

    }

    @Override
    public void reconnectionFailed(Exception e) {// 重新连接失败回调

    }


}
