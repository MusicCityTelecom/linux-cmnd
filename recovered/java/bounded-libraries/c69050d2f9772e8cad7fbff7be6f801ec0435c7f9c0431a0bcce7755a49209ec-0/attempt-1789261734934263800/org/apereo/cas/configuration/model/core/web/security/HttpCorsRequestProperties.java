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
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-core-web", automated=true)
@JsonFilter(value="HttpCorsRequestProperties")
public class HttpCorsRequestProperties
implements Serializable {
    private static final long serialVersionUID = 5938828345939769185L;
    @RequiredProperty
    private boolean enabled;
    private boolean allowCredentials = true;
    private List<String> allowOrigins = new ArrayList<String>(0);
    private List<String> allowOriginPatterns = new ArrayList<String>(0);
    private List<String> allowMethods = new ArrayList<String>(0);
    private List<String> allowHeaders = new ArrayList<String>(0);
    private long maxAge = 3600L;
    private List<String> exposedHeaders = new ArrayList<String>(0);

    public HttpCorsRequestProperties() {
        this.allowMethods.add("*");
        this.allowHeaders.add("*");
    }

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public boolean isAllowCredentials() {
        return this.allowCredentials;
    }

    @Generated
    public List<String> getAllowOrigins() {
        return this.allowOrigins;
    }

    @Generated
    public List<String> getAllowOriginPatterns() {
        return this.allowOriginPatterns;
    }

    @Generated
    public List<String> getAllowMethods() {
        return this.allowMethods;
    }

    @Generated
    public List<String> getAllowHeaders() {
        return this.allowHeaders;
    }

    @Generated
    public long getMaxAge() {
        return this.maxAge;
    }

    @Generated
    public List<String> getExposedHeaders() {
        return this.exposedHeaders;
    }

    @Generated
    public HttpCorsRequestProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Generated
    public HttpCorsRequestProperties setAllowCredentials(boolean allowCredentials) {
        this.allowCredentials = allowCredentials;
        return this;
    }

    @Generated
    public HttpCorsRequestProperties setAllowOrigins(List<String> allowOrigins) {
        this.allowOrigins = allowOrigins;
        return this;
    }

    @Generated
    public HttpCorsRequestProperties setAllowOriginPatterns(List<String> allowOriginPatterns) {
        this.allowOriginPatterns = allowOriginPatterns;
        return this;
    }

    @Generated
    public HttpCorsRequestProperties setAllowMethods(List<String> allowMethods) {
        this.allowMethods = allowMethods;
        return this;
    }

    @Generated
    public HttpCorsRequestProperties setAllowHeaders(List<String> allowHeaders) {
        this.allowHeaders = allowHeaders;
        return this;
    }

    @Generated
    public HttpCorsRequestProperties setMaxAge(long maxAge) {
        this.maxAge = maxAge;
        return this;
    }

    @Generated
    public HttpCorsRequestProperties setExposedHeaders(List<String> exposedHeaders) {
        this.exposedHeaders = exposedHeaders;
        return this;
    }
}

