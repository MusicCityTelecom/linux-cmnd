/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.inject;

import java.lang.annotation.Annotation;
import org.glassfish.jersey.internal.inject.Injectee;

public interface InjectionResolver<T extends Annotation> {
    public Object resolve(Injectee var1);

    public boolean isConstructorParameterIndicator();

    public boolean isMethodParameterIndicator();

    public Class<T> getAnnotation();
}

