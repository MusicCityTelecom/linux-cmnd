/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public interface RegisteredServiceExpirationPolicy
extends Serializable {
    public String getExpirationDate();

    public boolean isNotifyWhenDeleted();

    public boolean isNotifyWhenExpired();

    public boolean isDeleteWhenExpired();

    @JsonIgnore
    public boolean isExpired();
}

