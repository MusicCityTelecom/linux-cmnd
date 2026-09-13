/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.reflection;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import org.codehaus.groovy.classgen.asm.util.TypeUtil;
import org.codehaus.groovy.vmplugin.VMPlugin;
import org.codehaus.groovy.vmplugin.VMPluginFactory;

public class ReflectionUtils {
    private static final VMPlugin VM_PLUGIN = VMPluginFactory.getPlugin();
    private static final Set<String> IGNORED_PACKAGES;
    private static final Class<?>[] EMPTY_CLASS_ARRAY;
    private static final ClassContextHelper HELPER;
    private static final MethodHandle IS_SEALED_METHODHANDLE;
    private static final MethodHandle GET_PERMITTED_SUBCLASSES_METHODHANDLE;

    public static boolean isCallingClassReflectionAvailable() {
        return true;
    }

    public static Class getCallingClass() {
        return ReflectionUtils.getCallingClass(1);
    }

    public static Class getCallingClass(int matchLevel) {
        return ReflectionUtils.getCallingClass(matchLevel, Collections.emptySet());
    }

    public static Class getCallingClass(int matchLevel, Collection<String> extraIgnoredPackages) {
        Class[] classContext = HELPER.getClassContext();
        int depth = 0;
        int level = matchLevel;
        try {
            Class c;
            while (ReflectionUtils.classShouldBeIgnored(c = classContext[depth++], extraIgnoredPackages) || c != null && level-- > 0 && depth < classContext.length) {
            }
            return c;
        }
        catch (Throwable ignore) {
            return null;
        }
    }

    public static List<Method> getDeclaredMethods(Class<?> type, String name, Class<?> ... parameterTypes) {
        return ReflectionUtils.doGetMethods(type, name, parameterTypes, Class::getDeclaredMethods);
    }

    public static List<Method> getMethods(Class<?> type, String name, Class<?> ... parameterTypes) {
        return ReflectionUtils.doGetMethods(type, name, parameterTypes, Class::getMethods);
    }

    private static List<Method> doGetMethods(Class<?> type, String name, Class<?>[] parameterTypes, Function<? super Class<?>, ? extends Method[]> f) {
        LinkedList<Method> methodList = new LinkedList<Method>();
        for (Method m : f.apply(type)) {
            Class<?>[] methodParameterTypes;
            if (!m.getName().equals(name) || !ReflectionUtils.parameterTypeMatches(methodParameterTypes = m.getParameterTypes(), parameterTypes)) continue;
            methodList.add(m);
        }
        return methodList;
    }

    public static boolean parameterTypeMatches(Class<?>[] parameterTypes, Class<?>[] argTypes) {
        if (parameterTypes.length != argTypes.length) {
            return false;
        }
        int n = parameterTypes.length;
        for (int i = 0; i < n; ++i) {
            Class<?> parameterType = parameterTypes[i];
            if (Object.class == parameterType) continue;
            Class<?> argType = argTypes[i];
            if (null == argType) {
                return false;
            }
            if (parameterType == argType) continue;
            Class boxedArgType = TypeUtil.autoboxType(argType);
            Class boxedParameterType = TypeUtil.autoboxType(parameterType);
            if (boxedParameterType.isAssignableFrom(boxedArgType)) continue;
            return false;
        }
        return true;
    }

    public static boolean checkCanSetAccessible(AccessibleObject accessibleObject, Class<?> caller) {
        return VM_PLUGIN.checkCanSetAccessible(accessibleObject, caller);
    }

    public static boolean checkAccessible(Class<?> callerClass, Class<?> declaringClass, int memberModifiers, boolean allowIllegalAccess) {
        return VM_PLUGIN.checkAccessible(callerClass, declaringClass, memberModifiers, allowIllegalAccess);
    }

    public static boolean trySetAccessible(AccessibleObject ao) {
        try {
            return VM_PLUGIN.trySetAccessible(ao);
        }
        catch (Throwable throwable) {
            return false;
        }
    }

    public static Optional<AccessibleObject> makeAccessibleInPrivilegedAction(AccessibleObject ao) {
        return AccessController.doPrivileged(() -> ReflectionUtils.makeAccessible(ao));
    }

    public static Optional<AccessibleObject> makeAccessible(AccessibleObject ao) {
        try {
            if (ao.isAccessible() || ReflectionUtils.trySetAccessible(ao)) {
                return Optional.of(ao);
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        return Optional.empty();
    }

    public static AccessibleObject[] makeAccessible(AccessibleObject[] aoa) {
        try {
            AccessibleObject.setAccessible(aoa, true);
            return aoa;
        }
        catch (Throwable ignore) {
            ArrayList<AccessibleObject> ret = new ArrayList<AccessibleObject>(aoa.length);
            for (AccessibleObject ao : aoa) {
                boolean accessible = ReflectionUtils.trySetAccessible(ao);
                if (!accessible) continue;
                ret.add(ao);
            }
            return ret.toArray((AccessibleObject[])Array.newInstance(aoa.getClass().getComponentType(), 0));
        }
    }

    public static boolean isSealed(Class<?> clazz) {
        if (null == IS_SEALED_METHODHANDLE) {
            return false;
        }
        if (null == clazz) {
            return false;
        }
        boolean sealed = false;
        try {
            sealed = IS_SEALED_METHODHANDLE.invokeExact(clazz);
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        return sealed;
    }

    public static Class<?>[] getPermittedSubclasses(Class<?> clazz) {
        if (null == GET_PERMITTED_SUBCLASSES_METHODHANDLE) {
            return EMPTY_CLASS_ARRAY;
        }
        if (null == clazz) {
            return EMPTY_CLASS_ARRAY;
        }
        Class<?>[] result = EMPTY_CLASS_ARRAY;
        try {
            result = GET_PERMITTED_SUBCLASSES_METHODHANDLE.invokeExact(clazz);
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        return result;
    }

    private static boolean classShouldBeIgnored(Class c, Collection<String> extraIgnoredPackages) {
        return c != null && (c.isSynthetic() || c.getPackage() != null && (IGNORED_PACKAGES.contains(c.getPackage().getName()) || extraIgnoredPackages.contains(c.getPackage().getName())));
    }

    static {
        EMPTY_CLASS_ARRAY = new Class[0];
        HashSet<String> set = new HashSet<String>();
        set.add("groovy.lang");
        set.add("sun.reflect");
        set.add("java.security");
        set.add("java.lang.invoke");
        set.add("org.codehaus.groovy.reflection");
        set.add("org.codehaus.groovy.runtime");
        set.add("org.codehaus.groovy.runtime.callsite");
        set.add("org.codehaus.groovy.runtime.metaclass");
        set.add("org.codehaus.groovy.vmplugin.v5");
        set.add("org.codehaus.groovy.vmplugin.v6");
        set.add("org.codehaus.groovy.vmplugin.v7");
        set.add("org.codehaus.groovy.vmplugin.v8");
        set.add("org.codehaus.groovy.vmplugin.v9");
        set.add("org.codehaus.groovy.vmplugin.v10");
        set.add("org.codehaus.groovy.vmplugin.v16");
        IGNORED_PACKAGES = Collections.unmodifiableSet(set);
        HELPER = new ClassContextHelper();
        MethodHandle isSealedMethodHandle = null;
        try {
            isSealedMethodHandle = MethodHandles.lookup().findVirtual(Class.class, "isSealed", MethodType.methodType(Boolean.TYPE, new Class[0]));
        }
        catch (IllegalAccessException | NoSuchMethodException reflectiveOperationException) {
            // empty catch block
        }
        IS_SEALED_METHODHANDLE = isSealedMethodHandle;
        MethodHandle getPermittedSubclassesMethodHandle = null;
        try {
            getPermittedSubclassesMethodHandle = MethodHandles.lookup().findVirtual(Class.class, "getPermittedSubclasses", MethodType.methodType(Class[].class, new Class[0]));
        }
        catch (IllegalAccessException | NoSuchMethodException reflectiveOperationException) {
            // empty catch block
        }
        GET_PERMITTED_SUBCLASSES_METHODHANDLE = getPermittedSubclassesMethodHandle;
    }

    private static class ClassContextHelper
    extends SecurityManager {
        private ClassContextHelper() {
        }

        public Class[] getClassContext() {
            return super.getClassContext();
        }
    }
}

