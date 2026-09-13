/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 */
package org.apache.commons.jexl3.internal.introspection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.apache.commons.jexl3.internal.introspection.ClassMap;
import org.apache.commons.jexl3.internal.introspection.MethodKey;
import org.apache.commons.jexl3.internal.introspection.Permissions;
import org.apache.commons.logging.Log;

public final class Introspector {
    private static final Constructor<?> CTOR_MISS = CacheMiss.class.getConstructors()[0];
    protected final Log logger;
    private ClassLoader loader;
    private final Permissions permissions;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Map<Class<?>, ClassMap> classMethodMaps = new HashMap();
    private final Map<MethodKey, Constructor<?>> constructorsMap = new HashMap();
    private final Map<String, Class<?>> constructibleClasses = new HashMap();

    public Introspector(Log log, ClassLoader cloader) {
        this(log, cloader, null);
    }

    public Introspector(Log log, ClassLoader cloader, Permissions perms) {
        this.logger = log;
        this.loader = cloader;
        this.permissions = perms != null ? perms : Permissions.DEFAULT;
    }

    public Class<?> getClassByName(String className) {
        try {
            return Class.forName(className, false, this.loader);
        }
        catch (ClassNotFoundException xignore) {
            return null;
        }
    }

    public Method getMethod(Class<?> c, String name, Object[] params) {
        return this.getMethod(c, new MethodKey(name, params));
    }

    public Method getMethod(Class<?> c, MethodKey key) {
        try {
            return this.getMap(c).getMethod(key);
        }
        catch (MethodKey.AmbiguousException xambiguous) {
            if (this.logger != null && xambiguous.isSevere() && this.logger.isInfoEnabled()) {
                this.logger.info((Object)("ambiguous method invocation: " + c.getName() + "." + key.debugString()), (Throwable)xambiguous);
            }
            return null;
        }
    }

    public Field getField(Class<?> c, String key) {
        return this.getMap(c).getField(key);
    }

    public String[] getFieldNames(Class<?> c) {
        if (c == null) {
            return new String[0];
        }
        ClassMap classMap = this.getMap(c);
        return classMap.getFieldNames();
    }

    public String[] getMethodNames(Class<?> c) {
        if (c == null) {
            return new String[0];
        }
        ClassMap classMap = this.getMap(c);
        return classMap.getMethodNames();
    }

    public Method[] getMethods(Class<?> c, String methodName) {
        if (c == null) {
            return null;
        }
        ClassMap classMap = this.getMap(c);
        return classMap.getMethods(methodName);
    }

    public Constructor<?> getConstructor(MethodKey key) {
        return this.getConstructor(null, key);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Constructor<?> getConstructor(Class<?> c, MethodKey key) {
        Constructor<?> ctor;
        this.lock.readLock().lock();
        try {
            ctor = this.constructorsMap.get(key);
            if (ctor != null) {
                Constructor<?> constructor = CTOR_MISS.equals(ctor) ? null : ctor;
                return constructor;
            }
        }
        finally {
            this.lock.readLock().unlock();
        }
        this.lock.writeLock().lock();
        try {
            ctor = this.constructorsMap.get(key);
            if (ctor != null) {
                Constructor<?> constructor = CTOR_MISS.equals(ctor) ? null : ctor;
                return constructor;
            }
            String cname = key.getMethod();
            Class<?> clazz = this.constructibleClasses.get(cname);
            try {
                if (clazz == null) {
                    clazz = c != null && c.getName().equals(key.getMethod()) ? c : this.loader.loadClass(cname);
                    this.constructibleClasses.put(cname, clazz);
                }
                ArrayList l = new ArrayList();
                for (Constructor<?> ictor : clazz.getConstructors()) {
                    if (!this.permissions.allow(ictor)) continue;
                    l.add(ictor);
                }
                ctor = key.getMostSpecificConstructor(l.toArray(new Constructor[l.size()]));
                if (ctor != null) {
                    this.constructorsMap.put(key, ctor);
                } else {
                    this.constructorsMap.put(key, CTOR_MISS);
                }
            }
            catch (ClassNotFoundException xnotfound) {
                if (this.logger != null && this.logger.isDebugEnabled()) {
                    this.logger.debug((Object)("unable to find class: " + cname + "." + key.debugString()), (Throwable)xnotfound);
                }
                ctor = null;
            }
            catch (MethodKey.AmbiguousException xambiguous) {
                if (this.logger != null && xambiguous.isSevere() && this.logger.isInfoEnabled()) {
                    this.logger.info((Object)("ambiguous constructor invocation: " + cname + "." + key.debugString()), (Throwable)xambiguous);
                }
                ctor = null;
            }
            Constructor<?> constructor = ctor;
            return constructor;
        }
        finally {
            this.lock.writeLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private ClassMap getMap(Class<?> c) {
        ClassMap classMap;
        this.lock.readLock().lock();
        try {
            classMap = this.classMethodMaps.get(c);
        }
        finally {
            this.lock.readLock().unlock();
        }
        if (classMap == null) {
            this.lock.writeLock().lock();
            try {
                classMap = this.classMethodMaps.get(c);
                if (classMap == null) {
                    classMap = new ClassMap(c, this.permissions, this.logger);
                    this.classMethodMaps.put(c, classMap);
                }
            }
            finally {
                this.lock.writeLock().unlock();
            }
        }
        return classMap;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setLoader(ClassLoader cloader) {
        ClassLoader previous = this.loader;
        if (cloader == null) {
            cloader = this.getClass().getClassLoader();
        }
        if (!cloader.equals(this.loader)) {
            this.lock.writeLock().lock();
            try {
                Iterator<Map.Entry<MethodKey, Constructor<?>>> mentries = this.constructorsMap.entrySet().iterator();
                while (mentries.hasNext()) {
                    Map.Entry<MethodKey, Constructor<?>> entry = mentries.next();
                    Class<?> clazz = entry.getValue().getDeclaringClass();
                    if (!Introspector.isLoadedBy(previous, clazz)) continue;
                    mentries.remove();
                    this.constructibleClasses.remove(entry.getKey().getMethod());
                }
                Iterator<Map.Entry<Class<?>, ClassMap>> centries = this.classMethodMaps.entrySet().iterator();
                while (centries.hasNext()) {
                    Map.Entry<Class<?>, ClassMap> entry = centries.next();
                    Class<?> clazz = entry.getKey();
                    if (!Introspector.isLoadedBy(previous, clazz)) continue;
                    centries.remove();
                }
                this.loader = cloader;
            }
            finally {
                this.lock.writeLock().unlock();
            }
        }
    }

    public ClassLoader getLoader() {
        return this.loader;
    }

    private static boolean isLoadedBy(ClassLoader loader, Class<?> clazz) {
        if (loader != null) {
            for (ClassLoader cloader = clazz.getClassLoader(); cloader != null; cloader = cloader.getParent()) {
                if (!cloader.equals(loader)) continue;
                return true;
            }
        }
        return false;
    }

    private static class CacheMiss {
    }
}

