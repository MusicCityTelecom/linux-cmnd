/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.web.security;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-core-web", automated=true)
@JsonFilter(value="HttpHeadersRequestProperties")
public class HttpHeadersRequestProperties
implements Serializable {
    private static final long serialVersionUID = 5993704062519851359L;
    @RequiredProperty
    private boolean enabled = true;
    private boolean cache = true;
    private boolean hsts = true;
    private boolean xframe = true;
    private boolean xcontent = true;
    private boolean xss = true;
    private String hstsOptions = "max-age=15768000 ; includeSubDomains";
    private String xframeOptions = "DENY";
    private String xssOptions = "1; mode=block";
    private String contentSecurityPolicy;
    private String cacheControlStaticResources = "css|js|png|txt|jpg|ico|jpeg|bmp|gif";

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public boolean isCache() {
        return this.cache;
    }

    @Generated
    public boolean isHsts() {
        return this.hsts;
    }

    @Generated
    public boolean isXframe() {
        return this.xframe;
    }

    @Generated
    public boolean isXcontent() {
        return this.xcontent;
    }

    @Generated
    public boolean isXss() {
        return this.xss;
    }

    @Generated
    public String getHstsOptions() {
        return this.hstsOptions;
    }

    @Generated
    public String getXframeOptions() {
        return this.xframeOptions;
    }

    @Generated
    public String getXssOptions() {
        return this.xssOptions;
    }

    @Generated
    public String getContentSecurityPolicy() {
        return this.contentSecurityPolicy;
    }

    @Generated
    public String getCacheControlStaticResources() {
        return this.cacheControlStaticResources;
    }

    @Generated
    public HttpHeadersRequestProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Generated
    public HttpHeadersRequestProperties setCache(boolean cache) {
        this.cache = cache;
        return this;
    }

    @Generated
    public HttpHeadersRequestProperties setHsts(boolean hsts) {
        this.hsts = hsts;
        return this;
    }

    @Generated
    public HttpHeadersRequestProperties setXframe(boolean xframe) {
        this.xframe = xframe;
        return this;
    }

    @Generated
    public HttpHeadersRequestProperties setXcontent(boolean xcontent) {
        this.xcontent = xcontent;
        return this;
    }

    @Generated
    public HttpHeadersRequestProperties setXss(boolean xss) {
        this.xss = xss;
        return this;
    }

    @Generated
    public HttpHeadersRequestProperties setHstsOptions(String hstsOptions) {
        this.hstsOptions = hstsOptions;
        return this;
    }

    @Generated
    public HttpHeadersRequestProperties setXframeOptions(String xframeOptions) {
        this.xframeOptions = xframeOptions;
        return this;
    }

    @Generated
    public HttpHeadersRequestProperties setXssOptions(String xssOptions) {
        this.xssOptions = xssOptions;
        return this;
    }

    @Generated
    public HttpHeadersRequestProperties setContentSecurityPolicy(String contentSecurityPolicy) {
        this.contentSecurityPolicy = contentSecurityPolicy;
        return this;
    }

    @Generated
    public HttpHeadersRequestProperties setCacheControlStaticResources(String cacheControlStaticResources) {
        this.cacheControlStaticResources = cacheControlStaticResources;
        return this;
    }
}

