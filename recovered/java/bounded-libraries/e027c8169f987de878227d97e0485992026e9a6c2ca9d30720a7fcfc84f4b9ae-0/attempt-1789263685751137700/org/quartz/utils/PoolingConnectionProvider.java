/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.utils;

import javax.sql.DataSource;
import org.quartz.utils.ConnectionProvider;

public interface PoolingConnectionProvider
extends ConnectionProvider {
    public static final String POOLING_PROVIDER = "provider";
    public static final String POOLING_PROVIDER_C3P0 = "c3p0";
    public static final String POOLING_PROVIDER_HIKARICP = "hikaricp";
    public static final String DB_DRIVER = "driver";
    public static final String DB_URL = "URL";
    public static final String DB_USER = "user";
    public static final String DB_PASSWORD = "password";
    public static final String DB_MAX_CONNECTIONS = "maxConnections";
    public static final String DB_VALIDATION_QUERY = "validationQuery";
    public static final int DEFAULT_DB_MAX_CONNECTIONS = 10;

    public DataSource getDataSource();
}

