package com.example.administrator.demo.utils;

import android.graphics.Bitmap;

import org.jivesoftware.smackx.vcardtemp.packet.VCard;

public class ImgConfig {

    public static Bitmap showHeadImg(String username) {

        if (username == null) {
            return null;
        }
        VCard vCard = XMPPConnUtils.getInstance().getUserInfo(username);
        if (vCard != null) {
            String avatar = vCard.getField("avatar");
            if (avatar != null) {
                return ImageUtil.getBitmapFromBase64String(avatar);
            }
        }
        return null;

    }
}
