/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartcms.model;

public class Status {
    private int id;
    private String message;

    public Status() {
    }

    public Status(String message) {
        this.message = message;
    }

    public Status(int id, String message) {
        this.id = id;
        this.message = message;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

