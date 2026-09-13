/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.KeyDeserializer
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  org.springframework.beans.BeanUtils
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.beans.factory.HierarchicalBeanFactory
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.beans.factory.ListableBeanFactory
 *  org.springframework.core.ResolvableType
 *  org.springframework.core.annotation.MergedAnnotation
 *  org.springframework.core.annotation.MergedAnnotations
 *  org.springframework.core.annotation.MergedAnnotations$SearchStrategy
 *  org.springframework.util.Assert
 *  org.springframework.util.ObjectUtils
 */
package org.springframework.boot.jackson;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.function.BiConsumer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.HierarchicalBeanFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

public class JsonComponentModule
extends SimpleModule
implements BeanFactoryAware,
InitializingBean {
    private BeanFactory beanFactory;

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public void afterPropertiesSet() {
        this.registerJsonComponents();
    }

    public void registerJsonComponents() {
        BeanFactory beanFactory = this.beanFactory;
        while (beanFactory != null) {
            if (beanFactory instanceof ListableBeanFactory) {
                this.addJsonBeans((ListableBeanFactory)beanFactory);
            }
            beanFactory = beanFactory instanceof HierarchicalBeanFactory ? ((HierarchicalBeanFactory)beanFactory).getParentBeanFactory() : null;
        }
    }

    private void addJsonBeans(ListableBeanFactory beanFactory) {
        Map beans = beanFactory.getBeansWithAnnotation(JsonComponent.class);
        for (Object bean : beans.values()) {
            this.addJsonBean(bean);
        }
    }

    private void addJsonBean(Object bean) {
        MergedAnnotation annotation = MergedAnnotations.from(bean.getClass(), (MergedAnnotations.SearchStrategy)MergedAnnotations.SearchStrategy.TYPE_HIERARCHY).get(JsonComponent.class);
        Class[] types = annotation.getClassArray("type");
        JsonComponent.Scope scope = (JsonComponent.Scope)annotation.getEnum("scope", JsonComponent.Scope.class);
        this.addJsonBean(bean, types, scope);
    }

    private void addJsonBean(Object bean, Class<?>[] types, JsonComponent.Scope scope) {
        if (bean instanceof JsonSerializer) {
            this.addJsonSerializerBean((JsonSerializer)bean, scope, types);
        } else if (bean instanceof JsonDeserializer) {
            this.addJsonDeserializerBean((JsonDeserializer)bean, types);
        } else if (bean instanceof KeyDeserializer) {
            this.addKeyDeserializerBean((KeyDeserializer)bean, types);
        }
        for (Class<?> innerClass : bean.getClass().getDeclaredClasses()) {
            if (!this.isSuitableInnerClass(innerClass)) continue;
            Object innerInstance = BeanUtils.instantiateClass(innerClass);
            this.addJsonBean(innerInstance, types, scope);
        }
    }

    private boolean isSuitableInnerClass(Class<?> innerClass) {
        return !Modifier.isAbstract(innerClass.getModifiers()) && (JsonSerializer.class.isAssignableFrom(innerClass) || JsonDeserializer.class.isAssignableFrom(innerClass) || KeyDeserializer.class.isAssignableFrom(innerClass));
    }

    private <T> void addJsonSerializerBean(JsonSerializer<T> serializer, JsonComponent.Scope scope, Class<?>[] types) {
        Class baseType = ResolvableType.forClass(JsonSerializer.class, serializer.getClass()).resolveGeneric(new int[0]);
        this.addBeanToModule(serializer, baseType, types, scope == JsonComponent.Scope.VALUES ? (arg_0, arg_1) -> ((JsonComponentModule)this).addSerializer(arg_0, arg_1) : (arg_0, arg_1) -> ((JsonComponentModule)this).addKeySerializer(arg_0, arg_1));
    }

    private <T> void addJsonDeserializerBean(JsonDeserializer<T> deserializer, Class<?>[] types) {
        Class baseType = ResolvableType.forClass(JsonDeserializer.class, deserializer.getClass()).resolveGeneric(new int[0]);
        this.addBeanToModule(deserializer, baseType, types, (arg_0, arg_1) -> ((JsonComponentModule)this).addDeserializer(arg_0, arg_1));
    }

    private void addKeyDeserializerBean(KeyDeserializer deserializer, Class<?>[] types) {
        Assert.notEmpty((Object[])types, (String)"Type must be specified for KeyDeserializer");
        this.addBeanToModule(deserializer, Object.class, types, (arg_0, arg_1) -> ((JsonComponentModule)this).addKeyDeserializer(arg_0, arg_1));
    }

    private <E, T> void addBeanToModule(E element, Class<T> baseType, Class<?>[] types, BiConsumer<Class<T>, E> consumer) {
        if (ObjectUtils.isEmpty((Object[])types)) {
            consumer.accept(baseType, element);
            return;
        }
        for (Class<?> type : types) {
            Assert.isAssignable(baseType, type);
            consumer.accept(type, element);
        }
    }
}

