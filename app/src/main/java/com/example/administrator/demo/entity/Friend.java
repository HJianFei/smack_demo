package com.example.administrator.demo.entity;


import org.jivesoftware.smack.roster.packet.RosterPacket;

import java.io.Serializable;

public class Friend implements Serializable {
    public String id;
    public String username;
    public RosterPacket.ItemStatus status; // 为null则不在线
    public RosterPacket.ItemType type;
    public String mood;


    public Friend(String username) {
        super();
        this.username = username;
    }


    public Friend(String username, RosterPacket.ItemType type) {
        super();
        this.username = username;
        this.type = type;
    }

    public Friend(String id, String username, RosterPacket.ItemType type) {
        this.id = id;
        this.username = username;
        this.type = type;
    }

    public Friend(String id, String username, RosterPacket.ItemStatus status, String mood) {
        super();
        this.id = id;
        this.username = username;
        this.status = status;
        this.mood = mood;
    }

    @Override
    public boolean equals(Object obj) {
        boolean isEqual = false;
        if (obj instanceof Friend) {
            Friend t = (Friend) obj;
            isEqual = this.username.equals(t.username);
            return isEqual;
        }
        return super.equals(obj);
    }
}
