/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.util;

import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.CloseableThreadContext;
import org.apache.logging.log4j.ThreadContext;

public abstract class TpvRunableTask
implements Runnable {
    private Map<String, String> threadContextMap = ThreadContext.getImmutableContext();
    private List<String> threadContextMessages = ThreadContext.getImmutableStack().asList();

    @Override
    public final void run() {
        try (CloseableThreadContext.Instance ctc = CloseableThreadContext.putAll(this.threadContextMap).pushAll(this.threadContextMessages);){
            this.execute();
        }
    }

    public abstract void execute();
}

