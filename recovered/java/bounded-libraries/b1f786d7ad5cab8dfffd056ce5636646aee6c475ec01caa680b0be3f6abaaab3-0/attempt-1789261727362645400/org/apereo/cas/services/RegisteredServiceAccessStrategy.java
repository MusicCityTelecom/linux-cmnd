/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  org.springframework.core.Ordered
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.apereo.cas.services.RegisteredServiceDelegatedAuthenticationPolicy;
import org.springframework.core.Ordered;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public interface RegisteredServiceAccessStrategy
extends Serializable,
Ordered {
    @JsonIgnore
    default public boolean isServiceAccessAllowed() {
        return true;
    }

    @JsonIgnore
    default public void setServiceAccessAllowed(boolean enabled) {
    }

    @JsonIgnore
    default public boolean isServiceAccessAllowedForSso() {
        return true;
    }

    @JsonIgnore
    default public boolean doPrincipalAttributesAllowServiceAccess(String principal, Map<String, Object> attributes) {
        return true;
    }

    default public URI getUnauthorizedRedirectUrl() {
        return null;
    }

    default public int getOrder() {
        return Integer.MAX_VALUE;
    }

    default public RegisteredServiceDelegatedAuthenticationPolicy getDelegatedAuthenticationPolicy() {
        return null;
    }

    default public Map<String, Set<String>> getRequiredAttributes() {
        return new HashMap<String, Set<String>>(0);
    }
}

