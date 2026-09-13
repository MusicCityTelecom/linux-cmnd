/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.configuration.model.support.mfa.BaseMultifactorAuthenticationProviderProperties
 */
package org.apereo.cas.authentication;

import org.apereo.cas.authentication.MultifactorAuthenticationProvider;
import org.apereo.cas.configuration.model.support.mfa.BaseMultifactorAuthenticationProviderProperties;

@FunctionalInterface
public interface MultifactorAuthenticationProviderFactoryBean<T extends MultifactorAuthenticationProvider, P extends BaseMultifactorAuthenticationProviderProperties> {
    public static final String PROVIDER_SUFFIX = "-provider";

    public T createProvider(P var1);

    default public String beanName(String id) {
        return id.concat(PROVIDER_SUFFIX);
    }
}

