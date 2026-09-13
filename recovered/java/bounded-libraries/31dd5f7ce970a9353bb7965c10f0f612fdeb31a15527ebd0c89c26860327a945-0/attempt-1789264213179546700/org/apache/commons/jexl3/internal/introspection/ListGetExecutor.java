/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.internal.introspection;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.List;
import org.apache.commons.jexl3.internal.introspection.AbstractExecutor;
import org.apache.commons.jexl3.internal.introspection.Introspector;

public final class ListGetExecutor
extends AbstractExecutor.Get {
    private static final Method ARRAY_GET = ListGetExecutor.initMarker(Array.class, "get", Object.class, Integer.TYPE);
    private static final Method LIST_GET = ListGetExecutor.initMarker(List.class, "get", Integer.TYPE);
    private final Integer property;

    public static ListGetExecutor discover(Introspector is, Class<?> clazz, Integer index) {
        if (index != null) {
            if (clazz.isArray()) {
                return new ListGetExecutor(clazz, ARRAY_GET, index);
            }
            if (List.class.isAssignableFrom(clazz)) {
                return new ListGetExecutor(clazz, LIST_GET, index);
            }
        }
        return null;
    }

    private ListGetExecutor(Class<?> clazz, Method method, Integer index) {
        super(clazz, method);
        this.property = index;
    }

    @Override
    public Object getTargetProperty() {
        return this.property;
    }

    @Override
    public Object invoke(Object obj) {
        if (this.method == ARRAY_GET) {
            return Array.get(obj, this.property);
        }
        return ((List)obj).get(this.property);
    }

    @Override
    public Object tryInvoke(Object obj, Object identifier) {
        Integer index = ListGetExecutor.castInteger(identifier);
        if (obj != null && this.method != null && this.objectClass.equals(obj.getClass()) && index != null) {
            if (this.method == ARRAY_GET) {
                return Array.get(obj, index);
            }
            return ((List)obj).get(index);
        }
        return TRY_FAILED;
    }
}

