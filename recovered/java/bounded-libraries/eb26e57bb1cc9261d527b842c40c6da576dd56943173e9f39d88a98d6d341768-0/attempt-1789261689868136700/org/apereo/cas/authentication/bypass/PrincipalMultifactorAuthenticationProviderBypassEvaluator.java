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

public class PrincipalMultifactorAuthenticationProviderBypassEvaluator
extends BaseMultifactorAuthenticationProviderBypassEvaluator {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(PrincipalMultifactorAuthenticationProviderBypassEvaluator.class);
    private static final long serialVersionUID = -7553435418344342672L;
    private final String attributeName;
    private final String attributeValue;

    public PrincipalMultifactorAuthenticationProviderBypassEvaluator(MultifactorAuthenticationProviderBypassProperties bypassProperties, String providerId) {
        this(bypassProperties.getPrincipalAttributeName(), bypassProperties.getPrincipalAttributeValue(), providerId);
    }

    public PrincipalMultifactorAuthenticationProviderBypassEvaluator(String attributeName, String attributeValue, String providerId) {
        super(providerId);
        this.attributeName = attributeName;
        this.attributeValue = attributeValue;
    }

    @Override
    public boolean shouldMultifactorAuthenticationProviderExecuteInternal(Authentication authentication, RegisteredService registeredService, MultifactorAuthenticationProvider provider, HttpServletRequest request) {
        Principal principal = this.resolvePrincipal(authentication.getPrincipal());
        LOGGER.debug("Evaluating multifactor authentication bypass properties for principal [{}], service [{}] and provider [{}]", new Object[]{principal.getId(), registeredService, provider});
        boolean bypass = PrincipalMultifactorAuthenticationProviderBypassEvaluator.locateMatchingAttributeValue(this.attributeName, this.attributeValue, principal.getAttributes(), true);
        if (bypass) {
            LOGGER.debug("Bypass rules for principal [{}] indicate the request may be ignored", (Object)principal.getId());
            return false;
        }
        return true;
    }

    @Generated
    public String getAttributeName() {
        return this.attributeName;
    }

    @Generated
    public String getAttributeValue() {
        return this.attributeValue;
    }
}

