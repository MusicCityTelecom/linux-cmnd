/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.filtering.spi;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.glassfish.jersey.message.filtering.spi.EntityGraph;

public interface EntityProcessorContext {
    public Type getType();

    public Class<?> getEntityClass();

    public Field getField();

    public Method getMethod();

    public EntityGraph getEntityGraph();

    public static enum Type {
        CLASS_READER,
        CLASS_WRITER,
        PROPERTY_READER,
        PROPERTY_WRITER,
        METHOD_READER,
        METHOD_WRITER;

    }
}

