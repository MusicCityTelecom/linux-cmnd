/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.catalina.loader.ParallelWebappClassLoader
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.apache.tomcat.util.compat.JreCompat
 */
package org.springframework.boot.web.embedded.tomcat;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import org.apache.catalina.loader.ParallelWebappClassLoader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.util.compat.JreCompat;

public class TomcatEmbeddedWebappClassLoader
extends ParallelWebappClassLoader {
    private static final Log logger = LogFactory.getLog(TomcatEmbeddedWebappClassLoader.class);

    public TomcatEmbeddedWebappClassLoader() {
    }

    public TomcatEmbeddedWebappClassLoader(ClassLoader parent) {
        super(parent);
    }

    public URL findResource(String name) {
        return null;
    }

    public Enumeration<URL> findResources(String name) throws IOException {
        return Collections.emptyEnumeration();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        TomcatEmbeddedWebappClassLoader tomcatEmbeddedWebappClassLoader = JreCompat.isGraalAvailable() ? this : this.getClassLoadingLock(name);
        synchronized (tomcatEmbeddedWebappClassLoader) {
            Class<?> result = this.findExistingLoadedClass(name);
            Class<?> clazz = result = result != null ? result : this.doLoadClass(name);
            if (result == null) {
                throw new ClassNotFoundException(name);
            }
            return this.resolveIfNecessary(result, resolve);
        }
    }

    private Class<?> findExistingLoadedClass(String name) {
        Class resultClass = this.findLoadedClass0(name);
        resultClass = resultClass != null || JreCompat.isGraalAvailable() ? resultClass : this.findLoadedClass(name);
        return resultClass;
    }

    private Class<?> doLoadClass(String name) throws ClassNotFoundException {
        this.checkPackageAccess(name);
        if (this.delegate || this.filter(name, true)) {
            Class<?> result = this.loadFromParent(name);
            return result != null ? result : this.findClassIgnoringNotFound(name);
        }
        Class<?> result = this.findClassIgnoringNotFound(name);
        return result != null ? result : this.loadFromParent(name);
    }

    private Class<?> resolveIfNecessary(Class<?> resultClass, boolean resolve) {
        if (resolve) {
            this.resolveClass(resultClass);
        }
        return resultClass;
    }

    protected void addURL(URL url) {
        if (logger.isTraceEnabled()) {
            logger.trace((Object)("Ignoring request to add " + url + " to the tomcat classloader"));
        }
    }

    private Class<?> loadFromParent(String name) {
        if (this.parent == null) {
            return null;
        }
        try {
            return Class.forName(name, false, this.parent);
        }
        catch (ClassNotFoundException ex) {
            return null;
        }
    }

    private Class<?> findClassIgnoringNotFound(String name) {
        try {
            return this.findClass(name);
        }
        catch (ClassNotFoundException ex) {
            return null;
        }
    }

    private void checkPackageAccess(String name) throws ClassNotFoundException {
        if (this.securityManager != null && name.lastIndexOf(46) >= 0) {
            try {
                this.securityManager.checkPackageAccess(name.substring(0, name.lastIndexOf(46)));
            }
            catch (SecurityException ex) {
                throw new ClassNotFoundException("Security Violation, attempt to use Restricted Class: " + name, ex);
            }
        }
    }

    static {
        if (!JreCompat.isGraalAvailable()) {
            ClassLoader.registerAsParallelCapable();
        }
    }
}

