/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.web;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-core-web", automated=true)
@JsonFilter(value="MessageBundleProperties")
public class MessageBundleProperties
implements Serializable {
    public static final String DEFAULT_BUNDLE_PREFIX_AUTHN_FAILURE = "authenticationFailure.";
    private static final long serialVersionUID = 3769733438559663237L;
    private String encoding = StandardCharsets.UTF_8.name();
    private int cacheSeconds = 180;
    private boolean fallbackSystemLocale;
    private boolean useCodeMessage = true;
    private List<String> baseNames = Stream.of("file:/etc/cas/config/custom_messages", "classpath:custom_messages", "classpath:messages").collect(Collectors.toList());
    private List<String> commonNames = Stream.of("classpath:common_messages.properties", "file:/etc/cas/config/common_messages.properties").collect(Collectors.toList());

    @Generated
    public String getEncoding() {
        return this.encoding;
    }

    @Generated
    public int getCacheSeconds() {
        return this.cacheSeconds;
    }

    @Generated
    public boolean isFallbackSystemLocale() {
        return this.fallbackSystemLocale;
    }

    @Generated
    public boolean isUseCodeMessage() {
        return this.useCodeMessage;
    }

    @Generated
    public List<String> getBaseNames() {
        return this.baseNames;
    }

    @Generated
    public List<String> getCommonNames() {
        return this.commonNames;
    }

    @Generated
    public MessageBundleProperties setEncoding(String encoding) {
        this.encoding = encoding;
        return this;
    }

    @Generated
    public MessageBundleProperties setCacheSeconds(int cacheSeconds) {
        this.cacheSeconds = cacheSeconds;
        return this;
    }

    @Generated
    public MessageBundleProperties setFallbackSystemLocale(boolean fallbackSystemLocale) {
        this.fallbackSystemLocale = fallbackSystemLocale;
        return this;
    }

    @Generated
    public MessageBundleProperties setUseCodeMessage(boolean useCodeMessage) {
        this.useCodeMessage = useCodeMessage;
        return this;
    }

    @Generated
    public MessageBundleProperties setBaseNames(List<String> baseNames) {
        this.baseNames = baseNames;
        return this;
    }

    @Generated
    public MessageBundleProperties setCommonNames(List<String> commonNames) {
        this.commonNames = commonNames;
        return this;
    }
}

