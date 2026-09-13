/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.MultifactorAuthenticationProvider
 *  org.apereo.cas.authentication.bypass.MultifactorAuthenticationProviderBypassEvaluator
 *  org.apereo.cas.services.RegisteredService
 */
package org.apereo.cas.authentication.bypass;

import javax.servlet.http.HttpServletRequest;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.MultifactorAuthenticationProvider;
import org.apereo.cas.authentication.bypass.BaseMultifactorAuthenticationProviderBypassEvaluator;
import org.apereo.cas.authentication.bypass.MultifactorAuthenticationProviderBypassEvaluator;
import org.apereo.cas.services.RegisteredService;

public class NeverAllowMultifactorAuthenticationProviderBypassEvaluator
extends BaseMultifactorAuthenticationProviderBypassEvaluator {
    private static final long serialVersionUID = -2433888418344342672L;
    private static MultifactorAuthenticationProviderBypassEvaluator INSTANCE;

    protected NeverAllowMultifactorAuthenticationProviderBypassEvaluator() {
        super(NeverAllowMultifactorAuthenticationProviderBypassEvaluator.class.getSimpleName());
    }

    public static MultifactorAuthenticationProviderBypassEvaluator getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NeverAllowMultifactorAuthenticationProviderBypassEvaluator();
        }
        return INSTANCE;
    }

    @Override
    public boolean shouldMultifactorAuthenticationProviderExecuteInternal(Authentication authentication, RegisteredService registeredService, MultifactorAuthenticationProvider provider, HttpServletRequest request) {
        return true;
    }
}

