/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  liquibase.integration.spring.SpringLiquibase
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.boot.jdbc.SchemaManagement
 *  org.springframework.boot.jdbc.SchemaManagementProvider
 */
package org.springframework.boot.autoconfigure.liquibase;

import java.util.stream.StreamSupport;
import javax.sql.DataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.jdbc.SchemaManagement;
import org.springframework.boot.jdbc.SchemaManagementProvider;

class LiquibaseSchemaManagementProvider
implements SchemaManagementProvider {
    private final Iterable<SpringLiquibase> liquibaseInstances;

    LiquibaseSchemaManagementProvider(ObjectProvider<SpringLiquibase> liquibases) {
        this.liquibaseInstances = liquibases;
    }

    public SchemaManagement getSchemaManagement(DataSource dataSource) {
        return StreamSupport.stream(this.liquibaseInstances.spliterator(), false).map(SpringLiquibase::getDataSource).filter(dataSource::equals).findFirst().map(managedDataSource -> SchemaManagement.MANAGED).orElse(SchemaManagement.UNMANAGED);
    }
}

