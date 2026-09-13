/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.AuthenticationResult
 *  org.apereo.cas.authentication.principal.Service
 */
package org.apereo.cas.authentication;

import lombok.Generated;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.AuthenticationResult;
import org.apereo.cas.authentication.principal.Service;

public class DefaultAuthenticationResult
implements AuthenticationResult {
    private static final long serialVersionUID = 8454900425245262824L;
    private final Authentication authentication;
    private final Service service;
    private boolean credentialProvided;

    @Generated
    public String toString() {
        return "DefaultAuthenticationResult(authentication=" + this.authentication + ", service=" + this.service + ", credentialProvided=" + this.credentialProvided + ")";
    }

    @Generated
    public void setCredentialProvided(boolean credentialProvided) {
        this.credentialProvided = credentialProvided;
    }

    @Generated
    public Authentication getAuthentication() {
        return this.authentication;
    }

    @Generated
    public Service getService() {
        return this.service;
    }

    @Generated
    public boolean isCredentialProvided() {
        return this.credentialProvided;
    }

    @Generated
    public DefaultAuthenticationResult(Authentication authentication, Service service) {
        this.authentication = authentication;
        this.service = service;
    }
}

