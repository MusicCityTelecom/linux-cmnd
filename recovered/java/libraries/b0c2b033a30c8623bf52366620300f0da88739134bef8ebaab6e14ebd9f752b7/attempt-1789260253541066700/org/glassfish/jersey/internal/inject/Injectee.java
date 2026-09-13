/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.inject;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Type;
import java.util.Set;
import org.glassfish.jersey.internal.inject.ForeignDescriptor;

public interface Injectee {
    public Type getRequiredType();

    public Set<Annotation> getRequiredQualifiers();

    public int getPosition();

    public Class<?> getInjecteeClass();

    public AnnotatedElement getParent();

    public boolean isOptional();

    public ForeignDescriptor getInjecteeDescriptor();

    public Class<? extends Annotation> getParentClassScope();

    public boolean isFactory();

    public boolean isProvider();
}

