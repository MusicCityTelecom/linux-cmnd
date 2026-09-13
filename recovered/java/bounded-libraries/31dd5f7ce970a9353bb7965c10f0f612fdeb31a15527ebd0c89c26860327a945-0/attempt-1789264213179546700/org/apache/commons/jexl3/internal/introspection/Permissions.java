/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.internal.introspection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.apache.commons.jexl3.annotations.NoJexl;

public class Permissions {
    public static final Permissions DEFAULT = new Permissions();

    protected Permissions() {
    }

    public boolean allow(Package pack) {
        return pack != null && pack.getAnnotation(NoJexl.class) == null;
    }

    public boolean allow(Class<?> clazz) {
        return clazz != null && this.allow(clazz.getPackage()) && this.allow(clazz, true);
    }

    public boolean allow(Constructor<?> ctor) {
        if (ctor == null) {
            return false;
        }
        if (!Modifier.isPublic(ctor.getModifiers())) {
            return false;
        }
        Class<?> clazz = ctor.getDeclaringClass();
        if (!this.allow(clazz, false)) {
            return false;
        }
        NoJexl nojexl = ctor.getAnnotation(NoJexl.class);
        return nojexl == null;
    }

    public boolean allow(Field field) {
        if (field == null) {
            return false;
        }
        if (!Modifier.isPublic(field.getModifiers())) {
            return false;
        }
        Class<?> clazz = field.getDeclaringClass();
        if (!this.allow(clazz, false)) {
            return false;
        }
        NoJexl nojexl = field.getAnnotation(NoJexl.class);
        return nojexl == null;
    }

    public boolean allow(Method method) {
        if (method == null) {
            return false;
        }
        if (!Modifier.isPublic(method.getModifiers())) {
            return false;
        }
        NoJexl nojexl = method.getAnnotation(NoJexl.class);
        if (nojexl != null) {
            return false;
        }
        Class<?> clazz = method.getDeclaringClass();
        nojexl = clazz.getAnnotation(NoJexl.class);
        if (nojexl != null) {
            return false;
        }
        for (Class<?> inter : clazz.getInterfaces()) {
            if (this.allow(inter, method)) continue;
            return false;
        }
        for (clazz = clazz.getSuperclass(); clazz != null; clazz = clazz.getSuperclass()) {
            if (this.allow(clazz, method)) continue;
            return false;
        }
        return true;
    }

    protected boolean allow(Class<?> clazz, boolean interf) {
        if (clazz == null) {
            return false;
        }
        if (!Modifier.isPublic(clazz.getModifiers())) {
            return false;
        }
        if (interf) {
            for (Class<?> inter : clazz.getInterfaces()) {
                NoJexl nojexl = inter.getAnnotation(NoJexl.class);
                if (nojexl == null) continue;
                return false;
            }
        }
        for (clazz = clazz.getSuperclass(); clazz != null; clazz = clazz.getSuperclass()) {
            NoJexl nojexl = clazz.getAnnotation(NoJexl.class);
            if (nojexl == null) continue;
            return false;
        }
        return true;
    }

    protected boolean allow(Class<?> clazz, Method method) {
        if (clazz != null) {
            try {
                Method wmethod = clazz.getMethod(method.getName(), method.getParameterTypes());
                if (wmethod != null) {
                    NoJexl nojexl = clazz.getAnnotation(NoJexl.class);
                    if (nojexl != null) {
                        return false;
                    }
                    nojexl = wmethod.getAnnotation(NoJexl.class);
                    if (nojexl != null) {
                        return false;
                    }
                }
            }
            catch (NoSuchMethodException ex) {
                return true;
            }
            catch (SecurityException ex) {
                return false;
            }
        }
        return true;
    }
}

