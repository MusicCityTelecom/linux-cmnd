/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  lombok.Generated
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.ChainingMultifactorAuthenticationProvider
 *  org.apereo.cas.authentication.MultifactorAuthenticationContextValidationResult
 *  org.apereo.cas.authentication.MultifactorAuthenticationContextValidator
 *  org.apereo.cas.authentication.MultifactorAuthenticationProvider
 *  org.apereo.cas.authentication.MultifactorAuthenticationTriggerSelectionStrategy
 *  org.apereo.cas.authentication.bypass.MultifactorAuthenticationProviderBypassEvaluator
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.configuration.model.support.mfa.BaseMultifactorAuthenticationProviderProperties$MultifactorAuthenticationProviderFailureModes
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.ServicesManager
 *  org.apereo.cas.validation.Assertion
 *  org.apereo.cas.validation.AuthenticationContextValidationResult
 *  org.apereo.cas.validation.RequestedAuthenticationContextValidator
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.core.Ordered
 */
package org.apereo.cas.authentication;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Generated;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.ChainingMultifactorAuthenticationProvider;
import org.apereo.cas.authentication.MultifactorAuthenticationContextValidationResult;
import org.apereo.cas.authentication.MultifactorAuthenticationContextValidator;
import org.apereo.cas.authentication.MultifactorAuthenticationProvider;
import org.apereo.cas.authentication.MultifactorAuthenticationTriggerSelectionStrategy;
import org.apereo.cas.authentication.bypass.MultifactorAuthenticationProviderBypassEvaluator;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.configuration.model.support.mfa.BaseMultifactorAuthenticationProviderProperties;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.validation.Assertion;
import org.apereo.cas.validation.AuthenticationContextValidationResult;
import org.apereo.cas.validation.RequestedAuthenticationContextValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;

public class DefaultRequestedAuthenticationContextValidator
implements RequestedAuthenticationContextValidator {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultRequestedAuthenticationContextValidator.class);
    private final ServicesManager servicesManager;
    private final MultifactorAuthenticationTriggerSelectionStrategy multifactorTriggerSelectionStrategy;
    private final MultifactorAuthenticationContextValidator authenticationContextValidator;

    protected static AuthenticationContextValidationResult toSuccessfulResult() {
        return AuthenticationContextValidationResult.builder().success(true).build();
    }

    protected static AuthenticationContextValidationResult validateMultifactorProviderBypass(MultifactorAuthenticationProvider provider, RegisteredService registeredService, Authentication authentication, Service service, HttpServletRequest request) {
        if (provider.isAvailable(registeredService)) {
            MultifactorAuthenticationProviderBypassEvaluator bypassEvaluator = provider.getBypassEvaluator();
            if (bypassEvaluator != null) {
                if (!bypassEvaluator.shouldMultifactorAuthenticationProviderExecute(authentication, registeredService, provider, request)) {
                    LOGGER.debug("MFA provider [{}] should be bypassed for this service request [{}]", (Object)provider, (Object)service);
                    bypassEvaluator.rememberBypass(authentication, provider);
                    return DefaultRequestedAuthenticationContextValidator.toSuccessfulResult();
                }
                if (bypassEvaluator.isMultifactorAuthenticationBypassed(authentication, provider.getId())) {
                    LOGGER.debug("Authentication attempt indicates that MFA is bypassed for this request for [{}]", (Object)provider);
                    bypassEvaluator.rememberBypass(authentication, provider);
                    return DefaultRequestedAuthenticationContextValidator.toSuccessfulResult();
                }
            }
        } else {
            BaseMultifactorAuthenticationProviderProperties.MultifactorAuthenticationProviderFailureModes failure = provider.getFailureModeEvaluator().evaluate(registeredService, provider);
            if (failure != BaseMultifactorAuthenticationProviderProperties.MultifactorAuthenticationProviderFailureModes.CLOSED) {
                return DefaultRequestedAuthenticationContextValidator.toSuccessfulResult();
            }
        }
        return DefaultRequestedAuthenticationContextValidator.toFailureResult();
    }

    private static AuthenticationContextValidationResult toFailureResult() {
        return AuthenticationContextValidationResult.builder().success(false).build();
    }

    public AuthenticationContextValidationResult validateAuthenticationContext(Assertion assertion, HttpServletRequest request, HttpServletResponse response) {
        LOGGER.trace("Locating the primary authentication associated with this service request [{}]", (Object)assertion.getService());
        RegisteredService registeredService = this.servicesManager.findServiceBy((Service)assertion.getService());
        Authentication authentication = assertion.getPrimaryAuthentication();
        return this.validateAuthenticationContext(request, response, registeredService, authentication, (Service)assertion.getService());
    }

    public AuthenticationContextValidationResult validateAuthenticationContext(HttpServletRequest request, HttpServletResponse response, RegisteredService registeredService, Authentication authentication, Service service) {
        if (registeredService != null && registeredService.getMultifactorAuthenticationPolicy().isBypassEnabled()) {
            LOGGER.debug("Multifactor authentication execution is ignored for [{}]", (Object)registeredService.getName());
            return DefaultRequestedAuthenticationContextValidator.toSuccessfulResult();
        }
        Optional providerResult = this.multifactorTriggerSelectionStrategy.resolve(request, response, registeredService, authentication, service);
        if (providerResult.isEmpty()) {
            LOGGER.debug("No authentication context is required for this request");
            return DefaultRequestedAuthenticationContextValidator.toSuccessfulResult();
        }
        List providers = providerResult.map(provider -> {
            if (provider instanceof ChainingMultifactorAuthenticationProvider) {
                ChainingMultifactorAuthenticationProvider chain = (ChainingMultifactorAuthenticationProvider)ChainingMultifactorAuthenticationProvider.class.cast(provider);
                return chain.getMultifactorAuthenticationProviders().stream().filter(p -> p.equals(provider)).collect(Collectors.toList());
            }
            return List.of(provider);
        }).orElseGet(List::of);
        if (providers.stream().map(provider -> DefaultRequestedAuthenticationContextValidator.validateMultifactorProviderBypass(provider, registeredService, authentication, service, request)).allMatch(AuthenticationContextValidationResult::isSuccess)) {
            return DefaultRequestedAuthenticationContextValidator.toSuccessfulResult();
        }
        LOGGER.debug("Multifactor providers eligible for validation are [{}]", (Object)providers);
        return providers.stream().sorted(Comparator.comparing(Ordered::getOrder)).map(provider -> this.authenticationContextValidator.validate(authentication, provider.getId(), Optional.ofNullable(registeredService))).filter(MultifactorAuthenticationContextValidationResult::isSuccess).findAny().map(result -> AuthenticationContextValidationResult.builder().success(result.isSuccess()).contextId(result.getProvider().map(MultifactorAuthenticationProvider::getId)).build()).map(AuthenticationContextValidationResult.class::cast).orElseGet(DefaultRequestedAuthenticationContextValidator::toFailureResult);
    }

    @Generated
    public DefaultRequestedAuthenticationContextValidator(ServicesManager servicesManager, MultifactorAuthenticationTriggerSelectionStrategy multifactorTriggerSelectionStrategy, MultifactorAuthenticationContextValidator authenticationContextValidator) {
        this.servicesManager = servicesManager;
        this.multifactorTriggerSelectionStrategy = multifactorTriggerSelectionStrategy;
        this.authenticationContextValidator = authenticationContextValidator;
    }
}

