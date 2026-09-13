/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mchange.v2.c3p0.C3P0ProxyConnection
 */
package org.quartz.impl.jdbcjobstore;

import com.mchange.v2.c3p0.C3P0ProxyConnection;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Method;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.quartz.impl.jdbcjobstore.StdJDBCDelegate;

public class CUBRIDDelegate
extends StdJDBCDelegate {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected Object getObjectFromBlob(ResultSet rs, String colName) throws ClassNotFoundException, IOException, SQLException {
        Object obj = null;
        Blob blob = rs.getBlob(colName);
        byte[] bytes = blob.getBytes(1L, (int)blob.length());
        if (bytes != null && bytes.length != 0) {
            ByteArrayInputStream binaryInput = new ByteArrayInputStream(bytes);
            try (ObjectInputStream in = new ObjectInputStream(binaryInput);){
                obj = in.readObject();
            }
        }
        return obj;
    }

    @Override
    protected Object getJobDataFromBlob(ResultSet rs, String colName) throws ClassNotFoundException, IOException, SQLException {
        if (this.canUseProperties()) {
            Blob blob = rs.getBlob(colName);
            byte[] bytes = blob.getBytes(1L, (int)blob.length());
            if (bytes == null || bytes.length == 0) {
                return null;
            }
            ByteArrayInputStream binaryInput = new ByteArrayInputStream(bytes);
            return binaryInput;
        }
        return this.getObjectFromBlob(rs, colName);
    }

    @Override
    protected void setBytes(PreparedStatement ps, int index, ByteArrayOutputStream baos) throws SQLException {
        byte[] byteArray = baos == null ? new byte[]{} : baos.toByteArray();
        Connection conn = ps.getConnection();
        if (conn instanceof C3P0ProxyConnection) {
            try {
                C3P0ProxyConnection c3p0Conn = (C3P0ProxyConnection)conn;
                Method m = Connection.class.getMethod("createBlob", new Class[0]);
                Object[] args = new Object[]{};
                Blob blob = (Blob)c3p0Conn.rawConnectionOperation(m, C3P0ProxyConnection.RAW_CONNECTION, args);
                blob.setBytes(1L, byteArray);
                ps.setBlob(index, blob);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            Blob blob = ps.getConnection().createBlob();
            blob.setBytes(1L, byteArray);
            ps.setBlob(index, blob);
        }
    }
}

