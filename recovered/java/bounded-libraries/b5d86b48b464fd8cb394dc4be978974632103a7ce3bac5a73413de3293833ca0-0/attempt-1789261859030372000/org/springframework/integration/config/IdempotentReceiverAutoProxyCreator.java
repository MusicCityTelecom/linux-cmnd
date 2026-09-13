/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.aop.Pointcut
 *  org.springframework.aop.TargetSource
 *  org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator
 *  org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor
 *  org.springframework.aop.support.NameMatchMethodPointcut
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.messaging.MessageHandler
 *  org.springframework.util.Assert
 *  org.springframework.util.PatternMatchUtils
 */
package org.springframework.integration.config;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.aop.Pointcut;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.messaging.MessageHandler;
import org.springframework.util.Assert;
import org.springframework.util.PatternMatchUtils;

class IdempotentReceiverAutoProxyCreator
extends AbstractAutoProxyCreator {
    private volatile List<Map<String, String>> idempotentEndpointsMapping;
    private volatile Map<String, List<String>> idempotentEndpoints;

    IdempotentReceiverAutoProxyCreator() {
    }

    public void setIdempotentEndpointsMapping(List<Map<String, String>> idempotentEndpointsMapping) {
        Assert.notEmpty(idempotentEndpointsMapping, (String)"'idempotentEndpointsMapping' must not be empty");
        this.idempotentEndpointsMapping = idempotentEndpointsMapping;
    }

    protected Object[] getAdvicesAndAdvisorsForBean(Class<?> beanClass, String beanName, TargetSource customTargetSource) throws BeansException {
        this.initIdempotentEndpointsIfNecessary();
        if (MessageHandler.class.isAssignableFrom(beanClass)) {
            ArrayList<DefaultBeanFactoryPointcutAdvisor> interceptors = new ArrayList<DefaultBeanFactoryPointcutAdvisor>();
            for (Map.Entry<String, List<String>> entry : this.idempotentEndpoints.entrySet()) {
                List<String> mappedNames = entry.getValue();
                for (String mappedName : mappedNames) {
                    if (!this.isMatch(mappedName, beanName)) continue;
                    DefaultBeanFactoryPointcutAdvisor idempotentReceiverInterceptor = new DefaultBeanFactoryPointcutAdvisor();
                    idempotentReceiverInterceptor.setAdviceBeanName(entry.getKey());
                    NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
                    pointcut.setMappedName("handleMessage");
                    idempotentReceiverInterceptor.setPointcut((Pointcut)pointcut);
                    BeanFactory beanFactory = this.getBeanFactory();
                    if (beanFactory != null) {
                        idempotentReceiverInterceptor.setBeanFactory(beanFactory);
                    }
                    interceptors.add(idempotentReceiverInterceptor);
                }
            }
            if (!interceptors.isEmpty()) {
                return interceptors.toArray();
            }
        }
        return DO_NOT_PROXY;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void initIdempotentEndpointsIfNecessary() {
        if (this.idempotentEndpoints == null) {
            IdempotentReceiverAutoProxyCreator idempotentReceiverAutoProxyCreator = this;
            synchronized (idempotentReceiverAutoProxyCreator) {
                if (this.idempotentEndpoints == null) {
                    this.idempotentEndpoints = new LinkedHashMap<String, List<String>>();
                    for (Map<String, String> mapping : this.idempotentEndpointsMapping) {
                        Assert.isTrue((mapping.size() == 1 ? 1 : 0) != 0, (String)"The 'idempotentEndpointMapping' must be a SingletonMap");
                        String interceptor = mapping.keySet().iterator().next();
                        String endpoint = mapping.values().iterator().next();
                        Assert.hasText((String)interceptor, (String)"The 'idempotentReceiverInterceptor' can't be empty String");
                        Assert.hasText((String)endpoint, (String)"The 'idempotentReceiverEndpoint' can't be empty String");
                        List<String> endpoints = this.idempotentEndpoints.get(interceptor);
                        if (endpoints == null) {
                            endpoints = new ArrayList<String>();
                            this.idempotentEndpoints.put(interceptor, endpoints);
                        }
                        endpoints.add(endpoint);
                    }
                }
            }
        }
    }

    private boolean isMatch(String mappedName, String beanName) {
        BeanFactory beanFactory;
        boolean matched = PatternMatchUtils.simpleMatch((String)mappedName, (String)beanName);
        if (!matched && (beanFactory = this.getBeanFactory()) != null) {
            String alias;
            String[] aliases;
            String[] stringArray = aliases = beanFactory.getAliases(beanName);
            int n = stringArray.length;
            for (int i = 0; i < n && !(matched = PatternMatchUtils.simpleMatch((String)mappedName, (String)(alias = stringArray[i]))); ++i) {
            }
        }
        return matched;
    }
}

