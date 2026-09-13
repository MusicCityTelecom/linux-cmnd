/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.impl.jdbcjobstore;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.quartz.impl.jdbcjobstore.StdJDBCDelegate;

public class DB2v8Delegate
extends StdJDBCDelegate {
    @Override
    protected void setBoolean(PreparedStatement ps, int index, boolean val) throws SQLException {
        ps.setInt(index, val ? 1 : 0);
    }
}

