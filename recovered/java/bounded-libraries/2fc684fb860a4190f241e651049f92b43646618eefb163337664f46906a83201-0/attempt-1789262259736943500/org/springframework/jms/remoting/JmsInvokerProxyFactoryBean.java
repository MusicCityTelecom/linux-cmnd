/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aopalliance.intercept.Interceptor
 *  org.springframework.aop.framework.ProxyFactory
 *  org.springframework.beans.factory.BeanClassLoaderAware
 *  org.springframework.beans.factory.FactoryBean
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 */
package org.springframework.jms.remoting;

import org.aopalliance.intercept.Interceptor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.jms.remoting.JmsInvokerClientInterceptor;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

@Deprecated
public class JmsInvokerProxyFactoryBean
extends JmsInvokerClientInterceptor
implements FactoryBean<Object>,
BeanClassLoaderAware {
    @Nullable
    private Class<?> serviceInterface;
    @Nullable
    private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();
    @Nullable
    private Object serviceProxy;

    public void setServiceInterface(Class<?> serviceInterface) {
        Assert.notNull(serviceInterface, (String)"'serviceInterface' must not be null");
        Assert.isTrue((boolean)serviceInterface.isInterface(), (String)"'serviceInterface' must be an interface");
        this.serviceInterface = serviceInterface;
    }

    public void setBeanClassLoader(ClassLoader classLoader) {
        this.beanClassLoader = classLoader;
    }

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        Assert.notNull(this.serviceInterface, (String)"Property 'serviceInterface' is required");
        this.serviceProxy = new ProxyFactory(this.serviceInterface, (Interceptor)this).getProxy(this.beanClassLoader);
    }

    @Nullable
    public Object getObject() {
        return this.serviceProxy;
    }

    public Class<?> getObjectType() {
        return this.serviceInterface;
    }

    public boolean isSingleton() {
        return true;
    }
}

