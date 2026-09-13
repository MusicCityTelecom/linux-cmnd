/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeanUtils
 *  org.springframework.beans.factory.BeanDefinitionStoreException
 *  org.springframework.beans.factory.BeanNameAware
 *  org.springframework.beans.factory.FactoryBean
 *  org.springframework.beans.factory.InitializingBean
 */
package org.springframework.integration.config;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

public class SpelFunctionFactoryBean
implements FactoryBean<Method>,
InitializingBean,
BeanNameAware {
    private final Class<?> functionClass;
    private final String functionMethodSignature;
    private String functionName;
    private Method method;

    public SpelFunctionFactoryBean(Class<?> functionClass, String functionMethodSignature) {
        this.functionClass = functionClass;
        this.functionMethodSignature = functionMethodSignature;
    }

    public void setBeanName(String name) {
        this.functionName = name;
    }

    public String getFunctionName() {
        return this.functionName;
    }

    public void afterPropertiesSet() {
        this.method = BeanUtils.resolveSignature((String)this.functionMethodSignature, this.functionClass);
        if (this.method == null) {
            throw new BeanDefinitionStoreException(String.format("No declared method '%s' in class '%s'", this.functionMethodSignature, this.functionClass));
        }
        if (!Modifier.isStatic(this.method.getModifiers())) {
            throw new BeanDefinitionStoreException("SpEL-function method has to be 'static'");
        }
    }

    public Method getObject() {
        return this.method;
    }

    public Class<?> getObjectType() {
        return Method.class;
    }

    public boolean isSingleton() {
        return true;
    }
}

