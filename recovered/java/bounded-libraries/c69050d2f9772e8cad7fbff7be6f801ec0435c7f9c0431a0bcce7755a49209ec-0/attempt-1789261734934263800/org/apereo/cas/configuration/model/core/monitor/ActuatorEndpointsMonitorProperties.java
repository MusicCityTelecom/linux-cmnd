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
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.monitor.ActuatorEndpointProperties;
import org.apereo.cas.configuration.model.core.monitor.JaasSecurityActuatorEndpointsMonitorProperties;
import org.apereo.cas.configuration.model.core.monitor.JdbcSecurityActuatorEndpointsMonitorProperties;
import org.apereo.cas.configuration.model.core.monitor.LdapSecurityActuatorEndpointsMonitorProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-reports")
@JsonFilter(value="ActuatorEndpointsMonitorProperties")
public class ActuatorEndpointsMonitorProperties
implements Serializable {
    private static final long serialVersionUID = -3375777593395683691L;
    private Map<String, ActuatorEndpointProperties> endpoint = new HashMap<String, ActuatorEndpointProperties>(0);
    @NestedConfigurationProperty
    private JaasSecurityActuatorEndpointsMonitorProperties jaas = new JaasSecurityActuatorEndpointsMonitorProperties();
    @NestedConfigurationProperty
    private JdbcSecurityActuatorEndpointsMonitorProperties jdbc = new JdbcSecurityActuatorEndpointsMonitorProperties();
    @NestedConfigurationProperty
    private LdapSecurityActuatorEndpointsMonitorProperties ldap = new LdapSecurityActuatorEndpointsMonitorProperties();
    private boolean formLoginEnabled;

    public ActuatorEndpointsMonitorProperties() {
        ActuatorEndpointProperties defaultProps = new ActuatorEndpointProperties();
        defaultProps.setAccess(Stream.of(ActuatorEndpointProperties.EndpointAccessLevel.DENY).collect(Collectors.toList()));
        this.getEndpoint().put("defaults", defaultProps);
    }

    public ActuatorEndpointProperties getDefaultEndpointProperties() {
        return this.getEndpoint().get("defaults");
    }

    @Generated
    public Map<String, ActuatorEndpointProperties> getEndpoint() {
        return this.endpoint;
    }

    @Generated
    public JaasSecurityActuatorEndpointsMonitorProperties getJaas() {
        return this.jaas;
    }

    @Generated
    public JdbcSecurityActuatorEndpointsMonitorProperties getJdbc() {
        return this.jdbc;
    }

    @Generated
    public LdapSecurityActuatorEndpointsMonitorProperties getLdap() {
        return this.ldap;
    }

    @Generated
    public boolean isFormLoginEnabled() {
        return this.formLoginEnabled;
    }

    @Generated
    public ActuatorEndpointsMonitorProperties setEndpoint(Map<String, ActuatorEndpointProperties> endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    @Generated
    public ActuatorEndpointsMonitorProperties setJaas(JaasSecurityActuatorEndpointsMonitorProperties jaas) {
        this.jaas = jaas;
        return this;
    }

    @Generated
    public ActuatorEndpointsMonitorProperties setJdbc(JdbcSecurityActuatorEndpointsMonitorProperties jdbc) {
        this.jdbc = jdbc;
        return this;
    }

    @Generated
    public ActuatorEndpointsMonitorProperties setLdap(LdapSecurityActuatorEndpointsMonitorProperties ldap) {
        this.ldap = ldap;
        return this;
    }

    @Generated
    public ActuatorEndpointsMonitorProperties setFormLoginEnabled(boolean formLoginEnabled) {
        this.formLoginEnabled = formLoginEnabled;
        return this;
    }

    @Generated
    public String toString() {
        return "ActuatorEndpointsMonitorProperties(endpoint=" + this.endpoint + ", jaas=" + this.jaas + ", jdbc=" + this.jdbc + ", ldap=" + this.ldap + ", formLoginEnabled=" + this.formLoginEnabled + ")";
    }
}

