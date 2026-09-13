/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aopalliance.intercept.MethodInterceptor
 *  org.aopalliance.intercept.MethodInvocation
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.util.Assert
 *  org.springframework.util.ObjectUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.retry.interceptor;

import java.util.Arrays;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.classify.Classifier;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryOperations;
import org.springframework.retry.interceptor.MethodArgumentsKeyGenerator;
import org.springframework.retry.interceptor.MethodInvocationRecoverer;
import org.springframework.retry.interceptor.MethodInvocationRetryCallback;
import org.springframework.retry.interceptor.NewMethodArgumentsIdentifier;
import org.springframework.retry.policy.NeverRetryPolicy;
import org.springframework.retry.support.DefaultRetryState;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class StatefulRetryOperationsInterceptor
implements MethodInterceptor {
    private final transient Log logger = LogFactory.getLog(this.getClass());
    private MethodArgumentsKeyGenerator keyGenerator;
    private MethodInvocationRecoverer<?> recoverer;
    private NewMethodArgumentsIdentifier newMethodArgumentsIdentifier;
    private RetryOperations retryOperations;
    private String label;
    private Classifier<? super Throwable, Boolean> rollbackClassifier;
    private boolean useRawKey;

    public StatefulRetryOperationsInterceptor() {
        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setRetryPolicy(new NeverRetryPolicy());
        this.retryOperations = retryTemplate;
    }

    public void setRetryOperations(RetryOperations retryTemplate) {
        Assert.notNull((Object)retryTemplate, (String)"'retryOperations' cannot be null.");
        this.retryOperations = retryTemplate;
    }

    public void setRecoverer(MethodInvocationRecoverer<?> recoverer) {
        this.recoverer = recoverer;
    }

    public void setRollbackClassifier(Classifier<? super Throwable, Boolean> rollbackClassifier) {
        this.rollbackClassifier = rollbackClassifier;
    }

    public void setKeyGenerator(MethodArgumentsKeyGenerator keyGenerator) {
        this.keyGenerator = keyGenerator;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setNewItemIdentifier(NewMethodArgumentsIdentifier newMethodArgumentsIdentifier) {
        this.newMethodArgumentsIdentifier = newMethodArgumentsIdentifier;
    }

    public void setUseRawKey(boolean useRawKey) {
        this.useRawKey = useRawKey;
    }

    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug((Object)("Executing proxied method in stateful retry: " + invocation.getStaticPart() + "(" + ObjectUtils.getIdentityHexString((Object)invocation) + ")"));
        }
        Object[] args = invocation.getArguments();
        Object defaultKey = Arrays.asList(args);
        if (args.length == 1) {
            defaultKey = args[0];
        }
        Object key = this.createKey(invocation, defaultKey);
        DefaultRetryState retryState = new DefaultRetryState(key, this.newMethodArgumentsIdentifier != null && this.newMethodArgumentsIdentifier.isNew(args), this.rollbackClassifier);
        Object result = this.retryOperations.execute(new StatefulMethodInvocationRetryCallback(invocation, this.label), this.recoverer != null ? new ItemRecovererCallback(args, this.recoverer) : null, retryState);
        if (this.logger.isDebugEnabled()) {
            this.logger.debug((Object)("Exiting proxied method in stateful retry with result: (" + result + ")"));
        }
        return result;
    }

    private Object createKey(MethodInvocation invocation, Object defaultKey) {
        Object generatedKey = defaultKey;
        if (this.keyGenerator != null) {
            generatedKey = this.keyGenerator.getKey(invocation.getArguments());
        }
        if (generatedKey == null) {
            return null;
        }
        if (this.useRawKey) {
            return generatedKey;
        }
        String name = StringUtils.hasText((String)this.label) ? this.label : invocation.getMethod().toGenericString();
        return Arrays.asList(name, generatedKey);
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

    private static final class StatefulMethodInvocationRetryCallback
    extends MethodInvocationRetryCallback<Object, Throwable> {
        private StatefulMethodInvocationRetryCallback(MethodInvocation invocation, String label) {
            super(invocation, label);
        }

        @Override
        public Object doWithRetry(RetryContext context) throws Exception {
            context.setAttribute("context.name", this.label);
            try {
                return this.invocation.proceed();
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
    }
}

