/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.core.Configuration;
import org.glassfish.jersey.spi.ContentEncoder;

@Priority(value=4000)
public class DeflateEncoder
extends ContentEncoder {
    private final Configuration config;

    @Inject
    public DeflateEncoder(Configuration config) {
        super("deflate");
        this.config = config;
    }

    @Override
    public InputStream decode(String contentEncoding, InputStream encodedStream) throws IOException {
        InputStream markSupportingStream = encodedStream.markSupported() ? encodedStream : new BufferedInputStream(encodedStream);
        markSupportingStream.mark(1);
        int firstByte = markSupportingStream.read();
        markSupportingStream.reset();
        if ((firstByte & 0xF) == 8) {
            return new InflaterInputStream(markSupportingStream);
        }
        return new InflaterInputStream(markSupportingStream, new Inflater(true));
    }

    @Override
    public OutputStream encode(String contentEncoding, OutputStream entityStream) throws IOException {
        Object value = this.config.getProperty("jersey.config.deflate.nozlib");
        boolean deflateWithoutZLib = value instanceof String ? Boolean.valueOf((String)value) : (value instanceof Boolean ? (Boolean)value : false);
        return deflateWithoutZLib ? new DeflaterOutputStream(entityStream, new Deflater(-1, true)) : new DeflaterOutputStream(entityStream);
    }
}

