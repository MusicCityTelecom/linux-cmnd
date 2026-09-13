/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.jdbc.metadata;

import javax.sql.DataSource;
import org.springframework.boot.jdbc.metadata.DataSourcePoolMetadata;

@FunctionalInterface
public interface DataSourcePoolMetadataProvider {
    public DataSourcePoolMetadata getDataSourcePoolMetadata(DataSource var1);
}

