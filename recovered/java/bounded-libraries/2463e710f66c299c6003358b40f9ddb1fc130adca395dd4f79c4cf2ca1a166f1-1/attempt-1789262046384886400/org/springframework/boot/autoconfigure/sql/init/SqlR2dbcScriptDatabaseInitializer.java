/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.r2dbc.spi.ConnectionFactory
 *  org.springframework.boot.r2dbc.init.R2dbcScriptDatabaseInitializer
 *  org.springframework.boot.sql.init.DatabaseInitializationSettings
 */
package org.springframework.boot.autoconfigure.sql.init;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.boot.autoconfigure.sql.init.SettingsCreator;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationProperties;
import org.springframework.boot.r2dbc.init.R2dbcScriptDatabaseInitializer;
import org.springframework.boot.sql.init.DatabaseInitializationSettings;

public class SqlR2dbcScriptDatabaseInitializer
extends R2dbcScriptDatabaseInitializer {
    public SqlR2dbcScriptDatabaseInitializer(ConnectionFactory connectionFactory, SqlInitializationProperties properties) {
        super(connectionFactory, SqlR2dbcScriptDatabaseInitializer.getSettings(properties));
    }

    public SqlR2dbcScriptDatabaseInitializer(ConnectionFactory connectionFactory, DatabaseInitializationSettings settings) {
        super(connectionFactory, settings);
    }

    public static DatabaseInitializationSettings getSettings(SqlInitializationProperties properties) {
        return SettingsCreator.createFrom(properties);
    }
}

