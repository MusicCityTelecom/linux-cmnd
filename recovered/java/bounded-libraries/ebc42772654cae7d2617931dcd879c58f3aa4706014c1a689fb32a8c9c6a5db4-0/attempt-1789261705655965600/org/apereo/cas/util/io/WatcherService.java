/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.util.io;

import java.io.Closeable;

public interface WatcherService
extends Closeable,
AutoCloseable {
    public static WatcherService noOp() {
        return new WatcherService(){};
    }

    @Override
    default public void close() {
    }

    default public void start(String name) {
    }
}

