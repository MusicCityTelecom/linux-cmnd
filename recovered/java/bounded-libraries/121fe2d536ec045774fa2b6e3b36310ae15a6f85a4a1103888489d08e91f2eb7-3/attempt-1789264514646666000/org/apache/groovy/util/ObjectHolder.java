/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.util;

import org.apache.groovy.internal.util.Supplier;

public class ObjectHolder<T> {
    private T object;

    public ObjectHolder() {
    }

    public ObjectHolder(T object) {
        this.object = object;
    }

    public T getObject() {
        return this.object;
    }

    public T getObject(Supplier<? extends T> def) {
        if (null == this.object) {
            this.object = def.get();
        }
        return this.object;
    }

    public void setObject(T object) {
        this.object = object;
    }
}

