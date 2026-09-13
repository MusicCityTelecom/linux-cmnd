/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.pac4j;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.SpringResourceProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-pac4j")
@JsonFilter(value="Pac4jDelegatedAuthenticationProfileSelectionProperties")
public class Pac4jDelegatedAuthenticationProfileSelectionProperties
implements Serializable {
    private static final long serialVersionUID = 1478567744591488495L;
    @NestedConfigurationProperty
    private SpringResourceProperties groovy = new SpringResourceProperties();

    @Generated
    public SpringResourceProperties getGroovy() {
        return this.groovy;
    }

    @Generated
    public Pac4jDelegatedAuthenticationProfileSelectionProperties setGroovy(SpringResourceProperties groovy) {
        this.groovy = groovy;
        return this;
    }
}

