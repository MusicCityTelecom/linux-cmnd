/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.jdbc.authn;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.jdbc.authn.BaseJdbcAuthenticationProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-jdbc-authentication")
@JsonFilter(value="QueryJdbcAuthenticationProperties")
public class QueryJdbcAuthenticationProperties
extends BaseJdbcAuthenticationProperties {
    private static final long serialVersionUID = 7806132208223986680L;
    @RequiredProperty
    private String sql;
    @RequiredProperty
    private String fieldPassword;
    private String fieldExpired;
    private String fieldDisabled;
    private List<String> principalAttributeList = new ArrayList<String>(0);

    @Generated
    public String getSql() {
        return this.sql;
    }

    @Generated
    public String getFieldPassword() {
        return this.fieldPassword;
    }

    @Generated
    public String getFieldExpired() {
        return this.fieldExpired;
    }

    @Generated
    public String getFieldDisabled() {
        return this.fieldDisabled;
    }

    @Generated
    public List<String> getPrincipalAttributeList() {
        return this.principalAttributeList;
    }

    @Generated
    public QueryJdbcAuthenticationProperties setSql(String sql) {
        this.sql = sql;
        return this;
    }

    @Generated
    public QueryJdbcAuthenticationProperties setFieldPassword(String fieldPassword) {
        this.fieldPassword = fieldPassword;
        return this;
    }

    @Generated
    public QueryJdbcAuthenticationProperties setFieldExpired(String fieldExpired) {
        this.fieldExpired = fieldExpired;
        return this;
    }

    @Generated
    public QueryJdbcAuthenticationProperties setFieldDisabled(String fieldDisabled) {
        this.fieldDisabled = fieldDisabled;
        return this;
    }

    @Generated
    public QueryJdbcAuthenticationProperties setPrincipalAttributeList(List<String> principalAttributeList) {
        this.principalAttributeList = principalAttributeList;
        return this;
    }
}

