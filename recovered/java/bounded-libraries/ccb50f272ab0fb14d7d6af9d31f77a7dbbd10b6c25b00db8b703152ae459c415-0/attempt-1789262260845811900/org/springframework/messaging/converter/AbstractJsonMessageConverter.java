/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.MimeType
 */
package org.springframework.messaging.converter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.AbstractMessageConverter;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.util.ClassUtils;
import org.springframework.util.MimeType;

public abstract class AbstractJsonMessageConverter
extends AbstractMessageConverter {
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    protected AbstractJsonMessageConverter() {
        super(new MimeType("application", "json"));
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    @Nullable
    protected Object convertFromInternal(Message<?> message, Class<?> targetClass, @Nullable Object conversionHint) {
        try {
            Type resolvedType = AbstractJsonMessageConverter.getResolvedType(targetClass, conversionHint);
            Object payload = message.getPayload();
            if (ClassUtils.isAssignableValue(targetClass, payload)) {
                return payload;
            }
            if (payload instanceof byte[]) {
                return this.fromJson(this.getReader((byte[])payload, message.getHeaders()), resolvedType);
            }
            return this.fromJson(payload.toString(), resolvedType);
        }
        catch (Exception ex) {
            throw new MessageConversionException(message, "Could not read JSON: " + ex.getMessage(), ex);
        }
    }

    @Override
    @Nullable
    protected Object convertToInternal(Object payload, @Nullable MessageHeaders headers, @Nullable Object conversionHint) {
        try {
            Type resolvedType = AbstractJsonMessageConverter.getResolvedType(payload.getClass(), conversionHint);
            if (byte[].class == this.getSerializedPayloadClass()) {
                ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
                Writer writer = this.getWriter(out, headers);
                this.toJson(payload, resolvedType, writer);
                writer.flush();
                return out.toByteArray();
            }
            return this.toJson(payload, resolvedType);
        }
        catch (Exception ex) {
            throw new MessageConversionException("Could not write JSON: " + ex.getMessage(), (Throwable)ex);
        }
    }

    private Reader getReader(byte[] payload, @Nullable MessageHeaders headers) {
        ByteArrayInputStream in = new ByteArrayInputStream(payload);
        return new InputStreamReader((InputStream)in, this.getCharsetToUse(headers));
    }

    private Writer getWriter(ByteArrayOutputStream out, @Nullable MessageHeaders headers) {
        return new OutputStreamWriter((OutputStream)out, this.getCharsetToUse(headers));
    }

    private Charset getCharsetToUse(@Nullable MessageHeaders headers) {
        MimeType mimeType = this.getMimeType(headers);
        return mimeType != null && mimeType.getCharset() != null ? mimeType.getCharset() : DEFAULT_CHARSET;
    }

    protected abstract Object fromJson(Reader var1, Type var2);

    protected abstract Object fromJson(String var1, Type var2);

    protected abstract void toJson(Object var1, Type var2, Writer var3);

    protected abstract String toJson(Object var1, Type var2);
}

