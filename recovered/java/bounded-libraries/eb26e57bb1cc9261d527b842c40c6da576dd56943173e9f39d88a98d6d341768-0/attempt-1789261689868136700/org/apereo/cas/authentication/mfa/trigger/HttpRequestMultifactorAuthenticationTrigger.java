/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  javax.servlet.http.HttpSession
 *  lombok.Generated
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.AuthenticationException
 *  org.apereo.cas.authentication.MultifactorAuthenticationProvider
 *  org.apereo.cas.authentication.MultifactorAuthenticationTrigger
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.util.CollectionUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.context.ApplicationContext
 */
package org.apereo.cas.authentication.mfa.trigger;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.Generated;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.AuthenticationException;
import org.apereo.cas.authentication.MultifactorAuthenticationProvider;
import org.apereo.cas.authentication.MultifactorAuthenticationProviderAbsentException;
import org.apereo.cas.authentication.MultifactorAuthenticationTrigger;
import org.apereo.cas.authentication.MultifactorAuthenticationUtils;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

public class HttpRequestMultifactorAuthenticationTrigger
implements MultifactorAuthenticationTrigger {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequestMultifactorAuthenticationTrigger.class);
    private final CasConfigurationProperties casProperties;
    private final ApplicationContext applicationContext;
    private int order = Integer.MAX_VALUE;

    public Optional<MultifactorAuthenticationProvider> isActivated(Authentication authentication, RegisteredService registeredService, HttpServletRequest httpServletRequest, HttpServletResponse response, Service service) {
        if (authentication == null) {
            LOGGER.debug("No authentication is available to determine event for principal");
            return Optional.empty();
        }
        List<String> values = this.resolveEventFromHttpRequest(httpServletRequest);
        if (values != null && !values.isEmpty()) {
            LOGGER.debug("Received request as [{}]", values);
            Map<String, MultifactorAuthenticationProvider> providerMap = MultifactorAuthenticationUtils.getAvailableMultifactorAuthenticationProviders(this.applicationContext);
            if (providerMap.isEmpty()) {
                LOGGER.error("No multifactor authentication providers are available in the application context to satisfy [{}]", values);
                throw new AuthenticationException((Throwable)((Object)new MultifactorAuthenticationProviderAbsentException()));
            }
            Optional<MultifactorAuthenticationProvider> providerFound = MultifactorAuthenticationUtils.resolveProvider(providerMap, values.get(0));
            if (providerFound.isPresent()) {
                return providerFound;
            }
            LOGGER.warn("No multifactor provider could be found for request parameter [{}]", values);
            throw new AuthenticationException();
        }
        return Optional.empty();
    }

    protected List<String> resolveEventFromHttpRequest(HttpServletRequest request) {
        String mfaRequestHeader = this.casProperties.getAuthn().getMfa().getTriggers().getHttp().getRequestHeader();
        Enumeration headers = request.getHeaders(mfaRequestHeader);
        if (headers != null && headers.hasMoreElements()) {
            LOGGER.debug("Received request header [{}] as [{}]", (Object)mfaRequestHeader, (Object)headers);
            return Collections.list(headers);
        }
        String mfaRequestParameter = this.casProperties.getAuthn().getMfa().getTriggers().getHttp().getRequestParameter();
        String[] params = request.getParameterValues(mfaRequestParameter);
        if (params != null && params.length > 0) {
            LOGGER.debug("Received request parameter [{}] as [{}]", (Object)mfaRequestParameter, (Object)params);
            return Arrays.stream(params).collect(Collectors.toList());
        }
        String attributeName = this.casProperties.getAuthn().getMfa().getTriggers().getHttp().getSessionAttribute();
        HttpSession session = request.getSession(false);
        Object attributeValue = Optional.ofNullable(session).map(httpSession -> httpSession.getAttribute(attributeName)).orElse(null);
        if (attributeValue == null) {
            LOGGER.trace("No value could be found for session attribute [{}]. Checking request attributes...", (Object)attributeName);
            attributeValue = request.getAttribute(attributeName);
        }
        if (attributeValue == null) {
            LOGGER.trace("No value could be found for [{}]", (Object)attributeName);
            return null;
        }
        Set values = CollectionUtils.toCollection(attributeValue);
        LOGGER.debug("Found values [{}] mapped to attribute name [{}]", (Object)values, (Object)attributeName);
        return values.stream().map(Object::toString).collect(Collectors.toList());
    }

    @Generated
    public CasConfigurationProperties getCasProperties() {
        return this.casProperties;
    }

    @Generated
    public ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }

    @Generated
    public int getOrder() {
        return this.order;
    }

    @Generated
    public void setOrder(int order) {
        this.order = order;
    }

    @Generated
    public HttpRequestMultifactorAuthenticationTrigger(CasConfigurationProperties casProperties, ApplicationContext applicationContext) {
        this.casProperties = casProperties;
        this.applicationContext = applicationContext;
    }
}

