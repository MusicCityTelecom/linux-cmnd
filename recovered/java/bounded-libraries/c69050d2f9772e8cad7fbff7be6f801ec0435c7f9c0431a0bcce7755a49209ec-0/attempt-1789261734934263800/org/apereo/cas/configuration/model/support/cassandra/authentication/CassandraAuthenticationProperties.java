/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.cassandra.authentication;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.authentication.PasswordEncoderProperties;
import org.apereo.cas.configuration.model.core.authentication.PrincipalTransformationProperties;
import org.apereo.cas.configuration.model.support.cassandra.authentication.BaseCassandraProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-cassandra-authentication")
@JsonFilter(value="CassandraAuthenticationProperties")
public class CassandraAuthenticationProperties
extends BaseCassandraProperties {
    private static final long serialVersionUID = 1369405266376125234L;
    private String name;
    private Integer order;
    @RequiredProperty
    private String usernameAttribute;
    @RequiredProperty
    private String passwordAttribute;
    @RequiredProperty
    private String tableName;
    private String query = "SELECT * FROM %s WHERE %s = ? ALLOW FILTERING";
    @NestedConfigurationProperty
    private PasswordEncoderProperties passwordEncoder = new PasswordEncoderProperties();
    @NestedConfigurationProperty
    private PrincipalTransformationProperties principalTransformation = new PrincipalTransformationProperties();

    @Generated
    public String getName() {
        return this.name;
    }

    @Generated
    public Integer getOrder() {
        return this.order;
    }

    @Generated
    public String getUsernameAttribute() {
        return this.usernameAttribute;
    }

    @Generated
    public String getPasswordAttribute() {
        return this.passwordAttribute;
    }

    @Generated
    public String getTableName() {
        return this.tableName;
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
    public PrincipalTransformationProperties getPrincipalTransformation() {
        return this.principalTransformation;
    }

    @Generated
    public CassandraAuthenticationProperties setName(String name) {
        this.name = name;
        return this;
    }

    @Generated
    public CassandraAuthenticationProperties setOrder(Integer order) {
        this.order = order;
        return this;
    }

    @Generated
    public CassandraAuthenticationProperties setUsernameAttribute(String usernameAttribute) {
        this.usernameAttribute = usernameAttribute;
        return this;
    }

    @Generated
    public CassandraAuthenticationProperties setPasswordAttribute(String passwordAttribute) {
        this.passwordAttribute = passwordAttribute;
        return this;
    }

    @Generated
    public CassandraAuthenticationProperties setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    @Generated
    public CassandraAuthenticationProperties setQuery(String query) {
        this.query = query;
        return this;
    }

    @Generated
    public CassandraAuthenticationProperties setPasswordEncoder(PasswordEncoderProperties passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        return this;
    }

    @Generated
    public CassandraAuthenticationProperties setPrincipalTransformation(PrincipalTransformationProperties principalTransformation) {
        this.principalTransformation = principalTransformation;
        return this;
    }
}

