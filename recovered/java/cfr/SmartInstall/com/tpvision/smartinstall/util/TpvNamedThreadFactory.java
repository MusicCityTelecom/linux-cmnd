/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.util.StringUtils;

public class TpvNamedThreadFactory
implements ThreadFactory {
    private final AtomicInteger poolNumber = new AtomicInteger(1);
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    public final String namePrefix;

    public TpvNamedThreadFactory(String name) {
        if (!StringUtils.hasLength(name)) {
            name = "pool";
        }
        this.namePrefix = name + "-" + this.poolNumber.getAndIncrement() + "-thread-";
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, this.namePrefix + this.threadNumber.getAndIncrement());
        if (t.isDaemon()) {
            t.setDaemon(false);
        }
        if (t.getPriority() != 5) {
            t.setPriority(5);
        }
        return t;
    }
}

