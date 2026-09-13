/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.MultifactorAuthenticationProvider
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.RegisteredServiceMultifactorPolicy
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.authentication.bypass;

import javax.servlet.http.HttpServletRequest;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.MultifactorAuthenticationProvider;
import org.apereo.cas.authentication.bypass.BaseMultifactorAuthenticationProviderBypassEvaluator;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.RegisteredServiceMultifactorPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegisteredServicePrincipalAttributeMultifactorAuthenticationProviderBypassEvaluator
extends BaseMultifactorAuthenticationProviderBypassEvaluator {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisteredServicePrincipalAttributeMultifactorAuthenticationProviderBypassEvaluator.class);
    private static final long serialVersionUID = -6123435418344342672L;

    public RegisteredServicePrincipalAttributeMultifactorAuthenticationProviderBypassEvaluator(String providerId) {
        super(providerId);
    }

    @Override
    public boolean shouldMultifactorAuthenticationProviderExecuteInternal(Authentication authentication, RegisteredService registeredService, MultifactorAuthenticationProvider provider, HttpServletRequest request) {
        if (registeredService != null) {
            boolean bypassEnabled;
            RegisteredServiceMultifactorPolicy mfaPolicy = registeredService.getMultifactorAuthenticationPolicy();
            boolean bl = bypassEnabled = mfaPolicy != null && StringUtils.isNotBlank((CharSequence)mfaPolicy.getBypassPrincipalAttributeName()) && StringUtils.isNotBlank((CharSequence)mfaPolicy.getBypassPrincipalAttributeValue());
            if (bypassEnabled) {
                Principal principal = this.resolvePrincipal(authentication.getPrincipal());
                boolean bypass = RegisteredServicePrincipalAttributeMultifactorAuthenticationProviderBypassEvaluator.locateMatchingAttributeValue(mfaPolicy.getBypassPrincipalAttributeName(), mfaPolicy.getBypassPrincipalAttributeValue(), principal.getAttributes(), true);
                if (bypass) {
                    LOGGER.debug("Bypass rules for principal [{}] indicate the request may be ignored", (Object)principal.getId());
                    return false;
                }
            }
        }
        return true;
    }
}

