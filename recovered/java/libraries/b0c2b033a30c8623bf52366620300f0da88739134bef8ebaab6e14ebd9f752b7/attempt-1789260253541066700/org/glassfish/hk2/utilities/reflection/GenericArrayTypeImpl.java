/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.utilities.reflection;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import org.glassfish.hk2.utilities.general.GeneralUtilities;

public class GenericArrayTypeImpl
implements GenericArrayType {
    private final Type genericComponentType;

    public GenericArrayTypeImpl(Type gct) {
        this.genericComponentType = gct;
    }

    @Override
    public Type getGenericComponentType() {
        return this.genericComponentType;
    }

    public int hashCode() {
        return this.genericComponentType.hashCode();
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof GenericArrayType)) {
            return false;
        }
        GenericArrayType other = (GenericArrayType)o;
        return GeneralUtilities.safeEquals(this.genericComponentType, other.getGenericComponentType());
    }

    public String toString() {
        return "GenericArrayTypeImpl(" + this.genericComponentType + ")";
    }
}

