/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.MultifactorAuthenticationProvider
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.configuration.model.support.mfa.MultifactorAuthenticationProviderBypassProperties
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.util.RegexUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.util.StringUtils
 */
package org.apereo.cas.authentication.bypass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.MultifactorAuthenticationProvider;
import org.apereo.cas.authentication.bypass.BaseMultifactorAuthenticationProviderBypassEvaluator;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.configuration.model.support.mfa.MultifactorAuthenticationProviderBypassProperties;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.util.RegexUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequestMultifactorAuthenticationProviderBypassEvaluator
extends BaseMultifactorAuthenticationProviderBypassEvaluator {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequestMultifactorAuthenticationProviderBypassEvaluator.class);
    private static final long serialVersionUID = -7553981418344342672L;
    private final MultifactorAuthenticationProviderBypassProperties bypassProperties;
    private final Pattern httpRequestRemoteAddressPattern;
    private final Set<Pattern> httpRequestHeaderPatterns;

    public HttpRequestMultifactorAuthenticationProviderBypassEvaluator(MultifactorAuthenticationProviderBypassProperties bypassProperties, String providerId) {
        super(providerId);
        this.bypassProperties = bypassProperties;
        this.httpRequestRemoteAddressPattern = StringUtils.isNotBlank((CharSequence)bypassProperties.getHttpRequestRemoteAddress()) ? RegexUtils.createPattern((String)bypassProperties.getHttpRequestRemoteAddress()) : RegexUtils.MATCH_NOTHING_PATTERN;
        Set values = org.springframework.util.StringUtils.commaDelimitedListToSet((String)bypassProperties.getHttpRequestHeaders());
        this.httpRequestHeaderPatterns = values.stream().map(RegexUtils::createPattern).collect(Collectors.toSet());
    }

    @Override
    public boolean shouldMultifactorAuthenticationProviderExecuteInternal(Authentication authentication, RegisteredService registeredService, MultifactorAuthenticationProvider provider, HttpServletRequest request) {
        Principal principal = authentication.getPrincipal();
        boolean bypassByHttpRequest = this.locateMatchingHttpRequest(authentication, request);
        if (bypassByHttpRequest) {
            LOGGER.debug("Bypass rules for http request indicate the request may be ignored for [{}]", (Object)principal.getId());
            return false;
        }
        return true;
    }

    protected boolean locateMatchingHttpRequest(Authentication authentication, HttpServletRequest request) {
        if (StringUtils.isNotBlank((CharSequence)this.bypassProperties.getHttpRequestRemoteAddress())) {
            if (this.httpRequestRemoteAddressPattern.matcher(request.getRemoteAddr()).find()) {
                LOGGER.debug("Http request remote address [{}] matches [{}]", (Object)this.bypassProperties.getHttpRequestRemoteAddress(), (Object)request.getRemoteAddr());
                return true;
            }
            if (this.httpRequestRemoteAddressPattern.matcher(request.getRemoteHost()).find()) {
                LOGGER.debug("Http request remote host [{}] matches [{}]", (Object)this.bypassProperties.getHttpRequestRemoteAddress(), (Object)request.getRemoteHost());
                return true;
            }
        }
        if (StringUtils.isNotBlank((CharSequence)this.bypassProperties.getHttpRequestHeaders())) {
            ArrayList headerNames = Collections.list(request.getHeaderNames());
            boolean matched = this.httpRequestHeaderPatterns.stream().anyMatch(pattern -> headerNames.stream().anyMatch(pattern.asMatchPredicate()));
            if (matched) {
                LOGGER.debug("Http request remote headers [{}] match [{}]", headerNames, (Object)this.bypassProperties.getHttpRequestHeaders());
                return true;
            }
        }
        return false;
    }
}

