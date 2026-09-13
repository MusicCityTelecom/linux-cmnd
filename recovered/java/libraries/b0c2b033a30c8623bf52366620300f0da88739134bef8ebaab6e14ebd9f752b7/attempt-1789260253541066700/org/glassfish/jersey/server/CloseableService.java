/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server;

import java.io.Closeable;

public interface CloseableService {
    public boolean add(Closeable var1);

    public void close();
}

