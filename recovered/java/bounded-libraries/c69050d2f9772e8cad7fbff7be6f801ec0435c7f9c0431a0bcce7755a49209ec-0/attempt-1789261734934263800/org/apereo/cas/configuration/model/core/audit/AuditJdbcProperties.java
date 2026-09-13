/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.core.audit;

import lombok.Generated;
import org.apereo.cas.configuration.model.support.jpa.AbstractJpaProperties;
import org.apereo.cas.configuration.model.support.quartz.SchedulingProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-audit-jdbc")
public class AuditJdbcProperties
extends AbstractJpaProperties {
    private static final long serialVersionUID = 4227475246873515918L;
    private boolean asynchronous = true;
    private int maxAgeDays = 180;
    private int columnLength = 100;
    private String selectSqlQueryTemplate;
    private String dateFormatterPattern;
    @NestedConfigurationProperty
    private SchedulingProperties schedule = new SchedulingProperties();

    @Generated
    public boolean isAsynchronous() {
        return this.asynchronous;
    }

    @Generated
    public int getMaxAgeDays() {
        return this.maxAgeDays;
    }

    @Generated
    public int getColumnLength() {
        return this.columnLength;
    }

    @Generated
    public String getSelectSqlQueryTemplate() {
        return this.selectSqlQueryTemplate;
    }

    @Generated
    public String getDateFormatterPattern() {
        return this.dateFormatterPattern;
    }

    @Generated
    public SchedulingProperties getSchedule() {
        return this.schedule;
    }

    @Generated
    public AuditJdbcProperties setAsynchronous(boolean asynchronous) {
        this.asynchronous = asynchronous;
        return this;
    }

    @Generated
    public AuditJdbcProperties setMaxAgeDays(int maxAgeDays) {
        this.maxAgeDays = maxAgeDays;
        return this;
    }

    @Generated
    public AuditJdbcProperties setColumnLength(int columnLength) {
        this.columnLength = columnLength;
        return this;
    }

    @Generated
    public AuditJdbcProperties setSelectSqlQueryTemplate(String selectSqlQueryTemplate) {
        this.selectSqlQueryTemplate = selectSqlQueryTemplate;
        return this;
    }

    @Generated
    public AuditJdbcProperties setDateFormatterPattern(String dateFormatterPattern) {
        this.dateFormatterPattern = dateFormatterPattern;
        return this;
    }

    @Generated
    public AuditJdbcProperties setSchedule(SchedulingProperties schedule) {
        this.schedule = schedule;
        return this;
    }
}

