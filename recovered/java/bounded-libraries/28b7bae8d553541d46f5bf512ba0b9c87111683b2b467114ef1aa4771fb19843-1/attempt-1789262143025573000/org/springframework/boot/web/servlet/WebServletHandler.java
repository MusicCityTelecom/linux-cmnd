/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.MultipartConfigElement
 *  javax.servlet.annotation.MultipartConfig
 *  javax.servlet.annotation.WebServlet
 *  org.springframework.beans.factory.annotation.AnnotatedBeanDefinition
 *  org.springframework.beans.factory.config.BeanDefinition
 *  org.springframework.beans.factory.support.BeanDefinitionBuilder
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.web.servlet;

import java.util.Map;
import javax.servlet.MultipartConfigElement;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.web.servlet.ServletComponentHandler;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.util.StringUtils;

class WebServletHandler
extends ServletComponentHandler {
    WebServletHandler() {
        super(WebServlet.class);
    }

    @Override
    public void doHandle(Map<String, Object> attributes, AnnotatedBeanDefinition beanDefinition, BeanDefinitionRegistry registry) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(ServletRegistrationBean.class);
        builder.addPropertyValue("asyncSupported", attributes.get("asyncSupported"));
        builder.addPropertyValue("initParameters", this.extractInitParameters(attributes));
        builder.addPropertyValue("loadOnStartup", attributes.get("loadOnStartup"));
        String name = this.determineName(attributes, (BeanDefinition)beanDefinition);
        builder.addPropertyValue("name", (Object)name);
        builder.addPropertyValue("servlet", (Object)beanDefinition);
        builder.addPropertyValue("urlMappings", (Object)this.extractUrlPatterns(attributes));
        builder.addPropertyValue("multipartConfig", (Object)this.determineMultipartConfig(beanDefinition));
        registry.registerBeanDefinition(name, (BeanDefinition)builder.getBeanDefinition());
    }

    private String determineName(Map<String, Object> attributes, BeanDefinition beanDefinition) {
        return (String)(StringUtils.hasText((String)((String)attributes.get("name"))) ? attributes.get("name") : beanDefinition.getBeanClassName());
    }

    private MultipartConfigElement determineMultipartConfig(AnnotatedBeanDefinition beanDefinition) {
        Map attributes = beanDefinition.getMetadata().getAnnotationAttributes(MultipartConfig.class.getName());
        if (attributes == null) {
            return null;
        }
        return new MultipartConfigElement((String)attributes.get("location"), ((Long)attributes.get("maxFileSize")).longValue(), ((Long)attributes.get("maxRequestSize")).longValue(), ((Integer)attributes.get("fileSizeThreshold")).intValue());
    }
}

