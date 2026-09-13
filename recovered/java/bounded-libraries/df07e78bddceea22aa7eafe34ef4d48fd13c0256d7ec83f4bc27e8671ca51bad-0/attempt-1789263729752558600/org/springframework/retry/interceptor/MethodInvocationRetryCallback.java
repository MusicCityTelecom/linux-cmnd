/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aopalliance.intercept.MethodInvocation
 *  org.springframework.util.StringUtils
 */
package org.springframework.retry.interceptor;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.retry.RetryCallback;
import org.springframework.util.StringUtils;

public abstract class MethodInvocationRetryCallback<T, E extends Throwable>
implements RetryCallback<T, E> {
    protected final MethodInvocation invocation;
    protected final String label;

    public MethodInvocationRetryCallback(MethodInvocation invocation, String label) {
        this.invocation = invocation;
        this.label = StringUtils.hasText((String)label) ? label : invocation.getMethod().toGenericString();
    }

    public MethodInvocation getInvocation() {
        return this.invocation;
    }

    public String getLabel() {
        return this.label;
    }
}

