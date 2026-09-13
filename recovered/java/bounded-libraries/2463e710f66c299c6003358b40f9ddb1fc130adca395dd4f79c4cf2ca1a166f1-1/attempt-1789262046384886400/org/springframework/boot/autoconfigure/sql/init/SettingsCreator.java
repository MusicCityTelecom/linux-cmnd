/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.sql.init.DatabaseInitializationSettings
 */
package org.springframework.boot.autoconfigure.sql.init;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationProperties;
import org.springframework.boot.sql.init.DatabaseInitializationSettings;

final class SettingsCreator {
    private SettingsCreator() {
    }

    static DatabaseInitializationSettings createFrom(SqlInitializationProperties properties) {
        DatabaseInitializationSettings settings = new DatabaseInitializationSettings();
        settings.setSchemaLocations(SettingsCreator.scriptLocations(properties.getSchemaLocations(), "schema", properties.getPlatform()));
        settings.setDataLocations(SettingsCreator.scriptLocations(properties.getDataLocations(), "data", properties.getPlatform()));
        settings.setContinueOnError(properties.isContinueOnError());
        settings.setSeparator(properties.getSeparator());
        settings.setEncoding(properties.getEncoding());
        settings.setMode(properties.getMode());
        return settings;
    }

    private static List<String> scriptLocations(List<String> locations, String fallback, String platform) {
        if (locations != null) {
            return locations;
        }
        ArrayList<String> fallbackLocations = new ArrayList<String>();
        fallbackLocations.add("optional:classpath*:" + fallback + "-" + platform + ".sql");
        fallbackLocations.add("optional:classpath*:" + fallback + ".sql");
        return fallbackLocations;
    }
}

