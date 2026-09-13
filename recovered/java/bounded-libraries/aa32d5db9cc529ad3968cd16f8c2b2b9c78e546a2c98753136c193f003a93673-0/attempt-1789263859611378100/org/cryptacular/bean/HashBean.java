/*
 * Decompiled with CFR 0.152.
 */
package org.cryptacular.bean;

import org.cryptacular.CryptoException;
import org.cryptacular.StreamException;

public interface HashBean<T> {
    public T hash(Object ... var1) throws CryptoException, StreamException;

    public boolean compare(T var1, Object ... var2) throws CryptoException, StreamException;
}

