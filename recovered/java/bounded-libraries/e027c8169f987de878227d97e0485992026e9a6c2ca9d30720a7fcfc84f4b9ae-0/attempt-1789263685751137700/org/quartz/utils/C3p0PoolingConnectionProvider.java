/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mchange.v2.c3p0.ComboPooledDataSource
 */
package org.quartz.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import org.quartz.SchedulerException;
import org.quartz.utils.PoolingConnectionProvider;
import org.quartz.utils.PropertiesParser;

public class C3p0PoolingConnectionProvider
implements PoolingConnectionProvider {
    public static final String DB_MAX_CACHED_STATEMENTS_PER_CONNECTION = "maxCachedStatementsPerConnection";
    public static final String DB_IDLE_VALIDATION_SECONDS = "idleConnectionValidationSeconds";
    public static final String DB_VALIDATE_ON_CHECKOUT = "validateOnCheckout";
    public static final String DB_DISCARD_IDLE_CONNECTIONS_SECONDS = "maxIdleTime";
    public static final int DEFAULT_DB_MAX_CACHED_STATEMENTS_PER_CONNECTION = 120;
    private ComboPooledDataSource datasource;

    public C3p0PoolingConnectionProvider(String dbDriver, String dbURL, String dbUser, String dbPassword, int maxConnections, String dbValidationQuery) throws SQLException, SchedulerException {
        this.initialize(dbDriver, dbURL, dbUser, dbPassword, maxConnections, 120, dbValidationQuery, false, 50, 0);
    }

    public C3p0PoolingConnectionProvider(Properties config) throws SchedulerException, SQLException {
        PropertiesParser cfg = new PropertiesParser(config);
        this.initialize(cfg.getStringProperty("driver"), cfg.getStringProperty("URL"), cfg.getStringProperty("user", ""), cfg.getStringProperty("password", ""), cfg.getIntProperty("maxConnections", 10), cfg.getIntProperty(DB_MAX_CACHED_STATEMENTS_PER_CONNECTION, 120), cfg.getStringProperty("validationQuery"), cfg.getBooleanProperty(DB_VALIDATE_ON_CHECKOUT, false), cfg.getIntProperty(DB_IDLE_VALIDATION_SECONDS, 50), cfg.getIntProperty(DB_DISCARD_IDLE_CONNECTIONS_SECONDS, 0));
    }

    private void initialize(String dbDriver, String dbURL, String dbUser, String dbPassword, int maxConnections, int maxStatementsPerConnection, String dbValidationQuery, boolean validateOnCheckout, int idleValidationSeconds, int maxIdleSeconds) throws SQLException, SchedulerException {
        if (dbURL == null) {
            throw new SQLException("DBPool could not be created: DB URL cannot be null");
        }
        if (dbDriver == null) {
            throw new SQLException("DBPool '" + dbURL + "' could not be created: " + "DB driver class name cannot be null!");
        }
        if (maxConnections < 0) {
            throw new SQLException("DBPool '" + dbURL + "' could not be created: " + "Max connections must be greater than zero!");
        }
        this.datasource = new ComboPooledDataSource();
        try {
            this.datasource.setDriverClass(dbDriver);
        }
        catch (PropertyVetoException e) {
            throw new SchedulerException("Problem setting driver class name on datasource: " + e.getMessage(), e);
        }
        this.datasource.setJdbcUrl(dbURL);
        this.datasource.setUser(dbUser);
        this.datasource.setPassword(dbPassword);
        this.datasource.setMaxPoolSize(maxConnections);
        this.datasource.setMinPoolSize(1);
        this.datasource.setMaxIdleTime(maxIdleSeconds);
        this.datasource.setMaxStatementsPerConnection(maxStatementsPerConnection);
        if (dbValidationQuery != null) {
            this.datasource.setPreferredTestQuery(dbValidationQuery);
            if (!validateOnCheckout) {
                this.datasource.setTestConnectionOnCheckin(true);
            } else {
                this.datasource.setTestConnectionOnCheckout(true);
            }
            this.datasource.setIdleConnectionTestPeriod(idleValidationSeconds);
        }
    }

    public ComboPooledDataSource getDataSource() {
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

