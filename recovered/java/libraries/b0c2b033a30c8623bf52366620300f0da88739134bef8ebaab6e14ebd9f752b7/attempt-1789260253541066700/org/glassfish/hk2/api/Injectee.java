/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.api;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Type;
import java.util.Set;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Unqualified;

public interface Injectee {
    public Type getRequiredType();

    public Set<Annotation> getRequiredQualifiers();

    public int getPosition();

    public Class<?> getInjecteeClass();

    public AnnotatedElement getParent();

    public boolean isOptional();

    public boolean isSelf();

    public Unqualified getUnqualified();

    public ActiveDescriptor<?> getInjecteeDescriptor();
}

