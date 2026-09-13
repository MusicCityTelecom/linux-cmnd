/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  lombok.Generated
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.MultifactorAuthenticationProvider
 *  org.apereo.cas.authentication.bypass.ChainingMultifactorAuthenticationProviderBypassEvaluator
 *  org.apereo.cas.authentication.bypass.MultifactorAuthenticationProviderBypassEvaluator
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.inspektr.audit.annotation.Audit
 */
package org.apereo.cas.authentication.bypass;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.Generated;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.MultifactorAuthenticationProvider;
import org.apereo.cas.authentication.bypass.ChainingMultifactorAuthenticationProviderBypassEvaluator;
import org.apereo.cas.authentication.bypass.MultifactorAuthenticationProviderBypassEvaluator;
import org.apereo.cas.authentication.bypass.NeverAllowMultifactorAuthenticationProviderBypassEvaluator;
import org.apereo.cas.services.RegisteredService;
import org.apereo.inspektr.audit.annotation.Audit;

public class DefaultChainingMultifactorAuthenticationBypassProvider
implements ChainingMultifactorAuthenticationProviderBypassEvaluator {
    private static final long serialVersionUID = 2397239625822397286L;
    private final List<MultifactorAuthenticationProviderBypassEvaluator> multifactorAuthenticationProviderBypassEvaluators = new ArrayList<MultifactorAuthenticationProviderBypassEvaluator>(0);

    @Audit(action="MULTIFACTOR_AUTHENTICATION_BYPASS", actionResolverName="MULTIFACTOR_AUTHENTICATION_BYPASS_ACTION_RESOLVER", resourceResolverName="MULTIFACTOR_AUTHENTICATION_BYPASS_RESOURCE_RESOLVER")
    public boolean shouldMultifactorAuthenticationProviderExecute(Authentication authentication, RegisteredService registeredService, MultifactorAuthenticationProvider provider, HttpServletRequest request) {
        return this.multifactorAuthenticationProviderBypassEvaluators.stream().allMatch(bypass -> bypass.shouldMultifactorAuthenticationProviderExecute(authentication, registeredService, provider, request));
    }

    public void forgetBypass(Authentication authentication) {
        this.multifactorAuthenticationProviderBypassEvaluators.forEach(bypass -> bypass.forgetBypass(authentication));
    }

    public void rememberBypass(Authentication authentication, MultifactorAuthenticationProvider provider) {
        this.multifactorAuthenticationProviderBypassEvaluators.forEach(bypass -> bypass.rememberBypass(authentication, provider));
    }

    public boolean isMultifactorAuthenticationBypassed(Authentication authentication, String requestedContext) {
        return this.multifactorAuthenticationProviderBypassEvaluators.stream().allMatch(bypass -> bypass.isMultifactorAuthenticationBypassed(authentication, requestedContext));
    }

    public Optional<MultifactorAuthenticationProviderBypassEvaluator> belongsToMultifactorAuthenticationProvider(String providerId) {
        return this.multifactorAuthenticationProviderBypassEvaluators.stream().filter(bypass -> bypass.belongsToMultifactorAuthenticationProvider(providerId).isPresent()).findFirst();
    }

    public String getProviderId() {
        return this.getClass().getSimpleName();
    }

    public String getId() {
        return this.getProviderId();
    }

    public void addMultifactorAuthenticationProviderBypassEvaluator(MultifactorAuthenticationProviderBypassEvaluator bypass) {
        if (!bypass.isEmpty()) {
            this.multifactorAuthenticationProviderBypassEvaluators.add(bypass);
        }
    }

    public int size() {
        return this.multifactorAuthenticationProviderBypassEvaluators.size();
    }

    public boolean isEmpty() {
        return this.multifactorAuthenticationProviderBypassEvaluators.isEmpty();
    }

    public MultifactorAuthenticationProviderBypassEvaluator filterMultifactorAuthenticationProviderBypassEvaluatorsBy(String providerId) {
        DefaultChainingMultifactorAuthenticationBypassProvider chain = new DefaultChainingMultifactorAuthenticationBypassProvider();
        this.multifactorAuthenticationProviderBypassEvaluators.stream().filter(bp -> bp.belongsToMultifactorAuthenticationProvider(providerId).isPresent()).forEach(chain::addMultifactorAuthenticationProviderBypassEvaluator);
        if (chain.isEmpty()) {
            return NeverAllowMultifactorAuthenticationProviderBypassEvaluator.getInstance();
        }
        return chain;
    }

    @Generated
    public List<MultifactorAuthenticationProviderBypassEvaluator> getMultifactorAuthenticationProviderBypassEvaluators() {
        return this.multifactorAuthenticationProviderBypassEvaluators;
    }

    @Generated
    public DefaultChainingMultifactorAuthenticationBypassProvider() {
    }
}

