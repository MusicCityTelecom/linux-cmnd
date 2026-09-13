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
@Table(name="schedule")
public class Schedule {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @Column(name="name")
    private String name;
    @Column(name="platform")
    private String platform;
    @Column(name="schedule")
    private String schedule;
    @Column(name="content")
    private String content;
    @Column(name="lastEdit")
    private String lastEdit;
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

    public String getSchedule() {
        return this.schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLastEdit() {
        return this.lastEdit;
    }

    public void setLastEdit(String lastEdit) {
        this.lastEditModified = true;
        this.lastEdit = lastEdit;
    }

    public boolean isLastEditModified() {
        return this.lastEditModified;
    }
}

