/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="cast_server_setting")
public class CastServerSetting {
    @Id
    @Column(name="id")
    private int id;
    @Column(name="activation_code")
    private String activationCode;
    @Column(name="serial_number")
    private String serialNumber;
    @Column(name="mac_address")
    private String macAddress;
    @Column(name="hotel_id")
    private String hotelId;
    @Column(name="api_user")
    private String apiUser;
    @Column(name="api_password")
    private String apiPassword;
    @Column(name="property_id")
    private String propertyId;
    @Column(name="guest_wlan_ip")
    private String guestWlanIp = "";
    @Column(name="background_path")
    private String backgroundPath = "";
    @Column(name="ssid_name")
    private String ssidName = "";
    @Column(name="guest_dns_name")
    private String guestDnsName = "";
    @Column(name="show_url")
    private String showUrl = "false";

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActivationCode() {
        return this.activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getMacAddress() {
        return this.macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getHotelId() {
        return this.hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getApiUser() {
        return this.apiUser;
    }

    public void setApiUser(String apiUser) {
        this.apiUser = apiUser;
    }

    public String getApiPassword() {
        return this.apiPassword;
    }

    public void setApiPassword(String apiPassword) {
        this.apiPassword = apiPassword;
    }

    public String getPropertyId() {
        return this.propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getGuestWlanIp() {
        return this.guestWlanIp;
    }

    public void setGuestWlanIp(String guestWlanIp) {
        this.guestWlanIp = guestWlanIp;
    }

    public String getBackgroundPath() {
        return this.backgroundPath;
    }

    public void setBackgroundPath(String backgroundPath) {
        this.backgroundPath = backgroundPath;
    }

    public String getSsidName() {
        return this.ssidName;
    }

    public void setSsidName(String ssidName) {
        this.ssidName = ssidName;
    }

    public String getGuestDnsName() {
        return this.guestDnsName;
    }

    public void setGuestDnsName(String guestDnsName) {
        this.guestDnsName = guestDnsName;
    }

    public String getShowUrl() {
        return this.showUrl;
    }

    public void setShowUrl(String showUrl) {
        this.showUrl = showUrl;
    }
}

