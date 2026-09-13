/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.jdbc.AbstractDataSourceInitializer
 *  org.springframework.boot.jdbc.DataSourceInitializationMode
 *  org.springframework.boot.sql.init.DatabaseInitializationMode
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.autoconfigure.batch;

import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.jdbc.AbstractDataSourceInitializer;
import org.springframework.boot.jdbc.DataSourceInitializationMode;
import org.springframework.boot.sql.init.DatabaseInitializationMode;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Deprecated
public class BatchDataSourceInitializer
extends AbstractDataSourceInitializer {
    private final BatchProperties.Jdbc jdbcProperties;

    public BatchDataSourceInitializer(DataSource dataSource, ResourceLoader resourceLoader, BatchProperties properties) {
        super(dataSource, resourceLoader);
        Assert.notNull((Object)properties, (String)"BatchProperties must not be null");
        this.jdbcProperties = properties.getJdbc();
    }

    protected DataSourceInitializationMode getMode() {
        DatabaseInitializationMode mode = this.jdbcProperties.getInitializeSchema();
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
        return this.jdbcProperties.getSchema();
    }

    protected String getDatabaseName() {
        String platform = this.jdbcProperties.getPlatform();
        if (StringUtils.hasText((String)platform)) {
            return platform;
        }
        String databaseName = super.getDatabaseName();
        if ("oracle".equals(databaseName)) {
            return "oracle10g";
        }
        if ("mariadb".equals(databaseName)) {
            return "mysql";
        }
        return databaseName;
    }
}

