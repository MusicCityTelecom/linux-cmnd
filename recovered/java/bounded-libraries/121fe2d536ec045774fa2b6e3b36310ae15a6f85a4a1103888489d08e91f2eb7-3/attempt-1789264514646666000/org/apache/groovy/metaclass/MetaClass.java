/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.metaclass;

import groovy.lang.MetaMethod;
import org.apache.groovy.internal.metaclass.MetaClassConstant;
import org.apache.groovy.internal.util.ReevaluatingReference;
import org.apache.groovy.lang.annotation.Incubating;

@Incubating
public final class MetaClass<T> {
    private final ReevaluatingReference<MetaClassConstant<T>> implRef;

    MetaClass(ReevaluatingReference<MetaClassConstant<T>> implRef) {
        this.implRef = implRef;
    }

    public MetaMethod getMethod(String name, Class[] parameters) {
        return this.implRef.getPayload().getMethod(name, parameters);
    }
}

