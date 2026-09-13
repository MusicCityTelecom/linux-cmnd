/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao.core;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="password_reset")
public class PasswordReset {
    public static final short TYPE_EMAIL = 0;
    public static final short TYPE_TICKET = 1;
    public static final short TYPE_WEAK = 2;
    public static final short STATUS_PROCESS = 0;
    public static final short STATUS_COMPLETE = 1;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="reset_id")
    private int resetId;
    @Column(name="username")
    private String username;
    @Column(name="type")
    private short type;
    @Column(name="info")
    private String info;
    @Column(name="start_ip")
    private String startIp;
    @Column(name="start_time")
    private Date startTime;
    @Column(name="status")
    private short status;
    @Column(name="end_time")
    private Date endTime;

    public int getResetId() {
        return this.resetId;
    }

    public void setResetId(int resetId) {
        this.resetId = resetId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public short getType() {
        return this.type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public String getInfo() {
        return this.info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getStartIp() {
        return this.startIp;
    }

    public void setStartIp(String startIp) {
        this.startIp = startIp;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public short getStatus() {
        return this.status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}

