/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.impl.jdbcjobstore;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.quartz.impl.jdbcjobstore.StdJDBCDelegate;

public class MSSQLDelegate
extends StdJDBCDelegate {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected Object getObjectFromBlob(ResultSet rs, String colName) throws ClassNotFoundException, IOException, SQLException {
        InputStream binaryInput = rs.getBinaryStream(colName);
        if (binaryInput == null || binaryInput.available() == 0) {
            return null;
        }
        Object obj = null;
        try (ObjectInputStream in = new ObjectInputStream(binaryInput);){
            obj = in.readObject();
        }
        return obj;
    }

    @Override
    protected Object getJobDataFromBlob(ResultSet rs, String colName) throws ClassNotFoundException, IOException, SQLException {
        if (this.canUseProperties()) {
            InputStream binaryInput = rs.getBinaryStream(colName);
            return binaryInput;
        }
        return this.getObjectFromBlob(rs, colName);
    }
}

