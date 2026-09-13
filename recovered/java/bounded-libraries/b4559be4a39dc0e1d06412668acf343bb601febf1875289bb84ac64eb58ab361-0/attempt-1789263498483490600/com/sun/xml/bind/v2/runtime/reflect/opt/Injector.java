/*
 * Decompiled with CFR 0.152.
 */
package com.sun.xml.bind.v2.runtime.reflect.opt;

import com.sun.xml.bind.v2.runtime.reflect.Accessor;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.ProtectionDomain;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;

final class Injector {
    private static final ReentrantReadWriteLock irwl = new ReentrantReadWriteLock();
    private static final Lock ir = irwl.readLock();
    private static final Lock iw = irwl.writeLock();
    private static final Map<ClassLoader, WeakReference<Injector>> injectors = new WeakHashMap<ClassLoader, WeakReference<Injector>>();
    private static final Logger logger = Logger.getLogger(Injector.class.getName());
    private final Map<String, Class> classes = new HashMap<String, Class>();
    private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private final Lock r = this.rwl.readLock();
    private final Lock w = this.rwl.writeLock();
    private final ClassLoader parent;
    private final boolean loadable;
    private static Method defineClass;
    private static Method resolveClass;
    private static Method findLoadedClass;
    private static Object U;

    static Class inject(ClassLoader cl, String className, byte[] image) {
        Injector injector = Injector.get(cl);
        if (injector != null) {
            return injector.inject(className, image);
        }
        return null;
    }

    static Class find(ClassLoader cl, String className) {
        Injector injector = Injector.get(cl);
        if (injector != null) {
            return injector.find(className);
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static Injector get(ClassLoader cl) {
        WeakReference<Injector> wr;
        Injector injector = null;
        ir.lock();
        try {
            wr = injectors.get(cl);
        }
        finally {
            ir.unlock();
        }
        if (wr != null) {
            injector = (Injector)wr.get();
        }
        if (injector == null) {
            try {
                injector = new Injector(cl);
                wr = new WeakReference<Injector>(injector);
                iw.lock();
                try {
                    if (!injectors.containsKey(cl)) {
                        injectors.put(cl, wr);
                    }
                }
                finally {
                    iw.unlock();
                }
            }
            catch (SecurityException e) {
                logger.log(Level.FINE, "Unable to set up a back-door for the injector", e);
                return null;
            }
        }
        return injector;
    }

    private static Class classForNames(String ... names) throws ClassNotFoundException {
        for (String name : names) {
            try {
                return Class.forName(name);
            }
            catch (ClassNotFoundException e) {
            }
        }
        throw new ClassNotFoundException(String.format("No class found for supplied FQDNs %s", Arrays.toString(names)));
    }

    private static Method getMethod(Class<?> c, String methodname, Class<?> ... params) {
        try {
            Method m = c.getDeclaredMethod(methodname, params);
            m.setAccessible(true);
            return m;
        }
        catch (NoSuchMethodException e) {
            throw new NoSuchMethodError(e.getMessage());
        }
    }

    private Injector(ClassLoader parent) {
        this.parent = parent;
        assert (parent != null);
        boolean loadableCheck = false;
        try {
            loadableCheck = parent.loadClass(Accessor.class.getName()) == Accessor.class;
        }
        catch (ClassNotFoundException classNotFoundException) {
            // empty catch block
        }
        this.loadable = loadableCheck;
    }

    /*
     * Exception decompiling
     */
    private Class inject(String className, byte[] image) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 3 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    private Class find(String className) {
        this.r.lock();
        try {
            Class clazz = this.classes.get(className);
            return clazz;
        }
        finally {
            this.r.unlock();
        }
    }

    static {
        try {
            Method[] m = AccessController.doPrivileged(new PrivilegedAction<Method[]>(){

                @Override
                public Method[] run() {
                    return new Method[]{Injector.getMethod(ClassLoader.class, "defineClass", new Class[]{String.class, byte[].class, Integer.TYPE, Integer.TYPE}), Injector.getMethod(ClassLoader.class, "resolveClass", new Class[]{Class.class}), Injector.getMethod(ClassLoader.class, "findLoadedClass", new Class[]{String.class})};
                }
            });
            defineClass = m[0];
            resolveClass = m[1];
            findLoadedClass = m[2];
        }
        catch (Throwable t) {
            try {
                U = AccessController.doPrivileged(new PrivilegedExceptionAction(){

                    public Object run() throws Exception {
                        Class u = Injector.classForNames(new String[]{"sun.misc.Unsafe", "jdk.internal.misc.Unsafe"});
                        Field theUnsafe = u.getDeclaredField("theUnsafe");
                        theUnsafe.setAccessible(true);
                        return theUnsafe.get(null);
                    }
                });
                defineClass = AccessController.doPrivileged(new PrivilegedExceptionAction<Method>(){

                    @Override
                    public Method run() throws Exception {
                        return U.getClass().getMethod("defineClass", String.class, byte[].class, Integer.TYPE, Integer.TYPE, ClassLoader.class, ProtectionDomain.class);
                    }
                });
            }
            catch (SecurityException | PrivilegedActionException ex) {
                Logger.getLogger(Injector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

