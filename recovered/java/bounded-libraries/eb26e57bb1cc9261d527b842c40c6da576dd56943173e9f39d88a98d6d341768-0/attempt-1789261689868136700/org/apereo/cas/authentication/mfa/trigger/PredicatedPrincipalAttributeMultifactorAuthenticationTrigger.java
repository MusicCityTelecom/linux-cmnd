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
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.util.ResourceUtils
 *  org.apereo.cas.util.scripting.ScriptingUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.context.ApplicationContext
 *  org.springframework.core.Ordered
 *  org.springframework.core.io.Resource
 */
package org.apereo.cas.authentication.mfa.trigger;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Generated;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.MultifactorAuthenticationProvider;
import org.apereo.cas.authentication.MultifactorAuthenticationTrigger;
import org.apereo.cas.authentication.MultifactorAuthenticationUtils;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.util.ResourceUtils;
import org.apereo.cas.util.scripting.ScriptingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.io.Resource;

public class PredicatedPrincipalAttributeMultifactorAuthenticationTrigger
implements MultifactorAuthenticationTrigger {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(PredicatedPrincipalAttributeMultifactorAuthenticationTrigger.class);
    private static final Class[] PREDICATE_CTOR_PARAMETERS = new Class[]{Object.class, Object.class, Object.class, Object.class};
    private final CasConfigurationProperties casProperties;
    private final ApplicationContext applicationContext;
    private int order = Integer.MAX_VALUE;

    public Optional<MultifactorAuthenticationProvider> isActivated(Authentication authentication, RegisteredService registeredService, HttpServletRequest httpServletRequest, HttpServletResponse response, Service service) {
        Resource predicateResource = this.casProperties.getAuthn().getMfa().getTriggers().getPrincipal().getGlobalPrincipalAttributePredicate().getLocation();
        if (!ResourceUtils.doesResourceExist((Resource)predicateResource)) {
            LOGGER.trace("No predicate is defined to decide which multifactor authentication provider should be chosen");
            return Optional.empty();
        }
        Map<String, MultifactorAuthenticationProvider> providerMap = MultifactorAuthenticationUtils.getAvailableMultifactorAuthenticationProviders(this.applicationContext);
        Collection<MultifactorAuthenticationProvider> providers = providerMap.values();
        if (providers.isEmpty()) {
            LOGGER.error("No multifactor authentication providers are available in the application context");
            return Optional.empty();
        }
        Principal principal = authentication.getPrincipal();
        Object[] args = new Object[]{service, principal, providers, LOGGER};
        Predicate predicate = (Predicate)ScriptingUtils.getObjectInstanceFromGroovyResource((Resource)predicateResource, (Class[])PREDICATE_CTOR_PARAMETERS, (Object[])args, Predicate.class);
        if (predicate == null) {
            LOGGER.debug("No multifactor authentication provider is determined by the predicate");
            return Optional.empty();
        }
        LOGGER.debug("Created predicate instance [{}] from [{}] to filter multifactor authentication providers [{}]", new Object[]{predicate.getClass().getSimpleName(), predicateResource, providers});
        return providers.stream().filter(predicate).min(Comparator.comparingInt(Ordered::getOrder));
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
    public PredicatedPrincipalAttributeMultifactorAuthenticationTrigger(CasConfigurationProperties casProperties, ApplicationContext applicationContext) {
        this.casProperties = casProperties;
        this.applicationContext = applicationContext;
    }
}

