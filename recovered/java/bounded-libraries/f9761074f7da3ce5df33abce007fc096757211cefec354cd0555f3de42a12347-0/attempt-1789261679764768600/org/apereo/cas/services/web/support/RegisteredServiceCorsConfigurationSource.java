/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  lombok.Generated
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.authentication.principal.WebApplicationService
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.configuration.model.core.web.security.HttpCorsRequestProperties
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.RegisteredServiceProperty$RegisteredServiceProperties
 *  org.apereo.cas.services.ServicesManager
 *  org.apereo.cas.web.support.ArgumentExtractor
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.web.cors.CorsConfiguration
 *  org.springframework.web.cors.CorsConfigurationSource
 */
package org.apereo.cas.services.web.support;

import java.util.ArrayList;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import lombok.Generated;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.authentication.principal.WebApplicationService;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.model.core.web.security.HttpCorsRequestProperties;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.RegisteredServiceProperty;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.web.support.ArgumentExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

public class RegisteredServiceCorsConfigurationSource
implements CorsConfigurationSource {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisteredServiceCorsConfigurationSource.class);
    private final CasConfigurationProperties casProperties;
    private final ServicesManager servicesManager;
    private final ArgumentExtractor argumentExtractor;

    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
        HttpCorsRequestProperties cors = this.casProperties.getHttpWebRequest().getCors();
        CorsConfiguration config = new CorsConfiguration();
        WebApplicationService service = this.argumentExtractor.extractService(request);
        LOGGER.trace("Extracted service [{}] from the request", (Object)service);
        RegisteredService registeredService = this.servicesManager.findServiceBy((Service)service);
        config.setAllowCredentials(Boolean.valueOf(cors.isAllowCredentials()));
        config.setMaxAge(Long.valueOf(cors.getMaxAge()));
        config.setAllowedOrigins(cors.getAllowOrigins());
        config.setAllowedOriginPatterns(cors.getAllowOriginPatterns());
        config.setAllowedMethods(cors.getAllowMethods());
        config.setAllowedHeaders(cors.getAllowHeaders());
        config.setExposedHeaders(cors.getExposedHeaders());
        if (registeredService != null) {
            Set result;
            LOGGER.trace("Evaluating registered service [{}] for cors configuration", (Object)registeredService);
            if (RegisteredServiceProperty.RegisteredServiceProperties.CORS_ALLOW_CREDENTIALS.isAssignedTo(registeredService)) {
                boolean result2 = RegisteredServiceProperty.RegisteredServiceProperties.CORS_ALLOW_CREDENTIALS.getPropertyBooleanValue(registeredService);
                config.setAllowCredentials(Boolean.valueOf(result2));
            }
            if (RegisteredServiceProperty.RegisteredServiceProperties.CORS_MAX_AGE.isAssignedTo(registeredService)) {
                long result3 = RegisteredServiceProperty.RegisteredServiceProperties.CORS_MAX_AGE.getPropertyLongValue(registeredService);
                config.setMaxAge(Long.valueOf(result3));
            }
            if (RegisteredServiceProperty.RegisteredServiceProperties.CORS_ALLOWED_ORIGINS.isAssignedTo(registeredService) && (result = (Set)RegisteredServiceProperty.RegisteredServiceProperties.CORS_ALLOWED_ORIGINS.getPropertyValues(registeredService, Set.class)) != null) {
                config.setAllowedOrigins(new ArrayList(result));
            }
            if (RegisteredServiceProperty.RegisteredServiceProperties.CORS_ALLOWED_ORIGIN_PATTERNS.isAssignedTo(registeredService) && (result = (Set)RegisteredServiceProperty.RegisteredServiceProperties.CORS_ALLOWED_ORIGIN_PATTERNS.getPropertyValues(registeredService, Set.class)) != null) {
                config.setAllowedOriginPatterns(new ArrayList(result));
            }
            if (RegisteredServiceProperty.RegisteredServiceProperties.CORS_ALLOWED_METHODS.isAssignedTo(registeredService) && (result = (Set)RegisteredServiceProperty.RegisteredServiceProperties.CORS_ALLOWED_METHODS.getPropertyValues(registeredService, Set.class)) != null) {
                config.setAllowedMethods(new ArrayList(result));
            }
            if (RegisteredServiceProperty.RegisteredServiceProperties.CORS_ALLOWED_HEADERS.isAssignedTo(registeredService) && (result = (Set)RegisteredServiceProperty.RegisteredServiceProperties.CORS_ALLOWED_HEADERS.getPropertyValues(registeredService, Set.class)) != null) {
                config.setAllowedHeaders(new ArrayList(result));
            }
            if (RegisteredServiceProperty.RegisteredServiceProperties.CORS_EXPOSED_HEADERS.isAssignedTo(registeredService) && (result = (Set)RegisteredServiceProperty.RegisteredServiceProperties.CORS_EXPOSED_HEADERS.getPropertyValues(registeredService, Set.class)) != null) {
                config.setExposedHeaders(new ArrayList(result));
            }
        }
        return config;
    }

    @Generated
    public RegisteredServiceCorsConfigurationSource(CasConfigurationProperties casProperties, ServicesManager servicesManager, ArgumentExtractor argumentExtractor) {
        this.casProperties = casProperties;
        this.servicesManager = servicesManager;
        this.argumentExtractor = argumentExtractor;
    }
}

