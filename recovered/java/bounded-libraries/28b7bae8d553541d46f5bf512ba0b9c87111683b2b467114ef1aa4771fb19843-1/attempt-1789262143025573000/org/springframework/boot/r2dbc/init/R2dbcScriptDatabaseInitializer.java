/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.r2dbc.spi.ConnectionFactory
 *  org.springframework.core.io.Resource
 *  org.springframework.r2dbc.connection.init.ResourceDatabasePopulator
 */
package org.springframework.boot.r2dbc.init;

import io.r2dbc.spi.ConnectionFactory;
import java.nio.charset.Charset;
import java.util.List;
import org.springframework.boot.r2dbc.EmbeddedDatabaseConnection;
import org.springframework.boot.sql.init.AbstractScriptDatabaseInitializer;
import org.springframework.boot.sql.init.DatabaseInitializationSettings;
import org.springframework.core.io.Resource;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

public class R2dbcScriptDatabaseInitializer
extends AbstractScriptDatabaseInitializer {
    private final ConnectionFactory connectionFactory;

    public R2dbcScriptDatabaseInitializer(ConnectionFactory connectionFactory, DatabaseInitializationSettings settings) {
        super(settings);
        this.connectionFactory = connectionFactory;
    }

    @Override
    protected boolean isEmbeddedDatabase() {
        return EmbeddedDatabaseConnection.isEmbedded(this.connectionFactory);
    }

    @Override
    protected void runScripts(List<Resource> scripts, boolean continueOnError, String separator, Charset encoding) {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.setContinueOnError(continueOnError);
        populator.setSeparator(separator);
        if (encoding != null) {
            populator.setSqlScriptEncoding(encoding.name());
        }
        for (Resource script : scripts) {
            populator.addScript(script);
        }
        populator.populate(this.connectionFactory).block();
    }
}

