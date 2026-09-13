/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.Ordered
 *  org.springframework.webflow.execution.RequestContext
 */
package org.apereo.cas.logout;

import org.springframework.core.Ordered;
import org.springframework.webflow.execution.RequestContext;

public interface LogoutRedirectionStrategy
extends Ordered {
    public static final int DEFAULT_ORDER = 1000;

    default public int getOrder() {
        return 1000;
    }

    public boolean supports(RequestContext var1);

    public void handle(RequestContext var1);

    default public String getName() {
        return this.getClass().getSimpleName();
    }
}

