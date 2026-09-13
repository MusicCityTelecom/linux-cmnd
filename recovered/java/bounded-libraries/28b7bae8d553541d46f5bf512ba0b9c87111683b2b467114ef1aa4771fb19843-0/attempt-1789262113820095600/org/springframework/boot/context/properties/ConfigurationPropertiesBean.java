/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.aop.support.AopUtils
 *  org.springframework.beans.factory.NoSuchBeanDefinitionException
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.beans.factory.support.RootBeanDefinition
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.core.ResolvableType
 *  org.springframework.core.annotation.MergedAnnotation
 *  org.springframework.core.annotation.MergedAnnotations
 *  org.springframework.core.annotation.MergedAnnotations$SearchStrategy
 *  org.springframework.util.Assert
 *  org.springframework.util.ReflectionUtils
 *  org.springframework.validation.annotation.Validated
 */
package org.springframework.boot.context.properties;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindConstructorProvider;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.annotation.Validated;

public final class ConfigurationPropertiesBean {
    private final String name;
    private final Object instance;
    private final ConfigurationProperties annotation;
    private final Bindable<?> bindTarget;
    private final BindMethod bindMethod;

    private ConfigurationPropertiesBean(String name, Object instance, ConfigurationProperties annotation, Bindable<?> bindTarget) {
        this.name = name;
        this.instance = instance;
        this.annotation = annotation;
        this.bindTarget = bindTarget;
        this.bindMethod = BindMethod.forType(bindTarget.getType().resolve());
    }

    public String getName() {
        return this.name;
    }

    public Object getInstance() {
        return this.instance;
    }

    Class<?> getType() {
        return this.bindTarget.getType().resolve();
    }

    public BindMethod getBindMethod() {
        return this.bindMethod;
    }

    public ConfigurationProperties getAnnotation() {
        return this.annotation;
    }

    public Bindable<?> asBindTarget() {
        return this.bindTarget;
    }

    public static Map<String, ConfigurationPropertiesBean> getAll(ApplicationContext applicationContext) {
        Assert.notNull((Object)applicationContext, (String)"ApplicationContext must not be null");
        if (applicationContext instanceof ConfigurableApplicationContext) {
            return ConfigurationPropertiesBean.getAll((ConfigurableApplicationContext)applicationContext);
        }
        LinkedHashMap<String, ConfigurationPropertiesBean> propertiesBeans = new LinkedHashMap<String, ConfigurationPropertiesBean>();
        applicationContext.getBeansWithAnnotation(ConfigurationProperties.class).forEach((beanName, bean) -> {
            ConfigurationPropertiesBean propertiesBean = ConfigurationPropertiesBean.get(applicationContext, bean, beanName);
            if (propertiesBean != null) {
                propertiesBeans.put((String)beanName, propertiesBean);
            }
        });
        return propertiesBeans;
    }

    private static Map<String, ConfigurationPropertiesBean> getAll(ConfigurableApplicationContext applicationContext) {
        LinkedHashMap<String, ConfigurationPropertiesBean> propertiesBeans = new LinkedHashMap<String, ConfigurationPropertiesBean>();
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        Iterator beanNames = beanFactory.getBeanNamesIterator();
        while (beanNames.hasNext()) {
            String beanName = (String)beanNames.next();
            if (!ConfigurationPropertiesBean.isConfigurationPropertiesBean(beanFactory, beanName)) continue;
            try {
                Object bean = beanFactory.getBean(beanName);
                ConfigurationPropertiesBean propertiesBean = ConfigurationPropertiesBean.get((ApplicationContext)applicationContext, bean, beanName);
                if (propertiesBean == null) continue;
                propertiesBeans.put(beanName, propertiesBean);
            }
            catch (Exception exception) {}
        }
        return propertiesBeans;
    }

    private static boolean isConfigurationPropertiesBean(ConfigurableListableBeanFactory beanFactory, String beanName) {
        try {
            if (beanFactory.getBeanDefinition(beanName).isAbstract()) {
                return false;
            }
            if (beanFactory.findAnnotationOnBean(beanName, ConfigurationProperties.class) != null) {
                return true;
            }
            Method factoryMethod = ConfigurationPropertiesBean.findFactoryMethod(beanFactory, beanName);
            return ConfigurationPropertiesBean.findMergedAnnotation(factoryMethod, ConfigurationProperties.class).isPresent();
        }
        catch (NoSuchBeanDefinitionException ex) {
            return false;
        }
    }

    public static ConfigurationPropertiesBean get(ApplicationContext applicationContext, Object bean, String beanName) {
        Method factoryMethod = ConfigurationPropertiesBean.findFactoryMethod(applicationContext, beanName);
        return ConfigurationPropertiesBean.create(beanName, bean, bean.getClass(), factoryMethod);
    }

    private static Method findFactoryMethod(ApplicationContext applicationContext, String beanName) {
        if (applicationContext instanceof ConfigurableApplicationContext) {
            return ConfigurationPropertiesBean.findFactoryMethod((ConfigurableApplicationContext)applicationContext, beanName);
        }
        return null;
    }

    private static Method findFactoryMethod(ConfigurableApplicationContext applicationContext, String beanName) {
        return ConfigurationPropertiesBean.findFactoryMethod(applicationContext.getBeanFactory(), beanName);
    }

    private static Method findFactoryMethod(ConfigurableListableBeanFactory beanFactory, String beanName) {
        if (beanFactory.containsBeanDefinition(beanName)) {
            Method resolvedFactoryMethod;
            BeanDefinition beanDefinition = beanFactory.getMergedBeanDefinition(beanName);
            if (beanDefinition instanceof RootBeanDefinition && (resolvedFactoryMethod = ((RootBeanDefinition)beanDefinition).getResolvedFactoryMethod()) != null) {
                return resolvedFactoryMethod;
            }
            return ConfigurationPropertiesBean.findFactoryMethodUsingReflection(beanFactory, beanDefinition);
        }
        return null;
    }

    private static Method findFactoryMethodUsingReflection(ConfigurableListableBeanFactory beanFactory, BeanDefinition beanDefinition) {
        String factoryMethodName = beanDefinition.getFactoryMethodName();
        String factoryBeanName = beanDefinition.getFactoryBeanName();
        if (factoryMethodName == null || factoryBeanName == null) {
            return null;
        }
        Class factoryType = beanFactory.getType(factoryBeanName);
        if (factoryType.getName().contains("$$")) {
            factoryType = factoryType.getSuperclass();
        }
        AtomicReference factoryMethod = new AtomicReference();
        ReflectionUtils.doWithMethods(factoryType, method -> {
            if (method.getName().equals(factoryMethodName)) {
                factoryMethod.set(method);
            }
        });
        return (Method)factoryMethod.get();
    }

    static ConfigurationPropertiesBean forValueObject(Class<?> beanClass, String beanName) {
        ConfigurationPropertiesBean propertiesBean = ConfigurationPropertiesBean.create(beanName, null, beanClass, null);
        Assert.state((propertiesBean != null && propertiesBean.getBindMethod() == BindMethod.VALUE_OBJECT ? 1 : 0) != 0, () -> "Bean '" + beanName + "' is not a @ConfigurationProperties value object");
        return propertiesBean;
    }

    private static ConfigurationPropertiesBean create(String name, Object instance, Class<?> type, Method factory) {
        Annotation[] annotationArray;
        ConfigurationProperties annotation = ConfigurationPropertiesBean.findAnnotation(instance, type, factory, ConfigurationProperties.class);
        if (annotation == null) {
            return null;
        }
        Validated validated = ConfigurationPropertiesBean.findAnnotation(instance, type, factory, Validated.class);
        if (validated != null) {
            Annotation[] annotationArray2 = new Annotation[2];
            annotationArray2[0] = annotation;
            annotationArray = annotationArray2;
            annotationArray2[1] = validated;
        } else {
            Annotation[] annotationArray3 = new Annotation[1];
            annotationArray = annotationArray3;
            annotationArray3[0] = annotation;
        }
        Annotation[] annotations = annotationArray;
        ResolvableType bindType = factory != null ? ResolvableType.forMethodReturnType((Method)factory) : ResolvableType.forClass(type);
        Bindable<Object> bindTarget = Bindable.of(bindType).withAnnotations(annotations);
        if (instance != null) {
            bindTarget = bindTarget.withExistingValue(instance);
        }
        return new ConfigurationPropertiesBean(name, instance, annotation, bindTarget);
    }

    private static <A extends Annotation> A findAnnotation(Object instance, Class<?> type, Method factory, Class<A> annotationType) {
        MergedAnnotation annotation = MergedAnnotation.missing();
        if (factory != null) {
            annotation = ConfigurationPropertiesBean.findMergedAnnotation(factory, annotationType);
        }
        if (!annotation.isPresent()) {
            annotation = ConfigurationPropertiesBean.findMergedAnnotation(type, annotationType);
        }
        if (!annotation.isPresent() && AopUtils.isAopProxy((Object)instance)) {
            annotation = MergedAnnotations.from((AnnotatedElement)AopUtils.getTargetClass((Object)instance), (MergedAnnotations.SearchStrategy)MergedAnnotations.SearchStrategy.TYPE_HIERARCHY).get(annotationType);
        }
        return (A)(annotation.isPresent() ? annotation.synthesize() : null);
    }

    private static <A extends Annotation> MergedAnnotation<A> findMergedAnnotation(AnnotatedElement element, Class<A> annotationType) {
        return element != null ? MergedAnnotations.from((AnnotatedElement)element, (MergedAnnotations.SearchStrategy)MergedAnnotations.SearchStrategy.TYPE_HIERARCHY).get(annotationType) : MergedAnnotation.missing();
    }

    public static enum BindMethod {
        JAVA_BEAN,
        VALUE_OBJECT;


        static BindMethod forType(Class<?> type) {
            return ConfigurationPropertiesBindConstructorProvider.INSTANCE.getBindConstructor(type, false) != null ? VALUE_OBJECT : JAVA_BEAN;
        }
    }
}

