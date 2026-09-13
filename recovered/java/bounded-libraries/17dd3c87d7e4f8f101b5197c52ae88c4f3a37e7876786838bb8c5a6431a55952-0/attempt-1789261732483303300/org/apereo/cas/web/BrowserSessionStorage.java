/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 */
package org.apereo.cas.web;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public interface BrowserSessionStorage
extends Serializable {
    public static final String KEY_SESSION_STORAGE = "sessionStorage";

    public String getPayload();

    public String getDestinationUrl();

    public void setDestinationUrl(String var1);
}

