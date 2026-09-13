/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 */
package org.apache.commons.jexl3.internal.introspection;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.EnumSet;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.jexl3.JexlArithmetic;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlOperator;
import org.apache.commons.jexl3.internal.introspection.AbstractExecutor;
import org.apache.commons.jexl3.internal.introspection.ArrayIterator;
import org.apache.commons.jexl3.internal.introspection.BooleanGetExecutor;
import org.apache.commons.jexl3.internal.introspection.ConstructorMethod;
import org.apache.commons.jexl3.internal.introspection.DuckGetExecutor;
import org.apache.commons.jexl3.internal.introspection.DuckSetExecutor;
import org.apache.commons.jexl3.internal.introspection.EnumerationIterator;
import org.apache.commons.jexl3.internal.introspection.FieldGetExecutor;
import org.apache.commons.jexl3.internal.introspection.FieldSetExecutor;
import org.apache.commons.jexl3.internal.introspection.IndexedType;
import org.apache.commons.jexl3.internal.introspection.Introspector;
import org.apache.commons.jexl3.internal.introspection.ListGetExecutor;
import org.apache.commons.jexl3.internal.introspection.ListSetExecutor;
import org.apache.commons.jexl3.internal.introspection.MapGetExecutor;
import org.apache.commons.jexl3.internal.introspection.MapSetExecutor;
import org.apache.commons.jexl3.internal.introspection.MethodExecutor;
import org.apache.commons.jexl3.internal.introspection.MethodKey;
import org.apache.commons.jexl3.internal.introspection.Permissions;
import org.apache.commons.jexl3.internal.introspection.PropertyGetExecutor;
import org.apache.commons.jexl3.internal.introspection.PropertySetExecutor;
import org.apache.commons.jexl3.introspection.JexlMethod;
import org.apache.commons.jexl3.introspection.JexlPropertyGet;
import org.apache.commons.jexl3.introspection.JexlPropertySet;
import org.apache.commons.jexl3.introspection.JexlUberspect;
import org.apache.commons.logging.Log;

public class Uberspect
implements JexlUberspect {
    public static final Object TRY_FAILED = JexlEngine.TRY_FAILED;
    protected final Log logger;
    private final JexlUberspect.ResolverStrategy strategy;
    private final Permissions permissions;
    private final AtomicInteger version;
    private volatile Reference<Introspector> ref;
    private volatile Reference<ClassLoader> loader;
    private final Map<Class<? extends JexlArithmetic>, Set<JexlOperator>> operatorMap;

    public Uberspect(Log runtimeLogger, JexlUberspect.ResolverStrategy sty) {
        this(runtimeLogger, sty, null);
    }

    public Uberspect(Log runtimeLogger, JexlUberspect.ResolverStrategy sty, Permissions perms) {
        this.logger = runtimeLogger;
        this.strategy = sty == null ? JexlUberspect.JEXL_STRATEGY : sty;
        this.permissions = perms;
        this.ref = new SoftReference<Object>(null);
        this.loader = new SoftReference<ClassLoader>(this.getClass().getClassLoader());
        this.operatorMap = new ConcurrentHashMap<Class<? extends JexlArithmetic>, Set<JexlOperator>>();
        this.version = new AtomicInteger(0);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected final Introspector base() {
        Introspector intro = this.ref.get();
        if (intro == null) {
            Uberspect uberspect = this;
            synchronized (uberspect) {
                intro = this.ref.get();
                if (intro == null) {
                    intro = new Introspector(this.logger, this.loader.get(), this.permissions);
                    this.ref = new SoftReference<Introspector>(intro);
                    this.loader = new SoftReference<ClassLoader>(intro.getLoader());
                    this.version.incrementAndGet();
                }
            }
        }
        return intro;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void setClassLoader(ClassLoader nloader) {
        Uberspect uberspect = this;
        synchronized (uberspect) {
            Introspector intro = this.ref.get();
            if (intro != null) {
                intro.setLoader(nloader);
            } else {
                intro = new Introspector(this.logger, nloader, this.permissions);
                this.ref = new SoftReference<Introspector>(intro);
            }
            this.loader = new SoftReference<ClassLoader>(intro.getLoader());
            this.operatorMap.clear();
            this.version.incrementAndGet();
        }
    }

    @Override
    public ClassLoader getClassLoader() {
        return this.loader.get();
    }

    @Override
    public int getVersion() {
        return this.version.intValue();
    }

    public final Class<?> getClassByName(String className) {
        return this.base().getClassByName(className);
    }

    public final Field getField(Class<?> c, String key) {
        return this.base().getField(c, key);
    }

    public final String[] getFieldNames(Class<?> c) {
        return this.base().getFieldNames(c);
    }

    public final Method getMethod(Class<?> c, String name, Object[] params) {
        return this.base().getMethod(c, new MethodKey(name, params));
    }

    public final Method getMethod(Class<?> c, MethodKey key) {
        return this.base().getMethod(c, key);
    }

    public final String[] getMethodNames(Class<?> c) {
        return this.base().getMethodNames(c);
    }

    public final Method[] getMethods(Class<?> c, String methodName) {
        return this.base().getMethods(c, methodName);
    }

    @Override
    public JexlMethod getMethod(Object obj, String method, Object ... args) {
        return MethodExecutor.discover(this.base(), obj, method, args);
    }

    @Override
    public List<JexlUberspect.PropertyResolver> getResolvers(JexlOperator op, Object obj) {
        return this.strategy.apply(op, obj);
    }

    @Override
    public JexlPropertyGet getPropertyGet(Object obj, Object identifier) {
        return this.getPropertyGet(null, obj, identifier);
    }

    @Override
    public JexlPropertyGet getPropertyGet(List<JexlUberspect.PropertyResolver> resolvers, Object obj, Object identifier) {
        Class<?> claz = obj.getClass();
        String property = AbstractExecutor.castString(identifier);
        Introspector is = this.base();
        List<JexlUberspect.PropertyResolver> r = resolvers == null ? this.strategy.apply(null, obj) : resolvers;
        JexlPropertyGet executor = null;
        for (JexlUberspect.PropertyResolver resolver : r) {
            block14: {
                block13: {
                    if (!(resolver instanceof JexlUberspect.JexlResolver)) break block13;
                    switch ((JexlUberspect.JexlResolver)resolver) {
                        case PROPERTY: {
                            executor = PropertyGetExecutor.discover(is, claz, property);
                            if (executor == null) {
                                executor = BooleanGetExecutor.discover(is, claz, property);
                            }
                            break block14;
                        }
                        case MAP: {
                            executor = MapGetExecutor.discover(is, claz, identifier);
                            break block14;
                        }
                        case LIST: {
                            Integer index = AbstractExecutor.castInteger(identifier);
                            if (index != null) {
                                executor = ListGetExecutor.discover(is, claz, index);
                            }
                            break block14;
                        }
                        case DUCK: {
                            executor = DuckGetExecutor.discover(is, claz, identifier);
                            if (executor == null && property != null && property != identifier) {
                                executor = DuckGetExecutor.discover(is, claz, property);
                            }
                            break block14;
                        }
                        case FIELD: {
                            executor = FieldGetExecutor.discover(is, claz, property);
                            if (obj instanceof Class) {
                                executor = FieldGetExecutor.discover(is, (Class)obj, property);
                            }
                            break block14;
                        }
                        case CONTAINER: {
                            executor = IndexedType.discover(is, obj, property);
                            break block14;
                        }
                    }
                    continue;
                }
                executor = resolver.getPropertyGet(this, obj, identifier);
            }
            if (executor == null) continue;
            return executor;
        }
        return null;
    }

    @Override
    public JexlPropertySet getPropertySet(Object obj, Object identifier, Object arg) {
        return this.getPropertySet(null, obj, identifier, arg);
    }

    @Override
    public JexlPropertySet getPropertySet(List<JexlUberspect.PropertyResolver> resolvers, Object obj, Object identifier, Object arg) {
        Class<?> claz = obj.getClass();
        String property = AbstractExecutor.castString(identifier);
        Introspector is = this.base();
        List<JexlUberspect.PropertyResolver> actual = resolvers == null ? this.strategy.apply(null, obj) : resolvers;
        JexlPropertySet executor = null;
        for (JexlUberspect.PropertyResolver resolver : actual) {
            block11: {
                block10: {
                    if (!(resolver instanceof JexlUberspect.JexlResolver)) break block10;
                    switch ((JexlUberspect.JexlResolver)resolver) {
                        case PROPERTY: {
                            executor = PropertySetExecutor.discover(is, claz, property, arg);
                            break block11;
                        }
                        case MAP: {
                            executor = MapSetExecutor.discover(is, claz, identifier, arg);
                            break block11;
                        }
                        case LIST: {
                            Integer index = AbstractExecutor.castInteger(identifier);
                            if (index != null) {
                                executor = ListSetExecutor.discover(is, claz, identifier, arg);
                            }
                            break block11;
                        }
                        case DUCK: {
                            executor = DuckSetExecutor.discover(is, claz, identifier, arg);
                            if (executor == null && property != null && property != identifier) {
                                executor = DuckSetExecutor.discover(is, claz, property, arg);
                            }
                            break block11;
                        }
                        case FIELD: {
                            executor = FieldSetExecutor.discover(is, claz, property, arg);
                            break block11;
                        }
                    }
                    continue;
                }
                executor = resolver.getPropertySet(this, obj, identifier, arg);
            }
            if (executor == null) continue;
            return executor;
        }
        return null;
    }

    @Override
    public Iterator<?> getIterator(Object obj) {
        block8: {
            if (obj instanceof Iterator) {
                return (Iterator)obj;
            }
            if (obj.getClass().isArray()) {
                return new ArrayIterator(obj);
            }
            if (obj instanceof Map) {
                return ((Map)obj).values().iterator();
            }
            if (obj instanceof Enumeration) {
                return new EnumerationIterator((Enumeration)obj);
            }
            if (obj instanceof Iterable) {
                return ((Iterable)obj).iterator();
            }
            try {
                JexlMethod it = this.getMethod(obj, "iterator", (Object[])null);
                if (it != null && Iterator.class.isAssignableFrom(it.getReturnType())) {
                    return (Iterator)it.invoke(obj, null);
                }
            }
            catch (Exception xany) {
                if (this.logger == null || !this.logger.isDebugEnabled()) break block8;
                this.logger.info((Object)"unable to solve iterator()", (Throwable)xany);
            }
        }
        return null;
    }

    @Override
    public JexlMethod getConstructor(Object ctorHandle, Object ... args) {
        return ConstructorMethod.discover(this.base(), ctorHandle, args);
    }

    @Override
    public JexlArithmetic.Uberspect getArithmetic(JexlArithmetic arithmetic) {
        ArithmeticUberspect jau = null;
        if (arithmetic != null) {
            Class<?> aclass = arithmetic.getClass();
            Set<JexlOperator> ops = this.operatorMap.get(aclass);
            if (ops == null) {
                ops = EnumSet.noneOf(JexlOperator.class);
                if (!JexlArithmetic.class.equals(aclass)) {
                    for (JexlOperator op : JexlOperator.values()) {
                        Method[] methods = this.getMethods(arithmetic.getClass(), op.getMethodName());
                        if (methods == null) continue;
                        for (Method method : methods) {
                            Class<?>[] parms = method.getParameterTypes();
                            if (parms.length != op.getArity() || JexlArithmetic.class.equals(method.getDeclaringClass())) continue;
                            try {
                                JexlArithmetic.class.getMethod(method.getName(), method.getParameterTypes());
                            }
                            catch (NoSuchMethodException xmethod) {
                                ops.add(op);
                            }
                        }
                    }
                }
                this.operatorMap.put(aclass, ops);
            }
            jau = new ArithmeticUberspect(arithmetic, ops);
        }
        return jau;
    }

    protected class ArithmeticUberspect
    implements JexlArithmetic.Uberspect {
        private final JexlArithmetic arithmetic;
        private final Set<JexlOperator> overloads;

        private ArithmeticUberspect(JexlArithmetic theArithmetic, Set<JexlOperator> theOverloads) {
            this.arithmetic = theArithmetic;
            this.overloads = theOverloads;
        }

        @Override
        public JexlMethod getOperator(JexlOperator operator, Object ... args) {
            return this.overloads.contains((Object)operator) && args != null ? Uberspect.this.getMethod(this.arithmetic, operator.getMethodName(), args) : null;
        }

        @Override
        public boolean overloads(JexlOperator operator) {
            return this.overloads.contains((Object)operator);
        }
    }
}

