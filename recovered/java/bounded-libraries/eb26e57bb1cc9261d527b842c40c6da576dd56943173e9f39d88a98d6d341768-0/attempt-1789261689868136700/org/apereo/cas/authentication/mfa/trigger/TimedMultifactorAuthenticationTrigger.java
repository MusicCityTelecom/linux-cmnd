/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  lombok.Generated
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.AuthenticationException
 *  org.apereo.cas.authentication.MultifactorAuthenticationProvider
 *  org.apereo.cas.authentication.MultifactorAuthenticationTrigger
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.configuration.model.core.authentication.TimeBasedAuthenticationProperties
 *  org.apereo.cas.services.RegisteredService
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.context.ApplicationContext
 */
package org.apereo.cas.authentication.mfa.trigger;

import java.lang.invoke.LambdaMetafactory;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Generated;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.AuthenticationException;
import org.apereo.cas.authentication.MultifactorAuthenticationProvider;
import org.apereo.cas.authentication.MultifactorAuthenticationProviderAbsentException;
import org.apereo.cas.authentication.MultifactorAuthenticationTrigger;
import org.apereo.cas.authentication.MultifactorAuthenticationUtils;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.model.core.authentication.TimeBasedAuthenticationProperties;
import org.apereo.cas.services.RegisteredService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

public class TimedMultifactorAuthenticationTrigger
implements MultifactorAuthenticationTrigger {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(TimedMultifactorAuthenticationTrigger.class);
    private final CasConfigurationProperties casProperties;
    private final ApplicationContext applicationContext;
    private int order = Integer.MAX_VALUE;

    public Optional<MultifactorAuthenticationProvider> isActivated(Authentication authentication, RegisteredService registeredService, HttpServletRequest httpServletRequest, HttpServletResponse response, Service service) {
        List timedMultifactor = this.casProperties.getAuthn().getAdaptive().getPolicy().getRequireTimedMultifactor();
        if (service == null || authentication == null) {
            LOGGER.trace("No service or authentication is available to determine event for principal");
            return Optional.empty();
        }
        if (timedMultifactor == null || timedMultifactor.isEmpty()) {
            LOGGER.trace("Adaptive authentication is not configured to require multifactor authentication by time");
            return Optional.empty();
        }
        Map<String, MultifactorAuthenticationProvider> providerMap = MultifactorAuthenticationUtils.getAvailableMultifactorAuthenticationProviders(this.applicationContext);
        if (providerMap.isEmpty()) {
            LOGGER.error("No multifactor authentication providers are available in the application context");
            throw new AuthenticationException((Throwable)((Object)new MultifactorAuthenticationProviderAbsentException()));
        }
        return this.checkTimedMultifactorProvidersForRequest(registeredService);
    }

    protected Optional<MultifactorAuthenticationProvider> checkTimedMultifactorProvidersForRequest(RegisteredService service) {
        List timedMultifactor = this.casProperties.getAuthn().getAdaptive().getPolicy().getRequireTimedMultifactor();
        LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
        DayOfWeek dow = DayOfWeek.from(now);
        List dayNamesForToday = Arrays.stream(TextStyle.values()).map(style -> dow.getDisplayName((TextStyle)((Object)style), Locale.getDefault())).collect(Collectors.toList());
        TimeBasedAuthenticationProperties timed = timedMultifactor.stream().filter(arg_0 -> TimedMultifactorAuthenticationTrigger.lambda$checkTimedMultifactorProvidersForRequest$1(dayNamesForToday, now, arg_0)).findFirst().orElse(null);
        if (timed != null) {
            Map<String, MultifactorAuthenticationProvider> providerMap = MultifactorAuthenticationUtils.getAvailableMultifactorAuthenticationProviders(this.applicationContext);
            Optional<MultifactorAuthenticationProvider> providerFound = MultifactorAuthenticationUtils.resolveProvider(providerMap, timed.getProviderId());
            if (providerFound.isEmpty()) {
                LOGGER.error("Adaptive authentication is configured to require [{}] for [{}], yet [{}] absent in the configuration.", new Object[]{timed.getProviderId(), service, timed.getProviderId()});
                throw new AuthenticationException();
            }
            return providerFound;
        }
        return Optional.empty();
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
    public TimedMultifactorAuthenticationTrigger(CasConfigurationProperties casProperties, ApplicationContext applicationContext) {
        this.casProperties = casProperties;
        this.applicationContext = applicationContext;
    }

    /*
     * Unable to fully structure code
     */
    private static /* synthetic */ boolean lambda$checkTimedMultifactorProvidersForRequest$1(List dayNamesForToday, LocalDateTime now, TimeBasedAuthenticationProperties t) {
        if (t.getOnDays().isEmpty()) ** GOTO lbl-1000
        if (t.getOnDays().stream().anyMatch((Predicate<String>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Z, contains(java.lang.Object ), (Ljava/lang/String;)Z)((List)dayNamesForToday))) {
            v0 = true;
        } else lbl-1000:
        // 2 sources

        {
            v0 = providerEvent = false;
        }
        if (t.getOnOrAfterHour() >= 0L) {
            v1 = providerEvent = (long)now.getHour() >= t.getOnOrAfterHour();
        }
        if (t.getOnOrBeforeHour() >= 0L) {
            providerEvent = (long)now.getHour() <= t.getOnOrBeforeHour();
        }
        return providerEvent;
    }
}

