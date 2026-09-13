/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.internal.introspection;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.List;
import org.apache.commons.jexl3.internal.introspection.AbstractExecutor;
import org.apache.commons.jexl3.internal.introspection.Introspector;

public final class ListSetExecutor
extends AbstractExecutor.Set {
    private static final Method ARRAY_SET = ListSetExecutor.initMarker(Array.class, "set", Object.class, Integer.TYPE, Object.class);
    private static final Method LIST_SET = ListSetExecutor.initMarker(List.class, "set", Integer.TYPE, Object.class);
    private final Integer property;

    public static ListSetExecutor discover(Introspector is, Class<?> clazz, Object identifier, Object value) {
        Integer index = ListSetExecutor.castInteger(identifier);
        if (index != null) {
            if (clazz.isArray()) {
                return new ListSetExecutor(clazz, ARRAY_SET, index);
            }
            if (List.class.isAssignableFrom(clazz)) {
                return new ListSetExecutor(clazz, LIST_SET, index);
            }
        }
        return null;
    }

    private ListSetExecutor(Class<?> clazz, Method method, Integer key) {
        super(clazz, method);
        this.property = key;
    }

    @Override
    public Object getTargetProperty() {
        return this.property;
    }

    @Override
    public Object invoke(Object obj, Object value) {
        if (this.method == ARRAY_SET) {
            Array.set(obj, this.property, value);
        } else {
            List list = (List)obj;
            list.set(this.property, value);
        }
        return value;
    }

    @Override
    public Object tryInvoke(Object obj, Object key, Object value) {
        Integer index = ListSetExecutor.castInteger(key);
        if (obj != null && this.method != null && this.objectClass.equals(obj.getClass()) && index != null) {
            if (this.method == ARRAY_SET) {
                Array.set(obj, index, value);
            } else {
                List list = (List)obj;
                list.set(index, value);
            }
            return value;
        }
        return TRY_FAILED;
    }
}

