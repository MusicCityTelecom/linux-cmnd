/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.services.RegisteredService
 */
package org.apereo.cas.validation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.validation.Assertion;

@FunctionalInterface
public interface AuthenticationAttributeReleasePolicy {
    public static final String BEAN_NAME = "authenticationAttributeReleasePolicy";

    public Map<String, List<Object>> getAuthenticationAttributesForRelease(Authentication var1, Assertion var2, Map<String, Object> var3, RegisteredService var4);

    public static AuthenticationAttributeReleasePolicy none() {
        return (authentication, assertion, model, service) -> new HashMap(0);
    }
}

