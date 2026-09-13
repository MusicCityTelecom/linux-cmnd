/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.internal.introspection;

import java.lang.reflect.Method;
import java.util.Map;
import org.apache.commons.jexl3.internal.introspection.AbstractExecutor;
import org.apache.commons.jexl3.internal.introspection.Introspector;

public final class MapSetExecutor
extends AbstractExecutor.Set {
    private static final Method MAP_SET = MapSetExecutor.initMarker(Map.class, "put", Object.class, Object.class);
    private final Object property;
    private final Class<?> valueClass;

    public static MapSetExecutor discover(Introspector is, Class<?> clazz, Object identifier, Object value) {
        if (Map.class.isAssignableFrom(clazz)) {
            return new MapSetExecutor(clazz, MAP_SET, identifier, value);
        }
        return null;
    }

    private MapSetExecutor(Class<?> clazz, Method method, Object key, Object value) {
        super(clazz, method);
        this.property = key;
        this.valueClass = MapSetExecutor.classOf(value);
    }

    @Override
    public Object getTargetProperty() {
        return this.property;
    }

    @Override
    public Object invoke(Object obj, Object value) {
        Map map = (Map)obj;
        map.put(this.property, value);
        return value;
    }

    @Override
    public Object tryInvoke(Object obj, Object key, Object value) {
        if (obj != null && this.method != null && this.objectClass.equals(obj.getClass()) && (this.property == null && key == null || this.property != null && key != null && this.property.getClass().equals(key.getClass())) && this.valueClass.equals(MapSetExecutor.classOf(value))) {
            Map map = (Map)obj;
            map.put(key, value);
            return value;
        }
        return TRY_FAILED;
    }
}

