/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 */
package org.apereo.cas.authentication;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;
import java.util.Map;
import org.apereo.cas.authentication.CredentialMetaData;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public interface DetailedCredentialMetaData
extends CredentialMetaData {
    public static final String PROPERTY_USER_AGENT = "UserAgent";
    public static final String PROPERTY_GEO_LOCATION = "GeoLocation";

    public Map<String, Serializable> getProperties();

    public void putProperties(Map<String, Serializable> var1);
}

