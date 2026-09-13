/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.security.concurrent;

import java.util.concurrent.Callable;
import org.springframework.security.concurrent.DelegatingSecurityContextCallable;
import org.springframework.security.concurrent.DelegatingSecurityContextRunnable;
import org.springframework.security.core.context.SecurityContext;

abstract class AbstractDelegatingSecurityContextSupport {
    private final SecurityContext securityContext;

    AbstractDelegatingSecurityContextSupport(SecurityContext securityContext) {
        this.securityContext = securityContext;
    }

    protected final Runnable wrap(Runnable delegate) {
        return DelegatingSecurityContextRunnable.create(delegate, this.securityContext);
    }

    protected final <T> Callable<T> wrap(Callable<T> delegate) {
        return DelegatingSecurityContextCallable.create(delegate, this.securityContext);
    }
}

