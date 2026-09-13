/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.DispatcherType
 *  javax.servlet.annotation.WebFilter
 *  org.springframework.beans.factory.annotation.AnnotatedBeanDefinition
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.web.servlet;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Map;
import javax.servlet.DispatcherType;
import javax.servlet.annotation.WebFilter;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentHandler;
import org.springframework.util.StringUtils;

class WebFilterHandler
extends ServletComponentHandler {
    WebFilterHandler() {
        super(WebFilter.class);
    }

    @Override
    public void doHandle(Map<String, Object> attributes, AnnotatedBeanDefinition beanDefinition, BeanDefinitionRegistry registry) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(FilterRegistrationBean.class);
        builder.addPropertyValue("asyncSupported", attributes.get("asyncSupported"));
        builder.addPropertyValue("dispatcherTypes", this.extractDispatcherTypes(attributes));
        builder.addPropertyValue("filter", (Object)beanDefinition);
        builder.addPropertyValue("initParameters", this.extractInitParameters(attributes));
        String name = this.determineName(attributes, (BeanDefinition)beanDefinition);
        builder.addPropertyValue("name", (Object)name);
        builder.addPropertyValue("servletNames", attributes.get("servletNames"));
        builder.addPropertyValue("urlPatterns", (Object)this.extractUrlPatterns(attributes));
        registry.registerBeanDefinition(name, (BeanDefinition)builder.getBeanDefinition());
    }

    private EnumSet<DispatcherType> extractDispatcherTypes(Map<String, Object> attributes) {
        DispatcherType[] dispatcherTypes = (DispatcherType[])attributes.get("dispatcherTypes");
        if (dispatcherTypes.length == 0) {
            return EnumSet.noneOf(DispatcherType.class);
        }
        if (dispatcherTypes.length == 1) {
            return EnumSet.of(dispatcherTypes[0]);
        }
        return EnumSet.of(dispatcherTypes[0], (Enum[])Arrays.copyOfRange(dispatcherTypes, 1, dispatcherTypes.length));
    }

    private String determineName(Map<String, Object> attributes, BeanDefinition beanDefinition) {
        return (String)(StringUtils.hasText((String)((String)attributes.get("filterName"))) ? attributes.get("filterName") : beanDefinition.getBeanClassName());
    }
}

