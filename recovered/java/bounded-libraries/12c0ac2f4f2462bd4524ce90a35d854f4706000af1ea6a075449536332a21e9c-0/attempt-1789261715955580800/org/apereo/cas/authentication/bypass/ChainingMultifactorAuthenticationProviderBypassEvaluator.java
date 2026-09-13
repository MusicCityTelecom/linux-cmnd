/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.authentication.bypass;

import java.util.Arrays;
import java.util.List;
import org.apereo.cas.authentication.bypass.MultifactorAuthenticationProviderBypassEvaluator;

public interface ChainingMultifactorAuthenticationProviderBypassEvaluator
extends MultifactorAuthenticationProviderBypassEvaluator {
    public List<MultifactorAuthenticationProviderBypassEvaluator> getMultifactorAuthenticationProviderBypassEvaluators();

    public void addMultifactorAuthenticationProviderBypassEvaluator(MultifactorAuthenticationProviderBypassEvaluator var1);

    default public void addMultifactorAuthenticationProviderBypassEvaluator(MultifactorAuthenticationProviderBypassEvaluator ... bypasses) {
        Arrays.stream(bypasses).forEach(this::addMultifactorAuthenticationProviderBypassEvaluator);
    }

    public MultifactorAuthenticationProviderBypassEvaluator filterMultifactorAuthenticationProviderBypassEvaluatorsBy(String var1);
}

