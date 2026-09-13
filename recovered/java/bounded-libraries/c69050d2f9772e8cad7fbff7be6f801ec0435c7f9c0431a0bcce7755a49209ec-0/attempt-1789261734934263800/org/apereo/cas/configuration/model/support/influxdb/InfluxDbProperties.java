/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.influxdb;

import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-influxdb-core")
public class InfluxDbProperties
implements Serializable {
    private static final long serialVersionUID = -1945287308473842616L;
    @RequiredProperty
    private String url = "http://localhost:8086";
    @RequiredProperty
    private String username = "root";
    @RequiredProperty
    private String password = "password";
    @RequiredProperty
    private String database;
    @RequiredProperty
    private String organization = "CAS";

    @Generated
    public String getUrl() {
        return this.url;
    }

    @Generated
    public String getUsername() {
        return this.username;
    }

    @Generated
    public String getPassword() {
        return this.password;
    }

    @Generated
    public String getDatabase() {
        return this.database;
    }

    @Generated
    public String getOrganization() {
        return this.organization;
    }

    @Generated
    public InfluxDbProperties setUrl(String url) {
        this.url = url;
        return this;
    }

    @Generated
    public InfluxDbProperties setUsername(String username) {
        this.username = username;
        return this;
    }

    @Generated
    public InfluxDbProperties setPassword(String password) {
        this.password = password;
        return this;
    }

    @Generated
    public InfluxDbProperties setDatabase(String database) {
        this.database = database;
        return this;
    }

    @Generated
    public InfluxDbProperties setOrganization(String organization) {
        this.organization = organization;
        return this;
    }
}

