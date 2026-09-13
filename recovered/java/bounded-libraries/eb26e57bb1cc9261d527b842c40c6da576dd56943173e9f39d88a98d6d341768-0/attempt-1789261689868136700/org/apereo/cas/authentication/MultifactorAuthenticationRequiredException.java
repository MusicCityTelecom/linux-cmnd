/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.AuthenticationException
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.services.RegisteredService
 */
package org.apereo.cas.authentication;

import lombok.Generated;
import org.apereo.cas.authentication.AuthenticationException;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.services.RegisteredService;

public class MultifactorAuthenticationRequiredException
extends AuthenticationException {
    private static final long serialVersionUID = 5909155188558680032L;
    private static final String CODE = "MULTIFACTOR_AUTHN_REQUIRED";
    private final RegisteredService service;
    private final Principal principal;

    public String getCode() {
        return CODE;
    }

    @Generated
    public MultifactorAuthenticationRequiredException() {
        this.service = null;
        this.principal = null;
    }

    @Generated
    public RegisteredService getService() {
        return this.service;
    }

    @Generated
    public Principal getPrincipal() {
        return this.principal;
    }

    @Generated
    public MultifactorAuthenticationRequiredException(RegisteredService service, Principal principal) {
        this.service = service;
        this.principal = principal;
    }
}

