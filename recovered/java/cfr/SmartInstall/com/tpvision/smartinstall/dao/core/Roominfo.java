/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="roominfo")
public class Roominfo {
    @Id
    @Column(name="id")
    private String id;
    @Column(name="roomid")
    private String roomid;
    @Column(name="status")
    private String status;
    @Column(name="description")
    private String description;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoomid() {
        return this.roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

