/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.MultifactorAuthenticationProvider
 *  org.apereo.cas.configuration.model.support.mfa.MultifactorAuthenticationProviderBypassProperties
 *  org.apereo.cas.services.RegisteredService
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
import org.apereo.cas.configuration.model.support.mfa.MultifactorAuthenticationProviderBypassProperties;
import org.apereo.cas.services.RegisteredService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CredentialMultifactorAuthenticationProviderBypassEvaluator
extends BaseMultifactorAuthenticationProviderBypassEvaluator {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(CredentialMultifactorAuthenticationProviderBypassEvaluator.class);
    private static final long serialVersionUID = -1233888418344342672L;
    private final MultifactorAuthenticationProviderBypassProperties bypassProperties;

    public CredentialMultifactorAuthenticationProviderBypassEvaluator(MultifactorAuthenticationProviderBypassProperties bypassProperties, String providerId) {
        super(providerId);
        this.bypassProperties = bypassProperties;
    }

    protected static boolean locateMatchingCredentialType(Authentication authentication, String credentialClassType) {
        return StringUtils.isNotBlank((CharSequence)credentialClassType) && authentication.getCredentials().stream().anyMatch(e -> e.getCredentialClass().getName().matches(credentialClassType));
    }

    @Override
    public boolean shouldMultifactorAuthenticationProviderExecuteInternal(Authentication authentication, RegisteredService registeredService, MultifactorAuthenticationProvider provider, HttpServletRequest request) {
        boolean bypassByCredType = CredentialMultifactorAuthenticationProviderBypassEvaluator.locateMatchingCredentialType(authentication, this.bypassProperties.getCredentialClassType());
        if (bypassByCredType) {
            LOGGER.debug("Bypass rules for credential types [{}] indicate the request may be ignored", (Object)this.bypassProperties.getCredentialClassType());
            return false;
        }
        return true;
    }
}

