/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.internal.metaclass;

import groovy.lang.MetaClassImpl;
import groovy.lang.MetaMethod;
import java.lang.invoke.SwitchPoint;
import org.apache.groovy.lang.annotation.Incubating;

@Incubating
public final class MetaClassConstant<T> {
    private final SwitchPoint switchPoint = new SwitchPoint();
    private final MetaClassImpl impl;

    public MetaClassConstant(Class<T> clazz) {
        this.impl = new MetaClassImpl(clazz);
    }

    public SwitchPoint getSwitchPoint() {
        return this.switchPoint;
    }

    public MetaMethod getMethod(String name, Class[] parameters) {
        return this.impl.pickMethod(name, parameters);
    }
}

