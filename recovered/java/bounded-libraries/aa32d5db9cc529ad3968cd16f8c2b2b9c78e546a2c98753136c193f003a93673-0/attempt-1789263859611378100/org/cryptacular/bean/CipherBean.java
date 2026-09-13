/*
 * Decompiled with CFR 0.152.
 */
package org.cryptacular.bean;

import java.io.InputStream;
import java.io.OutputStream;
import org.cryptacular.CryptoException;
import org.cryptacular.StreamException;

public interface CipherBean {
    public byte[] encrypt(byte[] var1) throws CryptoException;

    public void encrypt(InputStream var1, OutputStream var2) throws CryptoException, StreamException;

    public byte[] decrypt(byte[] var1) throws CryptoException;

    public void decrypt(InputStream var1, OutputStream var2) throws CryptoException, StreamException;
}

