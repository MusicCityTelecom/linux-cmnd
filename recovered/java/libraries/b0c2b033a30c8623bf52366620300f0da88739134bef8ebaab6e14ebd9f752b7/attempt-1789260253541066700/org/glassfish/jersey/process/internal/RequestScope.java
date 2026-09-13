/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.process.internal;

import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.jersey.internal.BootstrapBag;
import org.glassfish.jersey.internal.BootstrapConfigurator;
import org.glassfish.jersey.internal.Errors;
import org.glassfish.jersey.internal.guava.Preconditions;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.internal.util.ExtendedLogger;
import org.glassfish.jersey.internal.util.Producer;
import org.glassfish.jersey.process.internal.RequestContext;

public abstract class RequestScope {
    private static final ExtendedLogger logger = new ExtendedLogger(Logger.getLogger(RequestScope.class.getName()), Level.FINEST);
    private final ThreadLocal<RequestContext> currentRequestContext = new ThreadLocal();
    private volatile boolean isActive = true;

    public boolean isActive() {
        return this.isActive;
    }

    public void shutdown() {
        this.isActive = false;
    }

    public RequestContext referenceCurrent() throws IllegalStateException {
        return this.current().getReference();
    }

    public RequestContext current() {
        Preconditions.checkState(this.isActive, "Request scope has been already shut down.");
        RequestContext scopeInstance = this.currentRequestContext.get();
        Preconditions.checkState(scopeInstance != null, "Not inside a request scope.");
        return scopeInstance;
    }

    private RequestContext retrieveCurrent() {
        Preconditions.checkState(this.isActive, "Request scope has been already shut down.");
        return this.currentRequestContext.get();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public RequestContext suspendCurrent() {
        RequestContext requestContext;
        RequestContext context = this.retrieveCurrent();
        if (context == null) {
            return null;
        }
        try {
            RequestContext referencedContext = context.getReference();
            this.suspend(referencedContext);
            requestContext = referencedContext;
        }
        catch (Throwable throwable) {
            logger.debugLog("Returned a new reference of the request scope context {0}", context);
            throw throwable;
        }
        logger.debugLog("Returned a new reference of the request scope context {0}", context);
        return requestContext;
    }

    protected void suspend(RequestContext context) {
    }

    public abstract RequestContext createContext();

    protected void activate(RequestContext context, RequestContext oldContext) {
        Preconditions.checkState(this.isActive, "Request scope has been already shut down.");
        this.currentRequestContext.set(context);
    }

    protected void resume(RequestContext context) {
        this.currentRequestContext.set(context);
    }

    protected void release(RequestContext context) {
        context.release();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void runInScope(RequestContext context, Runnable task) {
        RequestContext oldContext = this.retrieveCurrent();
        try {
            this.activate(context.getReference(), oldContext);
            Errors.process(task);
        }
        finally {
            this.release(context);
            this.resume(oldContext);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void runInScope(Runnable task) {
        RequestContext oldContext = this.retrieveCurrent();
        RequestContext context = this.createContext();
        try {
            this.activate(context, oldContext);
            Errors.process(task);
        }
        finally {
            this.release(context);
            this.resume(oldContext);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public <T> T runInScope(RequestContext context, Callable<T> task) throws Exception {
        RequestContext oldContext = this.retrieveCurrent();
        try {
            this.activate(context.getReference(), oldContext);
            T t = Errors.process(task);
            return t;
        }
        finally {
            this.release(context);
            this.resume(oldContext);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public <T> T runInScope(Callable<T> task) throws Exception {
        RequestContext oldContext = this.retrieveCurrent();
        RequestContext context = this.createContext();
        try {
            this.activate(context, oldContext);
            T t = Errors.process(task);
            return t;
        }
        finally {
            this.release(context);
            this.resume(oldContext);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public <T> T runInScope(RequestContext context, Producer<T> task) {
        RequestContext oldContext = this.retrieveCurrent();
        try {
            this.activate(context.getReference(), oldContext);
            T t = Errors.process(task);
            return t;
        }
        finally {
            this.release(context);
            this.resume(oldContext);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public <T> T runInScope(Producer<T> task) {
        RequestContext oldContext = this.retrieveCurrent();
        RequestContext context = this.createContext();
        try {
            this.activate(context, oldContext);
            T t = Errors.process(task);
            return t;
        }
        finally {
            this.release(context);
            this.resume(oldContext);
        }
    }

    public static class RequestScopeConfigurator
    implements BootstrapConfigurator {
        @Override
        public void init(InjectionManager injectionManagerFactory, BootstrapBag bootstrapBag) {
        }

        @Override
        public void postInit(InjectionManager injectionManager, BootstrapBag bootstrapBag) {
            RequestScope requestScope = injectionManager.getInstance(RequestScope.class);
            bootstrapBag.setRequestScope(requestScope);
        }
    }
}

