/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.ResolvableType
 */
package org.springframework.boot.diagnostics;

import org.springframework.boot.diagnostics.FailureAnalysis;
import org.springframework.boot.diagnostics.FailureAnalyzer;
import org.springframework.core.ResolvableType;

public abstract class AbstractFailureAnalyzer<T extends Throwable>
implements FailureAnalyzer {
    @Override
    public FailureAnalysis analyze(Throwable failure) {
        T cause = this.findCause(failure, this.getCauseType());
        return cause != null ? this.analyze(failure, cause) : null;
    }

    protected abstract FailureAnalysis analyze(Throwable var1, T var2);

    protected Class<? extends T> getCauseType() {
        return ResolvableType.forClass(AbstractFailureAnalyzer.class, this.getClass()).resolveGeneric(new int[0]);
    }

    protected final <E extends Throwable> E findCause(Throwable failure, Class<E> type) {
        while (failure != null) {
            if (type.isInstance(failure)) {
                return (E)failure;
            }
            failure = failure.getCause();
        }
        return null;
    }
}

