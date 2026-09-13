/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.tomcat.jdbc.pool.ConnectionPool
 *  org.apache.tomcat.jdbc.pool.DataSource
 */
package org.springframework.boot.jdbc.metadata;

import org.apache.tomcat.jdbc.pool.ConnectionPool;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.boot.jdbc.metadata.AbstractDataSourcePoolMetadata;

public class TomcatDataSourcePoolMetadata
extends AbstractDataSourcePoolMetadata<DataSource> {
    public TomcatDataSourcePoolMetadata(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Integer getActive() {
        ConnectionPool pool = ((DataSource)this.getDataSource()).getPool();
        return pool != null ? pool.getActive() : 0;
    }

    @Override
    public Integer getIdle() {
        return ((DataSource)this.getDataSource()).getNumIdle();
    }

    @Override
    public Integer getMax() {
        return ((DataSource)this.getDataSource()).getMaxActive();
    }

    @Override
    public Integer getMin() {
        return ((DataSource)this.getDataSource()).getMinIdle();
    }

    @Override
    public String getValidationQuery() {
        return ((DataSource)this.getDataSource()).getValidationQuery();
    }

    @Override
    public Boolean getDefaultAutoCommit() {
        return ((DataSource)this.getDataSource()).isDefaultAutoCommit();
    }
}

