/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.AuthenticationHandler
 *  org.apereo.cas.authentication.AuthenticationHandlerExecutionResult
 *  org.apereo.cas.authentication.Credential
 *  org.apereo.cas.authentication.CredentialMetaData
 *  org.apereo.cas.authentication.principal.PrincipalFactory
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.ServicesManager
 *  org.apereo.cas.util.http.HttpClient
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.authentication.handler.support;

import java.net.URL;
import java.security.GeneralSecurityException;
import javax.security.auth.login.FailedLoginException;
import lombok.Generated;
import org.apereo.cas.authentication.AbstractAuthenticationHandler;
import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.authentication.AuthenticationHandlerExecutionResult;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.CredentialMetaData;
import org.apereo.cas.authentication.DefaultAuthenticationHandlerExecutionResult;
import org.apereo.cas.authentication.credential.HttpBasedServiceCredential;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.util.http.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpBasedServiceCredentialsAuthenticationHandler
extends AbstractAuthenticationHandler {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpBasedServiceCredentialsAuthenticationHandler.class);
    private final HttpClient httpClient;

    public HttpBasedServiceCredentialsAuthenticationHandler(String name, ServicesManager servicesManager, PrincipalFactory principalFactory, Integer order, HttpClient httpClient) {
        super(name, servicesManager, principalFactory, order);
        this.httpClient = httpClient;
    }

    public AuthenticationHandlerExecutionResult authenticate(Credential credential, Service service) throws GeneralSecurityException {
        HttpBasedServiceCredential httpCredential = (HttpBasedServiceCredential)credential;
        if (!httpCredential.getService().getProxyPolicy().isAllowedProxyCallbackUrl((RegisteredService)httpCredential.getService(), httpCredential.getCallbackUrl())) {
            LOGGER.warn("Proxy policy for service [{}] cannot authorize the requested callback url [{}].", (Object)httpCredential.getService(), (Object)httpCredential.getCallbackUrl());
            throw new FailedLoginException(httpCredential.getCallbackUrl() + " cannot be authorized");
        }
        LOGGER.debug("Attempting to authenticate [{}]", (Object)httpCredential);
        URL callbackUrl = httpCredential.getCallbackUrl();
        if (!this.httpClient.isValidEndPoint(callbackUrl)) {
            throw new FailedLoginException(callbackUrl.toExternalForm() + " sent an unacceptable response status code");
        }
        return new DefaultAuthenticationHandlerExecutionResult((AuthenticationHandler)this, (CredentialMetaData)httpCredential, this.principalFactory.createPrincipal(httpCredential.getId()));
    }

    public boolean supports(Class<? extends Credential> clazz) {
        return HttpBasedServiceCredential.class.isAssignableFrom(clazz);
    }

    public boolean supports(Credential credential) {
        return credential instanceof HttpBasedServiceCredential;
    }
}

