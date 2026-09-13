/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.convert.converter.Converter
 *  org.springframework.util.ClassUtils
 */
package org.springframework.integration.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.UUID;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.ClassUtils;

public class UUIDConverter
implements Converter<Object, UUID> {
    public static final String DEFAULT_CHARSET = "UTF-8";

    public UUID convert(Object source) {
        return UUIDConverter.getUUID(source);
    }

    public static UUID getUUID(Object input) {
        if (input == null) {
            return null;
        }
        if (input instanceof UUID) {
            return (UUID)input;
        }
        if (input instanceof String) {
            try {
                return UUID.fromString((String)input);
            }
            catch (Exception e) {
                try {
                    return UUID.nameUUIDFromBytes(((String)input).getBytes(DEFAULT_CHARSET));
                }
                catch (UnsupportedEncodingException ex) {
                    IllegalStateException exception = new IllegalStateException("Cannot convert String using charset=UTF-8", ex);
                    exception.addSuppressed(e);
                    throw exception;
                }
            }
        }
        if (ClassUtils.isPrimitiveOrWrapper(input.getClass())) {
            try {
                return UUID.nameUUIDFromBytes(input.toString().getBytes(DEFAULT_CHARSET));
            }
            catch (UnsupportedEncodingException e) {
                throw new IllegalStateException("Cannot convert primitive using charset=UTF-8", e);
            }
        }
        byte[] bytes = UUIDConverter.serialize(input);
        return UUID.nameUUIDFromBytes(bytes);
    }

    private static byte[] serialize(Object object) {
        if (object == null) {
            return null;
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            new ObjectOutputStream(stream).writeObject(object);
        }
        catch (IOException e) {
            throw new IllegalArgumentException("Could not serialize object of type: " + object.getClass(), e);
        }
        return stream.toByteArray();
    }
}

