/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.ChainingMultifactorAuthenticationProvider
 *  org.apereo.cas.authentication.MultifactorAuthenticationFailureModeEvaluator
 *  org.apereo.cas.authentication.MultifactorAuthenticationProvider
 *  org.apereo.cas.authentication.bypass.MultifactorAuthenticationProviderBypassEvaluator
 *  org.springframework.core.OrderComparator
 */
package org.apereo.cas.authentication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.Generated;
import org.apereo.cas.authentication.ChainingMultifactorAuthenticationProvider;
import org.apereo.cas.authentication.MultifactorAuthenticationFailureModeEvaluator;
import org.apereo.cas.authentication.MultifactorAuthenticationProvider;
import org.apereo.cas.authentication.bypass.DefaultChainingMultifactorAuthenticationBypassProvider;
import org.apereo.cas.authentication.bypass.MultifactorAuthenticationProviderBypassEvaluator;
import org.springframework.core.OrderComparator;

public class DefaultChainingMultifactorAuthenticationProvider
implements ChainingMultifactorAuthenticationProvider {
    private static final long serialVersionUID = -3199297701531604341L;
    private final List<MultifactorAuthenticationProvider> multifactorAuthenticationProviders = new ArrayList<MultifactorAuthenticationProvider>(0);
    private final MultifactorAuthenticationFailureModeEvaluator failureModeEvaluator;

    public MultifactorAuthenticationProviderBypassEvaluator getBypassEvaluator() {
        DefaultChainingMultifactorAuthenticationBypassProvider bypass = new DefaultChainingMultifactorAuthenticationBypassProvider();
        this.getMultifactorAuthenticationProviders().stream().sorted(OrderComparator.INSTANCE).map(MultifactorAuthenticationProvider::getBypassEvaluator).forEach(bypass::addMultifactorAuthenticationProviderBypassEvaluator);
        return bypass;
    }

    public MultifactorAuthenticationProvider addMultifactorAuthenticationProvider(MultifactorAuthenticationProvider provider) {
        this.multifactorAuthenticationProviders.add(provider);
        return provider;
    }

    public void addMultifactorAuthenticationProviders(Collection<MultifactorAuthenticationProvider> providers) {
        this.multifactorAuthenticationProviders.addAll(providers);
    }

    @Generated
    public String toString() {
        return "DefaultChainingMultifactorAuthenticationProvider(multifactorAuthenticationProviders=" + this.multifactorAuthenticationProviders + ", failureModeEvaluator=" + this.failureModeEvaluator + ")";
    }

    @Generated
    public List<MultifactorAuthenticationProvider> getMultifactorAuthenticationProviders() {
        return this.multifactorAuthenticationProviders;
    }

    @Generated
    public MultifactorAuthenticationFailureModeEvaluator getFailureModeEvaluator() {
        return this.failureModeEvaluator;
    }

    @Generated
    public DefaultChainingMultifactorAuthenticationProvider(MultifactorAuthenticationFailureModeEvaluator failureModeEvaluator) {
        this.failureModeEvaluator = failureModeEvaluator;
    }
}

