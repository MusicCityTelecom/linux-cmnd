/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.pms;

public class CheckInVO {
    private String displayName;
    private String firstName;
    private String surName;
    private boolean guestDetailsEnabled;
    private boolean guestPreferLanguageEnabled;
    private boolean messagesEnabled;
    private String donotDisturb;
    private String viewBill;
    private String expressCheckout;
    private String language;
    private String roomStatus;
    private String sharingStatus;
    private String arrivalDate;
    private String arrivalTime;
    private String departureDate;
    private String departureTime;
    private String groupName;
    private boolean isDemo;

    public boolean isDemo() {
        return this.isDemo;
    }

    public void setDemo(boolean isDemo) {
        this.isDemo = isDemo;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return this.surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public boolean isGuestDetailsEnabled() {
        return this.guestDetailsEnabled;
    }

    public void setGuestDetailsEnabled(boolean guestDetails) {
        this.guestDetailsEnabled = guestDetails;
    }

    public boolean isGuestPreferLanguageEnabled() {
        return this.guestPreferLanguageEnabled;
    }

    public void setGuestPreferLanguageEnabled(boolean guestPreferLanguage) {
        this.guestPreferLanguageEnabled = guestPreferLanguage;
    }

    public boolean isMessagesEnabled() {
        return this.messagesEnabled;
    }

    public void setMessageEnabled(boolean messages) {
        this.messagesEnabled = messages;
    }

    public String getDonotDisturb() {
        return this.donotDisturb;
    }

    public void setDonotDisturb(String donotDisturb) {
        this.donotDisturb = donotDisturb;
    }

    public String getViewBill() {
        return this.viewBill;
    }

    public void setViewBill(String viewBill) {
        this.viewBill = viewBill;
    }

    public String getExpressCheckout() {
        return this.expressCheckout;
    }

    public void setExpressCheckout(String expressCheckout) {
        this.expressCheckout = expressCheckout;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getRoomStatus() {
        return this.roomStatus;
    }

    public void setRoomStatus(String roomStatus) {
        this.roomStatus = roomStatus;
    }

    public String getSharingStatus() {
        return this.sharingStatus;
    }

    public void setSharingStatus(String sharingStatus) {
        this.sharingStatus = sharingStatus;
    }

    public String getArrivalDate() {
        return this.arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getArrivalTime() {
        return this.arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getDepartureDate() {
        return this.departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getDepartureTime() {
        return this.departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}

