/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.security.core.context;

import org.springframework.security.core.context.SecurityContext;

public interface SecurityContextHolderStrategy {
    public void clearContext();

    public SecurityContext getContext();

    public void setContext(SecurityContext var1);

    public SecurityContext createEmptyContext();
}

