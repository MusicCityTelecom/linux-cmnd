/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.ChainingMultifactorAuthenticationProvider
 *  org.apereo.cas.authentication.MultifactorAuthenticationContextValidationResult
 *  org.apereo.cas.authentication.MultifactorAuthenticationContextValidator
 *  org.apereo.cas.authentication.MultifactorAuthenticationProvider
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.util.CollectionUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.core.OrderComparator
 *  org.springframework.core.Ordered
 */
package org.apereo.cas.authentication;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.ChainingMultifactorAuthenticationProvider;
import org.apereo.cas.authentication.MultifactorAuthenticationContextValidationResult;
import org.apereo.cas.authentication.MultifactorAuthenticationContextValidator;
import org.apereo.cas.authentication.MultifactorAuthenticationProvider;
import org.apereo.cas.authentication.MultifactorAuthenticationUtils;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.OrderComparator;
import org.springframework.core.Ordered;

public class DefaultMultifactorAuthenticationContextValidator
implements MultifactorAuthenticationContextValidator {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultMultifactorAuthenticationContextValidator.class);
    private final String authenticationContextAttribute;
    private final String mfaTrustedAuthnAttributeName;
    private final ConfigurableApplicationContext applicationContext;

    private static Optional<MultifactorAuthenticationProvider> locateRequestedProvider(Collection<MultifactorAuthenticationProvider> providersArray, String requestedProvider) {
        return providersArray.stream().filter(provider -> provider.getId().equals(requestedProvider)).findFirst();
    }

    public MultifactorAuthenticationContextValidationResult validate(Authentication authentication, String requestedContext, Optional<RegisteredService> service) {
        Map attributes = authentication.getAttributes();
        List ctxAttr = (List)attributes.get(this.authenticationContextAttribute);
        Set contexts = CollectionUtils.toCollection((Object)ctxAttr);
        LOGGER.trace("Attempting to match requested authentication context [{}] against [{}]", (Object)requestedContext, (Object)contexts);
        Map<String, MultifactorAuthenticationProvider> providerMap = MultifactorAuthenticationUtils.getAvailableMultifactorAuthenticationProviders((ApplicationContext)this.applicationContext);
        LOGGER.trace("Available multifactor providers are [{}]", providerMap.values());
        Optional<MultifactorAuthenticationProvider> requestedProvider = DefaultMultifactorAuthenticationContextValidator.locateRequestedProvider(providerMap.values(), requestedContext);
        if (requestedProvider.isEmpty()) {
            LOGGER.debug("Requested authentication provider cannot be recognized.");
            return MultifactorAuthenticationContextValidationResult.builder().success(false).build();
        }
        LOGGER.debug("Requested context is [{}] and available contexts are [{}]", (Object)requestedContext, (Object)contexts);
        if (contexts.stream().anyMatch(ctx -> ctx.toString().equals(requestedContext))) {
            LOGGER.debug("Requested authentication context [{}] is satisfied", (Object)requestedContext);
            return MultifactorAuthenticationContextValidationResult.builder().success(true).provider(requestedProvider).build();
        }
        if (StringUtils.isNotBlank((CharSequence)this.mfaTrustedAuthnAttributeName) && attributes.containsKey(this.mfaTrustedAuthnAttributeName)) {
            LOGGER.debug("Requested authentication context [{}] is satisfied since device is already trusted", (Object)requestedContext);
            return MultifactorAuthenticationContextValidationResult.builder().success(true).provider(requestedProvider).build();
        }
        MultifactorAuthenticationProvider provider = requestedProvider.get();
        Collection<MultifactorAuthenticationProvider> satisfiedProviders = this.getSatisfiedAuthenticationProviders(authentication, providerMap.values());
        if (satisfiedProviders != null && !satisfiedProviders.isEmpty()) {
            MultifactorAuthenticationProvider[] providers = (MultifactorAuthenticationProvider[])satisfiedProviders.toArray(MultifactorAuthenticationProvider[]::new);
            OrderComparator.sortIfNecessary((Object)providers);
            LOGGER.debug("Satisfied authentication context(s) are [{}]", satisfiedProviders);
            Optional<MultifactorAuthenticationProvider> result = Arrays.stream(providers).filter(p -> p.equals(provider) || p.getOrder() >= provider.getOrder()).findFirst();
            if (result.isPresent()) {
                LOGGER.debug("Current provider [{}] already satisfies the authentication requirements of [{}]; proceed with flow normally.", (Object)result.get(), requestedProvider);
                return MultifactorAuthenticationContextValidationResult.builder().success(true).provider(requestedProvider).build();
            }
        }
        LOGGER.info("No multifactor providers could be located to satisfy the requested context for [{}]", (Object)provider);
        return MultifactorAuthenticationContextValidationResult.builder().success(false).provider(requestedProvider).build();
    }

    private Collection<MultifactorAuthenticationProvider> getSatisfiedAuthenticationProviders(Authentication authentication, Collection<MultifactorAuthenticationProvider> providers) {
        Set contexts = CollectionUtils.toCollection(authentication.getAttributes().get(this.authenticationContextAttribute));
        LOGGER.debug("Available authentication context(s) are [{}]", (Object)contexts);
        return providers.stream().map(p -> {
            if (p instanceof ChainingMultifactorAuthenticationProvider) {
                return ((ChainingMultifactorAuthenticationProvider)p).getMultifactorAuthenticationProviders();
            }
            return List.of(p);
        }).flatMap(Collection::stream).sorted(Comparator.comparing(Ordered::getOrder)).filter(p -> contexts.contains(p.getId())).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Generated
    public String getAuthenticationContextAttribute() {
        return this.authenticationContextAttribute;
    }

    @Generated
    public String getMfaTrustedAuthnAttributeName() {
        return this.mfaTrustedAuthnAttributeName;
    }

    @Generated
    public ConfigurableApplicationContext getApplicationContext() {
        return this.applicationContext;
    }

    @Generated
    public DefaultMultifactorAuthenticationContextValidator(String authenticationContextAttribute, String mfaTrustedAuthnAttributeName, ConfigurableApplicationContext applicationContext) {
        this.authenticationContextAttribute = authenticationContextAttribute;
        this.mfaTrustedAuthnAttributeName = mfaTrustedAuthnAttributeName;
        this.applicationContext = applicationContext;
    }
}

