/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryUtils
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.beans.factory.ListableBeanFactory
 *  org.springframework.beans.factory.NoSuchBeanDefinitionException
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationContextAware
 *  org.springframework.core.convert.ConversionService
 *  org.springframework.expression.PropertyAccessor
 *  org.springframework.expression.TypeConverter
 *  org.springframework.expression.spel.support.StandardTypeConverter
 *  org.springframework.util.Assert
 */
package org.springframework.integration.config;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.convert.ConversionService;
import org.springframework.expression.PropertyAccessor;
import org.springframework.expression.TypeConverter;
import org.springframework.expression.spel.support.StandardTypeConverter;
import org.springframework.integration.config.SpelFunctionFactoryBean;
import org.springframework.integration.expression.SpelPropertyAccessorRegistrar;
import org.springframework.integration.support.utils.IntegrationUtils;
import org.springframework.util.Assert;

public abstract class AbstractEvaluationContextFactoryBean
implements ApplicationContextAware,
InitializingBean {
    private Map<String, PropertyAccessor> propertyAccessors = new LinkedHashMap<String, PropertyAccessor>();
    private Map<String, Method> functions = new LinkedHashMap<String, Method>();
    private TypeConverter typeConverter = new StandardTypeConverter();
    private ApplicationContext applicationContext;
    private boolean initialized;

    protected TypeConverter getTypeConverter() {
        return this.typeConverter;
    }

    protected ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void setPropertyAccessors(Map<String, PropertyAccessor> accessors) {
        Assert.isTrue((!this.initialized ? 1 : 0) != 0, (String)"'propertyAccessors' can't be changed after initialization.");
        Assert.notNull(accessors, (String)"'accessors' must not be null.");
        Assert.noNullElements((Object[])accessors.values().toArray(), (String)"'accessors' cannot have null values.");
        this.propertyAccessors = new LinkedHashMap<String, PropertyAccessor>(accessors);
    }

    public Map<String, PropertyAccessor> getPropertyAccessors() {
        return this.propertyAccessors;
    }

    public void setFunctions(Map<String, Method> functionsArg) {
        Assert.isTrue((!this.initialized ? 1 : 0) != 0, (String)"'functions' can't be changed after initialization.");
        Assert.notNull(functionsArg, (String)"'functions' must not be null.");
        Assert.noNullElements((Object[])functionsArg.values().toArray(), (String)"'functions' cannot have null values.");
        this.functions = new LinkedHashMap<String, Method>(functionsArg);
    }

    public Map<String, Method> getFunctions() {
        return this.functions;
    }

    protected void initialize(String beanName) {
        if (this.applicationContext != null) {
            this.conversionService();
            this.functions();
            this.propertyAccessors();
            this.processParentIfPresent(beanName);
        }
        this.initialized = true;
    }

    private void conversionService() {
        ConversionService conversionService = IntegrationUtils.getConversionService((BeanFactory)this.getApplicationContext());
        if (conversionService != null) {
            this.typeConverter = new StandardTypeConverter(conversionService);
        }
    }

    private void functions() {
        Map functionFactoryBeanMap = BeanFactoryUtils.beansOfTypeIncludingAncestors((ListableBeanFactory)this.applicationContext, SpelFunctionFactoryBean.class);
        for (SpelFunctionFactoryBean spelFunctionFactoryBean : functionFactoryBeanMap.values()) {
            if (this.getFunctions().containsKey(spelFunctionFactoryBean.getFunctionName())) continue;
            this.getFunctions().put(spelFunctionFactoryBean.getFunctionName(), spelFunctionFactoryBean.getObject());
        }
    }

    private void propertyAccessors() {
        try {
            SpelPropertyAccessorRegistrar propertyAccessorRegistrar = (SpelPropertyAccessorRegistrar)this.applicationContext.getBean(SpelPropertyAccessorRegistrar.class);
            for (Map.Entry<String, PropertyAccessor> entry : propertyAccessorRegistrar.getPropertyAccessors().entrySet()) {
                if (this.getPropertyAccessors().containsKey(entry.getKey())) continue;
                this.getPropertyAccessors().put(entry.getKey(), entry.getValue());
            }
        }
        catch (NoSuchBeanDefinitionException noSuchBeanDefinitionException) {
            // empty catch block
        }
    }

    private void processParentIfPresent(String beanName) {
        ApplicationContext parent = this.applicationContext.getParent();
        if (parent != null && parent.containsBean(beanName)) {
            AbstractEvaluationContextFactoryBean parentFactoryBean = (AbstractEvaluationContextFactoryBean)parent.getBean("&" + beanName, this.getClass());
            for (Map.Entry<String, PropertyAccessor> entry : parentFactoryBean.getPropertyAccessors().entrySet()) {
                if (this.getPropertyAccessors().containsKey(entry.getKey())) continue;
                this.getPropertyAccessors().put(entry.getKey(), entry.getValue());
            }
            for (Map.Entry<String, Object> entry : parentFactoryBean.getFunctions().entrySet()) {
                if (this.getFunctions().containsKey(entry.getKey())) continue;
                this.getFunctions().put(entry.getKey(), (Method)entry.getValue());
            }
        }
    }
}

