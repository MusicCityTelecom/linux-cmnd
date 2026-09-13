/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aopalliance.intercept.MethodInterceptor
 *  org.aopalliance.intercept.MethodInvocation
 *  org.springframework.aop.ProxyMethodInvocation
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.retry.interceptor;

import java.util.Arrays;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.ProxyMethodInvocation;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryOperations;
import org.springframework.retry.interceptor.MethodInvocationRecoverer;
import org.springframework.retry.interceptor.MethodInvocationRetryCallback;
import org.springframework.retry.support.RetrySynchronizationManager;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class RetryOperationsInterceptor
implements MethodInterceptor {
    private RetryOperations retryOperations = new RetryTemplate();
    private MethodInvocationRecoverer<?> recoverer;
    private String label;

    public void setLabel(String label) {
        this.label = label;
    }

    public void setRetryOperations(RetryOperations retryTemplate) {
        Assert.notNull((Object)retryTemplate, (String)"'retryOperations' cannot be null.");
        this.retryOperations = retryTemplate;
    }

    public void setRecoverer(MethodInvocationRecoverer<?> recoverer) {
        this.recoverer = recoverer;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Object invoke(MethodInvocation invocation) throws Throwable {
        String name = StringUtils.hasText((String)this.label) ? this.label : invocation.getMethod().toGenericString();
        String label = name;
        MethodInvocationRetryCallback<Object, Throwable> retryCallback = new MethodInvocationRetryCallback<Object, Throwable>(invocation, label){

            @Override
            public Object doWithRetry(RetryContext context) throws Exception {
                context.setAttribute("context.name", this.label);
                if (this.invocation instanceof ProxyMethodInvocation) {
                    context.setAttribute("___proxy___", ((ProxyMethodInvocation)this.invocation).getProxy());
                    try {
                        return ((ProxyMethodInvocation)this.invocation).invocableClone().proceed();
                    }
                    catch (Exception e) {
                        throw e;
                    }
                    catch (Error e) {
                        throw e;
                    }
                    catch (Throwable e) {
                        throw new IllegalStateException(e);
                    }
                }
                throw new IllegalStateException("MethodInvocation of the wrong type detected - this should not happen with Spring AOP, so please raise an issue if you see this exception");
            }
        };
        if (this.recoverer != null) {
            ItemRecovererCallback recoveryCallback = new ItemRecovererCallback(invocation.getArguments(), this.recoverer);
            try {
                Object recovered;
                Object object = recovered = this.retryOperations.execute(retryCallback, recoveryCallback);
                return object;
            }
            finally {
                RetryContext context = RetrySynchronizationManager.getContext();
                if (context != null) {
                    context.removeAttribute("__proxy__");
                }
            }
        }
        return this.retryOperations.execute(retryCallback);
    }

    private static final class ItemRecovererCallback
    implements RecoveryCallback<Object> {
        private final Object[] args;
        private final MethodInvocationRecoverer<?> recoverer;

        private ItemRecovererCallback(Object[] args, MethodInvocationRecoverer<?> recoverer) {
            this.args = Arrays.asList(args).toArray();
            this.recoverer = recoverer;
        }

        @Override
        public Object recover(RetryContext context) {
            return this.recoverer.recover(this.args, context.getLastThrowable());
        }
    }
}

