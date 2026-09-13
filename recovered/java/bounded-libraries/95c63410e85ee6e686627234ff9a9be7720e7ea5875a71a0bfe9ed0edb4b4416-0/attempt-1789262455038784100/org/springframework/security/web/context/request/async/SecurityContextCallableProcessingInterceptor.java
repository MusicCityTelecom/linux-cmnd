/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.security.core.context.SecurityContext
 *  org.springframework.security.core.context.SecurityContextHolder
 *  org.springframework.util.Assert
 *  org.springframework.web.context.request.NativeWebRequest
 *  org.springframework.web.context.request.async.CallableProcessingInterceptorAdapter
 */
package org.springframework.security.web.context.request.async;

import java.util.concurrent.Callable;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.async.CallableProcessingInterceptorAdapter;

public final class SecurityContextCallableProcessingInterceptor
extends CallableProcessingInterceptorAdapter {
    private volatile SecurityContext securityContext;

    public SecurityContextCallableProcessingInterceptor() {
    }

    public SecurityContextCallableProcessingInterceptor(SecurityContext securityContext) {
        Assert.notNull((Object)securityContext, (String)"securityContext cannot be null");
        this.setSecurityContext(securityContext);
    }

    public <T> void beforeConcurrentHandling(NativeWebRequest request, Callable<T> task) {
        if (this.securityContext == null) {
            this.setSecurityContext(SecurityContextHolder.getContext());
        }
    }

    public <T> void preProcess(NativeWebRequest request, Callable<T> task) {
        SecurityContextHolder.setContext((SecurityContext)this.securityContext);
    }

    public <T> void postProcess(NativeWebRequest request, Callable<T> task, Object concurrentResult) {
        SecurityContextHolder.clearContext();
    }

    private void setSecurityContext(SecurityContext securityContext) {
        this.securityContext = securityContext;
    }
}

