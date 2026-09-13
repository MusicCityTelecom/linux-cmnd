/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import org.quartz.utils.ConnectionProvider;

public class DBConnectionManager {
    public static final String DB_PROPS_PREFIX = "org.quartz.db.";
    private static DBConnectionManager instance = new DBConnectionManager();
    private HashMap<String, ConnectionProvider> providers = new HashMap();

    private DBConnectionManager() {
    }

    public void addConnectionProvider(String dataSourceName, ConnectionProvider provider) {
        this.providers.put(dataSourceName, provider);
    }

    public Connection getConnection(String dsName) throws SQLException {
        ConnectionProvider provider = this.providers.get(dsName);
        if (provider == null) {
            throw new SQLException("There is no DataSource named '" + dsName + "'");
        }
        return provider.getConnection();
    }

    public static DBConnectionManager getInstance() {
        return instance;
    }

    public void shutdown(String dsName) throws SQLException {
        ConnectionProvider provider = this.providers.get(dsName);
        if (provider == null) {
            throw new SQLException("There is no DataSource named '" + dsName + "'");
        }
        provider.shutdown();
    }

    ConnectionProvider getConnectionProvider(String key) {
        return this.providers.get(key);
    }
}

