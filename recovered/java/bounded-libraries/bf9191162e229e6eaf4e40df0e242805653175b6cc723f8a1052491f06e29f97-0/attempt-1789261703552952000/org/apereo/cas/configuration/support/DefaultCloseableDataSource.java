/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.support;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ConnectionBuilder;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.ShardingKeyBuilder;
import java.util.logging.Logger;
import javax.sql.DataSource;
import lombok.Generated;
import org.apereo.cas.configuration.support.CloseableDataSource;

public class DefaultCloseableDataSource
implements CloseableDataSource {
    private final DataSource targetDataSource;

    @Override
    public void close() throws IOException {
        if (this.targetDataSource instanceof Closeable) {
            ((Closeable)((Object)this.targetDataSource)).close();
        }
    }

    @Generated
    public DefaultCloseableDataSource(DataSource targetDataSource) {
        this.targetDataSource = targetDataSource;
    }

    @Override
    @Generated
    public DataSource getTargetDataSource() {
        return this.targetDataSource;
    }

    @Override
    @Generated
    public Connection getConnection() throws SQLException {
        return this.getTargetDataSource().getConnection();
    }

    @Override
    @Generated
    public Connection getConnection(String arg0, String arg1) throws SQLException {
        return this.getTargetDataSource().getConnection(arg0, arg1);
    }

    @Override
    @Generated
    public PrintWriter getLogWriter() throws SQLException {
        return this.getTargetDataSource().getLogWriter();
    }

    @Override
    @Generated
    public void setLogWriter(PrintWriter arg0) throws SQLException {
        this.getTargetDataSource().setLogWriter(arg0);
    }

    @Override
    @Generated
    public void setLoginTimeout(int arg0) throws SQLException {
        this.getTargetDataSource().setLoginTimeout(arg0);
    }

    @Override
    @Generated
    public int getLoginTimeout() throws SQLException {
        return this.getTargetDataSource().getLoginTimeout();
    }

    @Override
    @Generated
    public ConnectionBuilder createConnectionBuilder() throws SQLException {
        return this.getTargetDataSource().createConnectionBuilder();
    }

    @Override
    @Generated
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return this.getTargetDataSource().getParentLogger();
    }

    @Override
    @Generated
    public ShardingKeyBuilder createShardingKeyBuilder() throws SQLException {
        return this.getTargetDataSource().createShardingKeyBuilder();
    }

    @Override
    @Generated
    public <T> T unwrap(Class<T> arg0) throws SQLException {
        return this.getTargetDataSource().unwrap(arg0);
    }

    @Override
    @Generated
    public boolean isWrapperFor(Class<?> arg0) throws SQLException {
        return this.getTargetDataSource().isWrapperFor(arg0);
    }
}

