/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="message")
public class Message {
    @Id
    @Column(name="id")
    private String id;
    @Column(name="timeSend")
    private String timeSend;
    @Column(name="content")
    private String content;
    @Column(name="isSent")
    private String isSent;
    @Column(name="guestIds")
    private String guestIds;
    @Column(name="status")
    private String status = "New";
    @Column(name="title")
    private String title;
    @Column(name="icon")
    private String icon;
    @Column(name="msgId")
    private int msgId;

    public int getMsgId() {
        return this.msgId;
    }

    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimeSend() {
        return this.timeSend;
    }

    public void setTimeSend(String timeSend) {
        this.timeSend = timeSend;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIsSent() {
        return this.isSent;
    }

    public void setIsSent(String isSent) {
        this.isSent = isSent;
    }

    public String getGuestIds() {
        return this.guestIds;
    }

    public void setGuestIds(String guestIds) {
        this.guestIds = guestIds;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}

