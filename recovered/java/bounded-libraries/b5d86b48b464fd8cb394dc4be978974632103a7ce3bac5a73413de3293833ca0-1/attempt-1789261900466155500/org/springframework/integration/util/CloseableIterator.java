/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.util;

import java.util.Iterator;

public interface CloseableIterator<E>
extends Iterator<E>,
AutoCloseable {
    @Override
    public void close();
}

