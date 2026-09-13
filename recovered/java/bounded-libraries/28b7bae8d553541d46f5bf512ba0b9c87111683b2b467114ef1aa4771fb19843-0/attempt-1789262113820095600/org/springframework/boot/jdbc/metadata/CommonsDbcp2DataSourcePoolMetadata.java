/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.dbcp2.BasicDataSource
 */
package org.springframework.boot.jdbc.metadata;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.boot.jdbc.metadata.AbstractDataSourcePoolMetadata;

public class CommonsDbcp2DataSourcePoolMetadata
extends AbstractDataSourcePoolMetadata<BasicDataSource> {
    public CommonsDbcp2DataSourcePoolMetadata(BasicDataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Integer getActive() {
        return ((BasicDataSource)this.getDataSource()).getNumActive();
    }

    @Override
    public Integer getIdle() {
        return ((BasicDataSource)this.getDataSource()).getNumIdle();
    }

    @Override
    public Integer getMax() {
        return ((BasicDataSource)this.getDataSource()).getMaxTotal();
    }

    @Override
    public Integer getMin() {
        return ((BasicDataSource)this.getDataSource()).getMinIdle();
    }

    @Override
    public String getValidationQuery() {
        return ((BasicDataSource)this.getDataSource()).getValidationQuery();
    }

    @Override
    public Boolean getDefaultAutoCommit() {
        return ((BasicDataSource)this.getDataSource()).getDefaultAutoCommit();
    }
}

