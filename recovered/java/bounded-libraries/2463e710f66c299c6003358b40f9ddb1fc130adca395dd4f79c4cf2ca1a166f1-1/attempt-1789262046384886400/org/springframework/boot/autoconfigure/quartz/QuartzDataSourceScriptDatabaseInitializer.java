/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.jdbc.DatabaseDriver
 *  org.springframework.boot.jdbc.init.DataSourceScriptDatabaseInitializer
 *  org.springframework.boot.jdbc.init.PlatformPlaceholderDatabaseDriverResolver
 *  org.springframework.boot.sql.init.DatabaseInitializationSettings
 *  org.springframework.jdbc.datasource.init.ResourceDatabasePopulator
 *  org.springframework.util.ObjectUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.autoconfigure.quartz;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.boot.jdbc.init.DataSourceScriptDatabaseInitializer;
import org.springframework.boot.jdbc.init.PlatformPlaceholderDatabaseDriverResolver;
import org.springframework.boot.sql.init.DatabaseInitializationSettings;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class QuartzDataSourceScriptDatabaseInitializer
extends DataSourceScriptDatabaseInitializer {
    private final List<String> commentPrefixes;

    public QuartzDataSourceScriptDatabaseInitializer(DataSource dataSource, QuartzProperties properties) {
        this(dataSource, QuartzDataSourceScriptDatabaseInitializer.getSettings(dataSource, properties), properties.getJdbc().getCommentPrefix());
    }

    public QuartzDataSourceScriptDatabaseInitializer(DataSource dataSource, DatabaseInitializationSettings settings) {
        this(dataSource, settings, null);
    }

    private QuartzDataSourceScriptDatabaseInitializer(DataSource dataSource, DatabaseInitializationSettings settings, List<String> commentPrefixes) {
        super(dataSource, settings);
        this.commentPrefixes = commentPrefixes;
    }

    protected void customize(ResourceDatabasePopulator populator) {
        if (!ObjectUtils.isEmpty(this.commentPrefixes)) {
            populator.setCommentPrefixes(this.commentPrefixes.toArray(new String[0]));
        }
    }

    public static DatabaseInitializationSettings getSettings(DataSource dataSource, QuartzProperties properties) {
        DatabaseInitializationSettings settings = new DatabaseInitializationSettings();
        settings.setSchemaLocations(QuartzDataSourceScriptDatabaseInitializer.resolveSchemaLocations(dataSource, properties.getJdbc()));
        settings.setMode(properties.getJdbc().getInitializeSchema());
        settings.setContinueOnError(true);
        return settings;
    }

    private static List<String> resolveSchemaLocations(DataSource dataSource, QuartzProperties.Jdbc properties) {
        PlatformPlaceholderDatabaseDriverResolver platformResolver = new PlatformPlaceholderDatabaseDriverResolver();
        platformResolver = platformResolver.withDriverPlatform(DatabaseDriver.DB2, "db2_v95");
        platformResolver = platformResolver.withDriverPlatform(DatabaseDriver.MYSQL, "mysql_innodb");
        platformResolver = platformResolver.withDriverPlatform(DatabaseDriver.MARIADB, "mysql_innodb");
        platformResolver = platformResolver.withDriverPlatform(DatabaseDriver.POSTGRESQL, "postgres");
        platformResolver = platformResolver.withDriverPlatform(DatabaseDriver.SQLSERVER, "sqlServer");
        if (StringUtils.hasText((String)properties.getPlatform())) {
            return platformResolver.resolveAll(properties.getPlatform(), new String[]{properties.getSchema()});
        }
        return platformResolver.resolveAll(dataSource, new String[]{properties.getSchema()});
    }
}

