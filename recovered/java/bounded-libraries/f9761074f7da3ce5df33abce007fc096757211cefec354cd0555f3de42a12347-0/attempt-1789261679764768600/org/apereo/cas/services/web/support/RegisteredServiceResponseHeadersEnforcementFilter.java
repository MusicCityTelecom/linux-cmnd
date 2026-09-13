/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  lombok.Generated
 *  org.apache.commons.lang3.BooleanUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.audit.AuditableContext
 *  org.apereo.cas.audit.AuditableExecution
 *  org.apereo.cas.audit.AuditableExecutionResult
 *  org.apereo.cas.authentication.AuthenticationServiceSelectionPlan
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.authentication.principal.WebApplicationService
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.RegisteredServiceProperty
 *  org.apereo.cas.services.RegisteredServiceProperty$RegisteredServiceProperties
 *  org.apereo.cas.services.ServicesManager
 *  org.apereo.cas.util.LoggingUtils
 *  org.apereo.cas.web.support.ArgumentExtractor
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.http.HttpStatus
 */
package org.apereo.cas.services.web.support;

import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Generated;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.audit.AuditableContext;
import org.apereo.cas.audit.AuditableExecution;
import org.apereo.cas.audit.AuditableExecutionResult;
import org.apereo.cas.authentication.AuthenticationServiceSelectionPlan;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.authentication.principal.WebApplicationService;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.RegisteredServiceProperty;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.util.LoggingUtils;
import org.apereo.cas.web.support.ArgumentExtractor;
import org.apereo.cas.web.support.filters.ResponseHeadersEnforcementFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.http.HttpStatus;

public class RegisteredServiceResponseHeadersEnforcementFilter
extends ResponseHeadersEnforcementFilter {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisteredServiceResponseHeadersEnforcementFilter.class);
    private final ObjectProvider<ServicesManager> servicesManager;
    private final ObjectProvider<ArgumentExtractor> argumentExtractor;
    private final ObjectProvider<AuthenticationServiceSelectionPlan> authenticationRequestServiceSelectionStrategies;
    private final ObjectProvider<AuditableExecution> registeredServiceAccessStrategyEnforcer;

    private static String getStringProperty(Optional<Object> result, RegisteredServiceProperty.RegisteredServiceProperties property) {
        if (result.isPresent()) {
            RegisteredService registeredService = (RegisteredService)RegisteredService.class.cast(result.get());
            LOGGER.trace("Resolved registered service [{}] from request to enforce response headers", (Object)registeredService);
            Map properties = registeredService.getProperties();
            if (!properties.containsKey(property.getPropertyName())) {
                LOGGER.trace("Resolved registered service [{}] from request does not contain a property definition for [{}]", (Object)registeredService.getName(), (Object)property.getPropertyName());
                return null;
            }
            RegisteredServiceProperty prop = (RegisteredServiceProperty)properties.get(property.getPropertyName());
            return prop.getValue();
        }
        LOGGER.trace("Resolved registered service from request can not be located");
        return null;
    }

    @Override
    protected Optional<Object> prepareFilterBeforeExecution(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        try {
            return this.getRegisteredServiceFromRequest(httpServletRequest);
        }
        catch (Exception e) {
            LoggingUtils.error((Logger)LOGGER, (Throwable)e);
            httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
            return Optional.empty();
        }
    }

    @Override
    protected void decideInsertContentSecurityPolicyHeader(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest, Optional<Object> result) {
        Optional<Boolean> shouldInject = RegisteredServiceResponseHeadersEnforcementFilter.shouldHttpHeaderBeInjectedIntoResponse(result, RegisteredServiceProperty.RegisteredServiceProperties.HTTP_HEADER_ENABLE_CONTENT_SECURITY_POLICY);
        if (shouldInject.isPresent()) {
            if (shouldInject.get().booleanValue()) {
                super.insertContentSecurityPolicyHeader(httpServletResponse, httpServletRequest);
            } else {
                LOGGER.trace("ContentSecurityPolicy header disabled by service definition");
            }
        } else {
            super.decideInsertContentSecurityPolicyHeader(httpServletResponse, httpServletRequest, result);
        }
    }

    @Override
    protected void decideInsertXSSProtectionHeader(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest, Optional<Object> result) {
        Optional<Boolean> shouldInject = RegisteredServiceResponseHeadersEnforcementFilter.shouldHttpHeaderBeInjectedIntoResponse(result, RegisteredServiceProperty.RegisteredServiceProperties.HTTP_HEADER_ENABLE_XSS_PROTECTION);
        if (shouldInject.isPresent()) {
            if (shouldInject.get().booleanValue()) {
                super.insertXSSProtectionHeader(httpServletResponse, httpServletRequest);
            } else {
                LOGGER.trace("XSSProtection header disabled by service definition");
            }
        } else {
            super.decideInsertXSSProtectionHeader(httpServletResponse, httpServletRequest, result);
        }
    }

    @Override
    protected void decideInsertXFrameOptionsHeader(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest, Optional<Object> result) {
        Optional<Boolean> shouldInject = RegisteredServiceResponseHeadersEnforcementFilter.shouldHttpHeaderBeInjectedIntoResponse(result, RegisteredServiceProperty.RegisteredServiceProperties.HTTP_HEADER_ENABLE_XFRAME_OPTIONS);
        if (shouldInject.isPresent()) {
            if (shouldInject.get().booleanValue()) {
                String xFrameOptions = RegisteredServiceResponseHeadersEnforcementFilter.getStringProperty(result, RegisteredServiceProperty.RegisteredServiceProperties.HTTP_HEADER_XFRAME_OPTIONS);
                super.insertXFrameOptionsHeader(httpServletResponse, httpServletRequest, xFrameOptions);
            } else {
                LOGGER.trace("XFrameOptions header disabled by service definition");
            }
        } else {
            super.decideInsertXFrameOptionsHeader(httpServletResponse, httpServletRequest, result);
        }
    }

    @Override
    protected void decideInsertXContentTypeOptionsHeader(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest, Optional<Object> result) {
        Optional<Boolean> shouldInject = RegisteredServiceResponseHeadersEnforcementFilter.shouldHttpHeaderBeInjectedIntoResponse(result, RegisteredServiceProperty.RegisteredServiceProperties.HTTP_HEADER_ENABLE_XCONTENT_OPTIONS);
        if (shouldInject.isPresent()) {
            if (shouldInject.get().booleanValue()) {
                super.insertXContentTypeOptionsHeader(httpServletResponse, httpServletRequest);
            } else {
                LOGGER.trace("XContentOptions header disabled by service definition");
            }
        } else {
            super.decideInsertXContentTypeOptionsHeader(httpServletResponse, httpServletRequest, result);
        }
    }

    @Override
    protected void decideInsertCacheControlHeader(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest, Optional<Object> result) {
        Optional<Boolean> shouldInject = RegisteredServiceResponseHeadersEnforcementFilter.shouldHttpHeaderBeInjectedIntoResponse(result, RegisteredServiceProperty.RegisteredServiceProperties.HTTP_HEADER_ENABLE_CACHE_CONTROL);
        if (shouldInject.isPresent()) {
            if (shouldInject.get().booleanValue()) {
                super.insertCacheControlHeader(httpServletResponse, httpServletRequest);
            } else {
                LOGGER.trace("EnableCacheControl header disabled by service definition");
            }
        } else {
            super.decideInsertCacheControlHeader(httpServletResponse, httpServletRequest, result);
        }
    }

    @Override
    protected void decideInsertStrictTransportSecurityHeader(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest, Optional<Object> result) {
        Optional<Boolean> shouldInject = RegisteredServiceResponseHeadersEnforcementFilter.shouldHttpHeaderBeInjectedIntoResponse(result, RegisteredServiceProperty.RegisteredServiceProperties.HTTP_HEADER_ENABLE_STRICT_TRANSPORT_SECURITY);
        if (shouldInject.isPresent()) {
            if (shouldInject.get().booleanValue()) {
                String headerValue = (String)StringUtils.defaultIfBlank((CharSequence)RegisteredServiceResponseHeadersEnforcementFilter.getStringProperty(result, RegisteredServiceProperty.RegisteredServiceProperties.HTTP_HEADER_STRICT_TRANSPORT_SECURITY), (CharSequence)this.getStrictTransportSecurityHeader());
                super.insertStrictTransportSecurityHeader(httpServletResponse, httpServletRequest, headerValue);
            } else {
                LOGGER.trace("StrictTransportSecurity header disabled by service definition");
            }
        } else {
            super.decideInsertStrictTransportSecurityHeader(httpServletResponse, httpServletRequest, result);
        }
    }

    private static Optional<Boolean> shouldHttpHeaderBeInjectedIntoResponse(Optional<Object> registeredService, RegisteredServiceProperty.RegisteredServiceProperties property) {
        String propValue = RegisteredServiceResponseHeadersEnforcementFilter.getStringProperty(registeredService, property);
        if (propValue != null) {
            return Optional.of(BooleanUtils.toBoolean((String)propValue));
        }
        return Optional.empty();
    }

    private Optional<Object> getRegisteredServiceFromRequest(HttpServletRequest request) {
        WebApplicationService service = ((ArgumentExtractor)this.argumentExtractor.getObject()).extractService(request);
        if (service != null) {
            LOGGER.trace("Attempting to resolve service for [{}]", (Object)service);
            Service resolved = ((AuthenticationServiceSelectionPlan)this.authenticationRequestServiceSelectionStrategies.getObject()).resolveService((Service)service);
            RegisteredService registeredService = ((ServicesManager)this.servicesManager.getObject()).findServiceBy(resolved);
            AuditableContext audit = AuditableContext.builder().registeredService(registeredService).service((Service)service).build();
            AuditableExecutionResult accessResult = ((AuditableExecution)this.registeredServiceAccessStrategyEnforcer.getObject()).execute(audit);
            accessResult.throwExceptionIfNeeded();
            return Optional.of(registeredService);
        }
        LOGGER.trace("Service could not be extracted from request to enforce response headers");
        return Optional.empty();
    }

    @Generated
    public RegisteredServiceResponseHeadersEnforcementFilter(ObjectProvider<ServicesManager> servicesManager, ObjectProvider<ArgumentExtractor> argumentExtractor, ObjectProvider<AuthenticationServiceSelectionPlan> authenticationRequestServiceSelectionStrategies, ObjectProvider<AuditableExecution> registeredServiceAccessStrategyEnforcer) {
        this.servicesManager = servicesManager;
        this.argumentExtractor = argumentExtractor;
        this.authenticationRequestServiceSelectionStrategies = authenticationRequestServiceSelectionStrategies;
        this.registeredServiceAccessStrategyEnforcer = registeredServiceAccessStrategyEnforcer;
    }
}

