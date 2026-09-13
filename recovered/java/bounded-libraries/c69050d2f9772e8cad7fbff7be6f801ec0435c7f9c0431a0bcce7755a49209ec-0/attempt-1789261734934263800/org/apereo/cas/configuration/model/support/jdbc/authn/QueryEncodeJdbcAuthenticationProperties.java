/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.jdbc.authn;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.jdbc.authn.BaseJdbcAuthenticationProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-jdbc-authentication")
@JsonFilter(value="QueryEncodeJdbcAuthenticationProperties")
public class QueryEncodeJdbcAuthenticationProperties
extends BaseJdbcAuthenticationProperties {
    private static final long serialVersionUID = -6647373426301411768L;
    private String algorithmName;
    @RequiredProperty
    private String sql;
    private String passwordFieldName = "password";
    @RequiredProperty
    private String saltFieldName = "salt";
    private String expiredFieldName;
    private String disabledFieldName;
    private String numberOfIterationsFieldName = "numIterations";
    private int numberOfIterations;
    private String staticSalt;

    @Generated
    public String getAlgorithmName() {
        return this.algorithmName;
    }

    @Generated
    public String getSql() {
        return this.sql;
    }

    @Generated
    public String getPasswordFieldName() {
        return this.passwordFieldName;
    }

    @Generated
    public String getSaltFieldName() {
        return this.saltFieldName;
    }

    @Generated
    public String getExpiredFieldName() {
        return this.expiredFieldName;
    }

    @Generated
    public String getDisabledFieldName() {
        return this.disabledFieldName;
    }

    @Generated
    public String getNumberOfIterationsFieldName() {
        return this.numberOfIterationsFieldName;
    }

    @Generated
    public int getNumberOfIterations() {
        return this.numberOfIterations;
    }

    @Generated
    public String getStaticSalt() {
        return this.staticSalt;
    }

    @Generated
    public QueryEncodeJdbcAuthenticationProperties setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
        return this;
    }

    @Generated
    public QueryEncodeJdbcAuthenticationProperties setSql(String sql) {
        this.sql = sql;
        return this;
    }

    @Generated
    public QueryEncodeJdbcAuthenticationProperties setPasswordFieldName(String passwordFieldName) {
        this.passwordFieldName = passwordFieldName;
        return this;
    }

    @Generated
    public QueryEncodeJdbcAuthenticationProperties setSaltFieldName(String saltFieldName) {
        this.saltFieldName = saltFieldName;
        return this;
    }

    @Generated
    public QueryEncodeJdbcAuthenticationProperties setExpiredFieldName(String expiredFieldName) {
        this.expiredFieldName = expiredFieldName;
        return this;
    }

    @Generated
    public QueryEncodeJdbcAuthenticationProperties setDisabledFieldName(String disabledFieldName) {
        this.disabledFieldName = disabledFieldName;
        return this;
    }

    @Generated
    public QueryEncodeJdbcAuthenticationProperties setNumberOfIterationsFieldName(String numberOfIterationsFieldName) {
        this.numberOfIterationsFieldName = numberOfIterationsFieldName;
        return this;
    }

    @Generated
    public QueryEncodeJdbcAuthenticationProperties setNumberOfIterations(int numberOfIterations) {
        this.numberOfIterations = numberOfIterations;
        return this;
    }

    @Generated
    public QueryEncodeJdbcAuthenticationProperties setStaticSalt(String staticSalt) {
        this.staticSalt = staticSalt;
        return this;
    }
}

