/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.model;

import java.lang.annotation.Annotation;
import java.util.Collection;

public interface NameBound {
    public boolean isNameBound();

    public Collection<Class<? extends Annotation>> getNameBindings();
}

