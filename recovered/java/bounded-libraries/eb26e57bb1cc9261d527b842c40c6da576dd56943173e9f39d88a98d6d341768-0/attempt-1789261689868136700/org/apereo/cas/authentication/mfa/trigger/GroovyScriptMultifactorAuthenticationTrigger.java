/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.AuthenticationException
 *  org.apereo.cas.authentication.MultifactorAuthenticationProvider
 *  org.apereo.cas.authentication.MultifactorAuthenticationProviderResolver
 *  org.apereo.cas.authentication.MultifactorAuthenticationProviderSelector
 *  org.apereo.cas.authentication.MultifactorAuthenticationTrigger
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.util.function.FunctionUtils
 *  org.apereo.cas.util.scripting.WatchableGroovyScriptResource
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.DisposableBean
 *  org.springframework.context.ApplicationContext
 */
package org.apereo.cas.authentication.mfa.trigger;

import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.AuthenticationException;
import org.apereo.cas.authentication.MultifactorAuthenticationProvider;
import org.apereo.cas.authentication.MultifactorAuthenticationProviderResolver;
import org.apereo.cas.authentication.MultifactorAuthenticationProviderSelector;
import org.apereo.cas.authentication.MultifactorAuthenticationTrigger;
import org.apereo.cas.authentication.MultifactorAuthenticationUtils;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.util.function.FunctionUtils;
import org.apereo.cas.util.scripting.WatchableGroovyScriptResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;

public class GroovyScriptMultifactorAuthenticationTrigger
implements MultifactorAuthenticationTrigger,
DisposableBean {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(GroovyScriptMultifactorAuthenticationTrigger.class);
    private final WatchableGroovyScriptResource watchableScript;
    private final ApplicationContext applicationContext;
    private final MultifactorAuthenticationProviderResolver multifactorAuthenticationProviderResolver;
    private final MultifactorAuthenticationProviderSelector multifactorAuthenticationProviderSelector;
    private int order = Integer.MAX_VALUE;

    public Optional<MultifactorAuthenticationProvider> isActivated(Authentication authentication, RegisteredService registeredService, HttpServletRequest httpServletRequest, HttpServletResponse response, Service service) {
        if (authentication == null) {
            LOGGER.debug("No authentication is available to determine event for principal");
            return Optional.empty();
        }
        Map<String, MultifactorAuthenticationProvider> providerMap = MultifactorAuthenticationUtils.getAvailableMultifactorAuthenticationProviders(this.applicationContext);
        if (providerMap.isEmpty()) {
            LOGGER.error("No multifactor authentication providers are available in the application context");
            throw new AuthenticationException();
        }
        return (Optional)FunctionUtils.doUnchecked(() -> {
            Object[] args = new Object[]{service, registeredService, authentication, httpServletRequest, LOGGER};
            String provider = (String)this.watchableScript.execute(args, String.class);
            LOGGER.debug("Groovy script run for [{}] returned the provider id [{}]", (Object)registeredService, (Object)provider);
            if (StringUtils.isBlank((CharSequence)provider)) {
                return Optional.empty();
            }
            Principal principal = this.multifactorAuthenticationProviderResolver.resolvePrincipal(authentication.getPrincipal());
            if (provider.equals("mfa-composite")) {
                return Optional.of(this.multifactorAuthenticationProviderSelector.resolve(providerMap.values(), registeredService, principal));
            }
            return MultifactorAuthenticationUtils.resolveProvider(providerMap, provider);
        });
    }

    public void destroy() {
        this.watchableScript.close();
    }

    @Generated
    public WatchableGroovyScriptResource getWatchableScript() {
        return this.watchableScript;
    }

    @Generated
    public ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }

    @Generated
    public MultifactorAuthenticationProviderResolver getMultifactorAuthenticationProviderResolver() {
        return this.multifactorAuthenticationProviderResolver;
    }

    @Generated
    public MultifactorAuthenticationProviderSelector getMultifactorAuthenticationProviderSelector() {
        return this.multifactorAuthenticationProviderSelector;
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
    public GroovyScriptMultifactorAuthenticationTrigger(WatchableGroovyScriptResource watchableScript, ApplicationContext applicationContext, MultifactorAuthenticationProviderResolver multifactorAuthenticationProviderResolver, MultifactorAuthenticationProviderSelector multifactorAuthenticationProviderSelector) {
        this.watchableScript = watchableScript;
        this.applicationContext = applicationContext;
        this.multifactorAuthenticationProviderResolver = multifactorAuthenticationProviderResolver;
        this.multifactorAuthenticationProviderSelector = multifactorAuthenticationProviderSelector;
    }
}

