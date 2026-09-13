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
import org.apereo.cas.configuration.model.support.pac4j.Pac4jDelegatedAuthenticationGroovyProvisioningProperties;
import org.apereo.cas.configuration.model.support.pac4j.Pac4jDelegatedAuthenticationRestfulProvisioningProperties;
import org.apereo.cas.configuration.model.support.pac4j.Pac4jDelegatedAuthenticationScimProvisioningProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-pac4j")
@JsonFilter(value="Pac4jDelegatedAuthenticationProvisioningProperties")
public class Pac4jDelegatedAuthenticationProvisioningProperties
implements Serializable {
    private static final long serialVersionUID = 3478567744591488495L;
    @NestedConfigurationProperty
    private Pac4jDelegatedAuthenticationScimProvisioningProperties scim = new Pac4jDelegatedAuthenticationScimProvisioningProperties();
    @NestedConfigurationProperty
    private Pac4jDelegatedAuthenticationRestfulProvisioningProperties rest = new Pac4jDelegatedAuthenticationRestfulProvisioningProperties();
    @NestedConfigurationProperty
    private Pac4jDelegatedAuthenticationGroovyProvisioningProperties groovy = new Pac4jDelegatedAuthenticationGroovyProvisioningProperties();

    @Generated
    public Pac4jDelegatedAuthenticationScimProvisioningProperties getScim() {
        return this.scim;
    }

    @Generated
    public Pac4jDelegatedAuthenticationRestfulProvisioningProperties getRest() {
        return this.rest;
    }

    @Generated
    public Pac4jDelegatedAuthenticationGroovyProvisioningProperties getGroovy() {
        return this.groovy;
    }

    @Generated
    public Pac4jDelegatedAuthenticationProvisioningProperties setScim(Pac4jDelegatedAuthenticationScimProvisioningProperties scim) {
        this.scim = scim;
        return this;
    }

    @Generated
    public Pac4jDelegatedAuthenticationProvisioningProperties setRest(Pac4jDelegatedAuthenticationRestfulProvisioningProperties rest) {
        this.rest = rest;
        return this;
    }

    @Generated
    public Pac4jDelegatedAuthenticationProvisioningProperties setGroovy(Pac4jDelegatedAuthenticationGroovyProvisioningProperties groovy) {
        this.groovy = groovy;
        return this;
    }
}

