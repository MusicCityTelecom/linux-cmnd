/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.CannotLoadBeanClassException
 *  org.springframework.beans.factory.annotation.AnnotatedBeanDefinition
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.beans.factory.support.AbstractBeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.beans.factory.support.ManagedList
 *  org.springframework.beans.factory.support.ManagedMap
 *  org.springframework.core.type.MethodMetadata
 *  org.springframework.core.type.StandardMethodMetadata
 *  org.springframework.messaging.MessageHandler
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.config;

import java.util.List;
import java.util.Map;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.CannotLoadBeanClassException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.core.type.MethodMetadata;
import org.springframework.core.type.StandardMethodMetadata;
import org.springframework.integration.annotation.IdempotentReceiver;
import org.springframework.integration.config.IdempotentReceiverAutoProxyCreator;
import org.springframework.integration.config.IntegrationConfigurationInitializer;
import org.springframework.integration.handler.advice.IdempotentReceiverInterceptor;
import org.springframework.messaging.MessageHandler;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

public class IdempotentReceiverAutoProxyCreatorInitializer
implements IntegrationConfigurationInitializer {
    public static final String IDEMPOTENT_ENDPOINTS_MAPPING = "IDEMPOTENT_ENDPOINTS_MAPPING";
    private static final String IDEMPOTENT_RECEIVER_AUTO_PROXY_CREATOR_BEAN_NAME = IdempotentReceiverAutoProxyCreator.class.getName();

    @Override
    public void initialize(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry)beanFactory;
        ManagedList idempotentEndpointsMapping = new ManagedList();
        for (String beanName : registry.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = registry.getBeanDefinition(beanName);
            if (IdempotentReceiverInterceptor.class.getName().equals(beanDefinition.getBeanClassName())) {
                String[] endpoints;
                Object value = beanDefinition.removeAttribute(IDEMPOTENT_ENDPOINTS_MAPPING);
                Assert.isInstanceOf(String.class, (Object)value, (String)"The 'mapping' of BeanDefinition 'IDEMPOTENT_ENDPOINTS_MAPPING' must be String.");
                String mapping = (String)value;
                for (String endpoint : endpoints = StringUtils.tokenizeToStringArray((String)mapping, (String)",")) {
                    ManagedMap idempotentEndpoint = new ManagedMap();
                    idempotentEndpoint.put(beanName, beanFactory.resolveEmbeddedValue(endpoint) + ".handler");
                    idempotentEndpointsMapping.add(idempotentEndpoint);
                }
                continue;
            }
            if (!(beanDefinition instanceof AnnotatedBeanDefinition)) continue;
            this.annotated(beanFactory, (List<Map<String, String>>)idempotentEndpointsMapping, beanName, beanDefinition);
        }
        if (!idempotentEndpointsMapping.isEmpty()) {
            AbstractBeanDefinition bd = BeanDefinitionBuilder.rootBeanDefinition(IdempotentReceiverAutoProxyCreator.class, IdempotentReceiverAutoProxyCreator::new).addPropertyValue("idempotentEndpointsMapping", (Object)idempotentEndpointsMapping).getBeanDefinition();
            registry.registerBeanDefinition(IDEMPOTENT_RECEIVER_AUTO_PROXY_CREATOR_BEAN_NAME, (BeanDefinition)bd);
        }
    }

    private void annotated(ConfigurableListableBeanFactory beanFactory, List<Map<String, String>> idempotentEndpointsMapping, String beanName, BeanDefinition beanDefinition) throws LinkageError {
        Object value;
        String annotationType;
        MethodMetadata beanMethod;
        if (beanDefinition.getSource() instanceof MethodMetadata && (beanMethod = (MethodMetadata)beanDefinition.getSource()).isAnnotated(annotationType = IdempotentReceiver.class.getName()) && (value = beanMethod.getAnnotationAttributes(annotationType).get("value")) != null) {
            String[] interceptors;
            Class returnType;
            if (beanMethod instanceof StandardMethodMetadata) {
                returnType = ((StandardMethodMetadata)beanMethod).getIntrospectedMethod().getReturnType();
            } else {
                try {
                    returnType = ClassUtils.forName((String)beanMethod.getReturnTypeName(), (ClassLoader)beanFactory.getBeanClassLoader());
                }
                catch (ClassNotFoundException e) {
                    throw new CannotLoadBeanClassException(beanDefinition.getDescription(), beanName, beanMethod.getReturnTypeName(), e);
                }
            }
            String endpoint = beanName;
            if (!MessageHandler.class.isAssignableFrom(returnType)) {
                endpoint = beanDefinition.getFactoryBeanName() + "." + beanName + ".*" + ".handler";
            }
            for (String interceptor : interceptors = (String[])value) {
                ManagedMap idempotentEndpoint = new ManagedMap();
                idempotentEndpoint.put(interceptor, endpoint);
                idempotentEndpointsMapping.add((Map<String, String>)idempotentEndpoint);
            }
        }
    }
}

