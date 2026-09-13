/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;
import java.net.URL;
import org.apereo.cas.services.RegisteredService;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public interface RegisteredServiceProxyPolicy
extends Serializable {
    public boolean isAllowedToProxy();

    public boolean isAllowedProxyCallbackUrl(RegisteredService var1, URL var2);
}

