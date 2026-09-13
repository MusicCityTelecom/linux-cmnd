/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  org.apache.catalina.Container
 *  org.apache.catalina.Manager
 *  org.apache.catalina.SessionIdGenerator
 *  org.apache.catalina.Wrapper
 *  org.apache.catalina.core.StandardContext
 *  org.apache.catalina.core.StandardWrapper
 *  org.apache.catalina.session.ManagerBase
 *  org.springframework.util.ClassUtils
 */
package org.springframework.boot.web.embedded.tomcat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Stream;
import javax.servlet.ServletException;
import org.apache.catalina.Container;
import org.apache.catalina.Manager;
import org.apache.catalina.SessionIdGenerator;
import org.apache.catalina.Wrapper;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardWrapper;
import org.apache.catalina.session.ManagerBase;
import org.springframework.boot.web.embedded.tomcat.LazySessionIdGenerator;
import org.springframework.boot.web.embedded.tomcat.TomcatStarter;
import org.springframework.boot.web.server.WebServerException;
import org.springframework.util.ClassUtils;

class TomcatEmbeddedContext
extends StandardContext {
    private TomcatStarter starter;

    TomcatEmbeddedContext() {
    }

    public boolean loadOnStartup(Container[] children) {
        return true;
    }

    public void setManager(Manager manager) {
        if (manager instanceof ManagerBase) {
            manager.setSessionIdGenerator((SessionIdGenerator)new LazySessionIdGenerator());
        }
        super.setManager(manager);
    }

    void deferredLoadOnStartup() {
        this.doWithThreadContextClassLoader(this.getLoader().getClassLoader(), () -> this.getLoadOnStartupWrappers(this.findChildren()).forEach(this::load));
    }

    private Stream<Wrapper> getLoadOnStartupWrappers(Container[] children) {
        TreeMap<Integer, List> grouped = new TreeMap<Integer, List>();
        for (Container child : children) {
            Wrapper wrapper = (Wrapper)child;
            int order = wrapper.getLoadOnStartup();
            if (order < 0) continue;
            grouped.computeIfAbsent(order, o -> new ArrayList()).add(wrapper);
        }
        return grouped.values().stream().flatMap(Collection::stream);
    }

    private void load(Wrapper wrapper) {
        try {
            wrapper.load();
        }
        catch (ServletException ex) {
            String message = sm.getString("standardContext.loadOnStartup.loadException", new Object[]{this.getName(), wrapper.getName()});
            if (this.getComputedFailCtxIfServletStartFails()) {
                throw new WebServerException(message, ex);
            }
            this.getLogger().error((Object)message, StandardWrapper.getRootCause((ServletException)ex));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void doWithThreadContextClassLoader(ClassLoader classLoader, Runnable code) {
        ClassLoader existingLoader = classLoader != null ? ClassUtils.overrideThreadContextClassLoader((ClassLoader)classLoader) : null;
        try {
            code.run();
        }
        finally {
            if (existingLoader != null) {
                ClassUtils.overrideThreadContextClassLoader((ClassLoader)existingLoader);
            }
        }
    }

    void setStarter(TomcatStarter starter) {
        this.starter = starter;
    }

    TomcatStarter getStarter() {
        return this.starter;
    }
}

