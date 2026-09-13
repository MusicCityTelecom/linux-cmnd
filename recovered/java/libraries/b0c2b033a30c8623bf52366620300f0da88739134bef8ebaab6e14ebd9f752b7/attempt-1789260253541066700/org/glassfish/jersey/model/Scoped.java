/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.model;

import java.lang.annotation.Annotation;

public interface Scoped {
    public Class<? extends Annotation> getScope();
}

