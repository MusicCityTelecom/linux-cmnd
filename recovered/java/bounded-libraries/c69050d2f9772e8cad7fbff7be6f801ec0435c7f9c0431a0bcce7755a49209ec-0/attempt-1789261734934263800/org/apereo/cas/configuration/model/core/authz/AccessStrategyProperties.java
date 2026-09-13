/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.core.authz;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.SpringResourceProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-core", automated=true)
@JsonFilter(value="AccessStrategyProperties")
public class AccessStrategyProperties
implements Serializable {
    private static final long serialVersionUID = 2624916460241033347L;
    @NestedConfigurationProperty
    private SpringResourceProperties groovy = new SpringResourceProperties();

    @Generated
    public SpringResourceProperties getGroovy() {
        return this.groovy;
    }

    @Generated
    public AccessStrategyProperties setGroovy(SpringResourceProperties groovy) {
        this.groovy = groovy;
        return this;
    }
}

