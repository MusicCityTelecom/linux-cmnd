/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.Credential
 *  org.apereo.cas.authentication.MultifactorAuthenticationProvider
 *  org.springframework.core.Ordered
 *  org.springframework.util.MultiValueMap
 */
package org.apereo.cas.rest.factory;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.MultifactorAuthenticationProvider;
import org.springframework.core.Ordered;
import org.springframework.util.MultiValueMap;

@FunctionalInterface
public interface RestHttpRequestCredentialFactory
extends Ordered {
    public static final String PARAMETER_USERNAME = "username";
    public static final String PARAMETER_PASSWORD = "password";

    public List<Credential> fromRequest(HttpServletRequest var1, MultiValueMap<String, String> var2);

    default public int getOrder() {
        return Integer.MAX_VALUE;
    }

    default public List<Credential> fromAuthentication(HttpServletRequest request, MultiValueMap<String, String> requestBody, Authentication authentication, MultifactorAuthenticationProvider provider) {
        return new ArrayList<Credential>(0);
    }
}

