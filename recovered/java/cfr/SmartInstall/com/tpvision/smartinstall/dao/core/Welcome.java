/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="welcome")
public class Welcome {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="name")
    private String name;
    @Column(name="platform")
    private String platform;
    @Column(name="type")
    private int type;
    @Column(name="lastEdit")
    private String lastEdit;
    @Column(name="createdBy")
    private String createdBy;
    @Transient
    private boolean lastEditModified = false;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlatform() {
        return this.platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLastEdit() {
        return this.lastEdit;
    }

    public void setLastEdit(String lastEdit) {
        this.lastEditModified = true;
        this.lastEdit = lastEdit;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public boolean isLastEditModified() {
        return this.lastEditModified;
    }
}

