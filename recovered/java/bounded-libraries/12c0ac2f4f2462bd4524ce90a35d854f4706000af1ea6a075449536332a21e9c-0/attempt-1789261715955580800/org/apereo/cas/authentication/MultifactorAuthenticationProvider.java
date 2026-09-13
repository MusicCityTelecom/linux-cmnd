/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.authentication.AuthenticationException
 *  org.apereo.cas.configuration.model.support.mfa.BaseMultifactorAuthenticationProviderProperties$MultifactorAuthenticationProviderFailureModes
 *  org.apereo.cas.services.RegisteredService
 *  org.springframework.core.Ordered
 */
package org.apereo.cas.authentication;

import java.io.Serializable;
import org.apereo.cas.authentication.AuthenticationException;
import org.apereo.cas.authentication.MultifactorAuthenticationFailureModeEvaluator;
import org.apereo.cas.authentication.bypass.MultifactorAuthenticationProviderBypassEvaluator;
import org.apereo.cas.configuration.model.support.mfa.BaseMultifactorAuthenticationProviderProperties;
import org.apereo.cas.services.RegisteredService;
import org.springframework.core.Ordered;

public interface MultifactorAuthenticationProvider
extends Serializable,
Ordered {
    public boolean isAvailable(RegisteredService var1) throws AuthenticationException;

    public MultifactorAuthenticationProviderBypassEvaluator getBypassEvaluator();

    public MultifactorAuthenticationFailureModeEvaluator getFailureModeEvaluator();

    public String getId();

    public String getFriendlyName();

    public boolean matches(String var1);

    public BaseMultifactorAuthenticationProviderProperties.MultifactorAuthenticationProviderFailureModes getFailureMode();
}

