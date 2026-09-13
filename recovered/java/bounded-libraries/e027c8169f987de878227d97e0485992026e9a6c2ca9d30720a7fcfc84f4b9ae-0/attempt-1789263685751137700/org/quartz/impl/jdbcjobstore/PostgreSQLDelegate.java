/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.impl.jdbcjobstore;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.quartz.impl.jdbcjobstore.StdJDBCDelegate;

public class PostgreSQLDelegate
extends StdJDBCDelegate {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected Object getObjectFromBlob(ResultSet rs, String colName) throws ClassNotFoundException, IOException, SQLException {
        ByteArrayInputStream binaryInput = null;
        byte[] bytes = rs.getBytes(colName);
        Object obj = null;
        if (bytes != null && bytes.length != 0) {
            binaryInput = new ByteArrayInputStream(bytes);
            try (ObjectInputStream in = new ObjectInputStream(binaryInput);){
                obj = in.readObject();
            }
        }
        return obj;
    }

    @Override
    protected Object getJobDataFromBlob(ResultSet rs, String colName) throws ClassNotFoundException, IOException, SQLException {
        if (this.canUseProperties()) {
            ByteArrayInputStream binaryInput = null;
            byte[] bytes = rs.getBytes(colName);
            if (bytes == null || bytes.length == 0) {
                return null;
            }
            binaryInput = new ByteArrayInputStream(bytes);
            return binaryInput;
        }
        return this.getObjectFromBlob(rs, colName);
    }
}

