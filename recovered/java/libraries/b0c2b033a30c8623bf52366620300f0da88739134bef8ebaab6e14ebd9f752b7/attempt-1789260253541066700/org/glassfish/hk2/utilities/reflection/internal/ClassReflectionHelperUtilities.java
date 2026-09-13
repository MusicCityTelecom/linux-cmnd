/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.utilities.reflection.internal;

import java.lang.reflect.Field;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.glassfish.hk2.utilities.reflection.MethodWrapper;
import org.glassfish.hk2.utilities.reflection.Pretty;
import org.glassfish.hk2.utilities.reflection.internal.MethodWrapperImpl;

public class ClassReflectionHelperUtilities {
    static final String CONVENTION_POST_CONSTRUCT = "postConstruct";
    static final String CONVENTION_PRE_DESTROY = "preDestroy";
    private static final Set<MethodWrapper> OBJECT_METHODS = ClassReflectionHelperUtilities.getObjectMethods();
    private static final Set<Field> OBJECT_FIELDS = ClassReflectionHelperUtilities.getObjectFields();

    private static Set<MethodWrapper> getObjectMethods() {
        return AccessController.doPrivileged(new PrivilegedAction<Set<MethodWrapper>>(){

            @Override
            public Set<MethodWrapper> run() {
                LinkedHashSet<MethodWrapper> retVal = new LinkedHashSet<MethodWrapper>();
                for (Method method : Object.class.getDeclaredMethods()) {
                    retVal.add(new MethodWrapperImpl(method));
                }
                return retVal;
            }
        });
    }

    private static Set<Field> getObjectFields() {
        return AccessController.doPrivileged(new PrivilegedAction<Set<Field>>(){

            @Override
            public Set<Field> run() {
                LinkedHashSet<Field> retVal = new LinkedHashSet<Field>();
                for (Field field : Object.class.getDeclaredFields()) {
                    retVal.add(field);
                }
                return retVal;
            }
        });
    }

    private static Method[] secureGetDeclaredMethods(final Class<?> clazz) {
        return AccessController.doPrivileged(new PrivilegedAction<Method[]>(){

            @Override
            public Method[] run() {
                return clazz.getDeclaredMethods();
            }
        });
    }

    private static Field[] secureGetDeclaredFields(final Class<?> clazz) {
        return AccessController.doPrivileged(new PrivilegedAction<Field[]>(){

            @Override
            public Field[] run() {
                return clazz.getDeclaredFields();
            }
        });
    }

    private static Set<MethodWrapper> getDeclaredMethodWrappers(Class<?> clazz) {
        Method[] declaredMethods = ClassReflectionHelperUtilities.secureGetDeclaredMethods(clazz);
        LinkedHashSet<MethodWrapper> retVal = new LinkedHashSet<MethodWrapper>();
        for (Method method : declaredMethods) {
            retVal.add(new MethodWrapperImpl(method));
        }
        return retVal;
    }

    private static Set<Field> getDeclaredFieldWrappers(Class<?> clazz) {
        Field[] declaredFields = ClassReflectionHelperUtilities.secureGetDeclaredFields(clazz);
        LinkedHashSet<Field> retVal = new LinkedHashSet<Field>();
        for (Field field : declaredFields) {
            retVal.add(field);
        }
        return retVal;
    }

    static Set<Field> getAllFieldWrappers(Class<?> clazz) {
        if (clazz == null) {
            return Collections.emptySet();
        }
        if (Object.class.equals(clazz)) {
            return OBJECT_FIELDS;
        }
        if (clazz.isInterface()) {
            return Collections.emptySet();
        }
        LinkedHashSet<Field> retVal = new LinkedHashSet<Field>();
        retVal.addAll(ClassReflectionHelperUtilities.getDeclaredFieldWrappers(clazz));
        retVal.addAll(ClassReflectionHelperUtilities.getAllFieldWrappers(clazz.getSuperclass()));
        return retVal;
    }

    static Set<MethodWrapper> getAllMethodWrappers(Class<?> clazz) {
        if (clazz == null) {
            return Collections.emptySet();
        }
        if (Object.class.equals(clazz)) {
            return OBJECT_METHODS;
        }
        LinkedHashSet<MethodWrapper> retVal = new LinkedHashSet<MethodWrapper>();
        if (clazz.isInterface()) {
            for (Method method : clazz.getDeclaredMethods()) {
                MethodWrapperImpl wrapper = new MethodWrapperImpl(method);
                retVal.add(wrapper);
            }
            for (GenericDeclaration genericDeclaration : clazz.getInterfaces()) {
                retVal.addAll(ClassReflectionHelperUtilities.getAllMethodWrappers(genericDeclaration));
            }
        } else {
            retVal.addAll(ClassReflectionHelperUtilities.getDeclaredMethodWrappers(clazz));
            retVal.addAll(ClassReflectionHelperUtilities.getAllMethodWrappers(clazz.getSuperclass()));
        }
        return retVal;
    }

    static boolean isPostConstruct(Method m) {
        if (m.isAnnotationPresent(PostConstruct.class)) {
            if (m.getParameterTypes().length != 0) {
                throw new IllegalArgumentException("The method " + Pretty.method(m) + " annotated with @PostConstruct must not have any arguments");
            }
            return true;
        }
        if (m.getParameterTypes().length != 0) {
            return false;
        }
        return CONVENTION_POST_CONSTRUCT.equals(m.getName());
    }

    static boolean isPreDestroy(Method m) {
        if (m.isAnnotationPresent(PreDestroy.class)) {
            if (m.getParameterTypes().length != 0) {
                throw new IllegalArgumentException("The method " + Pretty.method(m) + " annotated with @PreDestroy must not have any arguments");
            }
            return true;
        }
        if (m.getParameterTypes().length != 0) {
            return false;
        }
        return CONVENTION_PRE_DESTROY.equals(m.getName());
    }
}

