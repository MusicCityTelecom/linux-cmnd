/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.NoSuchBeanDefinitionException
 */
package org.springframework.boot.web.context;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.boot.web.server.WebServerFactory;

public class MissingWebServerFactoryBeanException
extends NoSuchBeanDefinitionException {
    private final WebApplicationType webApplicationType;

    public MissingWebServerFactoryBeanException(Class<? extends WebServerApplicationContext> webServerApplicationContextClass, Class<? extends WebServerFactory> webServerFactoryClass, WebApplicationType webApplicationType) {
        super(webServerFactoryClass, String.format("Unable to start %s due to missing %s bean", webServerApplicationContextClass.getSimpleName(), webServerFactoryClass.getSimpleName()));
        this.webApplicationType = webApplicationType;
    }

    public WebApplicationType getWebApplicationType() {
        return this.webApplicationType;
    }
}

