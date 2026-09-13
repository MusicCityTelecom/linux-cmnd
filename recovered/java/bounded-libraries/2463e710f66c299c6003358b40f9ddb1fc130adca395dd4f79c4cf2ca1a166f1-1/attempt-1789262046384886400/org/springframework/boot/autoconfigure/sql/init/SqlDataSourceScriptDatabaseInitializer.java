/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.jdbc.init.DataSourceScriptDatabaseInitializer
 *  org.springframework.boot.sql.init.DatabaseInitializationSettings
 */
package org.springframework.boot.autoconfigure.sql.init;

import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.sql.init.SettingsCreator;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationProperties;
import org.springframework.boot.jdbc.init.DataSourceScriptDatabaseInitializer;
import org.springframework.boot.sql.init.DatabaseInitializationSettings;

public class SqlDataSourceScriptDatabaseInitializer
extends DataSourceScriptDatabaseInitializer {
    public SqlDataSourceScriptDatabaseInitializer(DataSource dataSource, SqlInitializationProperties properties) {
        this(dataSource, SqlDataSourceScriptDatabaseInitializer.getSettings(properties));
    }

    public SqlDataSourceScriptDatabaseInitializer(DataSource dataSource, DatabaseInitializationSettings settings) {
        super(dataSource, settings);
    }

    public static DatabaseInitializationSettings getSettings(SqlInitializationProperties properties) {
        return SettingsCreator.createFrom(properties);
    }
}

