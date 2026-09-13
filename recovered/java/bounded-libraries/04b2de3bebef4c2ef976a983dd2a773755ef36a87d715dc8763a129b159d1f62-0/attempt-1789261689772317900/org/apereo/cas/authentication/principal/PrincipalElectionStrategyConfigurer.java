/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.Ordered
 */
package org.apereo.cas.authentication.principal;

import org.apereo.cas.authentication.principal.ChainingPrincipalElectionStrategy;
import org.springframework.core.Ordered;

@FunctionalInterface
public interface PrincipalElectionStrategyConfigurer
extends Ordered {
    public void configurePrincipalElectionStrategy(ChainingPrincipalElectionStrategy var1);

    default public int getOrder() {
        return Integer.MAX_VALUE;
    }
}

