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
@JsonFilter(value="SearchJdbcAuthenticationProperties")
public class SearchJdbcAuthenticationProperties
extends BaseJdbcAuthenticationProperties {
    private static final long serialVersionUID = 6912107600297453730L;
    @RequiredProperty
    private String fieldUser;
    @RequiredProperty
    private String fieldPassword;
    @RequiredProperty
    private String tableUsers;

    @Generated
    public String getFieldUser() {
        return this.fieldUser;
    }

    @Generated
    public String getFieldPassword() {
        return this.fieldPassword;
    }

    @Generated
    public String getTableUsers() {
        return this.tableUsers;
    }

    @Generated
    public SearchJdbcAuthenticationProperties setFieldUser(String fieldUser) {
        this.fieldUser = fieldUser;
        return this;
    }

    @Generated
    public SearchJdbcAuthenticationProperties setFieldPassword(String fieldPassword) {
        this.fieldPassword = fieldPassword;
        return this;
    }

    @Generated
    public SearchJdbcAuthenticationProperties setTableUsers(String tableUsers) {
        this.tableUsers = tableUsers;
        return this;
    }
}

