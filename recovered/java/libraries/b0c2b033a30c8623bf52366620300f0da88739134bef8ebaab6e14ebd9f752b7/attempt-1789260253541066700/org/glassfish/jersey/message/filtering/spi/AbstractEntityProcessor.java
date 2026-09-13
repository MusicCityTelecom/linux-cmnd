/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.filtering.spi;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Set;
import org.glassfish.jersey.internal.util.ReflectionHelper;
import org.glassfish.jersey.message.filtering.spi.EntityGraph;
import org.glassfish.jersey.message.filtering.spi.EntityProcessor;
import org.glassfish.jersey.message.filtering.spi.EntityProcessorContext;
import org.glassfish.jersey.message.filtering.spi.FilteringHelper;

public abstract class AbstractEntityProcessor
implements EntityProcessor {
    @Override
    public EntityProcessor.Result process(EntityProcessorContext context) {
        switch (context.getType()) {
            case CLASS_READER: 
            case CLASS_WRITER: {
                return this.process(null, null, FilteringHelper.EMPTY_ANNOTATIONS, context.getEntityClass().getDeclaredAnnotations(), context.getEntityGraph());
            }
            case PROPERTY_READER: 
            case PROPERTY_WRITER: 
            case METHOD_READER: 
            case METHOD_WRITER: {
                Type fieldType;
                String fieldName;
                boolean isProperty;
                Field field = context.getField();
                Method method = context.getMethod();
                boolean bl = isProperty = field != null;
                if (isProperty) {
                    fieldName = field.getName();
                    fieldType = field.getGenericType();
                } else {
                    fieldName = ReflectionHelper.getPropertyName(method);
                    fieldType = ReflectionHelper.isGetter(method) ? method.getGenericReturnType() : method.getGenericParameterTypes()[0];
                }
                return this.process(fieldName, FilteringHelper.getEntityClass(fieldType), this.getAnnotations(field), this.getAnnotations(method), context.getEntityGraph());
            }
        }
        return EntityProcessor.Result.SKIP;
    }

    private Annotation[] getAnnotations(AccessibleObject accessibleObject) {
        return accessibleObject == null ? FilteringHelper.EMPTY_ANNOTATIONS : accessibleObject.getDeclaredAnnotations();
    }

    protected EntityProcessor.Result process(String fieldName, Class<?> fieldClass, Annotation[] fieldAnnotations, Annotation[] annotations, EntityGraph graph) {
        return EntityProcessor.Result.SKIP;
    }

    protected final void addFilteringScopes(String field, Class<?> fieldClass, Set<String> filteringScopes, EntityGraph graph) {
        if (!filteringScopes.isEmpty()) {
            if (FilteringHelper.filterableEntityClass(fieldClass)) {
                graph.addSubgraph(field, fieldClass, filteringScopes);
            } else {
                graph.addField(field, filteringScopes);
            }
        }
    }

    protected final void addGlobalScopes(Set<String> filteringScopes, EntityGraph graph) {
        graph.addFilteringScopes(filteringScopes);
    }
}

