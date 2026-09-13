/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  lombok.Generated
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.MultifactorAuthenticationProvider
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.configuration.model.support.mfa.MultifactorAuthenticationProviderBypassProperties
 *  org.apereo.cas.services.RegisteredService
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.authentication.bypass;

import javax.servlet.http.HttpServletRequest;
import lombok.Generated;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.MultifactorAuthenticationProvider;
import org.apereo.cas.authentication.bypass.BaseMultifactorAuthenticationProviderBypassEvaluator;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.configuration.model.support.mfa.MultifactorAuthenticationProviderBypassProperties;
import org.apereo.cas.services.RegisteredService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthenticationMultifactorAuthenticationProviderBypassEvaluator
extends BaseMultifactorAuthenticationProviderBypassEvaluator {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationMultifactorAuthenticationProviderBypassEvaluator.class);
    private static final long serialVersionUID = 5582655921143779773L;
    private final MultifactorAuthenticationProviderBypassProperties bypassProperties;

    public AuthenticationMultifactorAuthenticationProviderBypassEvaluator(MultifactorAuthenticationProviderBypassProperties bypassProperties, String providerId) {
        super(providerId);
        this.bypassProperties = bypassProperties;
    }

    protected static boolean locateMatchingAttributeBasedOnAuthenticationAttributes(MultifactorAuthenticationProviderBypassProperties bypass, Authentication authn) {
        return AuthenticationMultifactorAuthenticationProviderBypassEvaluator.locateMatchingAttributeValue(bypass.getAuthenticationAttributeName(), bypass.getAuthenticationAttributeValue(), authn.getAttributes(), false);
    }

    @Override
    public boolean shouldMultifactorAuthenticationProviderExecuteInternal(Authentication authentication, RegisteredService registeredService, MultifactorAuthenticationProvider provider, HttpServletRequest request) {
        Principal principal = this.resolvePrincipal(authentication.getPrincipal());
        boolean bypassByAuthn = AuthenticationMultifactorAuthenticationProviderBypassEvaluator.locateMatchingAttributeBasedOnAuthenticationAttributes(this.bypassProperties, authentication);
        if (bypassByAuthn) {
            LOGGER.debug("Bypass rules for authentication for principal [{}] indicate the request may be ignored", (Object)principal.getId());
            return false;
        }
        boolean bypassByAuthnMethod = AuthenticationMultifactorAuthenticationProviderBypassEvaluator.locateMatchingAttributeValue("authenticationMethod", this.bypassProperties.getAuthenticationMethodName(), authentication.getAttributes(), false);
        if (bypassByAuthnMethod) {
            LOGGER.debug("Bypass rules for authentication method [{}] indicate the request may be ignored", (Object)this.bypassProperties.getAuthenticationMethodName());
            return false;
        }
        boolean bypassByHandlerName = AuthenticationMultifactorAuthenticationProviderBypassEvaluator.locateMatchingAttributeValue("successfulAuthenticationHandlers", this.bypassProperties.getAuthenticationHandlerName(), authentication.getAttributes(), false);
        if (bypassByHandlerName) {
            LOGGER.debug("Bypass rules for authentication handlers [{}] indicate the request may be ignored", (Object)this.bypassProperties.getAuthenticationHandlerName());
            return false;
        }
        return true;
    }
}

