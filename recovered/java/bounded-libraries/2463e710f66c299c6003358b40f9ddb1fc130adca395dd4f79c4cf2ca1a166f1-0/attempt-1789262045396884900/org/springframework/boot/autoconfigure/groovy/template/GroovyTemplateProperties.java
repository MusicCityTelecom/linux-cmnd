/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.context.properties.ConfigurationProperties
 */
package org.springframework.boot.autoconfigure.groovy.template;

import org.springframework.boot.autoconfigure.template.AbstractTemplateViewResolverProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="spring.groovy.template", ignoreUnknownFields=true)
public class GroovyTemplateProperties
extends AbstractTemplateViewResolverProperties {
    public static final String DEFAULT_RESOURCE_LOADER_PATH = "classpath:/templates/";
    public static final String DEFAULT_PREFIX = "";
    public static final String DEFAULT_SUFFIX = ".tpl";
    public static final String DEFAULT_REQUEST_CONTEXT_ATTRIBUTE = "spring";
    private String resourceLoaderPath = "classpath:/templates/";

    public GroovyTemplateProperties() {
        super(DEFAULT_PREFIX, DEFAULT_SUFFIX);
        this.setRequestContextAttribute(DEFAULT_REQUEST_CONTEXT_ATTRIBUTE);
    }

    public String getResourceLoaderPath() {
        return this.resourceLoaderPath;
    }

    public void setResourceLoaderPath(String resourceLoaderPath) {
        this.resourceLoaderPath = resourceLoaderPath;
    }
}

