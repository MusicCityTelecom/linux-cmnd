/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.runtime.metaclass;

import groovy.lang.Closure;
import groovy.lang.MetaBeanProperty;
import groovy.lang.MetaMethod;
import groovy.lang.MetaProperty;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.groovy.util.concurrent.ConcurrentReferenceHashMap;
import org.apache.groovy.util.concurrent.ManagedIdentityConcurrentMap;
import org.codehaus.groovy.reflection.CachedClass;
import org.codehaus.groovy.reflection.ReflectionCache;
import org.codehaus.groovy.util.ReferenceBundle;

public class ThreadManagedMetaBeanProperty
extends MetaBeanProperty {
    private static final ConcurrentHashMap<String, ManagedIdentityConcurrentMap> PROPNAME_TO_MAP = new ConcurrentHashMap();
    private final ManagedIdentityConcurrentMap instance2Prop;
    private final Class declaringClass;
    private final ThreadBoundGetter getter;
    private final ThreadBoundSetter setter;
    private Object initialValue;
    private Closure initialValueCreator;
    private static final ReferenceBundle SOFT_BUNDLE = ReferenceBundle.getSoftBundle();

    public synchronized Object getInitialValue() {
        return this.getInitialValue(null);
    }

    public synchronized Object getInitialValue(Object object) {
        if (this.initialValueCreator != null) {
            return this.initialValueCreator.call(object);
        }
        return this.initialValue;
    }

    public void setInitialValueCreator(Closure callable) {
        this.initialValueCreator = callable;
    }

    public ThreadManagedMetaBeanProperty(Class declaringClass, String name, Class type, Object iv) {
        super(name, type, null, null);
        this.type = type;
        this.declaringClass = declaringClass;
        this.getter = new ThreadBoundGetter(name);
        this.setter = new ThreadBoundSetter(name);
        this.initialValue = iv;
        this.instance2Prop = ThreadManagedMetaBeanProperty.getInstance2PropName(name);
    }

    public ThreadManagedMetaBeanProperty(Class declaringClass, String name, Class type, Closure initialValueCreator) {
        super(name, type, null, null);
        this.type = type;
        this.declaringClass = declaringClass;
        this.getter = new ThreadBoundGetter(name);
        this.setter = new ThreadBoundSetter(name);
        this.initialValueCreator = initialValueCreator;
        this.instance2Prop = ThreadManagedMetaBeanProperty.getInstance2PropName(name);
    }

    private static ManagedIdentityConcurrentMap getInstance2PropName(String name) {
        ManagedIdentityConcurrentMap ores;
        ManagedIdentityConcurrentMap res = PROPNAME_TO_MAP.get(name);
        if (res == null && (ores = PROPNAME_TO_MAP.putIfAbsent(name, res = new ManagedIdentityConcurrentMap(ConcurrentReferenceHashMap.ReferenceType.SOFT))) != null) {
            return ores;
        }
        return res;
    }

    @Override
    public MetaMethod getGetter() {
        return this.getter;
    }

    @Override
    public MetaMethod getSetter() {
        return this.setter;
    }

    class ThreadBoundGetter
    extends MetaMethod {
        private final String name;

        public ThreadBoundGetter(String name) {
            this.setParametersTypes(CachedClass.EMPTY_ARRAY);
            this.name = MetaProperty.getGetterName(name, ThreadManagedMetaBeanProperty.this.type);
        }

        @Override
        public int getModifiers() {
            return 1;
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public Class getReturnType() {
            return ThreadManagedMetaBeanProperty.this.type;
        }

        @Override
        public CachedClass getDeclaringClass() {
            return ReflectionCache.getCachedClass(ThreadManagedMetaBeanProperty.this.declaringClass);
        }

        @Override
        public Object invoke(Object object, Object[] arguments) {
            return ThreadManagedMetaBeanProperty.this.instance2Prop.getOrPut(object, ThreadManagedMetaBeanProperty.this.getInitialValue());
        }
    }

    private class ThreadBoundSetter
    extends MetaMethod {
        private final String name;

        public ThreadBoundSetter(String name) {
            this.setParametersTypes(new CachedClass[]{ReflectionCache.getCachedClass(ThreadManagedMetaBeanProperty.this.type)});
            this.name = MetaProperty.getSetterName(name);
        }

        @Override
        public int getModifiers() {
            return 1;
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public Class getReturnType() {
            return ThreadManagedMetaBeanProperty.this.type;
        }

        @Override
        public CachedClass getDeclaringClass() {
            return ReflectionCache.getCachedClass(ThreadManagedMetaBeanProperty.this.declaringClass);
        }

        @Override
        public Object invoke(Object object, Object[] arguments) {
            ThreadManagedMetaBeanProperty.this.instance2Prop.put(object, arguments[0]);
            return null;
        }
    }
}

