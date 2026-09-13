/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collection;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.InterceptorContext;
import org.glassfish.jersey.internal.PropertiesDelegate;
import org.glassfish.jersey.message.internal.TracingLogger;

abstract class InterceptorExecutor<T>
implements InterceptorContext,
PropertiesDelegate {
    private final PropertiesDelegate propertiesDelegate;
    private Annotation[] annotations;
    private Class<?> type;
    private Type genericType;
    private MediaType mediaType;
    private final TracingLogger tracingLogger;
    private InterceptorTimestampPair<T> lastTracedInterceptor;

    public InterceptorExecutor(Class<?> rawType, Type type, Annotation[] annotations, MediaType mediaType, PropertiesDelegate propertiesDelegate) {
        this.type = rawType;
        this.genericType = type;
        this.annotations = annotations;
        this.mediaType = mediaType;
        this.propertiesDelegate = propertiesDelegate;
        this.tracingLogger = TracingLogger.getInstance(propertiesDelegate);
    }

    @Override
    public Object getProperty(String name) {
        return this.propertiesDelegate.getProperty(name);
    }

    @Override
    public Collection<String> getPropertyNames() {
        return this.propertiesDelegate.getPropertyNames();
    }

    @Override
    public void setProperty(String name, Object object) {
        this.propertiesDelegate.setProperty(name, object);
    }

    @Override
    public void removeProperty(String name) {
        this.propertiesDelegate.removeProperty(name);
    }

    protected final TracingLogger getTracingLogger() {
        return this.tracingLogger;
    }

    protected final void traceBefore(T interceptor, TracingLogger.Event event) {
        if (this.tracingLogger.isLogEnabled(event)) {
            if (this.lastTracedInterceptor != null && interceptor != null) {
                this.tracingLogger.logDuration(event, ((InterceptorTimestampPair)this.lastTracedInterceptor).getTimestamp(), ((InterceptorTimestampPair)this.lastTracedInterceptor).getInterceptor());
            }
            this.lastTracedInterceptor = new InterceptorTimestampPair(interceptor, System.nanoTime());
        }
    }

    protected final void traceAfter(T interceptor, TracingLogger.Event event) {
        if (this.tracingLogger.isLogEnabled(event)) {
            if (this.lastTracedInterceptor != null && ((InterceptorTimestampPair)this.lastTracedInterceptor).getInterceptor() != null) {
                this.tracingLogger.logDuration(event, ((InterceptorTimestampPair)this.lastTracedInterceptor).getTimestamp(), interceptor);
            }
            this.lastTracedInterceptor = new InterceptorTimestampPair(interceptor, System.nanoTime());
        }
    }

    protected final void clearLastTracedInterceptor() {
        this.lastTracedInterceptor = null;
    }

    @Override
    public Annotation[] getAnnotations() {
        return this.annotations;
    }

    @Override
    public void setAnnotations(Annotation[] annotations) {
        if (annotations == null) {
            throw new NullPointerException("Annotations must not be null.");
        }
        this.annotations = annotations;
    }

    public Class getType() {
        return this.type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    @Override
    public Type getGenericType() {
        return this.genericType;
    }

    @Override
    public void setGenericType(Type genericType) {
        this.genericType = genericType;
    }

    @Override
    public MediaType getMediaType() {
        return this.mediaType;
    }

    @Override
    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    private static class InterceptorTimestampPair<T> {
        private final T interceptor;
        private final long timestamp;

        private InterceptorTimestampPair(T interceptor, long timestamp) {
            this.interceptor = interceptor;
            this.timestamp = timestamp;
        }

        private T getInterceptor() {
            return this.interceptor;
        }

        private long getTimestamp() {
            return this.timestamp;
        }
    }
}

