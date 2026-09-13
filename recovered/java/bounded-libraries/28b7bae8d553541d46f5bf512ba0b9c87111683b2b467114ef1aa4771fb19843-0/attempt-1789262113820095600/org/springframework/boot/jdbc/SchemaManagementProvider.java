/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.jdbc;

import javax.sql.DataSource;
import org.springframework.boot.jdbc.SchemaManagement;

@FunctionalInterface
public interface SchemaManagementProvider {
    public SchemaManagement getSchemaManagement(DataSource var1);
}

