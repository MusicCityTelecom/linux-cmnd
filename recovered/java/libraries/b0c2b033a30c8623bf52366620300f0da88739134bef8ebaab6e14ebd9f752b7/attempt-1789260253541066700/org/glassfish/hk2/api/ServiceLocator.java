/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.api;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Descriptor;
import org.glassfish.hk2.api.Filter;
import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.MethodParameter;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.api.ServiceLocatorState;
import org.glassfish.hk2.api.Unqualified;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface ServiceLocator {
    public <T> T getService(Class<T> var1, Annotation ... var2) throws MultiException;

    public <T> T getService(Type var1, Annotation ... var2) throws MultiException;

    public <T> T getService(Class<T> var1, String var2, Annotation ... var3) throws MultiException;

    public <T> T getService(Type var1, String var2, Annotation ... var3) throws MultiException;

    public <T> List<T> getAllServices(Class<T> var1, Annotation ... var2) throws MultiException;

    public <T> List<T> getAllServices(Type var1, Annotation ... var2) throws MultiException;

    public <T> List<T> getAllServices(Annotation var1, Annotation ... var2) throws MultiException;

    public List<?> getAllServices(Filter var1) throws MultiException;

    public <T> ServiceHandle<T> getServiceHandle(Class<T> var1, Annotation ... var2) throws MultiException;

    public <T> ServiceHandle<T> getServiceHandle(Type var1, Annotation ... var2) throws MultiException;

    public <T> ServiceHandle<T> getServiceHandle(Class<T> var1, String var2, Annotation ... var3) throws MultiException;

    public <T> ServiceHandle<T> getServiceHandle(Type var1, String var2, Annotation ... var3) throws MultiException;

    public <T> List<ServiceHandle<T>> getAllServiceHandles(Class<T> var1, Annotation ... var2) throws MultiException;

    public List<ServiceHandle<?>> getAllServiceHandles(Type var1, Annotation ... var2) throws MultiException;

    public List<ServiceHandle<?>> getAllServiceHandles(Annotation var1, Annotation ... var2) throws MultiException;

    public List<ServiceHandle<?>> getAllServiceHandles(Filter var1) throws MultiException;

    public List<ActiveDescriptor<?>> getDescriptors(Filter var1);

    public ActiveDescriptor<?> getBestDescriptor(Filter var1);

    public ActiveDescriptor<?> reifyDescriptor(Descriptor var1, Injectee var2) throws MultiException;

    public ActiveDescriptor<?> reifyDescriptor(Descriptor var1) throws MultiException;

    public ActiveDescriptor<?> getInjecteeDescriptor(Injectee var1) throws MultiException;

    public <T> ServiceHandle<T> getServiceHandle(ActiveDescriptor<T> var1, Injectee var2) throws MultiException;

    public <T> ServiceHandle<T> getServiceHandle(ActiveDescriptor<T> var1) throws MultiException;

    @Deprecated
    public <T> T getService(ActiveDescriptor<T> var1, ServiceHandle<?> var2) throws MultiException;

    public <T> T getService(ActiveDescriptor<T> var1, ServiceHandle<?> var2, Injectee var3) throws MultiException;

    public String getDefaultClassAnalyzerName();

    public void setDefaultClassAnalyzerName(String var1);

    public Unqualified getDefaultUnqualified();

    public void setDefaultUnqualified(Unqualified var1);

    public String getName();

    public long getLocatorId();

    public ServiceLocator getParent();

    public void shutdown();

    public ServiceLocatorState getState();

    public boolean getNeutralContextClassLoader();

    public void setNeutralContextClassLoader(boolean var1);

    public <T> T create(Class<T> var1);

    public <T> T create(Class<T> var1, String var2);

    public void inject(Object var1);

    public void inject(Object var1, String var2);

    public Object assistedInject(Object var1, Method var2, MethodParameter ... var3);

    public Object assistedInject(Object var1, Method var2, ServiceHandle<?> var3, MethodParameter ... var4);

    public void postConstruct(Object var1);

    public void postConstruct(Object var1, String var2);

    public void preDestroy(Object var1);

    public void preDestroy(Object var1, String var2);

    public <U> U createAndInitialize(Class<U> var1);

    public <U> U createAndInitialize(Class<U> var1, String var2);
}

