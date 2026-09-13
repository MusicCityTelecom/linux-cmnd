/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.inject;

import org.glassfish.jersey.internal.inject.Binding;

public class ClassBinding<T>
extends Binding<T, ClassBinding<T>> {
    private final Class<T> service;

    ClassBinding(Class<T> service) {
        this.service = service;
        this.asType(service);
    }

    public Class<T> getService() {
        return this.service;
    }
}

