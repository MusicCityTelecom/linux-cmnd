/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.flywaydb.core.Flyway
 *  org.springframework.boot.jdbc.SchemaManagement
 *  org.springframework.boot.jdbc.SchemaManagementProvider
 */
package org.springframework.boot.autoconfigure.flyway;

import java.util.stream.StreamSupport;
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.springframework.boot.jdbc.SchemaManagement;
import org.springframework.boot.jdbc.SchemaManagementProvider;

class FlywaySchemaManagementProvider
implements SchemaManagementProvider {
    private final Iterable<Flyway> flywayInstances;

    FlywaySchemaManagementProvider(Iterable<Flyway> flywayInstances) {
        this.flywayInstances = flywayInstances;
    }

    public SchemaManagement getSchemaManagement(DataSource dataSource) {
        return StreamSupport.stream(this.flywayInstances.spliterator(), false).map(flyway -> flyway.getConfiguration().getDataSource()).filter(dataSource::equals).findFirst().map(managedDataSource -> SchemaManagement.MANAGED).orElse(SchemaManagement.UNMANAGED);
    }
}

