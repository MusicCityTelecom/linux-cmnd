/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao.core;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="rf_playedout_setting")
public class RfPlayedoutSetting {
    @Id
    @Column(name="id")
    private int id;
    @Column(name="value")
    private String value;
    @Column(name="created_by")
    private String createdBy;
    @Column(name="created_date")
    private Date createdDate;
    @Column(name="last_updated_by")
    private String lastUpdatedBy;
    @Column(name="last_updated_date")
    private Date lastUpdatedDate;
    @Column(name="rf_played_on")
    private Date rfPlayedOn;
    @Column(name="rf_played_by")
    private String rfPlayedBy;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastUpdatedBy() {
        return this.lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Date getLastUpdatedDate() {
        return this.lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Date getRfPlayedOn() {
        return this.rfPlayedOn;
    }

    public void setRfPlayedOn(Date rfPlayedOn) {
        this.rfPlayedOn = rfPlayedOn;
    }

    public String getRfPlayedBy() {
        return this.rfPlayedBy;
    }

    public void setRfPlayedBy(String rfPlayedBy) {
        this.rfPlayedBy = rfPlayedBy;
    }
}

