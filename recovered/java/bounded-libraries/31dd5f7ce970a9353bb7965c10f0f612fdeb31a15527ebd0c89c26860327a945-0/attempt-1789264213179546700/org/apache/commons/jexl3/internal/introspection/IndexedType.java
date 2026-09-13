/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.internal.introspection;

import java.beans.IntrospectionException;
import java.lang.reflect.Method;
import org.apache.commons.jexl3.internal.introspection.Introspector;
import org.apache.commons.jexl3.internal.introspection.MethodKey;
import org.apache.commons.jexl3.internal.introspection.Uberspect;
import org.apache.commons.jexl3.introspection.JexlPropertyGet;

public final class IndexedType
implements JexlPropertyGet {
    private final String container;
    private final Class<?> clazz;
    private final Method[] getters;
    private volatile Method get = null;
    private final Method[] setters;
    private volatile Method set = null;

    public static JexlPropertyGet discover(Introspector is, Object object, String name) {
        if (object != null && name != null && !name.isEmpty()) {
            String base = name.substring(0, 1).toUpperCase() + name.substring(1);
            String container = name;
            Class<?> clazz = object.getClass();
            Method[] getters = is.getMethods(object.getClass(), "get" + base);
            Method[] setters = is.getMethods(object.getClass(), "set" + base);
            if (getters != null) {
                return new IndexedType(container, clazz, getters, setters);
            }
        }
        return null;
    }

    private IndexedType(String name, Class<?> c, Method[] gets, Method[] sets) {
        this.container = name;
        this.clazz = c;
        this.getters = gets;
        this.setters = sets;
    }

    @Override
    public Object invoke(Object obj) throws Exception {
        if (obj != null && this.clazz.equals(obj.getClass())) {
            return new IndexedContainer(this, obj);
        }
        throw new IntrospectionException("property resolution error");
    }

    @Override
    public Object tryInvoke(Object obj, Object key) {
        if (obj != null && key != null && this.clazz.equals(obj.getClass()) && this.container.equals(key.toString())) {
            return new IndexedContainer(this, obj);
        }
        return Uberspect.TRY_FAILED;
    }

    @Override
    public boolean tryFailed(Object rval) {
        return rval == Uberspect.TRY_FAILED;
    }

    @Override
    public boolean isCacheable() {
        return true;
    }

    private Object invokeGet(Object object, Object key) throws Exception {
        if (this.getters != null && this.getters.length > 0) {
            Class<?>[] ptypes;
            Method jm = this.get;
            if (jm != null && (ptypes = jm.getParameterTypes())[0].isAssignableFrom(key.getClass())) {
                return jm.invoke(object, key);
            }
            Object[] args = new Object[]{key};
            String mname = this.getters[0].getName();
            MethodKey km = new MethodKey(mname, args);
            jm = km.getMostSpecificMethod(this.getters);
            if (jm != null) {
                Object invoked = jm.invoke(object, args);
                this.get = jm;
                return invoked;
            }
        }
        throw new IntrospectionException("property get error: " + object.getClass().toString() + "@" + key.toString());
    }

    private Object invokeSet(Object object, Object key, Object value) throws Exception {
        if (this.setters != null && this.setters.length > 0) {
            Class<?>[] ptypes;
            Method jm = this.set;
            if (jm != null && (ptypes = jm.getParameterTypes())[0].isAssignableFrom(key.getClass()) && (value == null || ptypes[1].isAssignableFrom(value.getClass()))) {
                return jm.invoke(object, key, value);
            }
            Object[] args = new Object[]{key, value};
            String mname = this.setters[0].getName();
            MethodKey km = new MethodKey(mname, args);
            jm = km.getMostSpecificMethod(this.setters);
            if (jm != null) {
                Object invoked = jm.invoke(object, args);
                this.set = jm;
                return invoked;
            }
        }
        throw new IntrospectionException("property set error: " + object.getClass().toString() + "@" + key.toString());
    }

    public static final class IndexedContainer {
        private final Object container;
        private final IndexedType type;

        private IndexedContainer(IndexedType theType, Object theContainer) {
            this.type = theType;
            this.container = theContainer;
        }

        public String getContainerName() {
            return this.type.container;
        }

        public Class<?> getContainerClass() {
            return this.type.clazz;
        }

        public Object get(Object key) throws Exception {
            return this.type.invokeGet(this.container, key);
        }

        public Object set(Object key, Object value) throws Exception {
            return this.type.invokeSet(this.container, key, value);
        }
    }
}

