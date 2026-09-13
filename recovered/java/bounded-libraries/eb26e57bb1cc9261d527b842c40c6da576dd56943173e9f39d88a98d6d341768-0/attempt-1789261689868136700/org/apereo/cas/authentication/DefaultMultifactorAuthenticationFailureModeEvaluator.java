/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.MultifactorAuthenticationFailureModeEvaluator
 *  org.apereo.cas.authentication.MultifactorAuthenticationProvider
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.configuration.model.support.mfa.BaseMultifactorAuthenticationProviderProperties$MultifactorAuthenticationProviderFailureModes
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.RegisteredServiceMultifactorPolicy
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.authentication;

import lombok.Generated;
import org.apereo.cas.authentication.MultifactorAuthenticationFailureModeEvaluator;
import org.apereo.cas.authentication.MultifactorAuthenticationProvider;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.model.support.mfa.BaseMultifactorAuthenticationProviderProperties;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.RegisteredServiceMultifactorPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultMultifactorAuthenticationFailureModeEvaluator
implements MultifactorAuthenticationFailureModeEvaluator {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultMultifactorAuthenticationFailureModeEvaluator.class);
    private static final long serialVersionUID = 3837589092620951038L;
    private final CasConfigurationProperties casProperties;

    public BaseMultifactorAuthenticationProviderProperties.MultifactorAuthenticationProviderFailureModes evaluate(RegisteredService service, MultifactorAuthenticationProvider provider) {
        RegisteredServiceMultifactorPolicy policy;
        BaseMultifactorAuthenticationProviderProperties.MultifactorAuthenticationProviderFailureModes failureMode = this.casProperties.getAuthn().getMfa().getCore().getGlobalFailureMode();
        LOGGER.debug("Setting failure mode to [{}] based on global policy", (Object)failureMode);
        if (provider.getFailureMode() != BaseMultifactorAuthenticationProviderProperties.MultifactorAuthenticationProviderFailureModes.UNDEFINED) {
            LOGGER.debug("Provider failure mode [{}] overriding global mode [{}]", (Object)provider.getFailureMode(), (Object)failureMode);
            failureMode = provider.getFailureMode();
        }
        if (service != null && (policy = service.getMultifactorAuthenticationPolicy()) != null && policy.getFailureMode() != BaseMultifactorAuthenticationProviderProperties.MultifactorAuthenticationProviderFailureModes.UNDEFINED) {
            LOGGER.debug("Service failure mode [{}] overriding current failure mode [{}]", (Object)policy.getFailureMode(), (Object)failureMode);
            failureMode = policy.getFailureMode();
        }
        return failureMode;
    }

    @Generated
    public DefaultMultifactorAuthenticationFailureModeEvaluator(CasConfigurationProperties casProperties) {
        this.casProperties = casProperties;
    }
}

