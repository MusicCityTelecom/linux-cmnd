/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.security.concurrent;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

public final class DelegatingSecurityContextRunnable
implements Runnable {
    private final Runnable delegate;
    private final SecurityContext delegateSecurityContext;
    private SecurityContext originalSecurityContext;

    public DelegatingSecurityContextRunnable(Runnable delegate, SecurityContext securityContext) {
        Assert.notNull((Object)delegate, (String)"delegate cannot be null");
        Assert.notNull((Object)securityContext, (String)"securityContext cannot be null");
        this.delegate = delegate;
        this.delegateSecurityContext = securityContext;
    }

    public DelegatingSecurityContextRunnable(Runnable delegate) {
        this(delegate, SecurityContextHolder.getContext());
    }

    @Override
    public void run() {
        this.originalSecurityContext = SecurityContextHolder.getContext();
        try {
            SecurityContextHolder.setContext(this.delegateSecurityContext);
            this.delegate.run();
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

    public static Runnable create(Runnable delegate, SecurityContext securityContext) {
        Assert.notNull((Object)delegate, (String)"delegate cannot be  null");
        return securityContext != null ? new DelegatingSecurityContextRunnable(delegate, securityContext) : new DelegatingSecurityContextRunnable(delegate);
    }
}

