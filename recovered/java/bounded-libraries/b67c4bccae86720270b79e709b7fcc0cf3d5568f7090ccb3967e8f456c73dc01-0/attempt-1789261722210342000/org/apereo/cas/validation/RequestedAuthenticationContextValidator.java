/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.services.RegisteredService
 */
package org.apereo.cas.validation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.validation.Assertion;
import org.apereo.cas.validation.AuthenticationContextValidationResult;

public interface RequestedAuthenticationContextValidator {
    public AuthenticationContextValidationResult validateAuthenticationContext(Assertion var1, HttpServletRequest var2, HttpServletResponse var3);

    public AuthenticationContextValidationResult validateAuthenticationContext(HttpServletRequest var1, HttpServletResponse var2, RegisteredService var3, Authentication var4, Service var5);
}

