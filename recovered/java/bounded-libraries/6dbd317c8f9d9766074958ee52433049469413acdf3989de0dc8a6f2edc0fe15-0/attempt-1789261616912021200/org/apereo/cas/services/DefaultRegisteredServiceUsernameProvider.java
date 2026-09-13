/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  lombok.Generated
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.services.RegisteredService
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Generated;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.services.BaseRegisteredServiceUsernameAttributeProvider;
import org.apereo.cas.services.RegisteredService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonInclude(value=JsonInclude.Include.NON_DEFAULT)
public class DefaultRegisteredServiceUsernameProvider
extends BaseRegisteredServiceUsernameAttributeProvider {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultRegisteredServiceUsernameProvider.class);
    private static final long serialVersionUID = 5823989148794052951L;

    public DefaultRegisteredServiceUsernameProvider(String canonicalizationMode) {
        super(canonicalizationMode, false, null);
    }

    @Override
    public String resolveUsernameInternal(Principal principal, Service service, RegisteredService registeredService) {
        LOGGER.debug("Returning the default principal id [{}] for username.", (Object)principal.getId());
        return principal.getId();
    }

    @Generated
    public DefaultRegisteredServiceUsernameProvider() {
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DefaultRegisteredServiceUsernameProvider)) {
            return false;
        }
        DefaultRegisteredServiceUsernameProvider other = (DefaultRegisteredServiceUsernameProvider)o;
        if (!other.canEqual(this)) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof DefaultRegisteredServiceUsernameProvider;
    }

    @Override
    @Generated
    public int hashCode() {
        int result = super.hashCode();
        return result;
    }
}

