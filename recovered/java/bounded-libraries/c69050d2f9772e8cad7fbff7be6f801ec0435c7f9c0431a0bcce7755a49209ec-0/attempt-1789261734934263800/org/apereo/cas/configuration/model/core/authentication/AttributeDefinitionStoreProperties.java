/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.core.authentication;

import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.SpringResourceProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-core-authentication", automated=true)
public class AttributeDefinitionStoreProperties
implements Serializable {
    private static final long serialVersionUID = 1248812041234879300L;
    @NestedConfigurationProperty
    private SpringResourceProperties json = new SpringResourceProperties();

    @Generated
    public SpringResourceProperties getJson() {
        return this.json;
    }

    @Generated
    public AttributeDefinitionStoreProperties setJson(SpringResourceProperties json) {
        this.json = json;
        return this;
    }
}

