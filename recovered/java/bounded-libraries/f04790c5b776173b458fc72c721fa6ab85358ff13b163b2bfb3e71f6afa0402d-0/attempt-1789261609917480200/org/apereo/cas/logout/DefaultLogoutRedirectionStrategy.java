/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.authentication.principal.ServiceFactory
 *  org.apereo.cas.authentication.principal.WebApplicationService
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.logout.LogoutRedirectionStrategy
 *  org.apereo.cas.logout.slo.SingleLogoutServiceLogoutUrlBuilder
 *  org.apereo.cas.util.function.FunctionUtils
 *  org.apereo.cas.web.support.ArgumentExtractor
 *  org.apereo.cas.web.support.WebUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.webflow.execution.RequestContext
 */
package org.apereo.cas.logout;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.authentication.principal.ServiceFactory;
import org.apereo.cas.authentication.principal.WebApplicationService;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.logout.LogoutRedirectionStrategy;
import org.apereo.cas.logout.slo.SingleLogoutServiceLogoutUrlBuilder;
import org.apereo.cas.util.function.FunctionUtils;
import org.apereo.cas.web.support.ArgumentExtractor;
import org.apereo.cas.web.support.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.webflow.execution.RequestContext;

public class DefaultLogoutRedirectionStrategy
implements LogoutRedirectionStrategy {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultLogoutRedirectionStrategy.class);
    private final ArgumentExtractor argumentExtractor;
    private final CasConfigurationProperties casProperties;
    private final SingleLogoutServiceLogoutUrlBuilder singleLogoutServiceLogoutUrlBuilder;
    private final ServiceFactory<WebApplicationService> serviceFactory;

    public boolean supports(RequestContext context) {
        return context != null;
    }

    public void handle(RequestContext requestContext) {
        HttpServletRequest request = WebUtils.getHttpServletRequestFromExternalWebflowContext((RequestContext)requestContext);
        HttpServletResponse response = WebUtils.getHttpServletResponseFromExternalWebflowContext((RequestContext)requestContext);
        Optional.ofNullable(this.argumentExtractor.extractService(request)).or(() -> {
            String redirectUrl = this.casProperties.getView().getDefaultRedirectUrl();
            return (Optional)FunctionUtils.doIf((boolean)StringUtils.isNotBlank((CharSequence)redirectUrl), () -> Optional.of((WebApplicationService)this.serviceFactory.createService(redirectUrl)), Optional::empty).get();
        }).filter(service -> this.singleLogoutServiceLogoutUrlBuilder.isServiceAuthorized(service, Optional.of(request), Optional.of(response))).ifPresentOrElse(service -> {
            WebUtils.putServiceIntoFlowScope((RequestContext)requestContext, (Service)service);
            if (this.casProperties.getLogout().isFollowServiceRedirects()) {
                LOGGER.debug("Redirecting to logout URL identified by service [{}]", service);
                WebUtils.putLogoutRedirectUrl((RequestContext)requestContext, (String)service.getOriginalUrl());
            } else {
                LOGGER.debug("Cannot redirect to [{}] given the service is unauthorized to use CAS, or following logout redirects is disabled in CAS settings. Ensure the service is registered with CAS and is enabled to allow access", service);
            }
        }, () -> {
            String authorizedRedirectUrlFromRequest = (String)WebUtils.getLogoutRedirectUrl((HttpServletRequest)request, String.class);
            if (StringUtils.isNotBlank((CharSequence)authorizedRedirectUrlFromRequest)) {
                WebUtils.putLogoutRedirectUrl((RequestContext)requestContext, (String)authorizedRedirectUrlFromRequest);
            }
        });
    }

    @Generated
    public DefaultLogoutRedirectionStrategy(ArgumentExtractor argumentExtractor, CasConfigurationProperties casProperties, SingleLogoutServiceLogoutUrlBuilder singleLogoutServiceLogoutUrlBuilder, ServiceFactory<WebApplicationService> serviceFactory) {
        this.argumentExtractor = argumentExtractor;
        this.casProperties = casProperties;
        this.singleLogoutServiceLogoutUrlBuilder = singleLogoutServiceLogoutUrlBuilder;
        this.serviceFactory = serviceFactory;
    }
}

