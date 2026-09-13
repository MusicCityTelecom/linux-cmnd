/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.bean;

public class PmsAlarmStatus {
    private boolean alarmEnabled;
    private String alarmTime;

    public boolean isAlarmEnabled() {
        return this.alarmEnabled;
    }

    public void setAlarmEnabled(boolean alarmEnabled) {
        this.alarmEnabled = alarmEnabled;
    }

    public String getAlarmTime() {
        return this.alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }
}

