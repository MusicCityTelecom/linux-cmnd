/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.jdbc.EmbeddedDatabaseConnection
 *  org.springframework.boot.jdbc.SchemaManagement
 *  org.springframework.boot.jdbc.SchemaManagementProvider
 */
package org.springframework.boot.autoconfigure.orm.jpa;

import java.util.stream.StreamSupport;
import javax.sql.DataSource;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.jdbc.SchemaManagement;
import org.springframework.boot.jdbc.SchemaManagementProvider;

class HibernateDefaultDdlAutoProvider
implements SchemaManagementProvider {
    private final Iterable<SchemaManagementProvider> providers;

    HibernateDefaultDdlAutoProvider(Iterable<SchemaManagementProvider> providers) {
        this.providers = providers;
    }

    String getDefaultDdlAuto(DataSource dataSource) {
        if (!EmbeddedDatabaseConnection.isEmbedded((DataSource)dataSource)) {
            return "none";
        }
        SchemaManagement schemaManagement = this.getSchemaManagement(dataSource);
        if (SchemaManagement.MANAGED.equals((Object)schemaManagement)) {
            return "none";
        }
        return "create-drop";
    }

    public SchemaManagement getSchemaManagement(DataSource dataSource) {
        return StreamSupport.stream(this.providers.spliterator(), false).map(provider -> provider.getSchemaManagement(dataSource)).filter(arg_0 -> SchemaManagement.MANAGED.equals(arg_0)).findFirst().orElse(SchemaManagement.UNMANAGED);
    }
}

