/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.security.concurrent;

import java.util.concurrent.Callable;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

public final class DelegatingSecurityContextCallable<V>
implements Callable<V> {
    private final Callable<V> delegate;
    private final SecurityContext delegateSecurityContext;
    private SecurityContext originalSecurityContext;

    public DelegatingSecurityContextCallable(Callable<V> delegate, SecurityContext securityContext) {
        Assert.notNull(delegate, (String)"delegate cannot be null");
        Assert.notNull((Object)securityContext, (String)"securityContext cannot be null");
        this.delegate = delegate;
        this.delegateSecurityContext = securityContext;
    }

    public DelegatingSecurityContextCallable(Callable<V> delegate) {
        this(delegate, SecurityContextHolder.getContext());
    }

    @Override
    public V call() throws Exception {
        this.originalSecurityContext = SecurityContextHolder.getContext();
        try {
            SecurityContextHolder.setContext(this.delegateSecurityContext);
            V v = this.delegate.call();
            return v;
        }
        finally {
            SecurityContext emptyContext = SecurityContextHolder.createEmptyContext();
            if (emptyContext.equals(this.originalSecurityContext)) {
                SecurityContextHolder.clearContext();
            } else {
                SecurityContextHolder.setContext(this.originalSecurityContext);
            }
            this.originalSecurityContext = null;
        }
    }

    public String toString() {
        return this.delegate.toString();
    }

    public static <V> Callable<V> create(Callable<V> delegate, SecurityContext securityContext) {
        return securityContext != null ? new DelegatingSecurityContextCallable<V>(delegate, securityContext) : new DelegatingSecurityContextCallable<V>(delegate);
    }
}

