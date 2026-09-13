/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.pm;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.SpringResourceProperties;
import org.apereo.cas.configuration.model.support.pm.PasswordHistoryCoreProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-pm-webflow")
@JsonFilter(value="PasswordHistoryProperties")
public class PasswordHistoryProperties
implements Serializable {
    private static final long serialVersionUID = 2211199066765183587L;
    @NestedConfigurationProperty
    private PasswordHistoryCoreProperties core = new PasswordHistoryCoreProperties();
    @NestedConfigurationProperty
    private SpringResourceProperties groovy = new SpringResourceProperties();

    @Generated
    public PasswordHistoryCoreProperties getCore() {
        return this.core;
    }

    @Generated
    public SpringResourceProperties getGroovy() {
        return this.groovy;
    }

    @Generated
    public PasswordHistoryProperties setCore(PasswordHistoryCoreProperties core) {
        this.core = core;
        return this;
    }

    @Generated
    public PasswordHistoryProperties setGroovy(SpringResourceProperties groovy) {
        this.groovy = groovy;
        return this;
    }
}

