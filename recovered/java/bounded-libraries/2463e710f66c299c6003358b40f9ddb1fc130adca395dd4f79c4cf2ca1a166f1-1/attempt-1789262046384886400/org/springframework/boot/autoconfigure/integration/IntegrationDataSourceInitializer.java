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
package org.springframework.boot.autoconfigure.integration;

import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.integration.IntegrationProperties;
import org.springframework.boot.jdbc.AbstractDataSourceInitializer;
import org.springframework.boot.jdbc.DataSourceInitializationMode;
import org.springframework.boot.sql.init.DatabaseInitializationMode;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Deprecated
public class IntegrationDataSourceInitializer
extends AbstractDataSourceInitializer {
    private final IntegrationProperties.Jdbc properties;

    public IntegrationDataSourceInitializer(DataSource dataSource, ResourceLoader resourceLoader, IntegrationProperties properties) {
        super(dataSource, resourceLoader);
        Assert.notNull((Object)properties, (String)"IntegrationProperties must not be null");
        this.properties = properties.getJdbc();
    }

    protected DataSourceInitializationMode getMode() {
        DatabaseInitializationMode mode = this.properties.getInitializeSchema();
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
        return this.properties.getSchema();
    }

    protected String getDatabaseName() {
        String platform = this.properties.getPlatform();
        if (StringUtils.hasText((String)platform)) {
            return platform;
        }
        return super.getDatabaseName();
    }
}

