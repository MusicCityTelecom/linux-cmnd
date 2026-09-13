/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  lombok.Generated
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.MultifactorAuthenticationProvider
 *  org.apereo.cas.authentication.MultifactorAuthenticationTrigger
 *  org.apereo.cas.authentication.MultifactorAuthenticationTriggerSelectionStrategy
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.util.spring.beans.BeanSupplier
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.authentication;

import java.util.Collection;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Generated;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.MultifactorAuthenticationProvider;
import org.apereo.cas.authentication.MultifactorAuthenticationTrigger;
import org.apereo.cas.authentication.MultifactorAuthenticationTriggerSelectionStrategy;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.util.spring.beans.BeanSupplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultMultifactorAuthenticationTriggerSelectionStrategy
implements MultifactorAuthenticationTriggerSelectionStrategy {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultMultifactorAuthenticationTriggerSelectionStrategy.class);
    private final Collection<MultifactorAuthenticationTrigger> multifactorAuthenticationTriggers;

    public Optional<MultifactorAuthenticationProvider> resolve(HttpServletRequest request, HttpServletResponse response, RegisteredService registeredService, Authentication authentication, Service service) {
        if (registeredService != null && registeredService.getMultifactorAuthenticationPolicy().isBypassEnabled()) {
            LOGGER.debug("Multifactor authentication policy for [{}] will ignore trigger executions", (Object)registeredService.getName());
            return Optional.empty();
        }
        for (MultifactorAuthenticationTrigger trigger : this.multifactorAuthenticationTriggers) {
            Optional activated;
            if (BeanSupplier.isNotProxy((Object)trigger) && !trigger.supports(request, registeredService, authentication, service) || !(activated = trigger.isActivated(authentication, registeredService, request, response, service)).isPresent()) continue;
            return activated;
        }
        return Optional.empty();
    }

    @Generated
    public DefaultMultifactorAuthenticationTriggerSelectionStrategy(Collection<MultifactorAuthenticationTrigger> multifactorAuthenticationTriggers) {
        this.multifactorAuthenticationTriggers = multifactorAuthenticationTriggers;
    }

    @Generated
    public Collection<MultifactorAuthenticationTrigger> getMultifactorAuthenticationTriggers() {
        return this.multifactorAuthenticationTriggers;
    }
}

