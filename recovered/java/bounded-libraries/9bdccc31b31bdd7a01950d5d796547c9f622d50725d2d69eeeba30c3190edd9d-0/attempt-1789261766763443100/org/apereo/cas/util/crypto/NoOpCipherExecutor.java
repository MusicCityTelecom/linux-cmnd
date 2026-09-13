/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.util.crypto;

import org.apereo.cas.util.crypto.CipherExecutor;

class NoOpCipherExecutor<I, O>
implements CipherExecutor<I, O> {
    public static final CipherExecutor INSTANCE = new NoOpCipherExecutor();

    NoOpCipherExecutor() {
    }

    @Override
    public O encode(I value, Object[] parameters) {
        return (O)value;
    }

    @Override
    public O decode(I value, Object[] parameters) {
        return (O)value;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}

