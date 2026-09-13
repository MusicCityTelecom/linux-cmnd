/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.integration.transformer;

import java.io.IOException;
import java.io.UncheckedIOException;
import org.springframework.integration.codec.Codec;
import org.springframework.integration.transformer.AbstractPayloadTransformer;
import org.springframework.util.Assert;

public class EncodingPayloadTransformer<T>
extends AbstractPayloadTransformer<T, byte[]> {
    private final Codec codec;

    public EncodingPayloadTransformer(Codec codec) {
        Assert.notNull((Object)codec, (String)"'codec' cannot be null");
        this.codec = codec;
    }

    @Override
    protected byte[] transformPayload(T payload) {
        try {
            return this.codec.encode(payload);
        }
        catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}

