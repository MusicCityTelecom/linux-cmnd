/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.zaxxer.hikari.HikariDataSource
 */
package org.quartz.utils;

import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import org.quartz.SchedulerException;
import org.quartz.utils.PoolingConnectionProvider;
import org.quartz.utils.PropertiesParser;

public class HikariCpPoolingConnectionProvider
implements PoolingConnectionProvider {
    public static final String POOLING_PROVIDER_NAME = "hikaricp";
    private static final String DB_DISCARD_IDLE_CONNECTIONS_SECONDS = "discardIdleConnectionsSeconds";
    private HikariDataSource datasource;

    public HikariCpPoolingConnectionProvider(String dbDriver, String dbURL, String dbUser, String dbPassword, int maxConnections, String dbValidationQuery) throws SQLException, SchedulerException {
        this.initialize(dbDriver, dbURL, dbUser, dbPassword, maxConnections, dbValidationQuery, 0);
    }

    public HikariCpPoolingConnectionProvider(Properties config) throws SchedulerException, SQLException {
        PropertiesParser cfg = new PropertiesParser(config);
        this.initialize(cfg.getStringProperty("driver"), cfg.getStringProperty("URL"), cfg.getStringProperty("user", ""), cfg.getStringProperty("password", ""), cfg.getIntProperty("maxConnections", 10), cfg.getStringProperty("validationQuery"), cfg.getIntProperty(DB_DISCARD_IDLE_CONNECTIONS_SECONDS, 0));
    }

    private void initialize(String dbDriver, String dbURL, String dbUser, String dbPassword, int maxConnections, String dbValidationQuery, int maxIdleSeconds) throws SQLException, SchedulerException {
        if (dbURL == null) {
            throw new SQLException("DBPool could not be created: DB URL cannot be null");
        }
        if (dbDriver == null) {
            throw new SQLException("DBPool '" + dbURL + "' could not be created: " + "DB driver class name cannot be null!");
        }
        if (maxConnections < 0) {
            throw new SQLException("DBPool '" + dbURL + "' could not be created: " + "Max connections must be greater than zero!");
        }
        this.datasource = new HikariDataSource();
        this.datasource.setDriverClassName(dbDriver);
        this.datasource.setJdbcUrl(dbURL);
        this.datasource.setUsername(dbUser);
        this.datasource.setPassword(dbPassword);
        this.datasource.setMaximumPoolSize(maxConnections);
        this.datasource.setIdleTimeout((long)maxIdleSeconds);
        if (dbValidationQuery != null) {
            this.datasource.setConnectionTestQuery(dbValidationQuery);
        }
    }

    public HikariDataSource getDataSource() {
        return this.datasource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return this.datasource.getConnection();
    }

    @Override
    public void shutdown() throws SQLException {
        this.datasource.close();
    }

    @Override
    public void initialize() throws SQLException {
    }
}

