/*
 * Decompiled with CFR 0.152.
 */
package org.cryptacular.adapter;

import org.cryptacular.CryptoException;
import org.cryptacular.adapter.CipherAdapter;

public interface BlockCipherAdapter
extends CipherAdapter {
    public int getOutputSize(int var1);

    public int doFinal(byte[] var1, int var2) throws CryptoException;
}

