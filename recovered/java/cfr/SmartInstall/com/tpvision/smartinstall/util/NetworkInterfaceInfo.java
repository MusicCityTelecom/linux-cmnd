/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.util;

public class NetworkInterfaceInfo {
    private String networksegment;
    private String firstaddress;
    private String lastaddress;
    private int networkprefixlength;
    private String displayName;
    private String hostAddress;

    public String getNetworksegment() {
        return this.networksegment;
    }

    public void setNetworksegment(String networksegment) {
        this.networksegment = networksegment;
    }

    public String getFirstaddress() {
        return this.firstaddress;
    }

    public void setFirstaddress(String firstaddress) {
        this.firstaddress = firstaddress;
    }

    public String getLastaddress() {
        return this.lastaddress;
    }

    public void setLastaddress(String lastaddress) {
        this.lastaddress = lastaddress;
    }

    public int getNetworkprefixlength() {
        return this.networkprefixlength;
    }

    public void setNetworkprefixlength(int networkprefixlength) {
        this.networkprefixlength = networkprefixlength;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getHostAddress() {
        return this.hostAddress;
    }

    public void setHostAddress(String hostAddress) {
        this.hostAddress = hostAddress;
    }
}

