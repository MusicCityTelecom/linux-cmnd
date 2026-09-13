/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.core.monitor;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.ldap.AbstractLdapAuthenticationProperties;
import org.apereo.cas.configuration.model.support.ldap.LdapAuthorizationProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-core-monitor", automated=true)
@JsonFilter(value="LdapSecurityActuatorEndpointsMonitorProperties")
public class LdapSecurityActuatorEndpointsMonitorProperties
extends AbstractLdapAuthenticationProperties {
    private static final long serialVersionUID = -7333244539096172557L;
    @NestedConfigurationProperty
    private LdapAuthorizationProperties ldapAuthz = new LdapAuthorizationProperties();

    @Generated
    public LdapAuthorizationProperties getLdapAuthz() {
        return this.ldapAuthz;
    }

    @Generated
    public LdapSecurityActuatorEndpointsMonitorProperties setLdapAuthz(LdapAuthorizationProperties ldapAuthz) {
        this.ldapAuthz = ldapAuthz;
        return this;
    }
}

