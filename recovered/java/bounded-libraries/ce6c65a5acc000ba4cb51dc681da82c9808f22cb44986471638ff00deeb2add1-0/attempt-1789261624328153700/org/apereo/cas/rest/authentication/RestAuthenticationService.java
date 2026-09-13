/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.apereo.cas.authentication.AuthenticationResult
 *  org.springframework.util.MultiValueMap
 */
package org.apereo.cas.rest.authentication;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apereo.cas.authentication.AuthenticationResult;
import org.springframework.util.MultiValueMap;

@FunctionalInterface
public interface RestAuthenticationService {
    public static final String DEFAULT_BEAN_NAME = "restAuthenticationService";

    public Optional<AuthenticationResult> authenticate(MultiValueMap<String, String> var1, HttpServletRequest var2, HttpServletResponse var3);
}

