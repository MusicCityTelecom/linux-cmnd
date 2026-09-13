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
package org.springframework.boot.autoconfigure.session;

import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.session.JdbcSessionProperties;
import org.springframework.boot.jdbc.AbstractDataSourceInitializer;
import org.springframework.boot.jdbc.DataSourceInitializationMode;
import org.springframework.boot.sql.init.DatabaseInitializationMode;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Deprecated
public class JdbcSessionDataSourceInitializer
extends AbstractDataSourceInitializer {
    private final JdbcSessionProperties properties;

    public JdbcSessionDataSourceInitializer(DataSource dataSource, ResourceLoader resourceLoader, JdbcSessionProperties properties) {
        super(dataSource, resourceLoader);
        Assert.notNull((Object)properties, (String)"JdbcSessionProperties must not be null");
        this.properties = properties;
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

