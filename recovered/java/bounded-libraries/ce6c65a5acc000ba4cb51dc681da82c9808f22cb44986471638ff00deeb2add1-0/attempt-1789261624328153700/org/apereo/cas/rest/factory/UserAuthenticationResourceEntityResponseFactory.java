/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.apereo.cas.authentication.AuthenticationResult
 *  org.springframework.http.ResponseEntity
 */
package org.apereo.cas.rest.factory;

import javax.servlet.http.HttpServletRequest;
import org.apereo.cas.authentication.AuthenticationResult;
import org.springframework.http.ResponseEntity;

@FunctionalInterface
public interface UserAuthenticationResourceEntityResponseFactory {
    public ResponseEntity<String> build(AuthenticationResult var1, HttpServletRequest var2) throws Exception;
}

