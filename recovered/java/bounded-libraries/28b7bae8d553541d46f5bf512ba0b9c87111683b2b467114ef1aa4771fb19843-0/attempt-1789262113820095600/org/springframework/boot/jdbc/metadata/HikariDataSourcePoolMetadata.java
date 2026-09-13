/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.zaxxer.hikari.HikariDataSource
 *  com.zaxxer.hikari.pool.HikariPool
 *  org.springframework.beans.DirectFieldAccessor
 */
package org.springframework.boot.jdbc.metadata;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariPool;
import org.springframework.beans.DirectFieldAccessor;
import org.springframework.boot.jdbc.metadata.AbstractDataSourcePoolMetadata;

public class HikariDataSourcePoolMetadata
extends AbstractDataSourcePoolMetadata<HikariDataSource> {
    public HikariDataSourcePoolMetadata(HikariDataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Integer getActive() {
        try {
            return this.getHikariPool().getActiveConnections();
        }
        catch (Exception ex) {
            return null;
        }
    }

    @Override
    public Integer getIdle() {
        try {
            return this.getHikariPool().getIdleConnections();
        }
        catch (Exception ex) {
            return null;
        }
    }

    private HikariPool getHikariPool() {
        return (HikariPool)new DirectFieldAccessor(this.getDataSource()).getPropertyValue("pool");
    }

    @Override
    public Integer getMax() {
        return ((HikariDataSource)this.getDataSource()).getMaximumPoolSize();
    }

    @Override
    public Integer getMin() {
        return ((HikariDataSource)this.getDataSource()).getMinimumIdle();
    }

    @Override
    public String getValidationQuery() {
        return ((HikariDataSource)this.getDataSource()).getConnectionTestQuery();
    }

    @Override
    public Boolean getDefaultAutoCommit() {
        return ((HikariDataSource)this.getDataSource()).isAutoCommit();
    }
}

