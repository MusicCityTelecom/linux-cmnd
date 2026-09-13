/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.crypto.BufferedBlockCipher
 *  org.bouncycastle.crypto.CipherParameters
 *  org.bouncycastle.crypto.InvalidCipherTextException
 */
package org.cryptacular.adapter;

import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.cryptacular.CryptoException;
import org.cryptacular.adapter.BlockCipherAdapter;

public class BufferedBlockCipherAdapter
implements BlockCipherAdapter {
    private final BufferedBlockCipher cipherDelegate;

    public BufferedBlockCipherAdapter(BufferedBlockCipher delegate) {
        this.cipherDelegate = delegate;
    }

    @Override
    public int getOutputSize(int len) {
        return this.cipherDelegate.getOutputSize(len);
    }

    @Override
    public void init(boolean forEncryption, CipherParameters params) throws CryptoException {
        try {
            this.cipherDelegate.init(forEncryption, params);
        }
        catch (RuntimeException e) {
            throw new CryptoException("Cipher initialization error", e);
        }
    }

    @Override
    public int processBytes(byte[] in, int inOff, int len, byte[] out, int outOff) throws CryptoException {
        try {
            return this.cipherDelegate.processBytes(in, inOff, len, out, outOff);
        }
        catch (RuntimeException e) {
            throw new CryptoException("Cipher processing error", e);
        }
    }

    @Override
    public int doFinal(byte[] out, int outOff) throws CryptoException {
        try {
            return this.cipherDelegate.doFinal(out, outOff);
        }
        catch (InvalidCipherTextException e) {
            throw new CryptoException("Error finalizing cipher", e);
        }
    }

    @Override
    public void reset() {
        this.cipherDelegate.reset();
    }
}

