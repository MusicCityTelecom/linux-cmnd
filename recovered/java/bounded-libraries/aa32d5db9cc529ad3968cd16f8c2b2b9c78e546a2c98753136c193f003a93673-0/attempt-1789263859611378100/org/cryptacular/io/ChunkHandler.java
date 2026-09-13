/*
 * Decompiled with CFR 0.152.
 */
package org.cryptacular.io;

import java.io.IOException;
import java.io.OutputStream;

public interface ChunkHandler {
    public void handle(byte[] var1, int var2, int var3, OutputStream var4) throws IOException;
}

