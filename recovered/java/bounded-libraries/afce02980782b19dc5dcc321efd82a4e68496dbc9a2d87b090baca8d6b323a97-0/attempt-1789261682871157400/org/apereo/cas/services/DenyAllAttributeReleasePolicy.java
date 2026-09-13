/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  lombok.Generated
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.RegisteredServiceAttributeReleasePolicyContext
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.services.AbstractRegisteredServiceAttributeReleasePolicy;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.RegisteredServiceAttributeReleasePolicyContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
public class DenyAllAttributeReleasePolicy
extends AbstractRegisteredServiceAttributeReleasePolicy {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DenyAllAttributeReleasePolicy.class);
    private static final long serialVersionUID = -6215588543966639050L;

    public DenyAllAttributeReleasePolicy() {
        this.setExcludeDefaultAttributes(true);
        this.setPrincipalIdAttribute(null);
        this.setAuthorizedToReleaseAuthenticationAttributes(false);
        this.setAuthorizedToReleaseCredentialPassword(false);
        this.setAuthorizedToReleaseProxyGrantingTicket(false);
    }

    @Override
    public Map<String, List<Object>> getAttributesInternal(RegisteredServiceAttributeReleasePolicyContext context, Map<String, List<Object>> attributes) {
        LOGGER.trace("Ignoring all attributes given the service is designed to never receive any.");
        return new HashMap<String, List<Object>>(0);
    }

    @Override
    public boolean isExcludeDefaultAttributes() {
        return true;
    }

    @Override
    public boolean isAuthorizedToReleaseCredentialPassword() {
        LOGGER.trace("CAS will not authorize the release of credential password, given the service is denied access to all attributes.");
        return false;
    }

    @Override
    public boolean isAuthorizedToReleaseProxyGrantingTicket() {
        LOGGER.trace("CAS will not authorize the release of proxy-granting tickets, given the service is denied access to all attributes.");
        return false;
    }

    @Override
    public boolean isAuthorizedToReleaseAuthenticationAttributes() {
        LOGGER.trace("CAS will not authorize the release of authentication attributes, given the service is denied access to all attributes.");
        return false;
    }

    @Override
    protected Map<String, List<Object>> returnFinalAttributesCollection(Map<String, List<Object>> attributesToRelease, RegisteredService service) {
        LOGGER.debug("CAS will not authorize anything for release, given the service is denied access to all attributes. If there are any default attributes set to be released to all services, those are also skipped for service id: [{}], id: [{}] and description: [{}]", new Object[]{service.getServiceId(), service.getId(), service.getDescription()});
        return new HashMap<String, List<Object>>(0);
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DenyAllAttributeReleasePolicy)) {
            return false;
        }
        DenyAllAttributeReleasePolicy other = (DenyAllAttributeReleasePolicy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof DenyAllAttributeReleasePolicy;
    }

    @Override
    @Generated
    public int hashCode() {
        int result = super.hashCode();
        return result;
    }
}

