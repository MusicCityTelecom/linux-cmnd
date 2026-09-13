/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.core.web;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.web.LocaleCookieProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-core-web", automated=true)
@JsonFilter(value="LocaleProperties")
public class LocaleProperties
implements Serializable {
    private static final long serialVersionUID = -1644471820900213781L;
    private String paramName = "locale";
    private String defaultValue = "en";
    private boolean forceDefaultLocale;
    @NestedConfigurationProperty
    private LocaleCookieProperties cookie = new LocaleCookieProperties();

    @Generated
    public String getParamName() {
        return this.paramName;
    }

    @Generated
    public String getDefaultValue() {
        return this.defaultValue;
    }

    @Generated
    public boolean isForceDefaultLocale() {
        return this.forceDefaultLocale;
    }

    @Generated
    public LocaleCookieProperties getCookie() {
        return this.cookie;
    }

    @Generated
    public LocaleProperties setParamName(String paramName) {
        this.paramName = paramName;
        return this;
    }

    @Generated
    public LocaleProperties setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    @Generated
    public LocaleProperties setForceDefaultLocale(boolean forceDefaultLocale) {
        this.forceDefaultLocale = forceDefaultLocale;
        return this;
    }

    @Generated
    public LocaleProperties setCookie(LocaleCookieProperties cookie) {
        this.cookie = cookie;
        return this;
    }
}

