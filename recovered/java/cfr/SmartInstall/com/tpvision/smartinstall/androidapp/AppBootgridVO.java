/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.androidapp;

import com.google.gson.JsonObject;

public class AppBootgridVO {
    private int appNo;
    private String name;
    private String hide;
    private String category;
    private String country;
    private long size;
    private String type;

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAppNo() {
        return this.appNo;
    }

    public void setAppNo(int appNo) {
        this.appNo = appNo;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHide() {
        return this.hide;
    }

    public void setHide(String hide) {
        this.hide = hide;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public long getSize() {
        return this.size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public JsonObject toJsonObject() {
        JsonObject object = new JsonObject();
        object.addProperty("size", this.getSize());
        object.addProperty("name", this.getName());
        object.addProperty("appNo", this.getAppNo());
        object.addProperty("hide", this.getHide());
        object.addProperty("category", this.getCategory());
        object.addProperty("country", this.getCountry());
        object.addProperty("type", this.getType());
        return object;
    }
}

