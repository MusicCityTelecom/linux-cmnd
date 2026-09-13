/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.services.RegisteredService
 *  org.springframework.core.Ordered
 */
package org.apereo.cas.authentication.bypass;

import java.io.Serializable;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.MultifactorAuthenticationProvider;
import org.apereo.cas.services.RegisteredService;
import org.springframework.core.Ordered;

public interface MultifactorAuthenticationProviderBypassEvaluator
extends Serializable,
Ordered {
    public static final String AUTHENTICATION_ATTRIBUTE_BYPASS_MFA = "bypassMultifactorAuthentication";
    public static final String AUTHENTICATION_ATTRIBUTE_BYPASS_MFA_PROVIDER = "bypassedMultifactorAuthenticationProviderId";

    public boolean shouldMultifactorAuthenticationProviderExecute(Authentication var1, RegisteredService var2, MultifactorAuthenticationProvider var3, HttpServletRequest var4);

    default public boolean isMultifactorAuthenticationBypassed(Authentication authentication, String requestedContext) {
        return false;
    }

    default public void forgetBypass(Authentication authentication) {
    }

    default public void rememberBypass(Authentication authentication, MultifactorAuthenticationProvider provider) {
    }

    default public int getOrder() {
        return Integer.MAX_VALUE;
    }

    public String getProviderId();

    public String getId();

    default public int size() {
        return 1;
    }

    default public boolean isEmpty() {
        return false;
    }

    public Optional<MultifactorAuthenticationProviderBypassEvaluator> belongsToMultifactorAuthenticationProvider(String var1);
}

