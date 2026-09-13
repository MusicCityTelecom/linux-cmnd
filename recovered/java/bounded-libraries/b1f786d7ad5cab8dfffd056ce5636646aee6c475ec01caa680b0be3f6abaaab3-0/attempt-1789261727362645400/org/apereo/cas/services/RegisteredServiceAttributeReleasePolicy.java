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
import java.util.List;
import java.util.Map;
import org.apereo.cas.authentication.principal.RegisteredServicePrincipalAttributesRepository;
import org.apereo.cas.services.RegisteredServiceAttributeFilter;
import org.apereo.cas.services.RegisteredServiceAttributeReleasePolicyContext;
import org.apereo.cas.services.RegisteredServiceConsentPolicy;
import org.springframework.core.Ordered;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public interface RegisteredServiceAttributeReleasePolicy
extends Serializable,
Ordered {
    default public boolean isAuthorizedToReleaseAuthenticationAttributes() {
        return true;
    }

    default public boolean isAuthorizedToReleaseCredentialPassword() {
        return false;
    }

    default public boolean isAuthorizedToReleaseProxyGrantingTicket() {
        return false;
    }

    default public void setAttributeFilter(RegisteredServiceAttributeFilter filter) {
    }

    default public RegisteredServiceConsentPolicy getConsentPolicy() {
        return null;
    }

    public RegisteredServicePrincipalAttributesRepository getPrincipalAttributesRepository();

    public Map<String, List<Object>> getAttributes(RegisteredServiceAttributeReleasePolicyContext var1);

    default public Map<String, List<Object>> getConsentableAttributes(RegisteredServiceAttributeReleasePolicyContext context) {
        return this.getAttributes(context);
    }

    default public int getOrder() {
        return 0;
    }

    @JsonIgnore
    default public String getName() {
        return this.getClass().getSimpleName();
    }
}

