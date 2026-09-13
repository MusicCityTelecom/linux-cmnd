/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.configuration.model.support.mfa.BaseMultifactorAuthenticationProviderProperties$MultifactorAuthenticationProviderFailureModes
 *  org.apereo.cas.services.RegisteredService
 *  org.springframework.core.Ordered
 */
package org.apereo.cas.authentication;

import java.io.Serializable;
import org.apereo.cas.authentication.MultifactorAuthenticationProvider;
import org.apereo.cas.configuration.model.support.mfa.BaseMultifactorAuthenticationProviderProperties;
import org.apereo.cas.services.RegisteredService;
import org.springframework.core.Ordered;

@FunctionalInterface
public interface MultifactorAuthenticationFailureModeEvaluator
extends Serializable,
Ordered {
    public BaseMultifactorAuthenticationProviderProperties.MultifactorAuthenticationProviderFailureModes evaluate(RegisteredService var1, MultifactorAuthenticationProvider var2);

    default public int getOrder() {
        return Integer.MAX_VALUE;
    }
}

