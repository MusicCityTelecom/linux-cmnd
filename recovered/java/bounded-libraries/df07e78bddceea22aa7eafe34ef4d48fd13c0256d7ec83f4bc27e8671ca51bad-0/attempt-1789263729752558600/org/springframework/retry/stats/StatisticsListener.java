/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.AttributeAccessor
 */
package org.springframework.retry.stats;

import org.springframework.core.AttributeAccessor;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryStatistics;
import org.springframework.retry.listener.RetryListenerSupport;
import org.springframework.retry.stats.StatisticsRepository;

public class StatisticsListener
extends RetryListenerSupport {
    private final StatisticsRepository repository;

    public StatisticsListener(StatisticsRepository repository) {
        this.repository = repository;
    }

    @Override
    public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
        String name = this.getName(context);
        if (name != null) {
            if (!this.isExhausted(context) || this.isGlobal(context)) {
                this.repository.addStarted(name);
            }
            if (this.isRecovered(context)) {
                this.repository.addRecovery(name);
            } else if (this.isExhausted(context)) {
                this.repository.addAbort(name);
            } else if (this.isClosed(context)) {
                this.repository.addComplete(name);
            }
            RetryStatistics stats = this.repository.findOne(name);
            if (stats instanceof AttributeAccessor) {
                AttributeAccessor accessor = (AttributeAccessor)stats;
                for (String key : new String[]{"circuit.open", "circuit.shortCount"}) {
                    if (!context.hasAttribute(key)) continue;
                    accessor.setAttribute(key, context.getAttribute(key));
                }
            }
        }
    }

    @Override
    public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
        String name = this.getName(context);
        if (name != null) {
            if (!this.hasState(context)) {
                this.repository.addStarted(name);
            }
            this.repository.addError(name);
        }
    }

    private boolean isGlobal(RetryContext context) {
        return context.hasAttribute("state.global");
    }

    private boolean isExhausted(RetryContext context) {
        return context.hasAttribute("context.exhausted");
    }

    private boolean isClosed(RetryContext context) {
        return context.hasAttribute("context.closed");
    }

    private boolean isRecovered(RetryContext context) {
        return context.hasAttribute("context.recovered");
    }

    private boolean hasState(RetryContext context) {
        return context.hasAttribute("context.state");
    }

    private String getName(RetryContext context) {
        return (String)context.getAttribute("context.name");
    }
}

