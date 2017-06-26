package com.example.administrator.demo.utils;

import android.util.Log;

import com.example.administrator.demo.constants.Constants;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.jxmpp.jid.parts.Localpart;

import java.net.InetAddress;


/**
 * Created by Administrator on 2017/6/17.
 */

public class XmppConnectionUtils {


    private static XMPPTCPConnection xmppConnection = null;
    private static XmppConnectionUtils connectionUtils;


    /**
     * 单例模式
     *
     * @return
     */
    public static XmppConnectionUtils getInstance() {
        if (connectionUtils == null) {
            connectionUtils = new XmppConnectionUtils();
        }
        return connectionUtils;
    }

    /**
     * 创建连接
     */
    public XMPPTCPConnection getConnection() {
        if (xmppConnection == null) {

            //打开XMPPTCPConnection连接
            openXMPPTCPConnection();
        }
        return xmppConnection;
    }

    /**
     * 打开XMPPTCPConnection连接
     */
    private boolean openXMPPTCPConnection() {

        try {
            if (null == xmppConnection || !xmppConnection.isAuthenticated()) {
                InetAddress inetAddress = InetAddress.getByName(Constants.SERVER_HOST);
                XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                        .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                        .setHostAddress(inetAddress)
                        .setPort(Constants.SERVER_PORT)
                        .setXmppDomain(Constants.SERVER_NAME)
                        .build();
                xmppConnection = new XMPPTCPConnection(config);
                xmppConnection.connect();
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("onResponse", e.toString());
            xmppConnection = null;
            return false;
        }
        return false;
    }

    /**
     * 登录
     *
     * @param account  登录帐号
     * @param password 登录密码
     * @return
     */
    public boolean login(String account, String password) {

        if (getConnection() == null) {
            return false;
        }
        if (!getConnection().isAuthenticated() && getConnection().isConnected()) {

            try {
                getConnection().login(account, password);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("onResponse", e.toString());
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 注册
     *
     * @param account  注册帐号
     * @param password 注册密码
     * @return true 注册成功 false 注册失败
     */
    public boolean register(String account, String password) {
        if (getConnection() == null) {
            return false;
        }
        try {
            AccountManager accountManager = AccountManager.getInstance(getConnection());
            accountManager.sensitiveOperationOverInsecureConnection(true);
            accountManager.createAccount(Localpart.from(account), password);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.d("onResponse", e.toString());
            return false;
        }
    }

    /**
     * 设置为空
     */

    public void setNull() {
        xmppConnection = null;
    }
}
