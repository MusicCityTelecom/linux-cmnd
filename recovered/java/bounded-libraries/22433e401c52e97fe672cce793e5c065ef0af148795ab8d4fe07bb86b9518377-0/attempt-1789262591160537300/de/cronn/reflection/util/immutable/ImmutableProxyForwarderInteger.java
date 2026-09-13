/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.bytebuddy.implementation.bind.annotation.AllArguments
 *  net.bytebuddy.implementation.bind.annotation.FieldValue
 *  net.bytebuddy.implementation.bind.annotation.Origin
 */
package de.cronn.reflection.util.immutable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;

public final class ImmutableProxyForwarderInteger {
    private ImmutableProxyForwarderInteger() {
    }

    public static Integer forward(@Origin Method method, @FieldValue(value="$delegate") Object delegate, @AllArguments Object[] args) throws InvocationTargetException, IllegalAccessException {
        return (Integer)method.invoke(delegate, args);
    }
}

