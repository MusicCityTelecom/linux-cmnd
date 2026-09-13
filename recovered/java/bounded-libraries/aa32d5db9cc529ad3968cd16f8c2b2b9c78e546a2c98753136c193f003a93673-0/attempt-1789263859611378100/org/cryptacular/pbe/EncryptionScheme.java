/*
 * Decompiled with CFR 0.152.
 */
package org.cryptacular.pbe;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface EncryptionScheme {
    public byte[] encrypt(byte[] var1);

    public void encrypt(InputStream var1, OutputStream var2) throws IOException;

    public byte[] decrypt(byte[] var1);

    public void decrypt(InputStream var1, OutputStream var2) throws IOException;
}

