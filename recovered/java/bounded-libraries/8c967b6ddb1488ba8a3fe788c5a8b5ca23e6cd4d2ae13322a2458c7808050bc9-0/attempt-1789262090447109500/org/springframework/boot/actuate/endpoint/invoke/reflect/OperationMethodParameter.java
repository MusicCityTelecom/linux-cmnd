/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nonnull
 *  javax.annotation.meta.When
 *  org.springframework.core.annotation.MergedAnnotation
 *  org.springframework.core.annotation.MergedAnnotations
 *  org.springframework.lang.Nullable
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.ObjectUtils
 */
package org.springframework.boot.actuate.endpoint.invoke.reflect;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Parameter;
import javax.annotation.Nonnull;
import javax.annotation.meta.When;
import org.springframework.boot.actuate.endpoint.invoke.OperationParameter;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;

class OperationMethodParameter
implements OperationParameter {
    private static final boolean jsr305Present = ClassUtils.isPresent((String)"javax.annotation.Nonnull", null);
    private final String name;
    private final Parameter parameter;

    OperationMethodParameter(String name, Parameter parameter) {
        this.name = name;
        this.parameter = parameter;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Class<?> getType() {
        return this.parameter.getType();
    }

    @Override
    public boolean isMandatory() {
        if (!ObjectUtils.isEmpty((Object[])this.parameter.getAnnotationsByType(Nullable.class))) {
            return false;
        }
        return jsr305Present ? new Jsr305().isMandatory(this.parameter) : true;
    }

    public String toString() {
        return this.name + " of type " + this.parameter.getType().getName();
    }

    private static class Jsr305 {
        private Jsr305() {
        }

        boolean isMandatory(Parameter parameter) {
            MergedAnnotation annotation = MergedAnnotations.from((AnnotatedElement)parameter).get(Nonnull.class);
            return !annotation.isPresent() || annotation.getEnum("when", When.class) == When.ALWAYS;
        }
    }
}

