package com.example.administrator.demo.utils;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.XMPPConnection;

/**
 * 连接监听
 */
public class XMPPConnListener implements ConnectionListener {
    @Override
    public void connected(XMPPConnection xmppConnection) {

    }

    @Override
    public void authenticated(XMPPConnection xmppConnection, boolean b) {

    }

    @Override
    public void connectionClosed() {
        XMPPConnUtils.getInstance().setNull();

    }

    @Override
    public void connectionClosedOnError(Exception e) {

        LogUtil.d("onResponse", e.toString());

        if (e.getMessage().contains("conflict")) {//登录冲突

            LogUtil.d("onResponse", "登录冲突");

        }

    }

    @Override
    public void reconnectionSuccessful() {

    }

    @Override
    public void reconnectingIn(int i) {

    }

    @Override
    public void reconnectionFailed(Exception e) {

    }


}
