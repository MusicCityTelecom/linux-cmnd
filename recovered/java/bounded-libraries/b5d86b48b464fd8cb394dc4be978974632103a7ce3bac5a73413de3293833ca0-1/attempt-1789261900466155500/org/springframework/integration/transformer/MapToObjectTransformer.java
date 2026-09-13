/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeanUtils
 *  org.springframework.beans.MutablePropertyValues
 *  org.springframework.beans.PropertyValues
 *  org.springframework.core.convert.ConversionService
 *  org.springframework.core.convert.support.DefaultConversionService
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 *  org.springframework.validation.DataBinder
 */
package org.springframework.integration.transformer;

import java.util.Map;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.integration.transformer.AbstractPayloadTransformer;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.validation.DataBinder;

public class MapToObjectTransformer
extends AbstractPayloadTransformer<Map<?, ?>, Object> {
    private final Class<?> targetClass;
    private final String targetBeanName;

    public MapToObjectTransformer(Class<?> targetClass) {
        Assert.notNull(targetClass, (String)"targetClass must not be null");
        this.targetClass = targetClass;
        this.targetBeanName = null;
    }

    public MapToObjectTransformer(String beanName) {
        Assert.hasText((String)beanName, (String)"beanName must not be empty");
        this.targetBeanName = beanName;
        this.targetClass = null;
    }

    @Override
    public String getComponentType() {
        return "map-to-object-transformer";
    }

    @Override
    protected void onInit() {
        if (StringUtils.hasText((String)this.targetBeanName)) {
            Assert.isTrue((boolean)this.getBeanFactory().isPrototype(this.targetBeanName), (String)("target bean [" + this.targetBeanName + "] must have 'prototype' scope"));
        }
    }

    @Override
    protected Object transformPayload(Map<?, ?> payload) {
        Object target = this.targetClass != null ? BeanUtils.instantiateClass(this.targetClass) : this.getBeanFactory().getBean(this.targetBeanName);
        DataBinder binder = new DataBinder(target);
        ConversionService conversionService = this.getConversionService();
        if (conversionService == null) {
            conversionService = DefaultConversionService.getSharedInstance();
        }
        binder.setConversionService(conversionService);
        binder.bind((PropertyValues)new MutablePropertyValues(payload));
        return target;
    }
}

