/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.MultipartConfigElement
 *  org.springframework.boot.context.properties.ConfigurationProperties
 *  org.springframework.boot.context.properties.PropertyMapper
 *  org.springframework.boot.web.servlet.MultipartConfigFactory
 *  org.springframework.util.unit.DataSize
 */
package org.springframework.boot.autoconfigure.web.servlet;

import javax.servlet.MultipartConfigElement;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.util.unit.DataSize;

@ConfigurationProperties(prefix="spring.servlet.multipart", ignoreUnknownFields=false)
public class MultipartProperties {
    private boolean enabled = true;
    private String location;
    private DataSize maxFileSize = DataSize.ofMegabytes((long)1L);
    private DataSize maxRequestSize = DataSize.ofMegabytes((long)10L);
    private DataSize fileSizeThreshold = DataSize.ofBytes((long)0L);
    private boolean resolveLazily = false;

    public boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public DataSize getMaxFileSize() {
        return this.maxFileSize;
    }

    public void setMaxFileSize(DataSize maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    public DataSize getMaxRequestSize() {
        return this.maxRequestSize;
    }

    public void setMaxRequestSize(DataSize maxRequestSize) {
        this.maxRequestSize = maxRequestSize;
    }

    public DataSize getFileSizeThreshold() {
        return this.fileSizeThreshold;
    }

    public void setFileSizeThreshold(DataSize fileSizeThreshold) {
        this.fileSizeThreshold = fileSizeThreshold;
    }

    public boolean isResolveLazily() {
        return this.resolveLazily;
    }

    public void setResolveLazily(boolean resolveLazily) {
        this.resolveLazily = resolveLazily;
    }

    public MultipartConfigElement createMultipartConfig() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        map.from((Object)this.fileSizeThreshold).to(arg_0 -> ((MultipartConfigFactory)factory).setFileSizeThreshold(arg_0));
        map.from((Object)this.location).whenHasText().to(arg_0 -> ((MultipartConfigFactory)factory).setLocation(arg_0));
        map.from((Object)this.maxRequestSize).to(arg_0 -> ((MultipartConfigFactory)factory).setMaxRequestSize(arg_0));
        map.from((Object)this.maxFileSize).to(arg_0 -> ((MultipartConfigFactory)factory).setMaxFileSize(arg_0));
        return factory.createMultipartConfig();
    }
}

