/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 *  org.springframework.web.servlet.view.AbstractTemplateViewResolver
 */
package org.springframework.boot.autoconfigure.template;

import org.springframework.boot.autoconfigure.template.AbstractViewResolverProperties;
import org.springframework.util.Assert;
import org.springframework.web.servlet.view.AbstractTemplateViewResolver;

public abstract class AbstractTemplateViewResolverProperties
extends AbstractViewResolverProperties {
    private String prefix;
    private String suffix;
    private String requestContextAttribute;
    private boolean exposeRequestAttributes = false;
    private boolean exposeSessionAttributes = false;
    private boolean allowRequestOverride = false;
    private boolean exposeSpringMacroHelpers = true;
    private boolean allowSessionOverride = false;

    protected AbstractTemplateViewResolverProperties(String defaultPrefix, String defaultSuffix) {
        this.prefix = defaultPrefix;
        this.suffix = defaultSuffix;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getRequestContextAttribute() {
        return this.requestContextAttribute;
    }

    public void setRequestContextAttribute(String requestContextAttribute) {
        this.requestContextAttribute = requestContextAttribute;
    }

    public boolean isExposeRequestAttributes() {
        return this.exposeRequestAttributes;
    }

    public void setExposeRequestAttributes(boolean exposeRequestAttributes) {
        this.exposeRequestAttributes = exposeRequestAttributes;
    }

    public boolean isExposeSessionAttributes() {
        return this.exposeSessionAttributes;
    }

    public void setExposeSessionAttributes(boolean exposeSessionAttributes) {
        this.exposeSessionAttributes = exposeSessionAttributes;
    }

    public boolean isAllowRequestOverride() {
        return this.allowRequestOverride;
    }

    public void setAllowRequestOverride(boolean allowRequestOverride) {
        this.allowRequestOverride = allowRequestOverride;
    }

    public boolean isAllowSessionOverride() {
        return this.allowSessionOverride;
    }

    public void setAllowSessionOverride(boolean allowSessionOverride) {
        this.allowSessionOverride = allowSessionOverride;
    }

    public boolean isExposeSpringMacroHelpers() {
        return this.exposeSpringMacroHelpers;
    }

    public void setExposeSpringMacroHelpers(boolean exposeSpringMacroHelpers) {
        this.exposeSpringMacroHelpers = exposeSpringMacroHelpers;
    }

    public void applyToMvcViewResolver(Object viewResolver) {
        Assert.isInstanceOf(AbstractTemplateViewResolver.class, (Object)viewResolver, () -> "ViewResolver is not an instance of AbstractTemplateViewResolver :" + viewResolver);
        AbstractTemplateViewResolver resolver = (AbstractTemplateViewResolver)viewResolver;
        resolver.setPrefix(this.getPrefix());
        resolver.setSuffix(this.getSuffix());
        resolver.setCache(this.isCache());
        if (this.getContentType() != null) {
            resolver.setContentType(this.getContentType().toString());
        }
        resolver.setViewNames(this.getViewNames());
        resolver.setExposeRequestAttributes(this.isExposeRequestAttributes());
        resolver.setAllowRequestOverride(this.isAllowRequestOverride());
        resolver.setAllowSessionOverride(this.isAllowSessionOverride());
        resolver.setExposeSessionAttributes(this.isExposeSessionAttributes());
        resolver.setExposeSpringMacroHelpers(this.isExposeSpringMacroHelpers());
        resolver.setRequestContextAttribute(this.getRequestContextAttribute());
        resolver.setOrder(0x7FFFFFFA);
    }
}

