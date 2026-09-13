/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.core;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.configuration.model.core.web.tomcat.CasEmbeddedApacheTomcatProperties;
import org.apereo.cas.configuration.support.ExpressionLanguageCapable;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-core", automated=true)
@JsonFilter(value="CasServerProperties")
public class CasServerProperties
implements Serializable {
    private static final long serialVersionUID = 7876382696803430817L;
    @RequiredProperty
    private String name = "https://cas.example.org:8443";
    @RequiredProperty
    @ExpressionLanguageCapable
    private String prefix;
    @RequiredProperty
    @ExpressionLanguageCapable
    private String scope = "example.org";
    @NestedConfigurationProperty
    private CasEmbeddedApacheTomcatProperties tomcat = new CasEmbeddedApacheTomcatProperties();

    public CasServerProperties() {
        this.setPrefix(StringUtils.appendIfMissing((String)this.getName(), (CharSequence)"/", (CharSequence[])new CharSequence[0]).concat("cas"));
    }

    @JsonIgnore
    public String getLoginUrl() {
        return this.getPrefix().concat("/login");
    }

    @JsonIgnore
    public String getLogoutUrl() {
        return this.getPrefix().concat("/logout");
    }

    @Generated
    public String getName() {
        return this.name;
    }

    @Generated
    public String getPrefix() {
        return this.prefix;
    }

    @Generated
    public String getScope() {
        return this.scope;
    }

    @Generated
    public CasEmbeddedApacheTomcatProperties getTomcat() {
        return this.tomcat;
    }

    @Generated
    public CasServerProperties setName(String name) {
        this.name = name;
        return this;
    }

    @Generated
    public CasServerProperties setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    @Generated
    public CasServerProperties setScope(String scope) {
        this.scope = scope;
        return this;
    }

    @Generated
    public CasServerProperties setTomcat(CasEmbeddedApacheTomcatProperties tomcat) {
        this.tomcat = tomcat;
        return this;
    }
}

