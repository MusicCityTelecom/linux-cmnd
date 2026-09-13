/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.authentication.adaptive.AdaptiveAuthenticationPolicy
 *  org.apereo.cas.authentication.adaptive.geo.GeoLocationRequest
 *  org.apereo.cas.authentication.adaptive.geo.GeoLocationResponse
 *  org.apereo.cas.authentication.adaptive.geo.GeoLocationService
 *  org.apereo.cas.authentication.adaptive.intel.IPAddressIntelligenceResponse
 *  org.apereo.cas.authentication.adaptive.intel.IPAddressIntelligenceService
 *  org.apereo.cas.configuration.model.core.authentication.AdaptiveAuthenticationProperties
 *  org.apereo.cas.util.RegexUtils
 *  org.apereo.inspektr.common.web.ClientInfo
 *  org.apereo.inspektr.common.web.ClientInfoHolder
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.webflow.execution.RequestContext
 */
package org.apereo.cas.authentication.adaptive;

import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.adaptive.AdaptiveAuthenticationPolicy;
import org.apereo.cas.authentication.adaptive.geo.GeoLocationRequest;
import org.apereo.cas.authentication.adaptive.geo.GeoLocationResponse;
import org.apereo.cas.authentication.adaptive.geo.GeoLocationService;
import org.apereo.cas.authentication.adaptive.intel.IPAddressIntelligenceResponse;
import org.apereo.cas.authentication.adaptive.intel.IPAddressIntelligenceService;
import org.apereo.cas.configuration.model.core.authentication.AdaptiveAuthenticationProperties;
import org.apereo.cas.util.RegexUtils;
import org.apereo.inspektr.common.web.ClientInfo;
import org.apereo.inspektr.common.web.ClientInfoHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.webflow.execution.RequestContext;

public class DefaultAdaptiveAuthenticationPolicy
implements AdaptiveAuthenticationPolicy {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultAdaptiveAuthenticationPolicy.class);
    private final GeoLocationService geoLocationService;
    private final IPAddressIntelligenceService ipAddressIntelligenceService;
    private final AdaptiveAuthenticationProperties adaptiveAuthenticationProperties;

    public boolean apply(RequestContext requestContext, String userAgent, GeoLocationRequest location) {
        ClientInfo clientInfo = ClientInfoHolder.getClientInfo();
        if (clientInfo == null || StringUtils.isBlank((CharSequence)userAgent)) {
            LOGGER.warn("No client IP or user-agent was provided. Skipping adaptive authentication policy...");
            return true;
        }
        String clientIp = clientInfo.getClientIpAddress();
        if (this.isIpAddressRejected(requestContext, clientIp)) {
            LOGGER.warn("Client IP [{}] is rejected for authentication", (Object)clientIp);
            return false;
        }
        if (this.isUserAgentRejected(userAgent)) {
            LOGGER.warn("User agent [{}] is rejected for authentication", (Object)userAgent);
            return false;
        }
        LOGGER.debug("User agent [{}] is authorized to proceed", (Object)userAgent);
        if (this.geoLocationService != null && location != null && StringUtils.isNotBlank((CharSequence)clientIp) && StringUtils.isNotBlank((CharSequence)this.adaptiveAuthenticationProperties.getPolicy().getRejectCountries())) {
            GeoLocationResponse loc = this.geoLocationService.locate(clientIp, location);
            if (loc != null) {
                LOGGER.debug("Determined geolocation for [{}] to be [{}]", (Object)clientIp, (Object)loc);
                if (this.isGeoLocationCountryRejected(loc)) {
                    LOGGER.warn("Client [{}] is rejected for authentication based on country location", (Object)clientIp);
                    return false;
                }
            } else {
                LOGGER.info("Could not determine geolocation for [{}]", (Object)clientIp);
            }
        }
        LOGGER.debug("Adaptive authentication policy has authorized client [{}] to proceed.", (Object)clientIp);
        return true;
    }

    private boolean isGeoLocationCountryRejected(GeoLocationResponse finalLoc) {
        String rejectCountries = this.adaptiveAuthenticationProperties.getPolicy().getRejectCountries();
        return StringUtils.isNotBlank((CharSequence)rejectCountries) && RegexUtils.find((String)rejectCountries, (String)finalLoc.build());
    }

    private boolean isUserAgentRejected(String userAgent) {
        String rejectBrowsers = this.adaptiveAuthenticationProperties.getPolicy().getRejectBrowsers();
        return StringUtils.isNotBlank((CharSequence)rejectBrowsers) && RegexUtils.find((String)rejectBrowsers, (String)userAgent);
    }

    private boolean isIpAddressRejected(RequestContext requestContext, String clientIp) {
        LOGGER.trace("Located client IP address as [{}]", (Object)clientIp);
        IPAddressIntelligenceResponse ipResult = this.ipAddressIntelligenceService.examine(requestContext, clientIp);
        if (ipResult == null || ipResult.isBanned()) {
            LOGGER.warn("Client IP [{}] is banned", (Object)clientIp);
            return true;
        }
        if (ipResult.isRanked()) {
            double threshold = this.adaptiveAuthenticationProperties.getRisk().getThreshold();
            if (ipResult.getScore() >= threshold) {
                LOGGER.warn("Client IP [{}] is rejected for authentication because intelligence score [{}] is higher than the configured risk threshold [{}]", new Object[]{clientIp, ipResult.getScore(), threshold});
                return true;
            }
        }
        return false;
    }

    @Generated
    public DefaultAdaptiveAuthenticationPolicy(GeoLocationService geoLocationService, IPAddressIntelligenceService ipAddressIntelligenceService, AdaptiveAuthenticationProperties adaptiveAuthenticationProperties) {
        this.geoLocationService = geoLocationService;
        this.ipAddressIntelligenceService = ipAddressIntelligenceService;
        this.adaptiveAuthenticationProperties = adaptiveAuthenticationProperties;
    }
}

