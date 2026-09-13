/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.AuthenticationException
 *  org.apereo.cas.authentication.MultifactorAuthenticationProvider
 *  org.apereo.cas.authentication.MultifactorAuthenticationTrigger
 *  org.apereo.cas.authentication.adaptive.geo.GeoLocationRequest
 *  org.apereo.cas.authentication.adaptive.geo.GeoLocationResponse
 *  org.apereo.cas.authentication.adaptive.geo.GeoLocationService
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.util.HttpRequestUtils
 *  org.apereo.cas.util.RegexUtils
 *  org.apereo.inspektr.common.web.ClientInfo
 *  org.apereo.inspektr.common.web.ClientInfoHolder
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.context.ApplicationContext
 */
package org.apereo.cas.authentication.mfa.trigger;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.AuthenticationException;
import org.apereo.cas.authentication.MultifactorAuthenticationProvider;
import org.apereo.cas.authentication.MultifactorAuthenticationProviderAbsentException;
import org.apereo.cas.authentication.MultifactorAuthenticationTrigger;
import org.apereo.cas.authentication.MultifactorAuthenticationUtils;
import org.apereo.cas.authentication.adaptive.geo.GeoLocationRequest;
import org.apereo.cas.authentication.adaptive.geo.GeoLocationResponse;
import org.apereo.cas.authentication.adaptive.geo.GeoLocationService;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.util.HttpRequestUtils;
import org.apereo.cas.util.RegexUtils;
import org.apereo.inspektr.common.web.ClientInfo;
import org.apereo.inspektr.common.web.ClientInfoHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

public class AdaptiveMultifactorAuthenticationTrigger
implements MultifactorAuthenticationTrigger {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(AdaptiveMultifactorAuthenticationTrigger.class);
    private final GeoLocationService geoLocationService;
    private final CasConfigurationProperties casProperties;
    private final ApplicationContext applicationContext;
    private int order = Integer.MAX_VALUE;

    private static boolean checkUserAgentOrClientIp(String clientIp, String agent, String mfaMethod, String pattern) {
        if (StringUtils.isNotBlank((CharSequence)agent) && RegexUtils.find((String)pattern, (String)agent)) {
            LOGGER.debug("Current user agent [{}] at [{}] matches the provided pattern [{}] for adaptive authentication and is required to use [{}]", new Object[]{agent, clientIp, pattern, mfaMethod});
            return true;
        }
        if (StringUtils.isNotBlank((CharSequence)clientIp) && RegexUtils.find((String)pattern, (String)clientIp)) {
            LOGGER.debug("Current client IP [{}] matches the provided pattern [{}] for adaptive authentication and is required to use [{}]", new Object[]{clientIp, pattern, mfaMethod});
            return true;
        }
        return false;
    }

    public Optional<MultifactorAuthenticationProvider> isActivated(Authentication authentication, RegisteredService registeredService, HttpServletRequest httpServletRequest, HttpServletResponse response, Service service) {
        Map multifactorMap = this.casProperties.getAuthn().getAdaptive().getPolicy().getRequireMultifactor();
        if (authentication == null) {
            LOGGER.trace("No authentication is available to determine event for principal");
            return Optional.empty();
        }
        if (multifactorMap == null || multifactorMap.isEmpty()) {
            LOGGER.trace("Adaptive authentication is not configured to require multifactor authentication");
            return Optional.empty();
        }
        Map<String, MultifactorAuthenticationProvider> providerMap = MultifactorAuthenticationUtils.getAvailableMultifactorAuthenticationProviders(this.applicationContext);
        if (providerMap.isEmpty()) {
            LOGGER.error("No multifactor authentication providers are available in the application context");
            throw new AuthenticationException((Throwable)((Object)new MultifactorAuthenticationProviderAbsentException()));
        }
        ClientInfo clientInfo = ClientInfoHolder.getClientInfo();
        String clientIp = clientInfo.getClientIpAddress();
        LOGGER.debug("Located client IP address as [{}]", (Object)clientIp);
        String agent = HttpRequestUtils.getHttpServletRequestUserAgent((HttpServletRequest)httpServletRequest);
        Set entries = multifactorMap.entrySet();
        for (Map.Entry entry : entries) {
            String mfaMethod = entry.getKey().toString();
            String pattern = entry.getValue().toString();
            Optional<MultifactorAuthenticationProvider> providerFound = MultifactorAuthenticationUtils.resolveProvider(providerMap, mfaMethod);
            if (providerFound.isEmpty()) {
                LOGGER.error("Adaptive authentication is configured to require [{}] for [{}], yet [{}] is absent in the configuration.", new Object[]{mfaMethod, pattern, mfaMethod});
                throw new AuthenticationException();
            }
            if (AdaptiveMultifactorAuthenticationTrigger.checkUserAgentOrClientIp(clientIp, agent, mfaMethod, pattern)) {
                return providerFound;
            }
            if (!this.checkRequestGeoLocation(httpServletRequest, clientIp, mfaMethod, pattern)) continue;
            return providerFound;
        }
        return Optional.empty();
    }

    private boolean checkRequestGeoLocation(HttpServletRequest httpServletRequest, String clientIp, String mfaMethod, String pattern) {
        if (this.geoLocationService == null) {
            LOGGER.trace("No geolocation service is defined");
            return false;
        }
        GeoLocationRequest location = HttpRequestUtils.getHttpServletRequestGeoLocation((HttpServletRequest)httpServletRequest);
        GeoLocationResponse loc = this.geoLocationService.locate(clientIp, location);
        if (loc == null) {
            LOGGER.trace("No geolocation response is provided");
            return false;
        }
        String address = loc.build();
        if (RegexUtils.find((String)pattern, (String)address)) {
            LOGGER.debug("Current address [{}] at [{}] matches the provided pattern [{}] for adaptive authentication and is required to use [{}]", new Object[]{address, clientIp, pattern, mfaMethod});
            return true;
        }
        return false;
    }

    @Generated
    public GeoLocationService getGeoLocationService() {
        return this.geoLocationService;
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
    public AdaptiveMultifactorAuthenticationTrigger(GeoLocationService geoLocationService, CasConfigurationProperties casProperties, ApplicationContext applicationContext) {
        this.geoLocationService = geoLocationService;
        this.casProperties = casProperties;
        this.applicationContext = applicationContext;
    }
}

