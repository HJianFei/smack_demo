package com.example.administrator.demo.utils;

import android.graphics.Bitmap;

import com.example.administrator.demo.constants.Constants;
import com.example.administrator.demo.entity.Friend;
import com.example.administrator.demo.entity.User;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.filter.StanzaTypeFilter;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smack.util.stringencoder.Base64;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.jivesoftware.smackx.search.ReportedData;
import org.jivesoftware.smackx.search.UserSearchManager;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;
import org.jivesoftware.smackx.vcardtemp.provider.VCardProvider;
import org.jivesoftware.smackx.xdata.Form;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Created by Administrator on 2017/6/17.
 */

public class XMPPConnUtils {


    private static XMPPTCPConnection xmppConnection = null;
    private static XMPPConnUtils connectionUtils;
    private static List<Friend> friendList = new ArrayList<>();
    public Roster roster;
    private XMPPConnListener XMPPConnListener;


    /**
     * 单例模式
     *
     * @return
     */
    public static XMPPConnUtils getInstance() {
        if (connectionUtils == null) {
            connectionUtils = new XMPPConnUtils();
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
                XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                        .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)//越过证书
                        .setSendPresence(true)// support presence
//                        .setUsernameAndPassword("账号","密码") // 构建的时候先传入账号密码，当连接成功的时候会自动进行授权登录
                        .setHost(Constants.SERVER_HOST)//IP地址
                        .setPort(Constants.SERVER_PORT)//端口
                        .setServiceName(Constants.SERVER_NAME)//域名
                        .setDebuggerEnabled(true)//debug模式
                        .build();
                xmppConnection = new XMPPTCPConnection(config);
                xmppConnection.connect();
                //连接监听
                XMPPConnListener = new XMPPConnListener();
                xmppConnection.addConnectionListener(XMPPConnListener);
                //用户状态监听
                xmppConnection.addAsyncStanzaListener(new XMPPPresenceListener(), new StanzaTypeFilter(Presence.class));
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.d("onResponse", e.toString());
            xmppConnection = null;
            return false;
        }
        return false;
    }


    /**
     * 关闭连接
     */
    public void closeConnection() {

        if (xmppConnection != null) {
            xmppConnection.removeConnectionListener(XMPPConnListener);
            ProviderManager.removeIQProvider("apace", "APACE");
            try {
                xmppConnection.disconnect();
            } catch (Exception e) {

                LogUtil.d("onResponse", e.getMessage());
                e.printStackTrace();
            } finally {
                xmppConnection = null;
                connectionUtils = null;
            }
        }
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
                getConnection().login(account, password, "Android");//多点登陆(设置第三个参数)
                // // 更改在线状态
                Presence presence = new Presence(Presence.Type.available);
                presence.setMode(Presence.Mode.available);
                getConnection().sendPacket(presence);
                roster = Roster.getInstanceFor(getConnection());
                roster.setSubscriptionMode(Roster.SubscriptionMode.manual);// 设置对方添加自己好友，需要询问

                return true;
            } catch (Exception e) {
                e.printStackTrace();
                LogUtil.d("onResponse", e.toString());
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
            accountManager.createAccount(account, password);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.d("onResponse", e.toString());
            return false;
        }
    }

    /**
     * 搜索好友
     *
     * @param key
     * @return
     */
    public List<User> searchUser(String key) {
        List<User> userList = new ArrayList<>();
        User user;
        try {
            UserSearchManager search = new UserSearchManager(getConnection());
            Form searchForm = search.getSearchForm("search." + Constants.SERVER_NAME);
            Form answerForm = searchForm.createAnswerForm();
            //搜索条件：用户名、昵称、邮箱
            answerForm.setAnswer("Username", true);
            answerForm.setAnswer("Name", true);
            answerForm.setAnswer("Email", true);
            answerForm.setAnswer("search", key);
            ReportedData data = search.getSearchResults(answerForm, "search." + Constants.SERVER_NAME);
            List<ReportedData.Row> rows = data.getRows();
            for (ReportedData.Row row : rows) {
                String username = row.getValues("Username").toString();
                String nickname = row.getValues("Name").toString();
                String email = row.getValues("Email").toString();

                user = new User(username.substring(1, username.length() - 1), nickname.substring(1, nickname.length() - 1), email.substring(1, email.length() - 1));
                userList.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.d("onResponse", e.toString());
        }
        return userList;
    }


    /**
     * 添加好友
     *
     * @param userName
     * @return
     */
    public boolean addUser(String userName) {

        Roster roster = Roster.getInstanceFor(getConnection());
        try {
            LogUtil.d("onResponse", getFullUsername(userName));
            roster.createEntry(getFullUsername(userName), userName, null);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.d("onResponse", e.toString());
            return false;
        }
    }

    /**
     * 删除好友
     *
     * @param userName
     * @return
     */
    public boolean delFriend(String userName) {

        try {
            Roster roster = Roster.getInstanceFor(getConnection());
            RosterEntry entry = roster.getEntry(getFullUsername(userName));
            roster.removeEntry(entry);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.d("onResponse", e.toString());
            return false;
        }
    }

    /**
     * 处理好友请求
     *
     * @param fromId
     * @param flag
     * @return
     */
    public boolean handleAsk(String fromId, boolean flag) {

        try {
            Presence pres;
            if (flag) {//同意好友请求
                pres = new Presence(Presence.Type.subscribed);
            } else {//拒绝好友请求
                pres = new Presence(Presence.Type.unsubscribed);
            }
            pres.setTo(getFullUsername(fromId));
            getConnection().sendStanza(pres);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.d("onResponse", e.toString());
            return false;

        }
    }

    /**
     * 获取好友
     */
    public List<Friend> getFriends() {
        friendList.clear();
        if (roster == null) {
            roster = Roster.getInstanceFor(getConnection());
        }
        Collection<RosterEntry> entries = roster.getEntries();

        for (RosterEntry entry : entries) {
            friendList.add(new Friend(getUsername(entry.getUser()), entry.getType()));
        }
        return friendList;
    }

    /**
     * 获取用户信息
     *
     * @param user
     * @return
     */

    public VCard getUserInfo(String user) {  //null 时查自己
        try {
            VCard vcard = new VCard();
            // 加入这句代码，解决No VCard for
            ProviderManager.addIQProvider("vCard", "vcard-temp", new VCardProvider());
            if (user == null) {
                vcard.load(getConnection());
            } else {
                vcard.load(getConnection(), getFullUsername(user));
            }
            if (vcard != null)
                return vcard;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.d("onResponse", e.toString());
        }
        return null;
    }

    /**
     * 修改用户信息
     *
     * @param vcard
     */
    public boolean changeVcard(VCard vcard) {
        if (getConnection() == null)
            return false;
        try {
            // 加入这句代码，解决No VCard for
            ProviderManager.addIQProvider("vCard", "vcard-temp", new VCardProvider());
            vcard.save(getConnection());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 修改用户头像
     *
     * @param file
     */
    public Bitmap changeImage(File file) {
        Bitmap bitmap = null;
        if (getConnection() == null)
            return bitmap;
        try {
            VCard vcard = getUserInfo(null);
            // 加入这句代码，解决No VCard for
            ProviderManager.addIQProvider("vCard", "vcard-temp", new VCardProvider());
            byte[] bytes;
            bytes = ImageUtil.getFileBytes(file);
            //Base64加密
            String encodedImage = Base64.encodeToString(bytes);
            vcard.setField("avatar", encodedImage);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            bitmap = FormatTools.getInstance().InputStream2Bitmap(bais);
            vcard.save(getConnection());

        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.d("onResponse", e.toString());

        }
        return bitmap;
    }

    /**
     * 通过username获得jid
     *
     * @param username
     * @return
     */
    public static String getFullUsername(String username) {

        return username + "@" + Constants.SERVER_NAME;
    }

    /**
     * 通过jid获得username
     *
     * @param fullUsername
     * @return
     */
    public static String getUsername(String fullUsername) {
        return fullUsername.split("@")[0];
    }

    /**
     * 设置XmppConnection为空
     */

    public void setNull() {
        xmppConnection = null;
    }


}
