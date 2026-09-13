/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.integration.codec;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import org.springframework.integration.codec.Codec;
import org.springframework.integration.util.ClassUtils;
import org.springframework.util.Assert;

public class CompositeCodec
implements Codec {
    private final Codec defaultCodec;
    private final Map<Class<?>, Codec> delegates;

    public CompositeCodec(Map<Class<?>, Codec> delegates, Codec defaultCodec) {
        Assert.notNull((Object)defaultCodec, (String)"'defaultCodec' cannot be null");
        this.defaultCodec = defaultCodec;
        this.delegates = new HashMap(delegates);
    }

    public CompositeCodec(Codec defaultCodec) {
        this(null, defaultCodec);
    }

    @Override
    public void encode(Object object, OutputStream outputStream) throws IOException {
        Assert.notNull((Object)object, (String)"cannot encode a null object");
        Assert.notNull((Object)outputStream, (String)"'outputStream' cannot be null");
        Codec codec = this.findDelegate(object.getClass());
        if (codec != null) {
            codec.encode(object, outputStream);
        } else {
            this.defaultCodec.encode(object, outputStream);
        }
    }

    @Override
    public byte[] encode(Object object) throws IOException {
        Assert.notNull((Object)object, (String)"cannot encode a null object");
        Codec codec = this.findDelegate(object.getClass());
        if (codec != null) {
            return codec.encode(object);
        }
        return this.defaultCodec.encode(object);
    }

    @Override
    public <T> T decode(InputStream inputStream, Class<T> type) throws IOException {
        Assert.notNull((Object)inputStream, (String)"'inputStream' cannot be null");
        Assert.notNull(type, (String)"'type' cannot be null");
        Codec codec = this.findDelegate(type);
        if (codec != null) {
            return codec.decode(inputStream, type);
        }
        return this.defaultCodec.decode(inputStream, type);
    }

    @Override
    public <T> T decode(byte[] bytes, Class<T> type) throws IOException {
        return this.decode(new ByteArrayInputStream(bytes), type);
    }

    private Codec findDelegate(Class<?> type) {
        if (this.delegates == null) {
            return null;
        }
        Class<?> clazz = ClassUtils.findClosestMatch(type, this.delegates.keySet(), false);
        return this.delegates.get(clazz);
    }
}

