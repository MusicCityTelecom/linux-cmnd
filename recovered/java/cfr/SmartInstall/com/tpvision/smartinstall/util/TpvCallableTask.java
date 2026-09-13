/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.util;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import org.apache.logging.log4j.CloseableThreadContext;
import org.apache.logging.log4j.ThreadContext;

public abstract class TpvCallableTask<V>
implements Callable<V> {
    private Map<String, String> threadContextMap = ThreadContext.getImmutableContext();
    private List<String> threadContextMessages = ThreadContext.getImmutableStack().asList();

    @Override
    public final V call() {
        try (CloseableThreadContext.Instance ctc = CloseableThreadContext.putAll(this.threadContextMap).pushAll(this.threadContextMessages);){
            V v = this.execute();
            return v;
        }
    }

    public abstract V execute();
}

