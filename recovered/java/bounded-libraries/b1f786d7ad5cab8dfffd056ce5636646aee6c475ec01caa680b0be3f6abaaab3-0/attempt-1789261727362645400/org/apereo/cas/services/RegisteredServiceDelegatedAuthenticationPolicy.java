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
import java.util.Collection;
import org.apereo.cas.services.RegisteredService;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public interface RegisteredServiceDelegatedAuthenticationPolicy
extends Serializable {
    public Collection<String> getAllowedProviders();

    public String getSelectionStrategy();

    public boolean isExclusive();

    public boolean isPermitUndefined();

    @JsonIgnore
    default public boolean isProviderAllowed(String provider, RegisteredService registeredService) {
        return true;
    }

    @JsonIgnore
    default public boolean isProviderRequired() {
        return this.isExclusive() && (!this.getAllowedProviders().isEmpty() || this.getAllowedProviders().isEmpty() && !this.isPermitUndefined());
    }
}

