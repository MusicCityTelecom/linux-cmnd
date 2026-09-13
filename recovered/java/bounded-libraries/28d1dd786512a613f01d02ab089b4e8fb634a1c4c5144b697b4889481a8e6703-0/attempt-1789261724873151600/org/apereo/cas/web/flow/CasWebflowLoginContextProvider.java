/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.Ordered
 *  org.springframework.webflow.execution.RequestContext
 */
package org.apereo.cas.web.flow;

import java.util.Optional;
import org.springframework.core.Ordered;
import org.springframework.webflow.execution.RequestContext;

@FunctionalInterface
public interface CasWebflowLoginContextProvider
extends Ordered {
    public Optional<String> getCandidateUsername(RequestContext var1);

    default public int getOrder() {
        return Integer.MAX_VALUE;
    }

    default public String getName() {
        return this.getClass().getSimpleName();
    }
}

