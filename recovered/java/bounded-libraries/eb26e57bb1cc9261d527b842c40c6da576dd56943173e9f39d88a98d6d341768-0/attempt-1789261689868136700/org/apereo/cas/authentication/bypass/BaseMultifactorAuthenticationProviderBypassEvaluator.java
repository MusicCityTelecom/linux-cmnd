/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.MultifactorAuthenticationProvider
 *  org.apereo.cas.authentication.bypass.MultifactorAuthenticationProviderBypassEvaluator
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.util.CollectionUtils
 *  org.apereo.cas.util.RegexUtils
 *  org.apereo.cas.util.spring.ApplicationContextProvider
 *  org.apereo.inspektr.audit.annotation.Audit
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.authentication.bypass;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.MultifactorAuthenticationProvider;
import org.apereo.cas.authentication.bypass.MultifactorAuthenticationProviderBypassEvaluator;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.util.RegexUtils;
import org.apereo.cas.util.spring.ApplicationContextProvider;
import org.apereo.inspektr.audit.annotation.Audit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseMultifactorAuthenticationProviderBypassEvaluator
implements MultifactorAuthenticationProviderBypassEvaluator {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseMultifactorAuthenticationProviderBypassEvaluator.class);
    private static final long serialVersionUID = 2372899636154131393L;
    private final String providerId;
    private final String id = this.getClass().getSimpleName();

    public void forgetBypass(Authentication authentication) {
        authentication.addAttribute("bypassMultifactorAuthentication", (Object)Boolean.FALSE);
    }

    public boolean isMultifactorAuthenticationBypassed(Authentication authentication, String requestedContext) {
        Map attributes = authentication.getAttributes();
        if (attributes.containsKey("bypassMultifactorAuthentication")) {
            Boolean bypass;
            Optional result = CollectionUtils.firstElement(attributes.get("bypassMultifactorAuthentication"));
            Optional providerRes = CollectionUtils.firstElement(attributes.get("bypassedMultifactorAuthenticationProviderId"));
            if (result.isPresent() && (bypass = (Boolean)result.get()).booleanValue() && providerRes.isPresent()) {
                String provider = providerRes.get().toString();
                return StringUtils.equalsIgnoreCase((CharSequence)requestedContext, (CharSequence)provider);
            }
        }
        return false;
    }

    public void rememberBypass(Authentication authentication, MultifactorAuthenticationProvider provider) {
        authentication.addAttribute("bypassMultifactorAuthentication", (Object)Boolean.TRUE);
        authentication.addAttribute("bypassedMultifactorAuthenticationProviderId", (Object)provider.getId());
    }

    public Optional<MultifactorAuthenticationProviderBypassEvaluator> belongsToMultifactorAuthenticationProvider(String providerId) {
        if (this.getProviderId().equalsIgnoreCase(providerId)) {
            return Optional.of(this);
        }
        return Optional.empty();
    }

    @Audit(action="MULTIFACTOR_AUTHENTICATION_BYPASS", actionResolverName="MULTIFACTOR_AUTHENTICATION_BYPASS_ACTION_RESOLVER", resourceResolverName="MULTIFACTOR_AUTHENTICATION_BYPASS_RESOURCE_RESOLVER")
    public boolean shouldMultifactorAuthenticationProviderExecute(Authentication authentication, RegisteredService registeredService, MultifactorAuthenticationProvider provider, HttpServletRequest request) {
        return this.shouldMultifactorAuthenticationProviderExecuteInternal(authentication, registeredService, provider, request);
    }

    protected abstract boolean shouldMultifactorAuthenticationProviderExecuteInternal(Authentication var1, RegisteredService var2, MultifactorAuthenticationProvider var3, HttpServletRequest var4);

    protected static boolean locateMatchingAttributeValue(String attrName, String attrValue, Map<String, List<Object>> attributes) {
        return BaseMultifactorAuthenticationProviderBypassEvaluator.locateMatchingAttributeValue(attrName, attrValue, attributes, true);
    }

    protected static boolean locateMatchingAttributeValue(String attrName, String attrValue, Map<String, List<Object>> attributes, boolean matchIfNoValueProvided) {
        LOGGER.debug("Locating matching attribute [{}] with value [{}] amongst the attribute collection [{}]", new Object[]{attrName, attrValue, attributes});
        if (StringUtils.isBlank((CharSequence)attrName)) {
            LOGGER.debug("Failed to match since attribute name is undefined");
            return false;
        }
        Set names = attributes.entrySet().stream().filter(e -> {
            LOGGER.debug("Attempting to match [{}] against [{}]", (Object)attrName, e.getKey());
            return RegexUtils.find((String)attrName, (String)((String)e.getKey()));
        }).collect(Collectors.toSet());
        LOGGER.debug("Found [{}] attributes relevant for multifactor authentication bypass", (Object)names.size());
        if (names.isEmpty()) {
            return false;
        }
        if (StringUtils.isBlank((CharSequence)attrValue)) {
            LOGGER.debug("No attribute value to match is provided; Match result is set to [{}]", (Object)matchIfNoValueProvided);
            return matchIfNoValueProvided;
        }
        Set values = names.stream().filter(e -> {
            Set valuesCol = CollectionUtils.toCollection(e.getValue());
            LOGGER.debug("Matching attribute [{}] with values [{}] against [{}]", new Object[]{e.getKey(), valuesCol, attrValue});
            return valuesCol.stream().anyMatch(v -> RegexUtils.find((String)attrValue, (String)v.toString()));
        }).collect(Collectors.toSet());
        LOGGER.debug("Matching attribute values remaining are [{}]", values);
        return !values.isEmpty();
    }

    protected Principal resolvePrincipal(Principal principal) {
        List resolvers = ApplicationContextProvider.getMultifactorAuthenticationPrincipalResolvers();
        return resolvers.stream().filter(resolver -> resolver.supports(principal)).findFirst().map(r -> r.resolve(principal)).orElseThrow(() -> new IllegalStateException("Unable to resolve principal for multifactor authentication"));
    }

    @Generated
    public String getProviderId() {
        return this.providerId;
    }

    @Generated
    public String getId() {
        return this.id;
    }

    @Generated
    protected BaseMultifactorAuthenticationProviderBypassEvaluator(String providerId) {
        this.providerId = providerId;
    }

    @Generated
    public String toString() {
        return "BaseMultifactorAuthenticationProviderBypassEvaluator(providerId=" + this.providerId + ", id=" + this.id + ")";
    }
}

