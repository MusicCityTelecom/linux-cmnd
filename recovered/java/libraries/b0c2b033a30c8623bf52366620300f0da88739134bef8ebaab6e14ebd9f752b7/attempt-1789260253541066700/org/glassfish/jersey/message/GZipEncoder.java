/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.annotation.Priority;
import org.glassfish.jersey.spi.ContentEncoder;

@Priority(value=4000)
public class GZipEncoder
extends ContentEncoder {
    public GZipEncoder() {
        super("gzip", "x-gzip");
    }

    @Override
    public InputStream decode(String contentEncoding, InputStream encodedStream) throws IOException {
        return new GZIPInputStream(encodedStream);
    }

    @Override
    public OutputStream encode(String contentEncoding, OutputStream entityStream) throws IOException {
        return new GZIPOutputStream(entityStream);
    }
}

