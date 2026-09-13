/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 *  org.springframework.util.Assert
 *  org.springframework.util.FileCopyUtils
 */
package org.springframework.integration.transformer;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import org.springframework.integration.StaticMessageHeaderAccessor;
import org.springframework.integration.transformer.AbstractTransformer;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;

public class StreamTransformer
extends AbstractTransformer {
    private final String charset;

    public StreamTransformer() {
        this(null);
    }

    public StreamTransformer(String charset) {
        this.charset = charset;
    }

    @Override
    protected Object doTransform(Message<?> message) {
        try {
            Assert.isTrue((boolean)(message.getPayload() instanceof InputStream), (String)"payload must be an InputStream");
            InputStream stream = (InputStream)message.getPayload();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            FileCopyUtils.copy((InputStream)stream, (OutputStream)baos);
            Closeable closeableResource = StaticMessageHeaderAccessor.getCloseableResource(message);
            if (closeableResource != null) {
                closeableResource.close();
            }
            return this.charset == null ? baos.toByteArray() : (byte[])baos.toString(this.charset);
        }
        catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}

