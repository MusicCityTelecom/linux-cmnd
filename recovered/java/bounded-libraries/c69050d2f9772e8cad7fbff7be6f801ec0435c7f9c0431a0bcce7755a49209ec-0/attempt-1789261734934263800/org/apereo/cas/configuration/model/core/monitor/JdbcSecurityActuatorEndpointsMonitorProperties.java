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
import org.apereo.cas.configuration.model.core.authentication.PasswordEncoderProperties;
import org.apereo.cas.configuration.model.support.jpa.AbstractJpaProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-core-monitor", automated=true)
@JsonFilter(value="JdbcSecurityActuatorEndpointsMonitorProperties")
public class JdbcSecurityActuatorEndpointsMonitorProperties
extends AbstractJpaProperties {
    private static final long serialVersionUID = 2625666117528467867L;
    private String rolePrefix;
    private String query;
    @NestedConfigurationProperty
    private PasswordEncoderProperties passwordEncoder = new PasswordEncoderProperties();

    @Generated
    public String getRolePrefix() {
        return this.rolePrefix;
    }

    @Generated
    public String getQuery() {
        return this.query;
    }

    @Generated
    public PasswordEncoderProperties getPasswordEncoder() {
        return this.passwordEncoder;
    }

    @Generated
    public JdbcSecurityActuatorEndpointsMonitorProperties setRolePrefix(String rolePrefix) {
        this.rolePrefix = rolePrefix;
        return this;
    }

    @Generated
    public JdbcSecurityActuatorEndpointsMonitorProperties setQuery(String query) {
        this.query = query;
        return this;
    }

    @Generated
    public JdbcSecurityActuatorEndpointsMonitorProperties setPasswordEncoder(PasswordEncoderProperties passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        return this;
    }
}

