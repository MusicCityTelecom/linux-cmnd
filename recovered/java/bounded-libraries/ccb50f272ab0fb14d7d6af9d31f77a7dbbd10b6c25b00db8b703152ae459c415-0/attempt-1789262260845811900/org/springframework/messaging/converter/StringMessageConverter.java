/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 *  org.springframework.util.MimeType
 */
package org.springframework.messaging.converter;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.AbstractMessageConverter;
import org.springframework.util.Assert;
import org.springframework.util.MimeType;

public class StringMessageConverter
extends AbstractMessageConverter {
    private final Charset defaultCharset;

    public StringMessageConverter() {
        this(StandardCharsets.UTF_8);
    }

    public StringMessageConverter(Charset defaultCharset) {
        super(new MimeType("text", "plain", defaultCharset));
        Assert.notNull((Object)defaultCharset, (String)"Default Charset must not be null");
        this.defaultCharset = defaultCharset;
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return String.class == clazz;
    }

    @Override
    protected Object convertFromInternal(Message<?> message, Class<?> targetClass, @Nullable Object conversionHint) {
        Charset charset = this.getContentTypeCharset(this.getMimeType(message.getHeaders()));
        Object payload = message.getPayload();
        return payload instanceof String ? payload : new String((byte[])payload, charset);
    }

    @Override
    @Nullable
    protected Object convertToInternal(Object payload, @Nullable MessageHeaders headers, @Nullable Object conversionHint) {
        if (byte[].class == this.getSerializedPayloadClass()) {
            Charset charset = this.getContentTypeCharset(this.getMimeType(headers));
            payload = ((String)payload).getBytes(charset);
        }
        return payload;
    }

    private Charset getContentTypeCharset(@Nullable MimeType mimeType) {
        if (mimeType != null && mimeType.getCharset() != null) {
            return mimeType.getCharset();
        }
        return this.defaultCharset;
    }
}

