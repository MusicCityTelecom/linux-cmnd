/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.annotation.AnnotationAttributes
 *  org.springframework.util.Assert
 */
package org.springframework.boot.actuate.endpoint.annotation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.boot.actuate.endpoint.OperationType;
import org.springframework.boot.actuate.endpoint.Producible;
import org.springframework.boot.actuate.endpoint.invoke.reflect.OperationMethod;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.util.Assert;

public class DiscoveredOperationMethod
extends OperationMethod {
    private final List<String> producesMediaTypes;

    public DiscoveredOperationMethod(Method method, OperationType operationType, AnnotationAttributes annotationAttributes) {
        super(method, operationType);
        Assert.notNull((Object)annotationAttributes, (String)"AnnotationAttributes must not be null");
        ArrayList<String> producesMediaTypes = new ArrayList<String>();
        producesMediaTypes.addAll(Arrays.asList(annotationAttributes.getStringArray("produces")));
        producesMediaTypes.addAll(this.getProducesFromProducable(annotationAttributes));
        this.producesMediaTypes = Collections.unmodifiableList(producesMediaTypes);
    }

    private <E extends Enum<E>> List<String> getProducesFromProducable(AnnotationAttributes annotationAttributes) {
        Class<?> type = this.getProducesFrom(annotationAttributes);
        if (type == Producible.class) {
            return Collections.emptyList();
        }
        ArrayList<String> produces = new ArrayList<String>();
        for (Object value : type.getEnumConstants()) {
            produces.add(((Producible)value).getProducedMimeType().toString());
        }
        return produces;
    }

    private Class<?> getProducesFrom(AnnotationAttributes annotationAttributes) {
        try {
            return annotationAttributes.getClass("producesFrom");
        }
        catch (IllegalArgumentException ex) {
            return Producible.class;
        }
    }

    public List<String> getProducesMediaTypes() {
        return this.producesMediaTypes;
    }
}

