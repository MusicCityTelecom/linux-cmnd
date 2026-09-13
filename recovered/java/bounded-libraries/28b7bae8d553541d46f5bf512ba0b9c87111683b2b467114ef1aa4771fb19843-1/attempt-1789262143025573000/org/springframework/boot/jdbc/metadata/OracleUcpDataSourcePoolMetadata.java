/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  oracle.ucp.jdbc.PoolDataSource
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.jdbc.metadata;

import java.sql.SQLException;
import oracle.ucp.jdbc.PoolDataSource;
import org.springframework.boot.jdbc.metadata.AbstractDataSourcePoolMetadata;
import org.springframework.util.StringUtils;

public class OracleUcpDataSourcePoolMetadata
extends AbstractDataSourcePoolMetadata<PoolDataSource> {
    public OracleUcpDataSourcePoolMetadata(PoolDataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Integer getActive() {
        try {
            return ((PoolDataSource)this.getDataSource()).getBorrowedConnectionsCount();
        }
        catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public Integer getIdle() {
        try {
            return ((PoolDataSource)this.getDataSource()).getAvailableConnectionsCount();
        }
        catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public Integer getMax() {
        return ((PoolDataSource)this.getDataSource()).getMaxPoolSize();
    }

    @Override
    public Integer getMin() {
        return ((PoolDataSource)this.getDataSource()).getMinPoolSize();
    }

    @Override
    public String getValidationQuery() {
        return ((PoolDataSource)this.getDataSource()).getSQLForValidateConnection();
    }

    @Override
    public Boolean getDefaultAutoCommit() {
        String autoCommit = ((PoolDataSource)this.getDataSource()).getConnectionProperty("autoCommit");
        return StringUtils.hasText((String)autoCommit) ? Boolean.valueOf(autoCommit) : null;
    }
}

