/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.cassandra.authentication;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Generated;
import org.apereo.cas.configuration.support.DurationCapable;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-cassandra-core")
@JsonFilter(value="BaseCassandraProperties")
public abstract class BaseCassandraProperties
implements Serializable {
    private static final long serialVersionUID = 3708645268337674572L;
    @RequiredProperty
    private String username;
    @RequiredProperty
    private String password;
    @RequiredProperty
    private String keyspace;
    @RequiredProperty
    private List<String> contactPoints = Stream.of("localhost:9042").collect(Collectors.toList());
    private String localDc;
    private String consistencyLevel = "LOCAL_QUORUM";
    @DurationCapable
    private String timeout = "PT5S";
    private String serialConsistencyLevel = "LOCAL_SERIAL";

    @Generated
    public String getUsername() {
        return this.username;
    }

    @Generated
    public String getPassword() {
        return this.password;
    }

    @Generated
    public String getKeyspace() {
        return this.keyspace;
    }

    @Generated
    public List<String> getContactPoints() {
        return this.contactPoints;
    }

    @Generated
    public String getLocalDc() {
        return this.localDc;
    }

    @Generated
    public String getConsistencyLevel() {
        return this.consistencyLevel;
    }

    @Generated
    public String getTimeout() {
        return this.timeout;
    }

    @Generated
    public String getSerialConsistencyLevel() {
        return this.serialConsistencyLevel;
    }

    @Generated
    public BaseCassandraProperties setUsername(String username) {
        this.username = username;
        return this;
    }

    @Generated
    public BaseCassandraProperties setPassword(String password) {
        this.password = password;
        return this;
    }

    @Generated
    public BaseCassandraProperties setKeyspace(String keyspace) {
        this.keyspace = keyspace;
        return this;
    }

    @Generated
    public BaseCassandraProperties setContactPoints(List<String> contactPoints) {
        this.contactPoints = contactPoints;
        return this;
    }

    @Generated
    public BaseCassandraProperties setLocalDc(String localDc) {
        this.localDc = localDc;
        return this;
    }

    @Generated
    public BaseCassandraProperties setConsistencyLevel(String consistencyLevel) {
        this.consistencyLevel = consistencyLevel;
        return this;
    }

    @Generated
    public BaseCassandraProperties setTimeout(String timeout) {
        this.timeout = timeout;
        return this;
    }

    @Generated
    public BaseCassandraProperties setSerialConsistencyLevel(String serialConsistencyLevel) {
        this.serialConsistencyLevel = serialConsistencyLevel;
        return this;
    }
}

