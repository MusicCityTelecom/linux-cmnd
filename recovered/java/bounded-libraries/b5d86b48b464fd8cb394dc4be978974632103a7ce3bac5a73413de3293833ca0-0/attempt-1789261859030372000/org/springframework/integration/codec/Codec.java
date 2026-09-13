/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.codec;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Codec {
    public void encode(Object var1, OutputStream var2) throws IOException;

    public byte[] encode(Object var1) throws IOException;

    public <T> T decode(InputStream var1, Class<T> var2) throws IOException;

    public <T> T decode(byte[] var1, Class<T> var2) throws IOException;
}

