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
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.monitor.ActuatorEndpointsMonitorProperties;
import org.apereo.cas.configuration.model.core.monitor.JdbcMonitorProperties;
import org.apereo.cas.configuration.model.core.monitor.LdapMonitorProperties;
import org.apereo.cas.configuration.model.core.monitor.MemcachedMonitorProperties;
import org.apereo.cas.configuration.model.core.monitor.MemoryMonitorProperties;
import org.apereo.cas.configuration.model.core.monitor.MongoDbMonitorProperties;
import org.apereo.cas.configuration.model.core.monitor.MonitorWarningProperties;
import org.apereo.cas.configuration.model.core.monitor.ServerLoadMonitorProperties;
import org.apereo.cas.configuration.model.core.monitor.ServiceTicketMonitorProperties;
import org.apereo.cas.configuration.model.core.monitor.TicketGrantingTicketMonitorProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-core-monitor", automated=true)
@JsonFilter(value="MonitorProperties")
public class MonitorProperties
implements Serializable {
    private static final long serialVersionUID = -7047060071480971606L;
    @NestedConfigurationProperty
    private MemoryMonitorProperties memory = new MemoryMonitorProperties();
    @NestedConfigurationProperty
    private TicketGrantingTicketMonitorProperties tgt = new TicketGrantingTicketMonitorProperties();
    @NestedConfigurationProperty
    private ServiceTicketMonitorProperties st = new ServiceTicketMonitorProperties();
    @NestedConfigurationProperty
    private ServerLoadMonitorProperties load = new ServerLoadMonitorProperties();
    @NestedConfigurationProperty
    private MonitorWarningProperties warn = new MonitorWarningProperties();
    @NestedConfigurationProperty
    private JdbcMonitorProperties jdbc = new JdbcMonitorProperties();
    private List<LdapMonitorProperties> ldap = new ArrayList<LdapMonitorProperties>(0);
    @NestedConfigurationProperty
    private MemcachedMonitorProperties memcached = new MemcachedMonitorProperties();
    private List<MongoDbMonitorProperties> mongo = new ArrayList<MongoDbMonitorProperties>();
    @NestedConfigurationProperty
    private ActuatorEndpointsMonitorProperties endpoints = new ActuatorEndpointsMonitorProperties();

    @Generated
    public MemoryMonitorProperties getMemory() {
        return this.memory;
    }

    @Generated
    public TicketGrantingTicketMonitorProperties getTgt() {
        return this.tgt;
    }

    @Generated
    public ServiceTicketMonitorProperties getSt() {
        return this.st;
    }

    @Generated
    public ServerLoadMonitorProperties getLoad() {
        return this.load;
    }

    @Generated
    public MonitorWarningProperties getWarn() {
        return this.warn;
    }

    @Generated
    public JdbcMonitorProperties getJdbc() {
        return this.jdbc;
    }

    @Generated
    public List<LdapMonitorProperties> getLdap() {
        return this.ldap;
    }

    @Generated
    public MemcachedMonitorProperties getMemcached() {
        return this.memcached;
    }

    @Generated
    public List<MongoDbMonitorProperties> getMongo() {
        return this.mongo;
    }

    @Generated
    public ActuatorEndpointsMonitorProperties getEndpoints() {
        return this.endpoints;
    }

    @Generated
    public MonitorProperties setMemory(MemoryMonitorProperties memory) {
        this.memory = memory;
        return this;
    }

    @Generated
    public MonitorProperties setTgt(TicketGrantingTicketMonitorProperties tgt) {
        this.tgt = tgt;
        return this;
    }

    @Generated
    public MonitorProperties setSt(ServiceTicketMonitorProperties st) {
        this.st = st;
        return this;
    }

    @Generated
    public MonitorProperties setLoad(ServerLoadMonitorProperties load) {
        this.load = load;
        return this;
    }

    @Generated
    public MonitorProperties setWarn(MonitorWarningProperties warn) {
        this.warn = warn;
        return this;
    }

    @Generated
    public MonitorProperties setJdbc(JdbcMonitorProperties jdbc) {
        this.jdbc = jdbc;
        return this;
    }

    @Generated
    public MonitorProperties setLdap(List<LdapMonitorProperties> ldap) {
        this.ldap = ldap;
        return this;
    }

    @Generated
    public MonitorProperties setMemcached(MemcachedMonitorProperties memcached) {
        this.memcached = memcached;
        return this;
    }

    @Generated
    public MonitorProperties setMongo(List<MongoDbMonitorProperties> mongo) {
        this.mongo = mongo;
        return this;
    }

    @Generated
    public MonitorProperties setEndpoints(ActuatorEndpointsMonitorProperties endpoints) {
        this.endpoints = endpoints;
        return this;
    }
}

