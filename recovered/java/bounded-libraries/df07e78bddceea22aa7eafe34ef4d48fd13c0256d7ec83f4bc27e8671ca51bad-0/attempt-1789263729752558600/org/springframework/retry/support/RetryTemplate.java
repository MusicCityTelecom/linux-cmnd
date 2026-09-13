/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package org.springframework.retry.support;

import java.util.ArrayList;
import java.util.Arrays;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.retry.ExhaustedRetryException;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryException;
import org.springframework.retry.RetryListener;
import org.springframework.retry.RetryOperations;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.RetryState;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.backoff.NoBackOffPolicy;
import org.springframework.retry.policy.MapRetryContextCache;
import org.springframework.retry.policy.RetryContextCache;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetrySynchronizationManager;
import org.springframework.retry.support.RetryTemplateBuilder;

public class RetryTemplate
implements RetryOperations {
    private static final String GLOBAL_STATE = "state.global";
    protected final Log logger = LogFactory.getLog(this.getClass());
    private volatile BackOffPolicy backOffPolicy = new NoBackOffPolicy();
    private volatile RetryPolicy retryPolicy = new SimpleRetryPolicy(3);
    private volatile RetryListener[] listeners = new RetryListener[0];
    private RetryContextCache retryContextCache = new MapRetryContextCache();
    private boolean throwLastExceptionOnExhausted;

    public static RetryTemplateBuilder builder() {
        return new RetryTemplateBuilder();
    }

    public static RetryTemplate defaultInstance() {
        return new RetryTemplateBuilder().build();
    }

    public void setThrowLastExceptionOnExhausted(boolean throwLastExceptionOnExhausted) {
        this.throwLastExceptionOnExhausted = throwLastExceptionOnExhausted;
    }

    public void setRetryContextCache(RetryContextCache retryContextCache) {
        this.retryContextCache = retryContextCache;
    }

    public void setListeners(RetryListener[] listeners) {
        this.listeners = Arrays.asList(listeners).toArray(new RetryListener[listeners.length]);
    }

    public void registerListener(RetryListener listener) {
        this.registerListener(listener, this.listeners.length);
    }

    public void registerListener(RetryListener listener, int index) {
        ArrayList<RetryListener> list = new ArrayList<RetryListener>(Arrays.asList(this.listeners));
        if (index >= list.size()) {
            list.add(listener);
        } else {
            list.add(index, listener);
        }
        this.listeners = list.toArray(new RetryListener[list.size()]);
    }

    public boolean hasListeners() {
        return this.listeners.length > 0;
    }

    public void setBackOffPolicy(BackOffPolicy backOffPolicy) {
        this.backOffPolicy = backOffPolicy;
    }

    public void setRetryPolicy(RetryPolicy retryPolicy) {
        this.retryPolicy = retryPolicy;
    }

    @Override
    public final <T, E extends Throwable> T execute(RetryCallback<T, E> retryCallback) throws E {
        return this.doExecute(retryCallback, null, null);
    }

    @Override
    public final <T, E extends Throwable> T execute(RetryCallback<T, E> retryCallback, RecoveryCallback<T> recoveryCallback) throws E {
        return this.doExecute(retryCallback, recoveryCallback, null);
    }

    @Override
    public final <T, E extends Throwable> T execute(RetryCallback<T, E> retryCallback, RetryState retryState) throws E, ExhaustedRetryException {
        return this.doExecute(retryCallback, null, retryState);
    }

    @Override
    public final <T, E extends Throwable> T execute(RetryCallback<T, E> retryCallback, RecoveryCallback<T> recoveryCallback, RetryState retryState) throws E, ExhaustedRetryException {
        return this.doExecute(retryCallback, recoveryCallback, retryState);
    }

    /*
     * Exception decompiling
     */
    protected <T, E extends Throwable> T doExecute(RetryCallback<T, E> retryCallback, RecoveryCallback<T> recoveryCallback, RetryState state) throws E, ExhaustedRetryException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 14[WHILELOOP]
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:435)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:484)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    protected boolean canRetry(RetryPolicy retryPolicy, RetryContext context) {
        return retryPolicy.canRetry(context);
    }

    protected void close(RetryPolicy retryPolicy, RetryContext context, RetryState state, boolean succeeded) {
        if (state != null) {
            if (succeeded) {
                if (!context.hasAttribute(GLOBAL_STATE)) {
                    this.retryContextCache.remove(state.getKey());
                }
                retryPolicy.close(context);
                context.setAttribute("context.closed", true);
            }
        } else {
            retryPolicy.close(context);
            context.setAttribute("context.closed", true);
        }
    }

    protected void registerThrowable(RetryPolicy retryPolicy, RetryState state, RetryContext context, Throwable e) {
        retryPolicy.registerThrowable(context, e);
        this.registerContext(context, state);
    }

    private void registerContext(RetryContext context, RetryState state) {
        Object key;
        if (state != null && (key = state.getKey()) != null) {
            if (context.getRetryCount() > 1 && !this.retryContextCache.containsKey(key)) {
                throw new RetryException("Inconsistent state for failed item key: cache key has changed. Consider whether equals() or hashCode() for the key might be inconsistent, or if you need to supply a better key");
            }
            this.retryContextCache.put(key, context);
        }
    }

    protected RetryContext open(RetryPolicy retryPolicy, RetryState state) {
        if (state == null) {
            return this.doOpenInternal(retryPolicy);
        }
        Object key = state.getKey();
        if (state.isForceRefresh()) {
            return this.doOpenInternal(retryPolicy, state);
        }
        if (!this.retryContextCache.containsKey(key)) {
            return this.doOpenInternal(retryPolicy, state);
        }
        RetryContext context = this.retryContextCache.get(key);
        if (context == null) {
            if (this.retryContextCache.containsKey(key)) {
                throw new RetryException("Inconsistent state for failed item: no history found. Consider whether equals() or hashCode() for the item might be inconsistent, or if you need to supply a better ItemKeyGenerator");
            }
            return this.doOpenInternal(retryPolicy, state);
        }
        context.removeAttribute("context.closed");
        context.removeAttribute("context.exhausted");
        context.removeAttribute("context.recovered");
        return context;
    }

    private RetryContext doOpenInternal(RetryPolicy retryPolicy, RetryState state) {
        RetryContext context = retryPolicy.open(RetrySynchronizationManager.getContext());
        if (state != null) {
            context.setAttribute("context.state", state.getKey());
        }
        if (context.hasAttribute(GLOBAL_STATE)) {
            this.registerContext(context, state);
        }
        return context;
    }

    private RetryContext doOpenInternal(RetryPolicy retryPolicy) {
        return this.doOpenInternal(retryPolicy, null);
    }

    protected <T> T handleRetryExhausted(RecoveryCallback<T> recoveryCallback, RetryContext context, RetryState state) throws Throwable {
        context.setAttribute("context.exhausted", true);
        if (state != null && !context.hasAttribute(GLOBAL_STATE)) {
            this.retryContextCache.remove(state.getKey());
        }
        if (recoveryCallback != null) {
            T recovered = recoveryCallback.recover(context);
            context.setAttribute("context.recovered", true);
            return recovered;
        }
        if (state != null) {
            this.logger.debug((Object)"Retry exhausted after last attempt with no recovery path.");
            this.rethrow(context, "Retry exhausted after last attempt with no recovery path");
        }
        throw RetryTemplate.wrapIfNecessary(context.getLastThrowable());
    }

    protected <E extends Throwable> void rethrow(RetryContext context, String message) throws E {
        if (this.throwLastExceptionOnExhausted) {
            Throwable rethrow = context.getLastThrowable();
            throw rethrow;
        }
        throw new ExhaustedRetryException(message, context.getLastThrowable());
    }

    protected boolean shouldRethrow(RetryPolicy retryPolicy, RetryContext context, RetryState state) {
        return state != null && state.rollbackFor(context.getLastThrowable());
    }

    private <T, E extends Throwable> boolean doOpenInterceptors(RetryCallback<T, E> callback, RetryContext context) {
        boolean result = true;
        for (RetryListener listener : this.listeners) {
            result = result && listener.open(context, callback);
        }
        return result;
    }

    private <T, E extends Throwable> void doCloseInterceptors(RetryCallback<T, E> callback, RetryContext context, Throwable lastException) {
        int i = this.listeners.length;
        while (i-- > 0) {
            this.listeners[i].close(context, callback, lastException);
        }
    }

    private <T, E extends Throwable> void doOnErrorInterceptors(RetryCallback<T, E> callback, RetryContext context, Throwable throwable) {
        int i = this.listeners.length;
        while (i-- > 0) {
            this.listeners[i].onError(context, callback, throwable);
        }
    }

    private static <E extends Throwable> E wrapIfNecessary(Throwable throwable) throws RetryException {
        if (throwable instanceof Error) {
            throw (Error)throwable;
        }
        if (throwable instanceof Exception) {
            Throwable rethrow = throwable;
            return (E)rethrow;
        }
        throw new RetryException("Exception in retry", throwable);
    }
}

