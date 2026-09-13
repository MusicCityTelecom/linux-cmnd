/*
 * Decompiled with CFR 0.152.
 */
package org.cryptacular.generator;

import org.cryptacular.generator.LimitException;

public interface Nonce {
    public byte[] generate() throws LimitException;

    public int getLength();
}

