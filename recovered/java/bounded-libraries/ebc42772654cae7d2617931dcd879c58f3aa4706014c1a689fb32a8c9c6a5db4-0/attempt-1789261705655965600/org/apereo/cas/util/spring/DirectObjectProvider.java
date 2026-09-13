/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.ObjectProvider
 */
package org.apereo.cas.util.spring;

import lombok.Generated;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectProvider;

public class DirectObjectProvider<T>
implements ObjectProvider<T> {
    private final T object;

    public T getIfAvailable() throws BeansException {
        return this.object;
    }

    public T getIfUnique() throws BeansException {
        return this.object;
    }

    public T getObject() throws BeansException {
        return this.object;
    }

    public T getObject(Object ... objects) throws BeansException {
        return this.object;
    }

    @Generated
    public DirectObjectProvider(T object) {
        this.object = object;
    }
}

