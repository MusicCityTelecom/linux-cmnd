/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.wsfed;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.wsfed.WsFederationIdentityProviderProperties;
import org.apereo.cas.configuration.model.support.wsfed.WsFederationSecurityTokenServiceProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-ws-idp")
@JsonFilter(value="WsFederationProperties")
public class WsFederationProperties
implements Serializable {
    private static final long serialVersionUID = -8679379856243224647L;
    @NestedConfigurationProperty
    private WsFederationIdentityProviderProperties idp = new WsFederationIdentityProviderProperties();
    @NestedConfigurationProperty
    private WsFederationSecurityTokenServiceProperties sts = new WsFederationSecurityTokenServiceProperties();

    @Generated
    public WsFederationIdentityProviderProperties getIdp() {
        return this.idp;
    }

    @Generated
    public WsFederationSecurityTokenServiceProperties getSts() {
        return this.sts;
    }

    @Generated
    public WsFederationProperties setIdp(WsFederationIdentityProviderProperties idp) {
        this.idp = idp;
        return this;
    }

    @Generated
    public WsFederationProperties setSts(WsFederationSecurityTokenServiceProperties sts) {
        this.sts = sts;
        return this;
    }
}

