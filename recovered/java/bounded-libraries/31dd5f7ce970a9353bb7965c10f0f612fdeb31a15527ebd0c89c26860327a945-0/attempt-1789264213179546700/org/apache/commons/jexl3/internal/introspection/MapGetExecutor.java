/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.internal.introspection;

import java.lang.reflect.Method;
import java.util.Map;
import org.apache.commons.jexl3.internal.introspection.AbstractExecutor;
import org.apache.commons.jexl3.internal.introspection.Introspector;

public final class MapGetExecutor
extends AbstractExecutor.Get {
    private static final Method MAP_GET = MapGetExecutor.initMarker(Map.class, "get", Object.class);
    private final Object property;

    public static MapGetExecutor discover(Introspector is, Class<?> clazz, Object identifier) {
        if (Map.class.isAssignableFrom(clazz)) {
            return new MapGetExecutor(clazz, MAP_GET, identifier);
        }
        return null;
    }

    private MapGetExecutor(Class<?> clazz, Method method, Object key) {
        super(clazz, method);
        this.property = key;
    }

    @Override
    public Object getTargetProperty() {
        return this.property;
    }

    @Override
    public Object invoke(Object obj) {
        Map map = (Map)obj;
        return map.get(this.property);
    }

    @Override
    public Object tryInvoke(Object obj, Object key) {
        if (obj != null && this.method != null && this.objectClass.equals(obj.getClass()) && (this.property == null && key == null || this.property != null && key != null && this.property.getClass().equals(key.getClass()))) {
            Map map = (Map)obj;
            return map.get(key);
        }
        return TRY_FAILED;
    }
}

