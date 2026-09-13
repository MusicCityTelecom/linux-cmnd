/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  lombok.Generated
 *  org.apache.http.HttpResponse
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.MultifactorAuthenticationProvider
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.configuration.model.support.mfa.MultifactorAuthenticationProviderBypassProperties
 *  org.apereo.cas.configuration.model.support.mfa.RestfulMultifactorAuthenticationProviderBypassProperties
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.util.CollectionUtils
 *  org.apereo.cas.util.HttpUtils
 *  org.apereo.cas.util.HttpUtils$HttpExecutionRequest
 *  org.apereo.cas.util.LoggingUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.http.HttpMethod
 *  org.springframework.http.HttpStatus
 */
package org.apereo.cas.authentication.bypass;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.Generated;
import org.apache.http.HttpResponse;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.MultifactorAuthenticationProvider;
import org.apereo.cas.authentication.bypass.BaseMultifactorAuthenticationProviderBypassEvaluator;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.configuration.model.support.mfa.MultifactorAuthenticationProviderBypassProperties;
import org.apereo.cas.configuration.model.support.mfa.RestfulMultifactorAuthenticationProviderBypassProperties;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.util.HttpUtils;
import org.apereo.cas.util.LoggingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

public class RestMultifactorAuthenticationProviderBypassEvaluator
extends BaseMultifactorAuthenticationProviderBypassEvaluator {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(RestMultifactorAuthenticationProviderBypassEvaluator.class);
    private static final long serialVersionUID = -7553888418344342672L;
    private final MultifactorAuthenticationProviderBypassProperties bypassProperties;

    public RestMultifactorAuthenticationProviderBypassEvaluator(MultifactorAuthenticationProviderBypassProperties bypassProperties, String providerId) {
        super(providerId);
        this.bypassProperties = bypassProperties;
    }

    @Override
    public boolean shouldMultifactorAuthenticationProviderExecuteInternal(Authentication authentication, RegisteredService registeredService, MultifactorAuthenticationProvider provider, HttpServletRequest request) {
        try {
            HttpUtils.HttpExecutionRequest exec;
            HttpResponse response;
            Principal principal = this.resolvePrincipal(authentication.getPrincipal());
            RestfulMultifactorAuthenticationProviderBypassProperties rest = this.bypassProperties.getRest();
            LOGGER.debug("Evaluating multifactor authentication bypass properties for principal [{}], service [{}] and provider [{}] via REST endpoint [{}]", new Object[]{principal.getId(), registeredService, provider, rest.getUrl()});
            Map parameters = CollectionUtils.wrap((String)"principal", (Object)principal.getId(), (String)"provider", (Object)provider.getId());
            if (registeredService != null) {
                parameters.put("service", registeredService.getServiceId());
            }
            return (response = HttpUtils.execute((HttpUtils.HttpExecutionRequest)(exec = HttpUtils.HttpExecutionRequest.builder().basicAuthPassword(rest.getBasicAuthPassword()).basicAuthUsername(rest.getBasicAuthUsername()).method(HttpMethod.valueOf((String)rest.getMethod().toUpperCase().trim())).url(rest.getUrl()).parameters(parameters).build()))) != null && response.getStatusLine().getStatusCode() == HttpStatus.ACCEPTED.value();
        }
        catch (Exception e) {
            LoggingUtils.error((Logger)LOGGER, (Throwable)e);
            return true;
        }
    }
}

