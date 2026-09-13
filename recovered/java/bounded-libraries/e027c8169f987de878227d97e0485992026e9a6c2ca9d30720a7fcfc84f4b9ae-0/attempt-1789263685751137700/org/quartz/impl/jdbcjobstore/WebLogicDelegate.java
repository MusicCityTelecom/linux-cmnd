/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.impl.jdbcjobstore;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.quartz.impl.jdbcjobstore.StdJDBCDelegate;

public class WebLogicDelegate
extends StdJDBCDelegate {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected Object getObjectFromBlob(ResultSet rs, String colName) throws ClassNotFoundException, IOException, SQLException {
        Object obj = null;
        Blob blobLocator = rs.getBlob(colName);
        InputStream binaryInput = null;
        try {
            if (null != blobLocator && blobLocator.length() > 0L) {
                binaryInput = blobLocator.getBinaryStream();
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        if (null != binaryInput) {
            try (ObjectInputStream in = new ObjectInputStream(binaryInput);){
                obj = in.readObject();
            }
        }
        return obj;
    }

    @Override
    protected Object getJobDataFromBlob(ResultSet rs, String colName) throws ClassNotFoundException, IOException, SQLException {
        if (this.canUseProperties()) {
            Blob blobLocator = rs.getBlob(colName);
            InputStream binaryInput = null;
            try {
                if (null != blobLocator && blobLocator.length() > 0L) {
                    binaryInput = blobLocator.getBinaryStream();
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
            return binaryInput;
        }
        return this.getObjectFromBlob(rs, colName);
    }
}

