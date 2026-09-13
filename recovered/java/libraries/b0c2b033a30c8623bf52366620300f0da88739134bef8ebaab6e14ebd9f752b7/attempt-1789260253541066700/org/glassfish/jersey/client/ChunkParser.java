/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client;

import java.io.IOException;
import java.io.InputStream;

public interface ChunkParser {
    public byte[] readChunk(InputStream var1) throws IOException;
}

