/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.NonNull
 */
package org.springframework.data.util;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import org.springframework.data.util.ParentTypeAwareTypeInformation;
import org.springframework.data.util.TypeDiscoverer;
import org.springframework.data.util.TypeInformation;
import org.springframework.lang.NonNull;

class GenericArrayTypeInformation<S>
extends ParentTypeAwareTypeInformation<S> {
    private final GenericArrayType type;

    protected GenericArrayTypeInformation(GenericArrayType type, TypeDiscoverer<?> parent) {
        super((Type)type, parent);
        this.type = type;
    }

    @Override
    public Class<S> getType() {
        return Array.newInstance(this.resolveType(this.type.getGenericComponentType()), 0).getClass();
    }

    @Override
    @NonNull
    protected TypeInformation<?> doGetComponentType() {
        Type componentType = this.type.getGenericComponentType();
        return this.createInfo(componentType);
    }

    public String toString() {
        return this.type.toString();
    }
}

