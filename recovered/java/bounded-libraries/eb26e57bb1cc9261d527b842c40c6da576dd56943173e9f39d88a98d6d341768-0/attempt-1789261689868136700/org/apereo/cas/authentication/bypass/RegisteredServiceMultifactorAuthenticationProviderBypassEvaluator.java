/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.MultifactorAuthenticationProvider
 *  org.apereo.cas.services.RegisteredService
 */
package org.apereo.cas.authentication.bypass;

import javax.servlet.http.HttpServletRequest;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.MultifactorAuthenticationProvider;
import org.apereo.cas.authentication.bypass.BaseMultifactorAuthenticationProviderBypassEvaluator;
import org.apereo.cas.services.RegisteredService;

public class RegisteredServiceMultifactorAuthenticationProviderBypassEvaluator
extends BaseMultifactorAuthenticationProviderBypassEvaluator {
    private static final long serialVersionUID = -3553888418344342672L;

    public RegisteredServiceMultifactorAuthenticationProviderBypassEvaluator(String providerId) {
        super(providerId);
    }

    @Override
    public boolean shouldMultifactorAuthenticationProviderExecuteInternal(Authentication authentication, RegisteredService registeredService, MultifactorAuthenticationProvider provider, HttpServletRequest request) {
        return registeredService == null || registeredService.getMultifactorAuthenticationPolicy() == null || !registeredService.getMultifactorAuthenticationPolicy().isBypassEnabled();
    }
}

