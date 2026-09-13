/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.core.io.Resource
 *  org.springframework.jdbc.datasource.init.DatabasePopulator
 *  org.springframework.jdbc.datasource.init.DatabasePopulatorUtils
 *  org.springframework.jdbc.datasource.init.ResourceDatabasePopulator
 */
package org.springframework.boot.jdbc.init;

import java.nio.charset.Charset;
import java.util.List;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.sql.init.AbstractScriptDatabaseInitializer;
import org.springframework.boot.sql.init.DatabaseInitializationSettings;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

public class DataSourceScriptDatabaseInitializer
extends AbstractScriptDatabaseInitializer {
    private static final Log logger = LogFactory.getLog(DataSourceScriptDatabaseInitializer.class);
    private final DataSource dataSource;

    public DataSourceScriptDatabaseInitializer(DataSource dataSource, DatabaseInitializationSettings settings) {
        super(settings);
        this.dataSource = dataSource;
    }

    protected final DataSource getDataSource() {
        return this.dataSource;
    }

    @Override
    protected boolean isEmbeddedDatabase() {
        try {
            return EmbeddedDatabaseConnection.isEmbedded(this.dataSource);
        }
        catch (Exception ex) {
            logger.debug((Object)"Could not determine if datasource is embedded", (Throwable)ex);
            return false;
        }
    }

    @Override
    protected void runScripts(List<Resource> resources, boolean continueOnError, String separator, Charset encoding) {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.setContinueOnError(continueOnError);
        populator.setSeparator(separator);
        if (encoding != null) {
            populator.setSqlScriptEncoding(encoding.name());
        }
        for (Resource resource : resources) {
            populator.addScript(resource);
        }
        this.customize(populator);
        DatabasePopulatorUtils.execute((DatabasePopulator)populator, (DataSource)this.dataSource);
    }

    protected void customize(ResourceDatabasePopulator populator) {
    }
}

