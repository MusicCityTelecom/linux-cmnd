/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.pm;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.authentication.PasswordEncoderProperties;
import org.apereo.cas.configuration.model.support.jpa.AbstractJpaProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-pm-jdbc")
@JsonFilter(value="JdbcPasswordManagementProperties")
public class JdbcPasswordManagementProperties
extends AbstractJpaProperties {
    private static final long serialVersionUID = 4746591112640513465L;
    @NestedConfigurationProperty
    private PasswordEncoderProperties passwordEncoder = new PasswordEncoderProperties();
    @RequiredProperty
    private String sqlChangePassword;
    @RequiredProperty
    private String sqlFindEmail;
    @RequiredProperty
    private String sqlFindPhone;
    @RequiredProperty
    private String sqlFindUser;
    private String sqlGetSecurityQuestions;
    private String sqlUpdateSecurityQuestions;
    private String sqlUnlockAccount;
    private String sqlDeleteSecurityQuestions;

    @Generated
    public PasswordEncoderProperties getPasswordEncoder() {
        return this.passwordEncoder;
    }

    @Generated
    public String getSqlChangePassword() {
        return this.sqlChangePassword;
    }

    @Generated
    public String getSqlFindEmail() {
        return this.sqlFindEmail;
    }

    @Generated
    public String getSqlFindPhone() {
        return this.sqlFindPhone;
    }

    @Generated
    public String getSqlFindUser() {
        return this.sqlFindUser;
    }

    @Generated
    public String getSqlGetSecurityQuestions() {
        return this.sqlGetSecurityQuestions;
    }

    @Generated
    public String getSqlUpdateSecurityQuestions() {
        return this.sqlUpdateSecurityQuestions;
    }

    @Generated
    public String getSqlUnlockAccount() {
        return this.sqlUnlockAccount;
    }

    @Generated
    public String getSqlDeleteSecurityQuestions() {
        return this.sqlDeleteSecurityQuestions;
    }

    @Generated
    public JdbcPasswordManagementProperties setPasswordEncoder(PasswordEncoderProperties passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        return this;
    }

    @Generated
    public JdbcPasswordManagementProperties setSqlChangePassword(String sqlChangePassword) {
        this.sqlChangePassword = sqlChangePassword;
        return this;
    }

    @Generated
    public JdbcPasswordManagementProperties setSqlFindEmail(String sqlFindEmail) {
        this.sqlFindEmail = sqlFindEmail;
        return this;
    }

    @Generated
    public JdbcPasswordManagementProperties setSqlFindPhone(String sqlFindPhone) {
        this.sqlFindPhone = sqlFindPhone;
        return this;
    }

    @Generated
    public JdbcPasswordManagementProperties setSqlFindUser(String sqlFindUser) {
        this.sqlFindUser = sqlFindUser;
        return this;
    }

    @Generated
    public JdbcPasswordManagementProperties setSqlGetSecurityQuestions(String sqlGetSecurityQuestions) {
        this.sqlGetSecurityQuestions = sqlGetSecurityQuestions;
        return this;
    }

    @Generated
    public JdbcPasswordManagementProperties setSqlUpdateSecurityQuestions(String sqlUpdateSecurityQuestions) {
        this.sqlUpdateSecurityQuestions = sqlUpdateSecurityQuestions;
        return this;
    }

    @Generated
    public JdbcPasswordManagementProperties setSqlUnlockAccount(String sqlUnlockAccount) {
        this.sqlUnlockAccount = sqlUnlockAccount;
        return this;
    }

    @Generated
    public JdbcPasswordManagementProperties setSqlDeleteSecurityQuestions(String sqlDeleteSecurityQuestions) {
        this.sqlDeleteSecurityQuestions = sqlDeleteSecurityQuestions;
        return this;
    }
}

