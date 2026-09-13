/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.aup;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.jpa.AbstractJpaProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-aup-jdbc")
@JsonFilter(value="JdbcAcceptableUsagePolicyProperties")
public class JdbcAcceptableUsagePolicyProperties
extends AbstractJpaProperties {
    private static final long serialVersionUID = -1325011278378393385L;
    private String tableName;
    private String aupColumn;
    private String principalIdColumn = "username";
    private String principalIdAttribute;
    private String sqlUpdate = "UPDATE %s SET %s=true WHERE %s=?";
    private String sqlSelect = "SELECT %s FROM %s WHERE %s=?";

    @Generated
    public String getTableName() {
        return this.tableName;
    }

    @Generated
    public String getAupColumn() {
        return this.aupColumn;
    }

    @Generated
    public String getPrincipalIdColumn() {
        return this.principalIdColumn;
    }

    @Generated
    public String getPrincipalIdAttribute() {
        return this.principalIdAttribute;
    }

    @Generated
    public String getSqlUpdate() {
        return this.sqlUpdate;
    }

    @Generated
    public String getSqlSelect() {
        return this.sqlSelect;
    }

    @Generated
    public JdbcAcceptableUsagePolicyProperties setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    @Generated
    public JdbcAcceptableUsagePolicyProperties setAupColumn(String aupColumn) {
        this.aupColumn = aupColumn;
        return this;
    }

    @Generated
    public JdbcAcceptableUsagePolicyProperties setPrincipalIdColumn(String principalIdColumn) {
        this.principalIdColumn = principalIdColumn;
        return this;
    }

    @Generated
    public JdbcAcceptableUsagePolicyProperties setPrincipalIdAttribute(String principalIdAttribute) {
        this.principalIdAttribute = principalIdAttribute;
        return this;
    }

    @Generated
    public JdbcAcceptableUsagePolicyProperties setSqlUpdate(String sqlUpdate) {
        this.sqlUpdate = sqlUpdate;
        return this;
    }

    @Generated
    public JdbcAcceptableUsagePolicyProperties setSqlSelect(String sqlSelect) {
        this.sqlSelect = sqlSelect;
        return this;
    }
}

