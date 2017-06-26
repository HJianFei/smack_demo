package com.example.administrator.demo.utils;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.XMPPConnection;


public class XmppConnecionListener implements ConnectionListener {
    @Override
    public void connected(XMPPConnection xmppConnection) {

    }

    @Override
    public void authenticated(XMPPConnection xmppConnection, boolean b) {

    }

    @Override
    public void connectionClosed() {
        XmppConnectionUtils.getInstance().setNull();

    }

    @Override
    public void connectionClosedOnError(Exception e) {

//        if (e.getMessage().contains("conflict")) {
//            MyAndroidUtil.removeXml(Constants.LOGIN_PWD);
//            if (!MyApplication.sharedPreferences.getBoolean(Constants.LOGIN_CHECK, false)) {
//                MyAndroidUtil.removeXml(Constants.LOGIN_ACCOUNT);
//            }
//            Constants.USER_NAME = "";
//            Constants.loginUser = null;
//            XmppConnection.getInstance().closeConnection();
//            MyApplication.getInstance().sendBroadcast(new Intent("conflict"));
//            //跳转
//            Intent intent = new Intent();
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.putExtra("isRelogin", true);
//            intent.setClass(MyApplication.getInstance(), LoginActivity.class);
//            MyApplication.getInstance().startActivity(intent);
//        }

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

//    @Override
//    public void connected(XMPPConnection xmppConnection) {
//
//    }
//
//    @Override
//    public void authenticated(XMPPConnection xmppConnection, boolean b) {
//
//    }
//
//    @Override
//    public void connectionClosed() {
//        Log.e("smack xmpp", "close");
//        XmppConnectionUtils.getInstance().setNull();
//    }
//
//    @Override
//    public void connectionClosedOnError(Exception e) {
////		Log.e("smack xmpp", e.getMessage());
//        if (e.getMessage().contains("conflict")) {
//            MyAndroidUtil.removeXml(Constants.LOGIN_PWD);
//            if (!MyApplication.sharedPreferences.getBoolean(Constants.LOGIN_CHECK, false)) {
//                MyAndroidUtil.removeXml(Constants.LOGIN_ACCOUNT);
//            }
//            Constants.USER_NAME = "";
//            Constants.loginUser = null;
//            XmppConnection.getInstance().closeConnection();
//            MyApplication.getInstance().sendBroadcast(new Intent("conflict"));
//            //跳转
//            Intent intent = new Intent();
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.putExtra("isRelogin", true);
//            intent.setClass(MyApplication.getInstance(), LoginActivity.class);
//            MyApplication.getInstance().startActivity(intent);
//        }
//    }
//
//    @Override
//    public void reconnectingIn(int seconds) {
////		Log.e("smack xmpp", "reconing:"+seconds);
//    }
//
//    @Override
//    public void reconnectionSuccessful() {
//        XmppConnection.getInstance().loadFriendAndJoinRoom();
////		Log.e("smack xmpp", "suc");
//    }
//
//    @Override
//    public void reconnectionFailed(Exception e) {
////		Log.e("smack xmpp", "re fail");
//    }


}
