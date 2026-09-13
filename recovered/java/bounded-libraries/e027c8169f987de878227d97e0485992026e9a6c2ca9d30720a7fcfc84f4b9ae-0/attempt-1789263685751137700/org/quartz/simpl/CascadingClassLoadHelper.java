/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.simpl;

import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import org.quartz.simpl.InitThreadContextClassLoadHelper;
import org.quartz.simpl.LoadingLoaderClassLoadHelper;
import org.quartz.simpl.SimpleClassLoadHelper;
import org.quartz.simpl.ThreadContextClassLoadHelper;
import org.quartz.spi.ClassLoadHelper;

public class CascadingClassLoadHelper
implements ClassLoadHelper {
    private LinkedList<ClassLoadHelper> loadHelpers;
    private ClassLoadHelper bestCandidate;

    @Override
    public void initialize() {
        this.loadHelpers = new LinkedList();
        this.loadHelpers.add(new LoadingLoaderClassLoadHelper());
        this.loadHelpers.add(new SimpleClassLoadHelper());
        this.loadHelpers.add(new ThreadContextClassLoadHelper());
        this.loadHelpers.add(new InitThreadContextClassLoadHelper());
        for (ClassLoadHelper loadHelper : this.loadHelpers) {
            loadHelper.initialize();
        }
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (this.bestCandidate != null) {
            try {
                return this.bestCandidate.loadClass(name);
            }
            catch (Throwable t) {
                this.bestCandidate = null;
            }
        }
        Throwable throwable = null;
        Class<?> clazz = null;
        ClassLoadHelper loadHelper2 = null;
        for (ClassLoadHelper loadHelper2 : this.loadHelpers) {
            try {
                clazz = loadHelper2.loadClass(name);
                break;
            }
            catch (Throwable t) {
                throwable = t;
            }
        }
        if (clazz == null) {
            if (throwable instanceof ClassNotFoundException) {
                throw (ClassNotFoundException)throwable;
            }
            throw new ClassNotFoundException(String.format("Unable to load class %s by any known loaders.", name), throwable);
        }
        this.bestCandidate = loadHelper2;
        return clazz;
    }

    @Override
    public <T> Class<? extends T> loadClass(String name, Class<T> clazz) throws ClassNotFoundException {
        return this.loadClass(name);
    }

    @Override
    public URL getResource(String name) {
        URL result = null;
        if (this.bestCandidate != null) {
            result = this.bestCandidate.getResource(name);
            if (result == null) {
                this.bestCandidate = null;
            } else {
                return result;
            }
        }
        ClassLoadHelper loadHelper = null;
        Iterator iter = this.loadHelpers.iterator();
        while (iter.hasNext() && (result = (loadHelper = (ClassLoadHelper)iter.next()).getResource(name)) == null) {
        }
        this.bestCandidate = loadHelper;
        return result;
    }

    @Override
    public InputStream getResourceAsStream(String name) {
        InputStream result = null;
        if (this.bestCandidate != null) {
            result = this.bestCandidate.getResourceAsStream(name);
            if (result == null) {
                this.bestCandidate = null;
            } else {
                return result;
            }
        }
        ClassLoadHelper loadHelper = null;
        Iterator iter = this.loadHelpers.iterator();
        while (iter.hasNext() && (result = (loadHelper = (ClassLoadHelper)iter.next()).getResourceAsStream(name)) == null) {
        }
        this.bestCandidate = loadHelper;
        return result;
    }

    @Override
    public ClassLoader getClassLoader() {
        return this.bestCandidate == null ? Thread.currentThread().getContextClassLoader() : this.bestCandidate.getClassLoader();
    }
}

