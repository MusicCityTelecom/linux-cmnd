/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  lombok.Generated
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.MultifactorAuthenticationProvider
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.configuration.model.support.mfa.MultifactorAuthenticationProviderBypassProperties
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.util.function.FunctionUtils
 *  org.apereo.cas.util.scripting.WatchableGroovyScriptResource
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.core.io.Resource
 */
package org.apereo.cas.authentication.bypass;

import javax.servlet.http.HttpServletRequest;
import lombok.Generated;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.MultifactorAuthenticationProvider;
import org.apereo.cas.authentication.bypass.BaseMultifactorAuthenticationProviderBypassEvaluator;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.configuration.model.support.mfa.MultifactorAuthenticationProviderBypassProperties;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.util.function.FunctionUtils;
import org.apereo.cas.util.scripting.WatchableGroovyScriptResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

public class GroovyMultifactorAuthenticationProviderBypassEvaluator
extends BaseMultifactorAuthenticationProviderBypassEvaluator {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(GroovyMultifactorAuthenticationProviderBypassEvaluator.class);
    private static final long serialVersionUID = -4909072898415688377L;
    private final transient WatchableGroovyScriptResource watchableScript;

    public GroovyMultifactorAuthenticationProviderBypassEvaluator(MultifactorAuthenticationProviderBypassProperties bypassProperties, String providerId) {
        super(providerId);
        Resource groovyScript = bypassProperties.getGroovy().getLocation();
        this.watchableScript = new WatchableGroovyScriptResource(groovyScript);
    }

    @Override
    public boolean shouldMultifactorAuthenticationProviderExecuteInternal(Authentication authentication, RegisteredService registeredService, MultifactorAuthenticationProvider provider, HttpServletRequest request) {
        return (Boolean)FunctionUtils.doAndHandle(() -> {
            Principal principal = this.resolvePrincipal(authentication.getPrincipal());
            LOGGER.debug("Evaluating multifactor authentication bypass properties for principal [{}], service [{}] and provider [{}] via Groovy script [{}]", new Object[]{principal.getId(), registeredService, provider, this.watchableScript.getResource()});
            Object[] args = new Object[]{authentication, principal, registeredService, provider, LOGGER, request};
            return (Boolean)this.watchableScript.execute(args, Boolean.class);
        }, e -> true).get();
    }
}

