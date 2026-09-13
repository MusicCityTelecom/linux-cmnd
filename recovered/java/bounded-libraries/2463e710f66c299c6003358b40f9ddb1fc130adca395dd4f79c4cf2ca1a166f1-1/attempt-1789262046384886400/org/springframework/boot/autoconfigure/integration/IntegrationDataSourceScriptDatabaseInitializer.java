/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.jdbc.DatabaseDriver
 *  org.springframework.boot.jdbc.init.DataSourceScriptDatabaseInitializer
 *  org.springframework.boot.jdbc.init.PlatformPlaceholderDatabaseDriverResolver
 *  org.springframework.boot.sql.init.DatabaseInitializationSettings
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.autoconfigure.integration;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.integration.IntegrationProperties;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.boot.jdbc.init.DataSourceScriptDatabaseInitializer;
import org.springframework.boot.jdbc.init.PlatformPlaceholderDatabaseDriverResolver;
import org.springframework.boot.sql.init.DatabaseInitializationSettings;
import org.springframework.util.StringUtils;

public class IntegrationDataSourceScriptDatabaseInitializer
extends DataSourceScriptDatabaseInitializer {
    public IntegrationDataSourceScriptDatabaseInitializer(DataSource dataSource, IntegrationProperties.Jdbc properties) {
        this(dataSource, IntegrationDataSourceScriptDatabaseInitializer.getSettings(dataSource, properties));
    }

    public IntegrationDataSourceScriptDatabaseInitializer(DataSource dataSource, DatabaseInitializationSettings settings) {
        super(dataSource, settings);
    }

    static DatabaseInitializationSettings getSettings(DataSource dataSource, IntegrationProperties.Jdbc properties) {
        DatabaseInitializationSettings settings = new DatabaseInitializationSettings();
        settings.setSchemaLocations(IntegrationDataSourceScriptDatabaseInitializer.resolveSchemaLocations(dataSource, properties));
        settings.setMode(properties.getInitializeSchema());
        settings.setContinueOnError(true);
        return settings;
    }

    private static List<String> resolveSchemaLocations(DataSource dataSource, IntegrationProperties.Jdbc properties) {
        PlatformPlaceholderDatabaseDriverResolver platformResolver = new PlatformPlaceholderDatabaseDriverResolver();
        platformResolver = platformResolver.withDriverPlatform(DatabaseDriver.MARIADB, "mysql");
        if (StringUtils.hasText((String)properties.getPlatform())) {
            return platformResolver.resolveAll(properties.getPlatform(), new String[]{properties.getSchema()});
        }
        return platformResolver.resolveAll(dataSource, new String[]{properties.getSchema()});
    }
}

