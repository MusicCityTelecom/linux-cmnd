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
@Table(name="smartcms_setting")
public class SmartcmsSetting {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="setting_id")
    private int settingId;
    @Column(name="smartcms_id")
    private int smartcmsId;
    @Column(name="created_by")
    private String createdBy;
    @Column(name="last_updated_by")
    private String lastUpdatedBy;
    @Column(name="created_date")
    private Date createdDate;
    @Column(name="last_updated_date")
    private Date lastUpdatedDate;
    @Column(name="name")
    private String name;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSettingId() {
        return this.settingId;
    }

    public void setSettingId(int settingId) {
        this.settingId = settingId;
    }

    public int getSmartcmsId() {
        return this.smartcmsId;
    }

    public void setSmartcmsId(int smartcmsId) {
        this.smartcmsId = smartcmsId;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastUpdatedBy() {
        return this.lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastUpdatedDate() {
        return this.lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

