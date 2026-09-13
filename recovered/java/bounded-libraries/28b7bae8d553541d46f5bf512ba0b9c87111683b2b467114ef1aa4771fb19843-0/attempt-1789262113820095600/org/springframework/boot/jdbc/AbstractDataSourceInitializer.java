/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.jdbc.datasource.init.DatabasePopulator
 *  org.springframework.jdbc.datasource.init.DatabasePopulatorUtils
 *  org.springframework.jdbc.datasource.init.ResourceDatabasePopulator
 *  org.springframework.jdbc.support.JdbcUtils
 *  org.springframework.jdbc.support.MetaDataAccessException
 *  org.springframework.util.Assert
 */
package org.springframework.boot.jdbc;

import java.sql.DatabaseMetaData;
import javax.sql.DataSource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.jdbc.DataSourceInitializationMode;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.MetaDataAccessException;
import org.springframework.util.Assert;

@Deprecated
public abstract class AbstractDataSourceInitializer
implements InitializingBean {
    private static final String PLATFORM_PLACEHOLDER = "@@platform@@";
    private final DataSource dataSource;
    private final ResourceLoader resourceLoader;

    protected AbstractDataSourceInitializer(DataSource dataSource, ResourceLoader resourceLoader) {
        Assert.notNull((Object)dataSource, (String)"DataSource must not be null");
        Assert.notNull((Object)resourceLoader, (String)"ResourceLoader must not be null");
        this.dataSource = dataSource;
        this.resourceLoader = resourceLoader;
    }

    public void afterPropertiesSet() {
        this.initialize();
    }

    protected void initialize() {
        if (!this.isEnabled()) {
            return;
        }
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        String schemaLocation = this.getSchemaLocation();
        if (schemaLocation.contains(PLATFORM_PLACEHOLDER)) {
            String platform = this.getDatabaseName();
            schemaLocation = schemaLocation.replace(PLATFORM_PLACEHOLDER, platform);
        }
        populator.addScript(this.resourceLoader.getResource(schemaLocation));
        populator.setContinueOnError(true);
        this.customize(populator);
        DatabasePopulatorUtils.execute((DatabasePopulator)populator, (DataSource)this.dataSource);
    }

    private boolean isEnabled() {
        if (this.getMode() == DataSourceInitializationMode.NEVER) {
            return false;
        }
        return this.getMode() != DataSourceInitializationMode.EMBEDDED || EmbeddedDatabaseConnection.isEmbedded(this.dataSource);
    }

    protected void customize(ResourceDatabasePopulator populator) {
    }

    protected abstract DataSourceInitializationMode getMode();

    protected abstract String getSchemaLocation();

    protected String getDatabaseName() {
        try {
            String productName = JdbcUtils.commonDatabaseName((String)((String)JdbcUtils.extractDatabaseMetaData((DataSource)this.dataSource, DatabaseMetaData::getDatabaseProductName)));
            DatabaseDriver databaseDriver = DatabaseDriver.fromProductName(productName);
            if (databaseDriver == DatabaseDriver.UNKNOWN) {
                throw new IllegalStateException("Unable to detect database type");
            }
            return databaseDriver.getId();
        }
        catch (MetaDataAccessException ex) {
            throw new IllegalStateException("Unable to detect database type", ex);
        }
    }
}

