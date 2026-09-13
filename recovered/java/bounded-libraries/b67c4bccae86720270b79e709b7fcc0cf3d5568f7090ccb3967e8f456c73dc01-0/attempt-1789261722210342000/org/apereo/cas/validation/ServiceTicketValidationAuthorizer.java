/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.apereo.cas.authentication.principal.Service
 */
package org.apereo.cas.validation;

import javax.servlet.http.HttpServletRequest;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.validation.Assertion;

@FunctionalInterface
public interface ServiceTicketValidationAuthorizer {
    public void authorize(HttpServletRequest var1, Service var2, Assertion var3);
}

