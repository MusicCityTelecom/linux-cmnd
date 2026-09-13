/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.utils;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionProvider {
    public Connection getConnection() throws SQLException;

    public void shutdown() throws SQLException;

    public void initialize() throws SQLException;
}

