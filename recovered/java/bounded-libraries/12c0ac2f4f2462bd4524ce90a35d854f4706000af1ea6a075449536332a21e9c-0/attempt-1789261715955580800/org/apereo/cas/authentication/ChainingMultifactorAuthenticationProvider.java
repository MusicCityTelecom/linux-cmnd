/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.authentication.AuthenticationException
 *  org.apereo.cas.configuration.model.support.mfa.BaseMultifactorAuthenticationProviderProperties$MultifactorAuthenticationProviderFailureModes
 *  org.apereo.cas.services.RegisteredService
 */
package org.apereo.cas.authentication;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.apereo.cas.authentication.AuthenticationException;
import org.apereo.cas.authentication.MultifactorAuthenticationProvider;
import org.apereo.cas.configuration.model.support.mfa.BaseMultifactorAuthenticationProviderProperties;
import org.apereo.cas.services.RegisteredService;

public interface ChainingMultifactorAuthenticationProvider
extends MultifactorAuthenticationProvider {
    public static final String DEFAULT_IDENTIFIER = "mfa-composite";

    public List<MultifactorAuthenticationProvider> getMultifactorAuthenticationProviders();

    public MultifactorAuthenticationProvider addMultifactorAuthenticationProvider(MultifactorAuthenticationProvider var1);

    public void addMultifactorAuthenticationProviders(Collection<MultifactorAuthenticationProvider> var1);

    default public void addMultifactorAuthenticationProviders(MultifactorAuthenticationProvider ... providers) {
        this.addMultifactorAuthenticationProviders(Arrays.stream(providers).collect(Collectors.toList()));
    }

    @Override
    default public boolean isAvailable(RegisteredService service) throws AuthenticationException {
        return this.getMultifactorAuthenticationProviders().stream().allMatch(p -> p.isAvailable(service));
    }

    @Override
    default public String getId() {
        return DEFAULT_IDENTIFIER;
    }

    @Override
    default public String getFriendlyName() {
        return "Multifactor Provider Selection";
    }

    @Override
    default public boolean matches(String identifier) {
        return this.getMultifactorAuthenticationProviders().stream().anyMatch(p -> p.matches(identifier));
    }

    @Override
    default public BaseMultifactorAuthenticationProviderProperties.MultifactorAuthenticationProviderFailureModes getFailureMode() {
        return BaseMultifactorAuthenticationProviderProperties.MultifactorAuthenticationProviderFailureModes.NONE;
    }

    default public int getOrder() {
        return 0;
    }
}

