/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.internal.introspection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class MethodKey {
    private static final int PRIMITIVE_SIZE = 11;
    private final int hashCode;
    private final String method;
    private final Class<?>[] params;
    private static final Class<?>[] NOARGS = new Class[0];
    private static final int HASH = 37;
    private static final Map<Class<?>, Class<?>[]> CONVERTIBLES = new HashMap(11);
    private static final Map<Class<?>, Class<?>[]> STRICT_CONVERTIBLES;
    private static final int MORE_SPECIFIC = 0;
    private static final int LESS_SPECIFIC = 1;
    private static final int INCOMPARABLE = 2;
    private static final Parameters<Method> METHODS;
    private static final Parameters<Constructor<?>> CONSTRUCTORS;

    public MethodKey(String aMethod, Object[] args) {
        int size;
        this.method = aMethod;
        int hash = this.method.hashCode();
        if (args != null && (size = args.length) > 0) {
            this.params = new Class[size];
            for (int p = 0; p < size; ++p) {
                Object arg = args[p];
                Class parm = arg == null ? Void.class : arg.getClass();
                hash = 37 * hash + parm.hashCode();
                this.params[p] = parm;
            }
        } else {
            this.params = NOARGS;
        }
        this.hashCode = hash;
    }

    MethodKey(Method aMethod) {
        this(aMethod.getName(), aMethod.getParameterTypes());
    }

    MethodKey(Constructor<?> aCtor) {
        this(aCtor.getDeclaringClass().getName(), aCtor.getParameterTypes());
    }

    MethodKey(String aMethod, Class<?>[] args) {
        int size;
        this.method = aMethod.intern();
        int hash = this.method.hashCode();
        if (args != null && (size = args.length) > 0) {
            this.params = new Class[size];
            for (int p = 0; p < size; ++p) {
                Class<?> parm = MethodKey.primitiveClass(args[p]);
                hash = 37 * hash + parm.hashCode();
                this.params[p] = parm;
            }
        } else {
            this.params = NOARGS;
        }
        this.hashCode = hash;
    }

    String getMethod() {
        return this.method;
    }

    Class<?>[] getParameters() {
        return this.params;
    }

    public int hashCode() {
        return this.hashCode;
    }

    public boolean equals(Object obj) {
        if (obj instanceof MethodKey) {
            MethodKey key = (MethodKey)obj;
            return this.method.equals(key.method) && Arrays.equals(this.params, key.params);
        }
        return false;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder(this.method);
        for (Class<?> c : this.params) {
            builder.append(c == Void.class ? "null" : c.getName());
        }
        return builder.toString();
    }

    public String debugString() {
        StringBuilder builder = new StringBuilder(this.method);
        builder.append('(');
        for (int i = 0; i < this.params.length; ++i) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(Void.class == this.params[i] ? "null" : this.params[i].getName());
        }
        builder.append(')');
        return builder.toString();
    }

    public static boolean isVarArgs(Method method) {
        if (method == null) {
            return false;
        }
        if (method.isVarArgs()) {
            return true;
        }
        Class<?>[] ptypes = method.getParameterTypes();
        if (ptypes.length == 0 || ptypes[ptypes.length - 1].getComponentType() == null) {
            return false;
        }
        String mname = method.getName();
        Class<?> clazz = method.getDeclaringClass();
        do {
            try {
                Method m = clazz.getMethod(mname, ptypes);
                if (m.isVarArgs()) {
                    return true;
                }
            }
            catch (NoSuchMethodException noSuchMethodException) {
                // empty catch block
            }
        } while ((clazz = clazz.getSuperclass()) != null);
        return false;
    }

    public Method getMostSpecificMethod(Method[] methods) {
        return (Method)((Parameters)MethodKey.METHODS).getMostSpecific(this, methods);
    }

    public Constructor<?> getMostSpecificConstructor(Constructor<?>[] methods) {
        return (Constructor)((Parameters)MethodKey.CONSTRUCTORS).getMostSpecific(this, methods);
    }

    public static boolean isInvocationConvertible(Class<?> formal, Class<?> actual, boolean possibleVarArg) {
        return MethodKey.isInvocationConvertible(formal, actual, false, possibleVarArg);
    }

    public static boolean isStrictInvocationConvertible(Class<?> formal, Class<?> actual, boolean possibleVarArg) {
        return MethodKey.isInvocationConvertible(formal, actual, true, possibleVarArg);
    }

    static Class<?> primitiveClass(Class<?> parm) {
        Class<?>[] prim = CONVERTIBLES.get(parm);
        return prim == null ? parm : prim[0];
    }

    private static Class<?>[] asArray(Class<?> ... args) {
        return args;
    }

    private static boolean isInvocationConvertible(Class<?> formal, Class<?> actual, boolean strict, boolean possibleVarArg) {
        if (actual == null && !formal.isPrimitive()) {
            return true;
        }
        if (actual != null && formal.isAssignableFrom(actual) && actual.isArray() == formal.isArray()) {
            return true;
        }
        if (!strict && formal == Object.class) {
            return true;
        }
        if (formal.isPrimitive()) {
            Class<?>[] clist;
            Class<?>[] classArray = clist = strict ? STRICT_CONVERTIBLES.get(formal) : CONVERTIBLES.get(formal);
            if (clist != null) {
                for (Class<?> aClass : clist) {
                    if (actual != aClass) continue;
                    return true;
                }
            }
            return false;
        }
        if (possibleVarArg && formal.isArray()) {
            if (actual != null && actual.isArray()) {
                actual = actual.getComponentType();
            }
            return MethodKey.isInvocationConvertible(formal.getComponentType(), actual, strict, false);
        }
        return false;
    }

    static {
        CONVERTIBLES.put(Boolean.TYPE, MethodKey.asArray(Boolean.class));
        CONVERTIBLES.put(Character.TYPE, MethodKey.asArray(Character.class));
        CONVERTIBLES.put(Byte.TYPE, MethodKey.asArray(Byte.class));
        CONVERTIBLES.put(Short.TYPE, MethodKey.asArray(Short.class, Byte.class));
        CONVERTIBLES.put(Integer.TYPE, MethodKey.asArray(Integer.class, Short.class, Byte.class));
        CONVERTIBLES.put(Long.TYPE, MethodKey.asArray(Long.class, Integer.class, Short.class, Byte.class));
        CONVERTIBLES.put(Float.TYPE, MethodKey.asArray(Float.class, Long.class, Integer.class, Short.class, Byte.class));
        CONVERTIBLES.put(Double.TYPE, MethodKey.asArray(Double.class, Float.class, Long.class, Integer.class, Short.class, Byte.class));
        STRICT_CONVERTIBLES = new HashMap(11);
        STRICT_CONVERTIBLES.put(Short.TYPE, MethodKey.asArray(Byte.TYPE));
        STRICT_CONVERTIBLES.put(Integer.TYPE, MethodKey.asArray(Short.TYPE, Byte.TYPE));
        STRICT_CONVERTIBLES.put(Long.TYPE, MethodKey.asArray(Integer.TYPE, Short.TYPE, Byte.TYPE));
        STRICT_CONVERTIBLES.put(Float.TYPE, MethodKey.asArray(Long.TYPE, Integer.TYPE, Short.TYPE, Byte.TYPE));
        STRICT_CONVERTIBLES.put(Double.TYPE, MethodKey.asArray(Float.TYPE, Long.TYPE, Integer.TYPE, Short.TYPE, Byte.TYPE));
        METHODS = new Parameters<Method>(){

            @Override
            protected Class<?>[] getParameterTypes(Method app) {
                return app.getParameterTypes();
            }

            @Override
            public boolean isVarArgs(Method app) {
                return MethodKey.isVarArgs(app);
            }
        };
        CONSTRUCTORS = new Parameters<Constructor<?>>(){

            @Override
            protected Class<?>[] getParameterTypes(Constructor<?> app) {
                return app.getParameterTypes();
            }

            @Override
            public boolean isVarArgs(Constructor<?> app) {
                return app.isVarArgs();
            }
        };
    }

    private static abstract class Parameters<T> {
        private Parameters() {
        }

        protected abstract Class<?>[] getParameterTypes(T var1);

        protected abstract boolean isVarArgs(T var1);

        private T getMostSpecific(MethodKey key, T[] methods) {
            Class[] args = key.params;
            LinkedList<T> applicables = this.getApplicables(methods, args);
            if (applicables.isEmpty()) {
                return null;
            }
            if (applicables.size() == 1) {
                return applicables.getFirst();
            }
            LinkedList maximals = new LinkedList();
            for (Object app : applicables) {
                Class<?>[] parms = this.getParameterTypes(app);
                boolean lessSpecific = false;
                Iterator maximal = maximals.iterator();
                while (!lessSpecific && maximal.hasNext()) {
                    Object max = maximal.next();
                    switch (this.moreSpecific(args, parms, this.getParameterTypes(max))) {
                        case 0: {
                            maximal.remove();
                            break;
                        }
                        case 1: {
                            lessSpecific = true;
                            break;
                        }
                    }
                }
                if (lessSpecific) continue;
                maximals.addLast(app);
            }
            if (maximals.size() > 1) {
                throw this.ambiguousException(args, applicables);
            }
            return (T)maximals.getFirst();
        }

        private AmbiguousException ambiguousException(Class<?>[] classes, List<T> applicables) {
            boolean severe = false;
            int instanceArgCount = 0;
            block0: for (int c = 0; c < classes.length; ++c) {
                Class<?> argClazz = classes[c];
                if (Void.class.equals(argClazz)) {
                    int objectParmCount = 0;
                    for (T app : applicables) {
                        Class<?>[] parmClasses = this.getParameterTypes(app);
                        Class<?> parmClass = parmClasses[c];
                        if (!Object.class.equals(parmClass) || objectParmCount++ != 2) continue;
                        severe = true;
                        continue block0;
                    }
                    continue;
                }
                ++instanceArgCount;
            }
            return new AmbiguousException(severe || instanceArgCount == classes.length);
        }

        private int moreSpecific(Class<?>[] a, Class<?>[] c1, Class<?>[] c2) {
            if (c1.length > a.length) {
                return 1;
            }
            if (c2.length > a.length) {
                return 0;
            }
            if (c1.length > c2.length) {
                return 0;
            }
            if (c2.length > c1.length) {
                return 1;
            }
            int length = c1.length;
            int ultimate = c1.length - 1;
            for (int i = 0; i < length; ++i) {
                boolean c2s;
                boolean c1s;
                boolean last;
                if (c1[i] == c2[i]) continue;
                boolean bl = last = i == ultimate;
                if (a[i] == Void.class) {
                    if (c1[i] == Object.class && c2[i] != Object.class) {
                        return 0;
                    }
                    if (c1[i] != Object.class && c2[i] == Object.class) {
                        return 1;
                    }
                }
                if ((c1s = this.isPrimitive(c1[i], last)) != (c2s = this.isPrimitive(c2[i], last))) {
                    return c1s == (a[i] != Void.class) ? 0 : 1;
                }
                c1s = this.isStrictConvertible(c2[i], c1[i], last);
                if (c1s == (c2s = this.isStrictConvertible(c1[i], c2[i], last))) continue;
                return c1s ? 0 : 1;
            }
            return 2;
        }

        private boolean isPrimitive(Class<?> c, boolean possibleVarArg) {
            if (c != null) {
                if (c.isPrimitive()) {
                    return true;
                }
                if (possibleVarArg) {
                    Class<?> t = c.getComponentType();
                    return t != null && t.isPrimitive();
                }
            }
            return false;
        }

        private LinkedList<T> getApplicables(T[] methods, Class<?>[] classes) {
            LinkedList<T> list = new LinkedList<T>();
            for (T method : methods) {
                if (!this.isApplicable(method, classes)) continue;
                list.add(method);
            }
            return list;
        }

        private boolean isApplicable(T method, Class<?>[] actuals) {
            Class<?>[] formals = this.getParameterTypes(method);
            if (formals.length == actuals.length) {
                for (int i = 0; i < actuals.length; ++i) {
                    if (this.isConvertible(formals[i], actuals[i], false)) continue;
                    if (i == actuals.length - 1 && formals[i].isArray()) {
                        return this.isConvertible(formals[i], actuals[i], true);
                    }
                    return false;
                }
                return true;
            }
            if (!this.isVarArgs(method)) {
                return false;
            }
            if (formals.length > actuals.length) {
                if (formals.length - actuals.length > 1) {
                    return false;
                }
                for (int i = 0; i < actuals.length; ++i) {
                    if (this.isConvertible(formals[i], actuals[i], false)) continue;
                    return false;
                }
                return true;
            }
            if (formals.length > 0 && actuals.length > 0) {
                for (int i = 0; i < formals.length - 1; ++i) {
                    if (this.isConvertible(formals[i], actuals[i], false)) continue;
                    return false;
                }
                Class<?> vararg = formals[formals.length - 1].getComponentType();
                for (int i = formals.length - 1; i < actuals.length; ++i) {
                    if (this.isConvertible(vararg, actuals[i], false)) continue;
                    return false;
                }
                return true;
            }
            return false;
        }

        private boolean isConvertible(Class<?> formal, Class<?> actual, boolean possibleVarArg) {
            return MethodKey.isInvocationConvertible(formal, actual.equals(Void.class) ? null : actual, possibleVarArg);
        }

        private boolean isStrictConvertible(Class<?> formal, Class<?> actual, boolean possibleVarArg) {
            return MethodKey.isStrictInvocationConvertible(formal, actual.equals(Void.class) ? null : actual, possibleVarArg);
        }
    }

    public static class AmbiguousException
    extends RuntimeException {
        private static final long serialVersionUID = -201801091655L;
        private final boolean severe;

        AmbiguousException(boolean flag) {
            this.severe = flag;
        }

        public boolean isSevere() {
            return this.severe;
        }
    }
}

