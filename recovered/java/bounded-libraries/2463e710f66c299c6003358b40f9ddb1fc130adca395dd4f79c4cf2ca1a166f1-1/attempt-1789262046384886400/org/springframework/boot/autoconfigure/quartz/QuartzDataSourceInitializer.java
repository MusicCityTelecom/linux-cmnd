/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.jdbc.AbstractDataSourceInitializer
 *  org.springframework.boot.jdbc.DataSourceInitializationMode
 *  org.springframework.boot.sql.init.DatabaseInitializationMode
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.jdbc.datasource.init.ResourceDatabasePopulator
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.autoconfigure.quartz;

import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.boot.jdbc.AbstractDataSourceInitializer;
import org.springframework.boot.jdbc.DataSourceInitializationMode;
import org.springframework.boot.sql.init.DatabaseInitializationMode;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Deprecated
public class QuartzDataSourceInitializer
extends AbstractDataSourceInitializer {
    private final QuartzProperties properties;

    public QuartzDataSourceInitializer(DataSource dataSource, ResourceLoader resourceLoader, QuartzProperties properties) {
        super(dataSource, resourceLoader);
        Assert.notNull((Object)properties, (String)"QuartzProperties must not be null");
        this.properties = properties;
    }

    protected void customize(ResourceDatabasePopulator populator) {
        populator.setCommentPrefixes(this.properties.getJdbc().getCommentPrefix().toArray(new String[0]));
    }

    protected DataSourceInitializationMode getMode() {
        DatabaseInitializationMode mode = this.properties.getJdbc().getInitializeSchema();
        switch (mode) {
            case ALWAYS: {
                return DataSourceInitializationMode.ALWAYS;
            }
            case EMBEDDED: {
                return DataSourceInitializationMode.EMBEDDED;
            }
        }
        return DataSourceInitializationMode.NEVER;
    }

    protected String getSchemaLocation() {
        return this.properties.getJdbc().getSchema();
    }

    protected String getDatabaseName() {
        String platform = this.properties.getJdbc().getPlatform();
        if (StringUtils.hasText((String)platform)) {
            return platform;
        }
        String databaseName = super.getDatabaseName();
        if ("db2".equals(databaseName)) {
            return "db2_v95";
        }
        if ("mysql".equals(databaseName) || "mariadb".equals(databaseName)) {
            return "mysql_innodb";
        }
        if ("postgresql".equals(databaseName)) {
            return "postgres";
        }
        if ("sqlserver".equals(databaseName)) {
            return "sqlServer";
        }
        return databaseName;
    }
}

