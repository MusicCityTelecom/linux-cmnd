/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="pms_status")
public class PmsStatus {
    public static final int PMS_STATUS_ID = 1;
    @Id
    @Column(name="id")
    private int id;
    @Column(name="pms")
    private String pms = "off";
    @Column(name="pmstype")
    private String pmstype = "None";
    @Column(name="pmsconfigs")
    private String pmsconfigs;
    @Column(name="pmsurl")
    private String pmsurl;
    @Column(name="pmskey")
    private String pmskey;
    @Column(name="pmssite")
    private String pmssite;
    @Column(name="guestname")
    private String guestname = "Off";
    @Column(name="guestlanguage")
    private String guestlanguage = "Off";
    @Column(name="billontv")
    private String billontv = "Off";
    @Column(name="bill_icon")
    private String billIcon;
    @Column(name="welcomemessage")
    private String welcomemessage = "Off";
    @Column(name="messages")
    private String messages = "Off";
    @Column(name="message_icon")
    private String messageIcon;
    @Column(name="expresscheckout")
    private String expresscheckout = "Off";
    @Column(name="changechannelpackage")
    private String changechannelpackage = "Off";
    @Column(name="changeapppackage")
    private String changeapppackage = "Off";
    @Column(name="pmsconnectiontype")
    private String pmsconnectiontype;
    @Column(name="pmsconnectionversion")
    private String pmsconnectionversion = "0.0.0";
    @Column(name="pmsconnectionstatus")
    private String pmsconnectionstatus = "Error";
    @Column(name="pmsconnectioninfo")
    private String pmsconnectioninfo;
    @Column(name="currency")
    private String currency = "\\u20ac EUR";
    @Column(name="autoWakeUpTv")
    private String autoWakeUpTv = "Off";
    @Column(name="currencyPreference")
    private String currencyPreference = "CurrencySymbol";
    @Column(name="autoSwitchOffTv")
    private String autoSwitchOffTv = "Off";
    @Column(name="doNotDisturb")
    private String doNotDisturb = "Off";
    @Column(name="allowmultipleroom")
    private String allowMultipleRoom = "Yes";
    @Column(name="alarm_ringing_volume")
    private int alarmRingingVolume = 25;
    @Column(name="limit_network_interface")
    private String limit_network_interface = "Off";

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPms() {
        return this.pms;
    }

    public void setPms(String pms) {
        this.pms = pms;
    }

    public String getPmstype() {
        return this.pmstype;
    }

    public void setPmstype(String pmstype) {
        this.pmstype = pmstype;
    }

    public String getPmsconfigs() {
        return this.pmsconfigs;
    }

    public void setPmsconfigs(String pmsconfigs) {
        this.pmsconfigs = pmsconfigs;
    }

    public String getPmsurl() {
        return this.pmsurl;
    }

    public void setPmsurl(String pmsurl) {
        this.pmsurl = pmsurl;
    }

    public String getPmskey() {
        return this.pmskey;
    }

    public void setPmskey(String pmskey) {
        this.pmskey = pmskey;
    }

    public String getPmssite() {
        return this.pmssite;
    }

    public void setPmssite(String pmssite) {
        this.pmssite = pmssite;
    }

    public String getGuestname() {
        return this.guestname;
    }

    public void setGuestname(String guestname) {
        this.guestname = guestname;
    }

    public String getGuestlanguage() {
        return this.guestlanguage;
    }

    public void setGuestlanguage(String guestlanguage) {
        this.guestlanguage = guestlanguage;
    }

    public String getBillontv() {
        return this.billontv;
    }

    public void setBillontv(String billontv) {
        this.billontv = billontv;
    }

    public String getWelcomemessage() {
        return this.welcomemessage;
    }

    public void setWelcomemessage(String welcomemessage) {
        this.welcomemessage = welcomemessage;
    }

    public String getMessages() {
        return this.messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public String getExpresscheckout() {
        return this.expresscheckout;
    }

    public void setExpresscheckout(String expresscheckout) {
        this.expresscheckout = expresscheckout;
    }

    public String getChangechannelpackage() {
        return this.changechannelpackage;
    }

    public void setChangechannelpackage(String changechannelpackage) {
        this.changechannelpackage = changechannelpackage;
    }

    public String getChangeapppackage() {
        return this.changeapppackage;
    }

    public void setChangeapppackage(String changeapppackage) {
        this.changeapppackage = changeapppackage;
    }

    public String getPmsconnectiontype() {
        return this.pmsconnectiontype;
    }

    public void setPmsconnectiontype(String pmsconnectiontype) {
        this.pmsconnectiontype = pmsconnectiontype;
    }

    public String getPmsconnectionversion() {
        return this.pmsconnectionversion;
    }

    public void setPmsconnectionversion(String pmsconnectionversion) {
        this.pmsconnectionversion = pmsconnectionversion;
    }

    public String getPmsconnectionstatus() {
        return this.pmsconnectionstatus;
    }

    public void setPmsconnectionstatus(String pmsconnectionstatus) {
        this.pmsconnectionstatus = pmsconnectionstatus;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAutoWakeUpTv() {
        return this.autoWakeUpTv;
    }

    public void setAutoWakeUpTv(String autoWakeUpTv) {
        this.autoWakeUpTv = autoWakeUpTv;
    }

    public String getCurrencyPreference() {
        return this.currencyPreference;
    }

    public void setCurrencyPreference(String currencyPreference) {
        this.currencyPreference = currencyPreference;
    }

    public String getAutoSwitchOffTv() {
        return this.autoSwitchOffTv;
    }

    public void setAutoSwitchOffTv(String autoSwitchOffTv) {
        this.autoSwitchOffTv = autoSwitchOffTv;
    }

    public String getDoNotDisturb() {
        return this.doNotDisturb;
    }

    public void setDoNotDisturb(String doNotDisturb) {
        this.doNotDisturb = doNotDisturb;
    }

    public String getAllowMultipleRoom() {
        return this.allowMultipleRoom;
    }

    public void setAllowMultipleRoom(String allowMultipleRoom) {
        this.allowMultipleRoom = allowMultipleRoom;
    }

    public String getPmsconnectioninfo() {
        return this.pmsconnectioninfo;
    }

    public void setPmsconnectioninfo(String pmsconnectioninfo) {
        this.pmsconnectioninfo = pmsconnectioninfo;
    }

    public boolean isDemoBill() {
        return this.billontv.equalsIgnoreCase("Demo");
    }

    public String getBillIcon() {
        return this.billIcon;
    }

    public void setBillIcon(String billIcon) {
        this.billIcon = billIcon;
    }

    public String getMessageIcon() {
        return this.messageIcon;
    }

    public void setMessageIcon(String messageIcon) {
        this.messageIcon = messageIcon;
    }

    public int getAlarmRingingVolume() {
        return this.alarmRingingVolume;
    }

    public void setAlarmRingingVolume(int alarmRingingVolume) {
        this.alarmRingingVolume = alarmRingingVolume;
    }

    public String getLimitNetworkInterface() {
        return this.limit_network_interface;
    }

    public void setLimitNetworkInterface(String limit_network_interface) {
        this.limit_network_interface = limit_network_interface;
    }
}

