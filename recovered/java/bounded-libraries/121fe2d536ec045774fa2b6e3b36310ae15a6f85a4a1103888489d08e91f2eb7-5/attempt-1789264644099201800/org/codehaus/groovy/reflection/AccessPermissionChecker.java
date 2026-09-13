/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.reflection;

import groovy.lang.GroovyObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ReflectPermission;
import java.security.AccessControlException;
import org.codehaus.groovy.reflection.CacheAccessControlException;

final class AccessPermissionChecker {
    private static final ReflectPermission REFLECT_PERMISSION = new ReflectPermission("suppressAccessChecks");

    private AccessPermissionChecker() {
    }

    private static void checkAccessPermission(Class<?> declaringClass, int modifiers, boolean isAccessible) {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null && isAccessible) {
            if (((modifiers & 2) != 0 || (modifiers & 5) == 0 && AccessPermissionChecker.packageCanNotBeAddedAnotherClass(declaringClass)) && !GroovyObject.class.isAssignableFrom(declaringClass)) {
                securityManager.checkPermission(REFLECT_PERMISSION);
            } else if ((modifiers & 4) != 0 && declaringClass.equals(ClassLoader.class)) {
                securityManager.checkCreateClassLoader();
            }
        }
    }

    private static boolean packageCanNotBeAddedAnotherClass(Class<?> declaringClass) {
        return declaringClass.getName().startsWith("java.");
    }

    static void checkAccessPermission(Method method) {
        try {
            AccessPermissionChecker.checkAccessPermission(method.getDeclaringClass(), method.getModifiers(), method.isAccessible());
        }
        catch (AccessControlException e) {
            throw AccessPermissionChecker.createCacheAccessControlExceptionOf(method, e);
        }
    }

    static void checkAccessPermission(Constructor constructor) {
        try {
            AccessPermissionChecker.checkAccessPermission(constructor.getDeclaringClass(), constructor.getModifiers(), constructor.isAccessible());
        }
        catch (AccessControlException e) {
            throw AccessPermissionChecker.createCacheAccessControlExceptionOf(constructor, e);
        }
    }

    private static CacheAccessControlException createCacheAccessControlExceptionOf(Method method, AccessControlException e) {
        return new CacheAccessControlException("Groovy object can not access method " + method.getName() + " cacheAccessControlExceptionOf class " + method.getDeclaringClass().getName() + " with modifiers \"" + Modifier.toString(method.getModifiers()) + "\"", e);
    }

    private static CacheAccessControlException createCacheAccessControlExceptionOf(Constructor constructor, AccessControlException e) {
        return new CacheAccessControlException("Groovy object can not access constructor " + constructor.getName() + " cacheAccessControlExceptionOf class " + constructor.getDeclaringClass().getName() + " with modifiers \"" + Modifier.toString(constructor.getModifiers()) + "\"", e);
    }

    static void checkAccessPermission(Field field) {
        try {
            AccessPermissionChecker.checkAccessPermission(field.getDeclaringClass(), field.getModifiers(), field.isAccessible());
        }
        catch (AccessControlException e) {
            throw AccessPermissionChecker.createCacheAccessControlExceptionOf(field, e);
        }
    }

    private static CacheAccessControlException createCacheAccessControlExceptionOf(Field field, AccessControlException e) {
        return new CacheAccessControlException("Groovy object can not access field " + field.getName() + " cacheAccessControlExceptionOf class " + field.getDeclaringClass().getName() + " with modifiers \"" + Modifier.toString(field.getModifiers()) + "\"", e);
    }
}

