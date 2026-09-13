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

@Entity
@Table(name="\"groups\"")
public class Groups {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="Id")
    private int id;
    @Column(name="GroupName")
    private String groupname;
    @Column(name="TVId")
    private String tvid;
    @Column(name="FirmwareId")
    private int firmwareid;
    @Column(name="CloneId")
    private int cloneid;
    @Column(name="Status")
    private String status;
    @Column(name="Progress")
    private String progress;
    @Column(name="TVStatus")
    private String tvstatus;
    @Column(name="PowerStatus")
    private String powerstatus;
    @Column(name="CreatedDate")
    private String createddate;
    @Column(name="ModifiedDate")
    private String modifieddate;
    @Column(name="clone_type")
    private String cloneType = "None";

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroupname() {
        return this.groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getTvid() {
        return this.tvid;
    }

    public void setTvid(String tvid) {
        this.tvid = tvid;
    }

    public int getFirmwareid() {
        return this.firmwareid;
    }

    public void setFirmwareid(int firmwareid) {
        this.firmwareid = firmwareid;
    }

    public int getCloneid() {
        return this.cloneid;
    }

    public void setCloneid(int cloneid) {
        this.cloneid = cloneid;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProgress() {
        return this.progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getTvstatus() {
        return this.tvstatus;
    }

    public void setTvstatus(String tvstatus) {
        this.tvstatus = tvstatus;
    }

    public String getPowerstatus() {
        return this.powerstatus;
    }

    public void setPowerstatus(String powerstatus) {
        this.powerstatus = powerstatus;
    }

    public String getCreateddate() {
        return this.createddate;
    }

    public void setCreateddate(String createddate) {
        this.createddate = createddate;
    }

    public String getModifieddate() {
        return this.modifieddate;
    }

    public void setModifieddate(String modifieddate) {
        this.modifieddate = modifieddate;
    }

    public String getCloneType() {
        return this.cloneType;
    }

    public void setCloneType(String cloneType) {
        this.cloneType = cloneType;
    }
}

