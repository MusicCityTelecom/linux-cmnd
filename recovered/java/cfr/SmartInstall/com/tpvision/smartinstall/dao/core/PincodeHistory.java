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
@Table(name="pincode_history")
public class PincodeHistory {
    public static final int SUCCESS_YES = 1;
    public static final int SUCCESS_NO = 0;
    public static final int DEACTIVATION_STATUS_YES = 1;
    public static final int DEACTIVATION_STATUS_NO = 0;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="room_id")
    private String roomId;
    @Column(name="package_name")
    private String packageName;
    @Column(name="days")
    private int days;
    @Column(name="stop_time")
    private String stopTime;
    @Column(name="pincode")
    private String pincode;
    @Column(name="success")
    private int success;
    @Column(name="result")
    private String result;
    @Column(name="create_time")
    private String createTime;
    @Column(name="deactivation_status")
    private int deactivationStatus;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoomId() {
        return this.roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getDays() {
        return this.days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getPincode() {
        return this.pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getSuccess() {
        return this.success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getStopTime() {
        return this.stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    public int getDeactivationStatus() {
        return this.deactivationStatus;
    }

    public void setDeactivationStatus(int deactivationStatus) {
        this.deactivationStatus = deactivationStatus;
    }
}

