/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.context.properties.ConfigurationProperties
 *  org.springframework.boot.context.properties.PropertyMapper
 *  org.springframework.boot.convert.DurationUnit
 *  org.springframework.util.CollectionUtils
 *  org.springframework.web.cors.CorsConfiguration
 */
package org.springframework.boot.autoconfigure.graphql;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.util.CollectionUtils;
import org.springframework.web.cors.CorsConfiguration;

@ConfigurationProperties(prefix="spring.graphql.cors")
public class GraphQlCorsProperties {
    private List<String> allowedOrigins = new ArrayList<String>();
    private List<String> allowedOriginPatterns = new ArrayList<String>();
    private List<String> allowedMethods = new ArrayList<String>();
    private List<String> allowedHeaders = new ArrayList<String>();
    private List<String> exposedHeaders = new ArrayList<String>();
    private Boolean allowCredentials;
    @DurationUnit(value=ChronoUnit.SECONDS)
    private Duration maxAge = Duration.ofSeconds(1800L);

    public List<String> getAllowedOrigins() {
        return this.allowedOrigins;
    }

    public void setAllowedOrigins(List<String> allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
    }

    public List<String> getAllowedOriginPatterns() {
        return this.allowedOriginPatterns;
    }

    public void setAllowedOriginPatterns(List<String> allowedOriginPatterns) {
        this.allowedOriginPatterns = allowedOriginPatterns;
    }

    public List<String> getAllowedMethods() {
        return this.allowedMethods;
    }

    public void setAllowedMethods(List<String> allowedMethods) {
        this.allowedMethods = allowedMethods;
    }

    public List<String> getAllowedHeaders() {
        return this.allowedHeaders;
    }

    public void setAllowedHeaders(List<String> allowedHeaders) {
        this.allowedHeaders = allowedHeaders;
    }

    public List<String> getExposedHeaders() {
        return this.exposedHeaders;
    }

    public void setExposedHeaders(List<String> exposedHeaders) {
        this.exposedHeaders = exposedHeaders;
    }

    public Boolean getAllowCredentials() {
        return this.allowCredentials;
    }

    public void setAllowCredentials(Boolean allowCredentials) {
        this.allowCredentials = allowCredentials;
    }

    public Duration getMaxAge() {
        return this.maxAge;
    }

    public void setMaxAge(Duration maxAge) {
        this.maxAge = maxAge;
    }

    public CorsConfiguration toCorsConfiguration() {
        if (CollectionUtils.isEmpty(this.allowedOrigins) && CollectionUtils.isEmpty(this.allowedOriginPatterns)) {
            return null;
        }
        PropertyMapper map = PropertyMapper.get();
        CorsConfiguration config = new CorsConfiguration();
        map.from(this::getAllowedOrigins).to(arg_0 -> ((CorsConfiguration)config).setAllowedOrigins(arg_0));
        map.from(this::getAllowedOriginPatterns).to(arg_0 -> ((CorsConfiguration)config).setAllowedOriginPatterns(arg_0));
        map.from(this::getAllowedHeaders).whenNot(CollectionUtils::isEmpty).to(arg_0 -> ((CorsConfiguration)config).setAllowedHeaders(arg_0));
        map.from(this::getAllowedMethods).whenNot(CollectionUtils::isEmpty).to(arg_0 -> ((CorsConfiguration)config).setAllowedMethods(arg_0));
        map.from(this::getExposedHeaders).whenNot(CollectionUtils::isEmpty).to(arg_0 -> ((CorsConfiguration)config).setExposedHeaders(arg_0));
        map.from(this::getMaxAge).whenNonNull().as(Duration::getSeconds).to(arg_0 -> ((CorsConfiguration)config).setMaxAge(arg_0));
        map.from(this::getAllowCredentials).whenNonNull().to(arg_0 -> ((CorsConfiguration)config).setAllowCredentials(arg_0));
        return config;
    }
}

