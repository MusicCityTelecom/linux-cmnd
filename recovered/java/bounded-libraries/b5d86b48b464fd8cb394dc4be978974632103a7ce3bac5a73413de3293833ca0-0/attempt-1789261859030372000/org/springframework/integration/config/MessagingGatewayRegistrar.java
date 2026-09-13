/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanDefinitionStoreException
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.BeanDefinitionHolder
 *  org.springframework.beans.factory.config.ConfigurableBeanFactory
 *  org.springframework.beans.factory.support.AbstractBeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.support.BeanDefinitionReaderUtils
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.beans.factory.support.ManagedMap
 *  org.springframework.beans.factory.support.RootBeanDefinition
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.context.annotation.ImportBeanDefinitionRegistrar
 *  org.springframework.core.type.AnnotationMetadata
 *  org.springframework.expression.common.LiteralExpression
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.MultiValueMap
 *  org.springframework.util.ObjectUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.config;

import java.beans.Introspector;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.config.ExpressionFactoryBean;
import org.springframework.integration.gateway.GatewayMethodMetadata;
import org.springframework.integration.gateway.GatewayProxyFactoryBean;
import org.springframework.integration.gateway.RequestReplyExchanger;
import org.springframework.integration.util.MessagingAnnotationUtils;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class MessagingGatewayRegistrar
implements ImportBeanDefinitionRegistrar {
    private static final String PROXY_DEFAULT_METHODS_ATTR = "proxyDefaultMethods";

    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        if (importingClassMetadata != null && importingClassMetadata.isAnnotated(MessagingGateway.class.getName())) {
            Assert.isTrue((boolean)importingClassMetadata.isInterface(), (String)"@MessagingGateway can only be specified on an interface");
            List<MultiValueMap<String, Object>> valuesHierarchy = MessagingGatewayRegistrar.captureMetaAnnotationValues(importingClassMetadata);
            Map annotationAttributes = importingClassMetadata.getAnnotationAttributes(MessagingGateway.class.getName());
            MessagingGatewayRegistrar.replaceEmptyOverrides(valuesHierarchy, annotationAttributes);
            annotationAttributes.put("serviceInterface", importingClassMetadata.getClassName());
            annotationAttributes.put(PROXY_DEFAULT_METHODS_ATTR, "" + annotationAttributes.remove(PROXY_DEFAULT_METHODS_ATTR));
            BeanDefinitionReaderUtils.registerBeanDefinition((BeanDefinitionHolder)this.parse(annotationAttributes, registry), (BeanDefinitionRegistry)registry);
        }
    }

    public BeanDefinitionHolder parse(Map<String, Object> gatewayAttributes, BeanDefinitionRegistry registry) {
        String defaultPayloadExpression = (String)gatewayAttributes.get("defaultPayloadExpression");
        Object[] defaultHeaders = (Map[])gatewayAttributes.get("defaultHeaders");
        String defaultRequestChannel = (String)gatewayAttributes.get("defaultRequestChannel");
        String defaultReplyChannel = (String)gatewayAttributes.get("defaultReplyChannel");
        String errorChannel = (String)gatewayAttributes.get("errorChannel");
        String asyncExecutor = (String)gatewayAttributes.get("asyncExecutor");
        String mapper = (String)gatewayAttributes.get("mapper");
        String proxyDefaultMethods = (String)gatewayAttributes.get(PROXY_DEFAULT_METHODS_ATTR);
        boolean hasMapper = StringUtils.hasText((String)mapper);
        boolean hasDefaultPayloadExpression = StringUtils.hasText((String)defaultPayloadExpression);
        Assert.state((!hasMapper || !hasDefaultPayloadExpression ? 1 : 0) != 0, (String)"'defaultPayloadExpression' is not allowed when a 'mapper' is provided");
        boolean hasDefaultHeaders = !ObjectUtils.isEmpty((Object[])defaultHeaders);
        Assert.state((!hasMapper || !hasDefaultHeaders ? 1 : 0) != 0, (String)"'defaultHeaders' are not allowed when a 'mapper' is provided");
        ConfigurableBeanFactory beanFactory = MessagingGatewayRegistrar.obtainBeanFactory(registry);
        Class<?> serviceInterface = MessagingGatewayRegistrar.getServiceInterface((String)gatewayAttributes.get("serviceInterface"), beanFactory);
        BeanDefinitionBuilder gatewayProxyBuilder = BeanDefinitionBuilder.genericBeanDefinition(GatewayProxyFactoryBean.class, () -> new GatewayProxyFactoryBean(serviceInterface));
        if (hasDefaultHeaders || hasDefaultPayloadExpression) {
            BeanDefinitionBuilder methodMetadataBuilder = BeanDefinitionBuilder.genericBeanDefinition(GatewayMethodMetadata.class, GatewayMethodMetadata::new);
            if (hasDefaultPayloadExpression) {
                methodMetadataBuilder.addPropertyValue("payloadExpression", (Object)BeanDefinitionBuilder.genericBeanDefinition(ExpressionFactoryBean.class).addConstructorArgValue((Object)defaultPayloadExpression).getBeanDefinition());
            }
            if (hasDefaultHeaders) {
                ManagedMap headerExpressions = new ManagedMap();
                for (Object header : defaultHeaders) {
                    String headerValue = (String)header.get("value");
                    String headerExpression = (String)header.get("expression");
                    boolean hasValue = StringUtils.hasText((String)headerValue);
                    if (hasValue == StringUtils.hasText((String)headerExpression)) {
                        throw new BeanDefinitionStoreException("exactly one of 'value' or 'expression' is required on a gateway's header.");
                    }
                    RootBeanDefinition expressionDef = new RootBeanDefinition(hasValue ? LiteralExpression.class : ExpressionFactoryBean.class);
                    expressionDef.getConstructorArgumentValues().addGenericArgumentValue((Object)(hasValue ? headerValue : headerExpression));
                    headerExpressions.put((String)header.get("name"), expressionDef);
                }
                methodMetadataBuilder.addPropertyValue("headerExpressions", (Object)headerExpressions);
            }
            gatewayProxyBuilder.addPropertyValue("globalMethodMetadata", (Object)methodMetadataBuilder.getBeanDefinition());
        }
        if (StringUtils.hasText((String)defaultRequestChannel)) {
            gatewayProxyBuilder.addPropertyValue("defaultRequestChannelName", (Object)defaultRequestChannel);
        }
        if (StringUtils.hasText((String)defaultReplyChannel)) {
            gatewayProxyBuilder.addPropertyValue("defaultReplyChannelName", (Object)defaultReplyChannel);
        }
        if (StringUtils.hasText((String)errorChannel)) {
            gatewayProxyBuilder.addPropertyValue("errorChannelName", (Object)errorChannel);
        }
        if (asyncExecutor == null || "__NULL__".equals(asyncExecutor)) {
            gatewayProxyBuilder.addPropertyValue("asyncExecutor", null);
        } else if (StringUtils.hasText((String)asyncExecutor)) {
            gatewayProxyBuilder.addPropertyReference("asyncExecutor", asyncExecutor);
        }
        if (StringUtils.hasText((String)mapper)) {
            gatewayProxyBuilder.addPropertyReference("mapper", mapper);
        }
        if (StringUtils.hasText((String)proxyDefaultMethods)) {
            gatewayProxyBuilder.addPropertyValue(PROXY_DEFAULT_METHODS_ATTR, (Object)proxyDefaultMethods);
        }
        gatewayProxyBuilder.addPropertyValue("defaultRequestTimeoutExpressionString", gatewayAttributes.get("defaultRequestTimeout"));
        gatewayProxyBuilder.addPropertyValue("defaultReplyTimeoutExpressionString", gatewayAttributes.get("defaultReplyTimeout"));
        gatewayProxyBuilder.addPropertyValue("methodMetadataMap", gatewayAttributes.get("methods"));
        String id = (String)gatewayAttributes.get("name");
        if (!StringUtils.hasText((String)id)) {
            String serviceInterfaceName = serviceInterface.getName();
            id = Introspector.decapitalize(serviceInterfaceName.substring(serviceInterfaceName.lastIndexOf(46) + 1));
        }
        gatewayProxyBuilder.addConstructorArgValue(serviceInterface);
        AbstractBeanDefinition beanDefinition = gatewayProxyBuilder.getBeanDefinition();
        beanDefinition.setAttribute("factoryBeanObjectType", serviceInterface);
        return new BeanDefinitionHolder((BeanDefinition)beanDefinition, id);
    }

    private static List<MultiValueMap<String, Object>> captureMetaAnnotationValues(AnnotationMetadata importingClassMetadata) {
        Set directAnnotations = importingClassMetadata.getAnnotationTypes();
        ArrayList<MultiValueMap<String, Object>> valuesHierarchy = new ArrayList<MultiValueMap<String, Object>>();
        for (String ann : directAnnotations) {
            Set chain = importingClassMetadata.getMetaAnnotationTypes(ann);
            if (!chain.contains(MessagingGateway.class.getName())) continue;
            for (String meta : chain) {
                MultiValueMap attributes = importingClassMetadata.getAllAnnotationAttributes(meta);
                if (attributes == null) continue;
                valuesHierarchy.add((MultiValueMap<String, Object>)attributes);
            }
        }
        return valuesHierarchy;
    }

    private static void replaceEmptyOverrides(List<MultiValueMap<String, Object>> valuesHierarchy, Map<String, Object> annotationAttributes) {
        block0: for (Map.Entry<String, Object> entry : annotationAttributes.entrySet()) {
            Object value = entry.getValue();
            if (MessagingAnnotationUtils.hasValue(value)) continue;
            for (MultiValueMap<String, Object> metaAttributesMap : valuesHierarchy) {
                Object newValue = metaAttributesMap.getFirst((Object)entry.getKey());
                if (!MessagingAnnotationUtils.hasValue(newValue)) continue;
                annotationAttributes.put(entry.getKey(), newValue);
                continue block0;
            }
        }
    }

    private static ConfigurableBeanFactory obtainBeanFactory(BeanDefinitionRegistry registry) {
        if (registry instanceof ConfigurableBeanFactory) {
            return (ConfigurableBeanFactory)registry;
        }
        if (registry instanceof ConfigurableApplicationContext) {
            return ((ConfigurableApplicationContext)registry).getBeanFactory();
        }
        throw new IllegalArgumentException("The provided 'BeanDefinitionRegistry' must be an instance of 'ConfigurableBeanFactory' or 'ConfigurableApplicationContext', but given is: " + registry.getClass());
    }

    private static Class<?> getServiceInterface(String serviceInterface, ConfigurableBeanFactory beanFactory) {
        String actualServiceInterface = beanFactory.resolveEmbeddedValue(serviceInterface);
        if (!StringUtils.hasText((String)actualServiceInterface)) {
            return RequestReplyExchanger.class;
        }
        try {
            return ClassUtils.forName((String)actualServiceInterface, (ClassLoader)beanFactory.getBeanClassLoader());
        }
        catch (ClassNotFoundException ex) {
            throw new BeanDefinitionStoreException("Cannot parse class for service interface", (Throwable)ex);
        }
    }
}

