/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.throttle;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.quartz.SchedulingProperties;
import org.apereo.cas.configuration.model.support.throttle.Bucket4jThrottleProperties;
import org.apereo.cas.configuration.model.support.throttle.HazelcastThrottleProperties;
import org.apereo.cas.configuration.model.support.throttle.JdbcThrottleProperties;
import org.apereo.cas.configuration.model.support.throttle.LdapThrottleProperties;
import org.apereo.cas.configuration.model.support.throttle.ThrottleCoreProperties;
import org.apereo.cas.configuration.model.support.throttle.ThrottleFailureProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-throttle")
@JsonFilter(value="ThrottleProperties")
public class ThrottleProperties
implements Serializable {
    private static final long serialVersionUID = 6813165633105563813L;
    @NestedConfigurationProperty
    private ThrottleFailureProperties failure = new ThrottleFailureProperties();
    @NestedConfigurationProperty
    private JdbcThrottleProperties jdbc = new JdbcThrottleProperties();
    @NestedConfigurationProperty
    private Bucket4jThrottleProperties bucket4j = new Bucket4jThrottleProperties();
    @NestedConfigurationProperty
    private HazelcastThrottleProperties hazelcast = new HazelcastThrottleProperties();
    @NestedConfigurationProperty
    private LdapThrottleProperties ldap = new LdapThrottleProperties();
    @NestedConfigurationProperty
    private ThrottleCoreProperties core = new ThrottleCoreProperties();
    @NestedConfigurationProperty
    private SchedulingProperties schedule = new SchedulingProperties();

    public ThrottleProperties() {
        this.schedule.setEnabled(true);
        this.schedule.setStartDelay("PT10S");
        this.schedule.setRepeatInterval("PT30S");
    }

    @Generated
    public ThrottleFailureProperties getFailure() {
        return this.failure;
    }

    @Generated
    public JdbcThrottleProperties getJdbc() {
        return this.jdbc;
    }

    @Generated
    public Bucket4jThrottleProperties getBucket4j() {
        return this.bucket4j;
    }

    @Generated
    public HazelcastThrottleProperties getHazelcast() {
        return this.hazelcast;
    }

    @Generated
    public LdapThrottleProperties getLdap() {
        return this.ldap;
    }

    @Generated
    public ThrottleCoreProperties getCore() {
        return this.core;
    }

    @Generated
    public SchedulingProperties getSchedule() {
        return this.schedule;
    }

    @Generated
    public ThrottleProperties setFailure(ThrottleFailureProperties failure) {
        this.failure = failure;
        return this;
    }

    @Generated
    public ThrottleProperties setJdbc(JdbcThrottleProperties jdbc) {
        this.jdbc = jdbc;
        return this;
    }

    @Generated
    public ThrottleProperties setBucket4j(Bucket4jThrottleProperties bucket4j) {
        this.bucket4j = bucket4j;
        return this;
    }

    @Generated
    public ThrottleProperties setHazelcast(HazelcastThrottleProperties hazelcast) {
        this.hazelcast = hazelcast;
        return this;
    }

    @Generated
    public ThrottleProperties setLdap(LdapThrottleProperties ldap) {
        this.ldap = ldap;
        return this;
    }

    @Generated
    public ThrottleProperties setCore(ThrottleCoreProperties core) {
        this.core = core;
        return this;
    }

    @Generated
    public ThrottleProperties setSchedule(SchedulingProperties schedule) {
        this.schedule = schedule;
        return this;
    }
}

