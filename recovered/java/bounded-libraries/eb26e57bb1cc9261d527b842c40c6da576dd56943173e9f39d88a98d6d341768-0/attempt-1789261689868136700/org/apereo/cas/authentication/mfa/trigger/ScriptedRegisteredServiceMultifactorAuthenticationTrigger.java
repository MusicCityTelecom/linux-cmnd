/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  javax.persistence.Transient
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.AuthenticationException
 *  org.apereo.cas.authentication.MultifactorAuthenticationProvider
 *  org.apereo.cas.authentication.MultifactorAuthenticationTrigger
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.configuration.CasConfigurationProperties
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.RegisteredServiceMultifactorPolicy
 *  org.apereo.cas.util.LoggingUtils
 *  org.apereo.cas.util.ResourceUtils
 *  org.apereo.cas.util.scripting.ExecutableCompiledGroovyScript
 *  org.apereo.cas.util.scripting.GroovyShellScript
 *  org.apereo.cas.util.scripting.ScriptingUtils
 *  org.apereo.cas.util.scripting.WatchableGroovyScriptResource
 *  org.apereo.cas.util.spring.SpringExpressionLanguageValueResolver
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.context.ApplicationContext
 *  org.springframework.core.io.AbstractResource
 *  org.springframework.core.io.Resource
 *  org.springframework.data.annotation.Transient
 */
package org.apereo.cas.authentication.mfa.trigger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import javax.persistence.Transient;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.AuthenticationException;
import org.apereo.cas.authentication.MultifactorAuthenticationProvider;
import org.apereo.cas.authentication.MultifactorAuthenticationProviderAbsentException;
import org.apereo.cas.authentication.MultifactorAuthenticationTrigger;
import org.apereo.cas.authentication.MultifactorAuthenticationUtils;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.RegisteredServiceMultifactorPolicy;
import org.apereo.cas.util.LoggingUtils;
import org.apereo.cas.util.ResourceUtils;
import org.apereo.cas.util.scripting.ExecutableCompiledGroovyScript;
import org.apereo.cas.util.scripting.GroovyShellScript;
import org.apereo.cas.util.scripting.ScriptingUtils;
import org.apereo.cas.util.scripting.WatchableGroovyScriptResource;
import org.apereo.cas.util.spring.SpringExpressionLanguageValueResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.Resource;

@Deprecated(since="6.2.0")
public class ScriptedRegisteredServiceMultifactorAuthenticationTrigger
implements MultifactorAuthenticationTrigger {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(ScriptedRegisteredServiceMultifactorAuthenticationTrigger.class);
    private final CasConfigurationProperties casProperties;
    private final ApplicationContext applicationContext;
    private int order = Integer.MAX_VALUE;
    @JsonIgnore
    @Transient
    @org.springframework.data.annotation.Transient
    private Map<String, ExecutableCompiledGroovyScript> scriptCache = new ConcurrentHashMap<String, ExecutableCompiledGroovyScript>(0);

    public Optional<MultifactorAuthenticationProvider> isActivated(Authentication authentication, RegisteredService registeredService, HttpServletRequest httpServletRequest, HttpServletResponse response, Service service) {
        if (authentication == null || registeredService == null) {
            LOGGER.debug("No authentication or service is available to determine event for principal");
            return Optional.empty();
        }
        RegisteredServiceMultifactorPolicy policy = registeredService.getMultifactorAuthenticationPolicy();
        if (policy == null || StringUtils.isBlank((CharSequence)policy.getScript())) {
            LOGGER.trace("Multifactor authentication policy is absent or does not define a script to trigger multifactor authentication");
            return Optional.empty();
        }
        String mfaScript = policy.getScript();
        Map<String, MultifactorAuthenticationProvider> providerMap = MultifactorAuthenticationUtils.getAvailableMultifactorAuthenticationProviders(this.applicationContext);
        if (providerMap.isEmpty()) {
            LOGGER.error("No multifactor authentication providers are available in the application context");
            throw new AuthenticationException((Throwable)((Object)new MultifactorAuthenticationProviderAbsentException()));
        }
        LOGGER.trace("Locating multifactor authentication trigger script [{}] in script cache...", (Object)mfaScript);
        if (!this.scriptCache.containsKey(mfaScript)) {
            Matcher matcherInline = ScriptingUtils.getMatcherForInlineGroovyScript((String)mfaScript);
            Matcher matcherFile = ScriptingUtils.getMatcherForExternalGroovyScript((String)mfaScript);
            if (matcherInline.find()) {
                GroovyShellScript script = new GroovyShellScript(matcherInline.group(1));
                this.scriptCache.put(mfaScript, (ExecutableCompiledGroovyScript)script);
                LOGGER.trace("Caching multifactor authentication trigger script as an executable shell script");
            } else if (matcherFile.find()) {
                try {
                    String scriptPath = SpringExpressionLanguageValueResolver.getInstance().resolve(matcherFile.group());
                    AbstractResource resource = ResourceUtils.getResourceFrom((String)scriptPath);
                    WatchableGroovyScriptResource script = new WatchableGroovyScriptResource((Resource)resource);
                    this.scriptCache.put(mfaScript, (ExecutableCompiledGroovyScript)script);
                    LOGGER.trace("Caching multifactor authentication trigger script as script resource [{}]", (Object)resource);
                }
                catch (Exception e) {
                    LoggingUtils.error((Logger)LOGGER, (Throwable)e);
                }
            }
        }
        if (this.scriptCache.containsKey(mfaScript)) {
            ExecutableCompiledGroovyScript executableScript = this.scriptCache.get(mfaScript);
            LOGGER.debug("Executing multifactor authentication trigger script [{}]", (Object)executableScript);
            String result = (String)executableScript.execute(new Object[]{authentication, registeredService, httpServletRequest, service, this.applicationContext, LOGGER}, String.class);
            LOGGER.debug("Multifactor authentication provider delivered by trigger script is [{}]", (Object)result);
            if (StringUtils.isBlank((CharSequence)result)) {
                LOGGER.debug("No multifactor authentication is returned from trigger script");
                return Optional.empty();
            }
            Optional<MultifactorAuthenticationProvider> providerResult = providerMap.values().stream().filter(provider -> provider.getId().equalsIgnoreCase(result)).findFirst();
            if (providerResult.isEmpty()) {
                LOGGER.error("Unable to locate multifactor authentication provider [{}] in the application context", (Object)result);
                throw new AuthenticationException((Throwable)((Object)new MultifactorAuthenticationProviderAbsentException()));
            }
            return providerResult;
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
    public Map<String, ExecutableCompiledGroovyScript> getScriptCache() {
        return this.scriptCache;
    }

    @Generated
    public ScriptedRegisteredServiceMultifactorAuthenticationTrigger(CasConfigurationProperties casProperties, ApplicationContext applicationContext) {
        this.casProperties = casProperties;
        this.applicationContext = applicationContext;
    }

    @Generated
    public void setOrder(int order) {
        this.order = order;
    }
}

