/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.utilities.reflection;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import org.glassfish.hk2.utilities.reflection.Pretty;

public class ParameterizedTypeImpl
implements ParameterizedType {
    private final Type rawType;
    private final Type[] actualTypeArguments;

    public ParameterizedTypeImpl(Type rawType, Type ... actualTypeArguments) {
        this.rawType = rawType;
        this.actualTypeArguments = actualTypeArguments;
    }

    @Override
    public Type[] getActualTypeArguments() {
        return this.actualTypeArguments;
    }

    @Override
    public Type getRawType() {
        return this.rawType;
    }

    @Override
    public Type getOwnerType() {
        return null;
    }

    public int hashCode() {
        int retVal = Arrays.hashCode(this.actualTypeArguments);
        if (this.rawType == null) {
            return retVal;
        }
        return retVal ^ this.rawType.hashCode();
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof ParameterizedType)) {
            return false;
        }
        ParameterizedType other = (ParameterizedType)o;
        if (!this.rawType.equals(other.getRawType())) {
            return false;
        }
        Type[] otherActuals = other.getActualTypeArguments();
        if (otherActuals.length != this.actualTypeArguments.length) {
            return false;
        }
        for (int lcv = 0; lcv < otherActuals.length; ++lcv) {
            if (this.actualTypeArguments[lcv].equals(otherActuals[lcv])) continue;
            return false;
        }
        return true;
    }

    public String toString() {
        return Pretty.pType(this);
    }
}

