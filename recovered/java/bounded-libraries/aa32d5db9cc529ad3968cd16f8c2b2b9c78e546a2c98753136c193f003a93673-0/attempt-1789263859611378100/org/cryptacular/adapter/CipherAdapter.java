/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.crypto.CipherParameters
 */
package org.cryptacular.adapter;

import org.bouncycastle.crypto.CipherParameters;
import org.cryptacular.CryptoException;

public interface CipherAdapter {
    public void init(boolean var1, CipherParameters var2) throws CryptoException;

    public int processBytes(byte[] var1, int var2, int var3, byte[] var4, int var5) throws CryptoException;

    public void reset();
}

