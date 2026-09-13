/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  lombok.Generated
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.AuthenticationException
 *  org.apereo.cas.authentication.AuthenticationResult
 *  org.apereo.cas.authentication.AuthenticationResultBuilder
 *  org.apereo.cas.authentication.AuthenticationSystemSupport
 *  org.apereo.cas.authentication.Credential
 *  org.apereo.cas.authentication.MultifactorAuthenticationProvider
 *  org.apereo.cas.authentication.MultifactorAuthenticationTriggerSelectionStrategy
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.authentication.principal.ServiceFactory
 *  org.apereo.cas.authentication.principal.WebApplicationService
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.ServicesManager
 *  org.apereo.cas.validation.RequestedAuthenticationContextValidator
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.util.MultiValueMap
 */
package org.apereo.cas.rest.authentication;

import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Generated;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.AuthenticationException;
import org.apereo.cas.authentication.AuthenticationResult;
import org.apereo.cas.authentication.AuthenticationResultBuilder;
import org.apereo.cas.authentication.AuthenticationSystemSupport;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.MultifactorAuthenticationProvider;
import org.apereo.cas.authentication.MultifactorAuthenticationTriggerSelectionStrategy;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.authentication.principal.ServiceFactory;
import org.apereo.cas.authentication.principal.WebApplicationService;
import org.apereo.cas.rest.BadRestRequestException;
import org.apereo.cas.rest.authentication.RestAuthenticationService;
import org.apereo.cas.rest.factory.RestHttpRequestCredentialFactory;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.validation.RequestedAuthenticationContextValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MultiValueMap;

public class DefaultRestAuthenticationService
implements RestAuthenticationService {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultRestAuthenticationService.class);
    private final AuthenticationSystemSupport authenticationSystemSupport;
    private final RestHttpRequestCredentialFactory credentialFactory;
    private final ServiceFactory<WebApplicationService> serviceFactory;
    private final MultifactorAuthenticationTriggerSelectionStrategy multifactorTriggerSelectionStrategy;
    private final ServicesManager servicesManager;
    private final RequestedAuthenticationContextValidator requestedContextValidator;

    @Override
    public Optional<AuthenticationResult> authenticate(MultiValueMap<String, String> requestBody, HttpServletRequest request, HttpServletResponse response) {
        List<Credential> credentials = this.credentialFactory.fromRequest(request, requestBody);
        if (credentials == null || credentials.isEmpty()) {
            throw new BadRestRequestException("No credentials can be extracted to authenticate the REST request");
        }
        WebApplicationService service = (WebApplicationService)this.serviceFactory.createService(request);
        RegisteredService registeredService = this.servicesManager.findServiceBy((Service)service);
        Optional<AuthenticationResultBuilder> authResult = Optional.ofNullable(this.authenticationSystemSupport.handleInitialAuthenticationTransaction((Service)service, (Credential[])credentials.toArray(Credential[]::new)));
        return authResult.map(result -> result.getInitialAuthentication().filter(authn -> !this.requestedContextValidator.validateAuthenticationContext(request, response, registeredService, authn, (Service)service).isSuccess()).map(authn -> this.multifactorTriggerSelectionStrategy.resolve(request, response, registeredService, authn, (Service)service).map(provider -> {
            LOGGER.debug("Extracting credentials for multifactor authentication via [{}]", provider);
            List<Credential> authnCredentials = this.credentialFactory.fromAuthentication(request, requestBody, (Authentication)authn, (MultifactorAuthenticationProvider)provider);
            if (authnCredentials == null || authnCredentials.isEmpty()) {
                throw new AuthenticationException("Unable to extract credentials for multifactor authentication");
            }
            return this.authenticationSystemSupport.finalizeAuthenticationTransaction((Service)service, authnCredentials);
        }).orElseGet(() -> this.authenticationSystemSupport.finalizeAllAuthenticationTransactions(result, (Service)service))).orElseGet(() -> this.authenticationSystemSupport.finalizeAllAuthenticationTransactions(result, (Service)service)));
    }

    @Generated
    public DefaultRestAuthenticationService(AuthenticationSystemSupport authenticationSystemSupport, RestHttpRequestCredentialFactory credentialFactory, ServiceFactory<WebApplicationService> serviceFactory, MultifactorAuthenticationTriggerSelectionStrategy multifactorTriggerSelectionStrategy, ServicesManager servicesManager, RequestedAuthenticationContextValidator requestedContextValidator) {
        this.authenticationSystemSupport = authenticationSystemSupport;
        this.credentialFactory = credentialFactory;
        this.serviceFactory = serviceFactory;
        this.multifactorTriggerSelectionStrategy = multifactorTriggerSelectionStrategy;
        this.servicesManager = servicesManager;
        this.requestedContextValidator = requestedContextValidator;
    }

    @Generated
    public AuthenticationSystemSupport getAuthenticationSystemSupport() {
        return this.authenticationSystemSupport;
    }

    @Generated
    public RestHttpRequestCredentialFactory getCredentialFactory() {
        return this.credentialFactory;
    }

    @Generated
    public ServiceFactory<WebApplicationService> getServiceFactory() {
        return this.serviceFactory;
    }

    @Generated
    public MultifactorAuthenticationTriggerSelectionStrategy getMultifactorTriggerSelectionStrategy() {
        return this.multifactorTriggerSelectionStrategy;
    }

    @Generated
    public ServicesManager getServicesManager() {
        return this.servicesManager;
    }

    @Generated
    public RequestedAuthenticationContextValidator getRequestedContextValidator() {
        return this.requestedContextValidator;
    }
}

