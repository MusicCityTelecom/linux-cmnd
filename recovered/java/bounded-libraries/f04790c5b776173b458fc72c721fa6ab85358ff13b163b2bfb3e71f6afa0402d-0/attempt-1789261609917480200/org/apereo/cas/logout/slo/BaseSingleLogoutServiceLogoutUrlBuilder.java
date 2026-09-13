/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  lombok.Generated
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.authentication.principal.WebApplicationService
 *  org.apereo.cas.logout.slo.SingleLogoutServiceLogoutUrlBuilder
 *  org.apereo.cas.logout.slo.SingleLogoutUrl
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.ServicesManager
 *  org.apereo.cas.services.WebBasedRegisteredService
 *  org.apereo.cas.util.CollectionUtils
 *  org.apereo.cas.web.UrlValidator
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.util.StringUtils
 */
package org.apereo.cas.logout.slo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Generated;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.authentication.principal.WebApplicationService;
import org.apereo.cas.logout.slo.SingleLogoutServiceLogoutUrlBuilder;
import org.apereo.cas.logout.slo.SingleLogoutUrl;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.services.WebBasedRegisteredService;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.web.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public abstract class BaseSingleLogoutServiceLogoutUrlBuilder
implements SingleLogoutServiceLogoutUrlBuilder {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseSingleLogoutServiceLogoutUrlBuilder.class);
    protected final ServicesManager servicesManager;
    protected final UrlValidator urlValidator;

    public boolean supports(RegisteredService registeredService, WebApplicationService singleLogoutService, Optional<HttpServletRequest> httpRequest) {
        return registeredService != null && singleLogoutService != null && registeredService.getAccessStrategy().isServiceAccessAllowed();
    }

    public boolean isServiceAuthorized(WebApplicationService service, Optional<HttpServletRequest> request, Optional<HttpServletResponse> response) {
        RegisteredService registeredService = this.servicesManager.findServiceBy((Service)service);
        return this.supports(registeredService, service, request);
    }

    public Collection<SingleLogoutUrl> determineLogoutUrl(RegisteredService registeredService, WebApplicationService singleLogoutService, Optional<HttpServletRequest> httpRequest) {
        String originalUrl = singleLogoutService.getOriginalUrl();
        if (registeredService instanceof WebBasedRegisteredService) {
            WebBasedRegisteredService webRegisteredService = (WebBasedRegisteredService)registeredService;
            String serviceLogoutUrl = webRegisteredService.getLogoutUrl();
            if (StringUtils.hasText((String)serviceLogoutUrl)) {
                LOGGER.debug("Logout request will be sent to [{}] for service [{}]", (Object)serviceLogoutUrl, (Object)singleLogoutService);
                return SingleLogoutUrl.from((RegisteredService)registeredService);
            }
            if (this.urlValidator.isValid(originalUrl)) {
                LOGGER.debug("Logout request will be sent to [{}] for service [{}]", (Object)originalUrl, (Object)singleLogoutService);
                return CollectionUtils.wrap((Object)new SingleLogoutUrl(originalUrl, webRegisteredService.getLogoutType()));
            }
        }
        LOGGER.debug("Logout request will not be sent; The URL [{}] for service [{}] is not valid", (Object)originalUrl, (Object)singleLogoutService);
        return new ArrayList<SingleLogoutUrl>(0);
    }

    @Generated
    protected BaseSingleLogoutServiceLogoutUrlBuilder(ServicesManager servicesManager, UrlValidator urlValidator) {
        this.servicesManager = servicesManager;
        this.urlValidator = urlValidator;
    }

    @Generated
    public ServicesManager getServicesManager() {
        return this.servicesManager;
    }

    @Generated
    public UrlValidator getUrlValidator() {
        return this.urlValidator;
    }
}

