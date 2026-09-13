/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.DirectFieldAccessor
 *  org.springframework.core.ConfigurableObjectInputStream
 *  org.springframework.core.NestedIOException
 *  org.springframework.core.convert.converter.Converter
 *  org.springframework.core.serializer.DefaultDeserializer
 *  org.springframework.core.serializer.Deserializer
 *  org.springframework.core.serializer.support.SerializationFailedException
 *  org.springframework.util.Assert
 *  org.springframework.util.PatternMatchUtils
 */
package org.springframework.integration.support.converter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamClass;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import org.springframework.beans.DirectFieldAccessor;
import org.springframework.core.ConfigurableObjectInputStream;
import org.springframework.core.NestedIOException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.serializer.DefaultDeserializer;
import org.springframework.core.serializer.Deserializer;
import org.springframework.core.serializer.support.SerializationFailedException;
import org.springframework.util.Assert;
import org.springframework.util.PatternMatchUtils;

public class AllowListDeserializingConverter
implements Converter<byte[], Object> {
    private final Deserializer<Object> deserializer;
    private final ClassLoader defaultDeserializerClassLoader;
    private final boolean usingDefaultDeserializer;
    private final Set<String> allowedPatterns = new LinkedHashSet<String>();

    public AllowListDeserializingConverter() {
        this((Deserializer<Object>)new DefaultDeserializer());
    }

    public AllowListDeserializingConverter(ClassLoader classLoader) {
        this((Deserializer<Object>)new DefaultDeserializer(classLoader));
    }

    public AllowListDeserializingConverter(Deserializer<Object> deserializer) {
        Assert.notNull(deserializer, (String)"Deserializer must not be null");
        this.deserializer = deserializer;
        if (deserializer instanceof DefaultDeserializer) {
            ClassLoader classLoader = null;
            try {
                classLoader = (ClassLoader)new DirectFieldAccessor(deserializer).getPropertyValue("classLoader");
            }
            catch (Exception exception) {
                // empty catch block
            }
            this.defaultDeserializerClassLoader = classLoader;
            this.usingDefaultDeserializer = true;
        } else {
            this.defaultDeserializerClassLoader = null;
            this.usingDefaultDeserializer = false;
        }
    }

    public void setAllowedPatterns(String ... allowedPatterns) {
        this.allowedPatterns.clear();
        Collections.addAll(this.allowedPatterns, allowedPatterns);
    }

    public void addAllowedPatterns(String ... patterns) {
        Collections.addAll(this.allowedPatterns, patterns);
    }

    public Object convert(byte[] source) {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(source);
        try {
            if (this.usingDefaultDeserializer) {
                return this.deserialize(byteStream);
            }
            Object result = this.deserializer.deserialize((InputStream)byteStream);
            this.checkAllowList(result.getClass());
            return result;
        }
        catch (Exception ex) {
            throw new SerializationFailedException("Failed to deserialize payload. Is the byte array a result of corresponding serialization for " + this.deserializer.getClass().getSimpleName() + "?", (Throwable)ex);
        }
    }

    protected Object deserialize(ByteArrayInputStream inputStream) throws IOException {
        try {
            ConfigurableObjectInputStream objectInputStream = new ConfigurableObjectInputStream(inputStream, this.defaultDeserializerClassLoader){

                protected Class<?> resolveClass(ObjectStreamClass classDesc) throws IOException, ClassNotFoundException {
                    Class clazz = super.resolveClass(classDesc);
                    AllowListDeserializingConverter.this.checkAllowList(clazz);
                    return clazz;
                }
            };
            return objectInputStream.readObject();
        }
        catch (ClassNotFoundException ex) {
            throw new NestedIOException("Failed to deserialize object type", (Throwable)ex);
        }
    }

    protected void checkAllowList(Class<?> clazz) {
        if (this.allowedPatterns.isEmpty()) {
            return;
        }
        if (clazz.isArray() || clazz.isPrimitive() || clazz.equals(String.class) || Number.class.isAssignableFrom(clazz)) {
            return;
        }
        String className = clazz.getName();
        for (String pattern : this.allowedPatterns) {
            if (!PatternMatchUtils.simpleMatch((String)pattern, (String)className)) continue;
            return;
        }
        throw new SecurityException("Attempt to deserialize unauthorized " + clazz);
    }
}

